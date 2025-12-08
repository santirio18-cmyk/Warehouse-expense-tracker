# EmailJS Setup Guide for Email Notifications

## Overview
The expense management system now supports email notifications using EmailJS. Users will receive emails when:
- An expense is submitted (notifies approvers)
- An expense is approved (notifies submitter)
- An expense is rejected (notifies submitter)

## Step 1: Create EmailJS Account

1. Go to https://www.emailjs.com/
2. Click **"Sign Up"** (free account available)
3. Create account with your email
4. Verify your email address

## Step 2: Add Email Service

1. Log in to EmailJS Dashboard: https://dashboard.emailjs.com/
2. Go to **"Email Services"** in the left menu
3. Click **"Add New Service"**
4. Choose your email provider:
   - **Gmail** (recommended for testing)
   - **Outlook**
   - **Yahoo**
   - **Custom SMTP**
5. Follow the setup instructions for your provider
6. **Save the Service ID** (you'll need this later)

## Step 3: Create Email Templates

You need to create 3 email templates:

### Template 1: Expense Submission Notification

1. Go to **"Email Templates"** in the left menu
2. Click **"Create New Template"**
3. **Template Name**: `expense_submission`
4. **Subject**: `New Expense Submitted - Approval Required`
5. **Content** (HTML):
```html
<h2>New Expense Submitted</h2>
<p>Dear Approver,</p>
<p>A new expense has been submitted and requires your approval:</p>
<ul>
    <li><strong>Submitted By:</strong> {{submitter_name}}</li>
    <li><strong>Amount:</strong> {{expense_amount}}</li>
    <li><strong>Vendor:</strong> {{expense_vendor}}</li>
    <li><strong>Category:</strong> {{expense_category}}</li>
    <li><strong>Warehouse:</strong> {{expense_warehouse}}</li>
    <li><strong>Date:</strong> {{expense_date}}</li>
    <li><strong>Invoice Number:</strong> {{expense_invoice}}</li>
    <li><strong>Description:</strong> {{expense_description}}</li>
</ul>
<p>Please log in to the system to review and approve/reject this expense.</p>
<p>Thank you!</p>
```
6. Click **"Save"**
7. **Copy the Template ID** (you'll need this)

### Template 2: Expense Approval Notification

1. Click **"Create New Template"**
2. **Template Name**: `expense_approval`
3. **Subject**: `Expense Approved - {{expense_amount}}`
4. **Content** (HTML):
```html
<h2>Expense Approved ✅</h2>
<p>Dear {{submitter_name}},</p>
<p>Your expense has been approved by {{approver_name}}:</p>
<ul>
    <li><strong>Amount:</strong> {{expense_amount}}</li>
    <li><strong>Vendor:</strong> {{expense_vendor}}</li>
    <li><strong>Category:</strong> {{expense_category}}</li>
    <li><strong>Warehouse:</strong> {{expense_warehouse}}</li>
    <li><strong>Date:</strong> {{expense_date}}</li>
    <li><strong>Invoice Number:</strong> {{expense_invoice}}</li>
</ul>
<p>Thank you!</p>
```
5. Click **"Save"**
6. **Copy the Template ID**

### Template 3: Expense Rejection Notification

1. Click **"Create New Template"**
2. **Template Name**: `expense_rejection`
3. **Subject**: `Expense Rejected - {{expense_amount}}`
4. **Content** (HTML):
```html
<h2>Expense Rejected ❌</h2>
<p>Dear {{submitter_name}},</p>
<p>Your expense has been rejected by {{approver_name}}:</p>
<ul>
    <li><strong>Amount:</strong> {{expense_amount}}</li>
    <li><strong>Vendor:</strong> {{expense_vendor}}</li>
    <li><strong>Category:</strong> {{expense_category}}</li>
    <li><strong>Warehouse:</strong> {{expense_warehouse}}</li>
    <li><strong>Date:</strong> {{expense_date}}</li>
    <li><strong>Invoice Number:</strong> {{expense_invoice}}</li>
</ul>
<p>Please contact the approver if you have any questions.</p>
<p>Thank you!</p>
```
5. Click **"Save"**
6. **Copy the Template ID**

## Step 4: Get Your Public Key

1. Go to **"Account"** → **"General"** in the left menu
2. Find **"API Keys"** section
3. Copy your **Public Key** (starts with something like `user_xxxxxxxxxxxxx`)

## Step 5: Update HTML File

Open `warehouse-expense-tracker.html` and find this section (around line 1515):

```javascript
// Initialize EmailJS
const EMAILJS_PUBLIC_KEY = 'YOUR_EMAILJS_PUBLIC_KEY';
const EMAILJS_SERVICE_ID = 'YOUR_SERVICE_ID';
const EMAILJS_TEMPLATE_SUBMIT = 'YOUR_TEMPLATE_ID_SUBMIT';
const EMAILJS_TEMPLATE_APPROVE = 'YOUR_TEMPLATE_ID_APPROVE';
const EMAILJS_TEMPLATE_REJECT = 'YOUR_TEMPLATE_ID_REJECT';
```

Replace with your actual values:

```javascript
const EMAILJS_PUBLIC_KEY = 'user_xxxxxxxxxxxxx'; // Your Public Key from Step 4
const EMAILJS_SERVICE_ID = 'service_xxxxxxxx'; // Your Service ID from Step 2
const EMAILJS_TEMPLATE_SUBMIT = 'template_xxxxxxxx'; // Template ID for submission
const EMAILJS_TEMPLATE_APPROVE = 'template_xxxxxxxx'; // Template ID for approval
const EMAILJS_TEMPLATE_REJECT = 'template_xxxxxxxx'; // Template ID for rejection
```

## Step 6: Test Email Notifications

1. Create a new user with a valid email address
2. Submit an expense
3. Check the email inbox for the submission notification
4. Approve the expense
5. Check the submitter's email for approval notification

## EmailJS Free Tier Limits

- **200 emails/month** (free tier)
- **2 email services**
- **2 email templates**
- **Unlimited contacts**

For production use with high volume, consider upgrading to a paid plan.

## Troubleshooting

### Emails not sending?
1. Check browser console for errors
2. Verify all IDs are correct in the HTML file
3. Check EmailJS dashboard for delivery status
4. Ensure email service is properly connected

### Template variables not working?
- Make sure variable names match exactly (case-sensitive)
- Use double curly braces: `{{variable_name}}`

### Need help?
- EmailJS Documentation: https://www.emailjs.com/docs/
- EmailJS Support: https://www.emailjs.com/support/

---

**Note:** Email notifications will only work if:
1. EmailJS is properly configured
2. Users have valid email addresses in their profiles
3. Email service is connected and verified

