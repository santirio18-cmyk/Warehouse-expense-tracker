# EmailJS Setup Guide

This guide will help you set up EmailJS to enable email notifications in the SCM Petty Cash Expense Management System.

## What is EmailJS?

EmailJS is a service that allows you to send emails directly from client-side JavaScript without a backend server. It's perfect for this application as it enables email notifications for:
- User creation (welcome emails)
- Expense submission (notifications to approvers)
- Expense approval/rejection (notifications to submitters)

## Step 1: Create EmailJS Account

1. Go to [https://www.emailjs.com/](https://www.emailjs.com/)
2. Click "Sign Up" and create a free account
3. Verify your email address

## Step 2: Get Your Public Key

1. After logging in, go to **Account** → **General**
2. Find your **Public Key** (it looks like: `abcdefghijklmnop`)
3. Copy this key - you'll need it in Step 5

## Step 3: Add Email Service

1. Go to **Email Services** in the dashboard
2. Click **Add New Service**
3. Choose your email provider:
   - **Gmail** (recommended for testing)
   - **Outlook**
   - **Yahoo**
   - **Custom SMTP** (for corporate emails)
4. Follow the setup instructions for your provider
5. Note your **Service ID** (e.g., `service_abc123`)

### For Gmail:
- You'll need to enable "Less secure app access" or use an App Password
- Or use OAuth 2.0 for better security

## Step 4: Create Email Templates

You need to create **3 email templates**:

### Template 1: User Creation (Welcome Email)

1. Go to **Email Templates** → **Create New Template**
2. **Template ID**: Name it `template_user_creation` (or note the ID provided)
3. **Subject**: `Welcome to {{app_name}} - Your Account Has Been Created`
4. **Content**:
```
Hello {{user_name}},

Your account has been successfully created in the {{app_name}}.

Account Details:
- Username: {{username}}
- Password: {{password}}
- Role: {{role}}

Please log in and change your password for security.

Best regards,
{{app_name}} Team
```
5. **To Email**: `{{to_email}}`
6. **From Name**: `SCM Expense Management`
7. **From Email**: Your verified email address
8. Click **Save**

### Template 2: Expense Submission (Notification to Approvers)

1. Go to **Email Templates** → **Create New Template**
2. **Template ID**: Name it `template_expense_submission` (or note the ID provided)
3. **Subject**: `New Expense Submitted - {{expense_amount}} - {{expense_category}}`
4. **Content**:
```
Hello,

A new expense has been submitted and requires your approval.

Expense Details:
- Amount: {{expense_amount}}
- Category: {{expense_category}}
- Vendor: {{expense_vendor}}
- Warehouse: {{expense_warehouse}}
- Date: {{expense_date}}
- Invoice Number: {{expense_invoice}}
- Submitted by: {{submitter_name}} ({{submitter_email}})

Please log in to review and approve/reject this expense.

Best regards,
{{app_name}}
```
5. **To Email**: `{{to_email}}` (will be comma-separated list of approver emails)
6. **From Name**: `SCM Expense Management`
7. **From Email**: Your verified email address
8. Click **Save**

### Template 3: Approval/Rejection (Notification to Submitter)

1. Go to **Email Templates** → **Create New Template**
2. **Template ID**: Name it `template_approval` (or note the ID provided)
3. **Subject**: `Expense {{action}} - {{expense_amount}} - {{expense_category}}`
4. **Content**:
```
Hello {{submitter_name}},

Your expense submission has been {{action}}.

Expense Details:
- Amount: {{expense_amount}}
- Category: {{expense_category}}
- Vendor: {{expense_vendor}}
- Warehouse: {{expense_warehouse}}
- Date: {{expense_date}}
- Invoice Number: {{expense_invoice}}
- Status: {{action}}
- Reviewed by: {{approver_name}}

{{#if (eq action "approved")}}
✅ Your expense has been approved!
{{else}}
❌ Your expense has been rejected. Please review and resubmit if needed.
{{/if}}

Best regards,
{{app_name}}
```
5. **To Email**: `{{to_email}}`
6. **From Name**: `SCM Expense Management`
7. **From Email**: Your verified email address
8. Click **Save**

## Step 5: Update HTML File with Your Credentials

1. Open `warehouse-expense-tracker.html`
2. Find the EmailJS configuration section (around line 1557)
3. Replace the placeholder values:

```javascript
const EMAILJS_PUBLIC_KEY = 'YOUR_EMAILJS_PUBLIC_KEY'; // Replace with your Public Key from Step 2
const EMAILJS_SERVICE_ID = 'YOUR_SERVICE_ID'; // Replace with your Service ID from Step 3
const EMAILJS_TEMPLATE_ID_USER = 'YOUR_TEMPLATE_ID_USER'; // Replace with Template 1 ID
const EMAILJS_TEMPLATE_ID_EXPENSE = 'YOUR_TEMPLATE_ID_EXPENSE'; // Replace with Template 2 ID
const EMAILJS_TEMPLATE_ID_APPROVAL = 'YOUR_TEMPLATE_ID_APPROVAL'; // Replace with Template 3 ID
```

### Example:
```javascript
const EMAILJS_PUBLIC_KEY = 'abcdefghijklmnop';
const EMAILJS_SERVICE_ID = 'service_abc123';
const EMAILJS_TEMPLATE_ID_USER = 'template_xyz789';
const EMAILJS_TEMPLATE_ID_EXPENSE = 'template_def456';
const EMAILJS_TEMPLATE_ID_APPROVAL = 'template_ghi789';
```

## Step 6: Test Email Notifications

1. **Test User Creation**:
   - Create a new user with a valid email address
   - Check the user's inbox for the welcome email

2. **Test Expense Submission**:
   - Log in as a warehouse user
   - Submit an expense
   - Check approver emails for the notification

3. **Test Approval/Rejection**:
   - Log in as an approver/master
   - Approve or reject an expense
   - Check submitter's email for the notification

## EmailJS Free Tier Limits

- **200 emails per month** (free tier)
- **2 email services**
- **2 email templates** (you need 3, so you may need to upgrade or reuse templates)

### Upgrade Options:
- **Paid plans** start at $15/month for 1,000 emails
- **Business plans** available for higher volumes

## Troubleshooting

### Emails Not Sending?

1. **Check Browser Console**:
   - Open Developer Tools (F12)
   - Look for EmailJS errors in the Console tab
   - Common errors:
     - `EmailJS not configured` - Check your public key
     - `Service not found` - Check your service ID
     - `Template not found` - Check your template IDs

2. **Verify EmailJS Initialization**:
   - Look for: `✅ EmailJS initialized successfully` in console
   - If you see: `ℹ️ EmailJS not configured` - your public key is not set

3. **Check Email Service**:
   - Go to EmailJS dashboard → Email Services
   - Ensure your service is "Active"
   - Test the service connection

4. **Check Template Variables**:
   - Ensure all template variables match exactly (case-sensitive)
   - Variables should be wrapped in `{{}}` in templates

### Email Formatting Issues?

- EmailJS templates support HTML
- You can use HTML tags for better formatting:
```html
<h2>Expense Details</h2>
<p><strong>Amount:</strong> {{expense_amount}}</p>
```

## Security Notes

1. **Public Key Exposure**: The EmailJS public key is safe to expose in client-side code. It's designed for public use.

2. **Rate Limiting**: EmailJS has rate limits to prevent abuse. If you exceed limits, emails will fail.

3. **Email Validation**: The application validates email format before sending, but EmailJS will also validate.

## Next Steps

1. Set up your EmailJS account
2. Configure the 3 email templates
3. Update the HTML file with your credentials
4. Test all three email notification types
5. Monitor email delivery in the EmailJS dashboard

## Support

- **EmailJS Documentation**: [https://www.emailjs.com/docs/](https://www.emailjs.com/docs/)
- **EmailJS Support**: [https://www.emailjs.com/support/](https://www.emailjs.com/support/)

---

**Note**: Email notifications will only work after you complete the EmailJS setup. Until then, the application will log messages to the console but won't send emails.


