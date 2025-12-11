# Gmail Automated Email Setup Guide

This guide will help you set up automated Gmail sending from `scm.dispatch@tvs.in` using EmailJS.

## Overview

To enable automated Gmail sending, you need to:
1. Create an EmailJS account
2. Generate a Gmail App Password for `scm.dispatch@tvs.in`
3. Connect Gmail to EmailJS
4. Create email templates
5. Update the HTML file with your credentials

---

## Step 1: Create EmailJS Account

1. Go to [https://www.emailjs.com/](https://www.emailjs.com/)
2. Click **"Sign Up"** and create a free account
3. Use your email address (can be different from `scm.dispatch@tvs.in`)
4. Verify your email address

---

## Step 2: Get Your EmailJS Public Key

1. After logging in, go to **Account** → **General**
2. Find your **Public Key** (it looks like: `abcdefghijklmnop`)
3. Copy this key - you'll need it later

---

## Step 3: Generate Gmail App Password

Since `scm.dispatch@tvs.in` is a corporate Gmail account (Google Workspace), you need to generate an **App Password**.

### For Google Workspace (Corporate Gmail):

1. **Sign in to your Google Account**: Go to [https://myaccount.google.com/](https://myaccount.google.com/)
2. **Enable 2-Step Verification** (if not already enabled):
   - Go to **Security** → **2-Step Verification**
   - Follow the prompts to enable it
   - This is required to generate App Passwords

3. **Generate App Password**:
   - Go to **Security** → **2-Step Verification**
   - Scroll down to **App passwords**
   - Click **App passwords**
   - Select **Mail** as the app
   - Select **Other (Custom name)** as the device
   - Enter name: `EmailJS Expense Tracker`
   - Click **Generate**
   - **Copy the 16-character password** (it will look like: `abcd efgh ijkl mnop`)
   - **Important**: Save this password immediately - you won't be able to see it again!

### Alternative: If 2-Step Verification is Not Available

If your organization doesn't allow 2-Step Verification, you may need to:
- Contact your IT administrator to enable "Less secure app access" (not recommended)
- Or use OAuth 2.0 setup (more complex, requires admin approval)

---

## Step 4: Add Gmail Service to EmailJS

1. **Go to EmailJS Dashboard**: [https://dashboard.emailjs.com/](https://dashboard.emailjs.com/)
2. Click **Email Services** in the left menu
3. Click **Add New Service**
4. Select **Gmail** from the list
5. **Service Name**: Enter `Gmail SCM Dispatch` (or any name you prefer)
6. **Gmail Account**: Enter `scm.dispatch@tvs.in`
7. **Password**: Enter the **16-character App Password** you generated in Step 3
   - Remove any spaces from the password (e.g., `abcdefghijklmnop`)
8. Click **Create Service**
9. **Note your Service ID** (it will look like: `service_abc123`)

### Test the Connection

1. After creating the service, click **Test** to verify the connection
2. Send a test email to yourself
3. If successful, you'll see a green checkmark ✅

---

## Step 5: Create Email Templates

You need to create **3 email templates** in EmailJS:

### Template 1: User Creation (Welcome Email)

1. Go to **Email Templates** → **Create New Template**
2. **Template Name**: `User Creation Welcome`
3. **Template ID**: Note the ID provided (e.g., `template_xyz789`)
4. **Service**: Select your Gmail service
5. **To Email**: `{{to_email}}`
6. **From Name**: `SCM Expense Management`
7. **From Email**: `scm.dispatch@tvs.in`
8. **Subject**: `Welcome to {{app_name}} - Your Account Has Been Created`
9. **Content** (HTML):
```html
<p>Hello {{user_name}},</p>

<p>Your account has been successfully created in the {{app_name}}.</p>

<p><strong>Account Details:</strong></p>
<ul>
  <li>Username: {{username}}</li>
  <li>Password: {{password}}</li>
  <li>Role: {{role}}</li>
</ul>

<p>Please log in and change your password for security.</p>

<p>Best regards,<br>
{{app_name}} Team</p>
```
10. Click **Save**

### Template 2: Expense Submission (Notification to Approvers)

1. Go to **Email Templates** → **Create New Template**
2. **Template Name**: `Expense Submission Notification`
3. **Template ID**: Note the ID provided
4. **Service**: Select your Gmail service
5. **To Email**: `{{to_email}}`
6. **From Name**: `SCM Expense Management`
7. **From Email**: `scm.dispatch@tvs.in`
8. **Subject**: `New Expense Submitted - ₹{{expense_amount}} - {{expense_category}}`
9. **Content** (HTML):
```html
<p>Hello,</p>

<p>A new expense has been submitted and requires your approval.</p>

<p><strong>Expense Details:</strong></p>
<ul>
  <li>Amount: ₹{{expense_amount}}</li>
  <li>Category: {{expense_category}}</li>
  <li>Vendor: {{expense_vendor}}</li>
  <li>Warehouse: {{expense_warehouse}}</li>
  <li>Date: {{expense_date}}</li>
  <li>Invoice Number: {{expense_invoice}}</li>
  <li>Submitted by: {{submitter_name}} ({{submitter_email}})</li>
</ul>

<p>Please log in to review and approve/reject this expense.</p>

<p>Best regards,<br>
{{app_name}}</p>
```
10. Click **Save**

### Template 3: Approval/Rejection (Notification to Submitter)

1. Go to **Email Templates** → **Create New Template**
2. **Template Name**: `Expense Approval Notification`
3. **Template ID**: Note the ID provided
4. **Service**: Select your Gmail service
5. **To Email**: `{{to_email}}`
6. **From Name**: `SCM Expense Management`
7. **From Email**: `scm.dispatch@tvs.in`
8. **Subject**: `Expense {{action}} - ₹{{expense_amount}} - {{expense_category}}`
9. **Content** (HTML):
```html
<p>Hello {{submitter_name}},</p>

<p>Your expense submission has been <strong>{{action}}</strong>.</p>

<p><strong>Expense Details:</strong></p>
<ul>
  <li>Amount: ₹{{expense_amount}}</li>
  <li>Category: {{expense_category}}</li>
  <li>Vendor: {{expense_vendor}}</li>
  <li>Warehouse: {{expense_warehouse}}</li>
  <li>Date: {{expense_date}}</li>
  <li>Invoice Number: {{expense_invoice}}</li>
  <li>Status: <strong>{{action}}</strong></li>
  <li>Reviewed by: {{approver_name}}</li>
</ul>

{{#if (eq action "approved")}}
<p>✅ Your expense has been approved!</p>
{{else}}
<p>❌ Your expense has been rejected. Please review and resubmit if needed.</p>
{{/if}}

<p>Best regards,<br>
{{app_name}}</p>
```
10. Click **Save**

---

## Step 6: Update HTML File with Your Credentials

1. Open `warehouse-expense-tracker.html`
2. Find the EmailJS configuration section (around line 1563)
3. Replace the placeholder values with your actual credentials:

```javascript
const EMAILJS_PUBLIC_KEY = 'YOUR_ACTUAL_PUBLIC_KEY'; // From Step 2
const EMAILJS_SERVICE_ID = 'YOUR_ACTUAL_SERVICE_ID'; // From Step 4
const EMAILJS_TEMPLATE_ID_USER = 'YOUR_ACTUAL_TEMPLATE_ID_USER'; // Template 1 ID
const EMAILJS_TEMPLATE_ID_EXPENSE = 'YOUR_ACTUAL_TEMPLATE_ID_EXPENSE'; // Template 2 ID
const EMAILJS_TEMPLATE_ID_APPROVAL = 'YOUR_ACTUAL_TEMPLATE_ID_APPROVAL'; // Template 3 ID
```

### Example:
```javascript
const EMAILJS_PUBLIC_KEY = 'abcdefghijklmnop';
const EMAILJS_SERVICE_ID = 'service_gmail123';
const EMAILJS_TEMPLATE_ID_USER = 'template_user_xyz';
const EMAILJS_TEMPLATE_ID_EXPENSE = 'template_expense_abc';
const EMAILJS_TEMPLATE_ID_APPROVAL = 'template_approval_def';
```

4. Save the file

---

## Step 7: Test Email Notifications

1. **Open the application** in your browser
2. **Open Browser Console** (F12 → Console tab)
3. Look for: `✅ EmailJS initialized successfully`

### Test User Creation Email:
1. Create a new user with a valid email address
2. Check the user's inbox for the welcome email from `scm.dispatch@tvs.in`
3. Check the console for any errors

### Test Expense Submission Email:
1. Log in as a warehouse user
2. Submit an expense
3. Check approver emails for the notification
4. Check the console for any errors

### Test Approval/Rejection Email:
1. Log in as an approver/master
2. Approve or reject an expense
3. Check submitter's email for the notification
4. Check the console for any errors

---

## Troubleshooting

### Issue: "EmailJS not configured"
- **Solution**: Check that `EMAILJS_PUBLIC_KEY` is set correctly in the HTML file
- Look for: `✅ EmailJS initialized successfully` in console

### Issue: "Service not found"
- **Solution**: Verify `EMAILJS_SERVICE_ID` matches your Gmail service ID in EmailJS dashboard
- Go to Email Services and check the Service ID

### Issue: "Template not found"
- **Solution**: Verify all three template IDs match exactly (case-sensitive)
- Go to Email Templates and copy the exact Template IDs

### Issue: "Authentication failed" or "Invalid credentials"
- **Solution**: 
  - Verify the App Password is correct (16 characters, no spaces)
  - Regenerate the App Password if needed
  - Ensure 2-Step Verification is enabled

### Issue: "Emails not sending"
- **Check EmailJS Dashboard**:
  - Go to **Logs** to see email delivery status
  - Check for any error messages
- **Check Gmail Account**:
  - Verify `scm.dispatch@tvs.in` can send emails normally
  - Check if there are any restrictions from your IT admin

### Issue: "Rate limit exceeded"
- **Solution**: EmailJS free tier allows 200 emails/month
- Upgrade to a paid plan if you need more emails

---

## EmailJS Free Tier Limits

- **200 emails per month** (free tier)
- **2 email services**
- **2 email templates** (you need 3, so you may need to upgrade)

### Upgrade Options:
- **Paid plans** start at $15/month for 1,000 emails
- **Business plans** available for higher volumes

---

## Security Best Practices

1. **App Password Security**:
   - Never share your App Password
   - If compromised, revoke it immediately and generate a new one
   - Store it securely (password manager)

2. **EmailJS Public Key**:
   - The public key is safe to expose in client-side code
   - It's designed for public use

3. **Gmail Account**:
   - Use a dedicated email account for automated emails (like `scm.dispatch@tvs.in`)
   - Monitor the account for any suspicious activity

---

## Next Steps

1. ✅ Set up EmailJS account
2. ✅ Generate Gmail App Password
3. ✅ Connect Gmail to EmailJS
4. ✅ Create 3 email templates
5. ✅ Update HTML file with credentials
6. ✅ Test all email notifications
7. ✅ Monitor email delivery in EmailJS dashboard

---

## Support Resources

- **EmailJS Documentation**: [https://www.emailjs.com/docs/](https://www.emailjs.com/docs/)
- **EmailJS Support**: [https://www.emailjs.com/support/](https://www.emailjs.com/support/)
- **Gmail App Passwords Help**: [https://support.google.com/accounts/answer/185833](https://support.google.com/accounts/answer/185833)
- **Google Workspace Admin Help**: [https://support.google.com/a](https://support.google.com/a)

---

**Note**: All emails will be sent from `scm.dispatch@tvs.in` once configured. Make sure this email account has permission to send automated emails from your organization.


