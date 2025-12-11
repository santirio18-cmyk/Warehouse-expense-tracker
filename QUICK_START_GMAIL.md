# Quick Start: Gmail Email Setup (5 Minutes)

## Step 1: Generate Gmail App Password (2 minutes)

1. Go to [https://myaccount.google.com/security](https://myaccount.google.com/security)
2. Enable **2-Step Verification** (if not already enabled)
3. Go to **App passwords** → Generate
4. Name: `SCM Expense Tracker`
5. Copy the 16-character password (e.g., `abcd efgh ijkl mnop`)

## Step 2: Create .env File (1 minute)

Create a file named `.env` in the project folder:

```env
GMAIL_USER=scm.dispatch@tvs.in
GMAIL_APP_PASSWORD=abcdefghijklmnop
PORT=3000
```

**Important**: Remove spaces from the App Password!

## Step 3: Install & Start Server (1 minute)

```bash
npm install
node email-server.js
```

You should see:
```
✅ Gmail server is ready to send emails
🚀 Gmail Email Server running on port 3000
```

## Step 4: Update HTML File (30 seconds)

Open `warehouse-expense-tracker.html` and find line ~1563:

```javascript
const EMAIL_SERVER_URL = 'http://localhost:3000';
```

Keep it as `http://localhost:3000` for local testing, or change to your production URL.

## Step 5: Test (30 seconds)

1. Open `warehouse-expense-tracker.html` in browser
2. Open Console (F12)
3. Look for: `✅ Gmail email server configured`
4. Create a test user with email
5. Check inbox!

---

## For Production Deployment

See `GMAIL_DIRECT_SETUP.md` for deployment options:
- **Railway** (Free tier) - Recommended
- **Render** (Free tier)
- **Heroku** (Paid)
- **VPS** (Your own server)

---

## Troubleshooting

**"Cannot connect to email server"**
- Make sure `email-server.js` is running
- Check `EMAIL_SERVER_URL` in HTML file

**"Authentication failed"**
- Verify App Password is correct (16 chars, no spaces)
- Ensure 2-Step Verification is enabled

**Need help?** See `GMAIL_DIRECT_SETUP.md` for detailed guide.


