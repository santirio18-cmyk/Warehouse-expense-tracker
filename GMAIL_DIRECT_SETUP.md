# Gmail Direct Setup Guide (No EmailJS)

This guide will help you set up Gmail email sending directly without using EmailJS. This uses a simple Node.js backend server that connects to Gmail SMTP.

## Why This Approach?

- ✅ **Free** - No EmailJS subscription needed
- ✅ **Direct Gmail** - Uses your Gmail account directly
- ✅ **Unlimited emails** - Only limited by Gmail's sending limits (500/day for free accounts)
- ✅ **Full control** - You control the email server

## Prerequisites

1. **Gmail Account**: `scm.dispatch@tvs.in`
2. **Node.js**: Version 14 or higher ([Download](https://nodejs.org/))
3. **Gmail App Password**: Generated from your Google Account

---

## Step 1: Generate Gmail App Password

1. **Sign in to Google Account**: Go to [https://myaccount.google.com/](https://myaccount.google.com/)
2. **Enable 2-Step Verification** (if not already enabled):
   - Go to **Security** → **2-Step Verification**
   - Follow the prompts to enable it
   - **Required** to generate App Passwords

3. **Generate App Password**:
   - Go to **Security** → **2-Step Verification**
   - Scroll down to **App passwords**
   - Click **App passwords**
   - Select **Mail** as the app
   - Select **Other (Custom name)** as the device
   - Enter name: `SCM Expense Tracker Server`
   - Click **Generate**
   - **Copy the 16-character password** (e.g., `abcd efgh ijkl mnop`)
   - **Important**: Save this password - you won't see it again!

---

## Step 2: Install Node.js Dependencies

1. **Open Terminal/Command Prompt** in the project folder
2. **Install dependencies**:
   ```bash
   npm install
   ```

This will install:
- `express` - Web server framework
- `nodemailer` - Gmail SMTP email library
- `cors` - Cross-origin resource sharing
- `dotenv` - Environment variables

---

## Step 3: Configure Environment Variables

1. **Create `.env` file** in the project root:
   ```bash
   cp .env.example .env
   ```

2. **Edit `.env` file** and add your Gmail credentials:
   ```env
   GMAIL_USER=scm.dispatch@tvs.in
   GMAIL_APP_PASSWORD=your_16_character_app_password_here
   PORT=3000
   ```

   **Important**: 
   - Remove spaces from the App Password (e.g., `abcdefghijklmnop`)
   - Never commit `.env` file to Git (it's already in `.gitignore`)

---

## Step 4: Start the Email Server

### Local Development:

```bash
node email-server.js
```

You should see:
```
✅ Gmail server is ready to send emails
🚀 Gmail Email Server running on port 3000
📧 Gmail User: scm.dispatch@tvs.in
```

### Keep Server Running:

- **Option 1**: Run in background (Linux/Mac):
  ```bash
  nohup node email-server.js > email-server.log 2>&1 &
  ```

- **Option 2**: Use PM2 (recommended for production):
  ```bash
  npm install -g pm2
  pm2 start email-server.js --name email-server
  pm2 save
  pm2 startup
  ```

---

## Step 5: Update HTML File

1. **Open `warehouse-expense-tracker.html`**
2. **Find the EmailJS configuration section** (around line 1559)
3. **Replace with Gmail API configuration**:

```javascript
// ============================================
// GMAIL EMAIL SERVER CONFIGURATION
// ============================================
// Replace with your email server URL
// Local: http://localhost:3000
// Production: https://your-server-url.com
const EMAIL_SERVER_URL = 'http://localhost:3000'; // Change this to your server URL

let emailServerConfigured = false;
if (EMAIL_SERVER_URL && EMAIL_SERVER_URL !== 'YOUR_SERVER_URL') {
    emailServerConfigured = true;
    console.log('✅ Gmail email server configured:', EMAIL_SERVER_URL);
} else {
    console.log('ℹ️ Email server not configured - email notifications will be disabled');
}
```

4. **Replace the email sending functions** (see next section)

---

## Step 6: Update Email Functions in HTML

Replace the three email functions with API calls to your server:

### Function 1: sendUserCreationEmail

```javascript
async function sendUserCreationEmail(userEmail, userName, username, password, role) {
    if (!emailServerConfigured) {
        console.log('ℹ️ Email server not configured - skipping email notification');
        return;
    }
    
    try {
        const response = await fetch(`${EMAIL_SERVER_URL}/api/send-user-email`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                to_email: userEmail,
                user_name: userName,
                username: username,
                password: password,
                role: role
            })
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('✅ User creation email sent successfully');
        } else {
            throw new Error(result.error || 'Failed to send email');
        }
    } catch (error) {
        console.error('❌ Error sending user creation email:', error);
        // Don't throw error - email failure shouldn't block user creation
    }
}
```

### Function 2: sendExpenseSubmissionEmail

```javascript
async function sendExpenseSubmissionEmail(expense, userEmail, userName) {
    if (!emailServerConfigured) {
        console.log('ℹ️ Email server not configured - skipping email notification');
        return;
    }
    
    try {
        // Get approver emails (Master and Approver roles)
        const approvers = Object.entries(users)
            .filter(([_, user]) => user.role === 'master' || user.role === 'approver')
            .map(([_, user]) => user.email)
            .filter(email => email);
        
        if (approvers.length === 0) {
            console.log('ℹ️ No approver emails found - skipping notification');
            return;
        }
        
        const response = await fetch(`${EMAIL_SERVER_URL}/api/send-expense-submission-email`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                to_emails: approvers,
                submitter_name: userName,
                submitter_email: userEmail,
                expense_amount: expense.amount.toFixed(2),
                expense_category: expense.category,
                expense_vendor: expense.vendorName || 'N/A',
                expense_warehouse: expense.warehouse,
                expense_date: expense.date,
                expense_invoice: expense.invoiceNumber || 'N/A'
            })
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('✅ Expense submission email sent successfully');
        } else {
            throw new Error(result.error || 'Failed to send email');
        }
    } catch (error) {
        console.error('❌ Error sending expense submission email:', error);
        // Don't throw error - email failure shouldn't block expense submission
    }
}
```

### Function 3: sendApprovalEmail

```javascript
async function sendApprovalEmail(expense, action, approverName) {
    if (!emailServerConfigured) {
        console.log('ℹ️ Email server not configured - skipping email notification');
        return;
    }
    
    try {
        // Get submitter email
        const submitter = users[expense.createdBy];
        if (!submitter || !submitter.email) {
            console.log('ℹ️ Submitter email not found - skipping notification');
            return;
        }
        
        const response = await fetch(`${EMAIL_SERVER_URL}/api/send-approval-email`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                to_email: submitter.email,
                submitter_name: submitter.name,
                approver_name: approverName,
                action: action, // 'approved' or 'rejected'
                expense_amount: expense.amount.toFixed(2),
                expense_category: expense.category,
                expense_vendor: expense.vendorName || 'N/A',
                expense_warehouse: expense.warehouse,
                expense_date: expense.date,
                expense_invoice: expense.invoiceNumber || 'N/A'
            })
        });
        
        const result = await response.json();
        if (result.success) {
            console.log(`✅ ${action} email sent successfully`);
        } else {
            throw new Error(result.error || 'Failed to send email');
        }
    } catch (error) {
        console.error(`❌ Error sending ${action} email:`, error);
        // Don't throw error - email failure shouldn't block approval/rejection
    }
}
```

---

## Step 7: Deploy Email Server (Production)

### Option 1: Railway (Recommended - Free Tier Available)

1. Go to [https://railway.app/](https://railway.app/)
2. Sign up with GitHub
3. Click **New Project** → **Deploy from GitHub repo**
4. Select your repository
5. Add environment variables:
   - `GMAIL_USER=scm.dispatch@tvs.in`
   - `GMAIL_APP_PASSWORD=your_app_password`
6. Railway will auto-detect Node.js and deploy
7. Copy the generated URL (e.g., `https://your-app.railway.app`)
8. Update `EMAIL_SERVER_URL` in HTML file

### Option 2: Render (Free Tier Available)

1. Go to [https://render.com/](https://render.com/)
2. Sign up and create **New Web Service**
3. Connect your GitHub repository
4. Settings:
   - **Build Command**: `npm install`
   - **Start Command**: `node email-server.js`
5. Add environment variables (same as Railway)
6. Deploy and copy the URL

### Option 3: Heroku (Free Tier Discontinued, Paid)

1. Install Heroku CLI
2. Login: `heroku login`
3. Create app: `heroku create your-app-name`
4. Set environment variables:
   ```bash
   heroku config:set GMAIL_USER=scm.dispatch@tvs.in
   heroku config:set GMAIL_APP_PASSWORD=your_app_password
   ```
5. Deploy: `git push heroku main`
6. Copy the URL

### Option 4: VPS/Cloud Server

If you have a VPS (AWS EC2, DigitalOcean, etc.):

1. SSH into your server
2. Install Node.js: `curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash - && sudo apt-get install -y nodejs`
3. Clone your repository
4. Install dependencies: `npm install`
5. Set up PM2: `pm2 start email-server.js --name email-server`
6. Configure reverse proxy (Nginx) to point to port 3000
7. Set up SSL certificate (Let's Encrypt)

---

## Step 8: Test Email Notifications

1. **Start the email server** (local or production)
2. **Open the HTML file** in browser
3. **Open Browser Console** (F12)
4. **Look for**: `✅ Gmail email server configured`

### Test User Creation:
- Create a new user
- Check console for success message
- Check user's inbox

### Test Expense Submission:
- Submit an expense
- Check approver emails
- Check console for success message

### Test Approval/Rejection:
- Approve/reject an expense
- Check submitter's email
- Check console for success message

---

## Troubleshooting

### Issue: "Cannot connect to email server"
- **Solution**: 
  - Check if server is running: `curl http://localhost:3000`
  - Verify `EMAIL_SERVER_URL` in HTML file
  - Check CORS settings (server should allow your domain)

### Issue: "Authentication failed"
- **Solution**:
  - Verify Gmail App Password is correct (16 characters, no spaces)
  - Ensure 2-Step Verification is enabled
  - Regenerate App Password if needed

### Issue: "Emails not sending"
- **Check server logs**: Look at console output or log files
- **Check Gmail limits**: Free Gmail accounts can send 500 emails/day
- **Check spam folder**: Emails might be going to spam

### Issue: "CORS error"
- **Solution**: Server already has CORS enabled. If issues persist:
  - Update CORS in `email-server.js` to allow your domain
  - Or use a proxy server

---

## Gmail Sending Limits

- **Free Gmail**: 500 emails per day
- **Google Workspace**: 2,000 emails per day (per user)
- **Rate limit**: ~100 emails per minute

If you exceed limits, Gmail will temporarily block sending. Wait 24 hours or upgrade to Google Workspace.

---

## Security Notes

1. **Never commit `.env` file** - It contains sensitive credentials
2. **Use App Passwords** - Never use your regular Gmail password
3. **HTTPS in production** - Always use HTTPS for production server
4. **Environment variables** - Store credentials as environment variables, not in code

---

## Next Steps

1. ✅ Generate Gmail App Password
2. ✅ Install Node.js dependencies
3. ✅ Configure `.env` file
4. ✅ Start email server
5. ✅ Update HTML file with server URL
6. ✅ Test email notifications
7. ✅ Deploy to production
8. ✅ Update HTML with production URL

---

## Support

- **Nodemailer Docs**: [https://nodemailer.com/](https://nodemailer.com/)
- **Gmail App Passwords**: [https://support.google.com/accounts/answer/185833](https://support.google.com/accounts/answer/185833)
- **Express.js Docs**: [https://expressjs.com/](https://expressjs.com/)

---

**Note**: This setup gives you full control over email sending without any third-party service fees. The server can be hosted on any Node.js platform.


