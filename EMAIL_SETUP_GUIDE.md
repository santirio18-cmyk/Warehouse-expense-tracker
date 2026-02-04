# Email Notification Setup Guide

## Overview
The app now supports email notifications using EmailJS (free email service). Approvers will receive emails when:
- ✅ New expenses are submitted (pending approval)
- ✅ Expenses are approved (1st level or final)
- ✅ Expenses are rejected (with rejection reason)

---

## Setup Steps (5 minutes)

### Step 1: Sign up for EmailJS
1. Go to **https://www.emailjs.com**
2. Click **"Sign Up"** (free account)
3. Verify your email address

### Step 2: Create Email Service
1. Go to **Email Services** → **Add New Service**
2. Choose your email provider:
   - **Gmail** (recommended)
   - **Outlook**
   - **Custom SMTP**
3. Follow the setup instructions
4. **Copy your Service ID** (e.g., `service_abc123`)

### Step 3: Create Email Templates

#### Template 1: Expense Submission Notification
1. Go to **Email Templates** → **Create New Template**
2. **Template Name**: `expense_submission`
3. **Subject**: `New Expense Pending Approval - ₹{{expense_amount}}`
4. **Content**:
```
Hello {{to_name}},

A new expense has been submitted and requires your approval:

Amount: {{expense_amount}}
Category: {{expense_category}}
Vendor: {{expense_vendor}}
Warehouse: {{expense_warehouse}}
Invoice: {{expense_invoice}}
Date: {{expense_date}}

Submitted by: {{creator_name}} ({{creator_email}})

Please review and approve/reject this expense in the system.

Link: {{expense_link}}
```
5. **Copy Template ID** (e.g., `template_xyz789`)

#### Template 2: Approval Notification
1. Create New Template
2. **Template Name**: `expense_approval`
3. **Subject**: `Expense Approved - ₹{{expense_amount}}`
4. **Content**:
```
Hello {{to_name}},

The following expense has been {{action}}:

Amount: {{expense_amount}}
Category: {{expense_category}}
Vendor: {{expense_vendor}}
Warehouse: {{expense_warehouse}}
Invoice: {{expense_invoice}}
Date: {{expense_date}}

{{action}} by: {{to_name}}

Link: {{expense_link}}
```
5. **Copy Template ID**

#### Template 3: Rejection Notification
1. Create New Template
2. **Template Name**: `expense_rejection`
3. **Subject**: `Expense Rejected - ₹{{expense_amount}}`
4. **Content**:
```
Hello {{to_name}},

The following expense has been rejected:

Amount: {{expense_amount}}
Category: {{expense_category}}
Vendor: {{expense_vendor}}
Warehouse: {{expense_warehouse}}
Invoice: {{expense_invoice}}
Date: {{expense_date}}

Rejection Reason: {{rejection_reason}}

Rejected by: {{to_name}}

Link: {{expense_link}}
```
5. **Copy Template ID**

### Step 4: Get Public Key
1. Go to **Account** → **General**
2. Find **Public Key**
3. **Copy Public Key** (e.g., `abcdefghijklmnop`)

### Step 5: Update Configuration in App
1. Open `warehouse-expense-tracker.html`
2. Find the `EMAILJS_CONFIG` section (around line 1663)
3. Update with your values:

```javascript
const EMAILJS_CONFIG = {
    PUBLIC_KEY: 'YOUR_PUBLIC_KEY_HERE', // From Step 4
    SERVICE_ID: 'YOUR_SERVICE_ID_HERE', // From Step 2
    TEMPLATE_APPROVAL: 'YOUR_APPROVAL_TEMPLATE_ID', // From Step 3 Template 2
    TEMPLATE_REJECTION: 'YOUR_REJECTION_TEMPLATE_ID', // From Step 3 Template 3
    TEMPLATE_SUBMISSION: 'YOUR_SUBMISSION_TEMPLATE_ID' // From Step 3 Template 1
};
```

### Step 6: Add Email Addresses to Users
1. Login as Master Admin
2. Go to **Users** tab
3. Edit existing users or create new users
4. **Add email address** in the "Email Address" field
5. Save user

---

## How It Works

### When Expense is Submitted:
- ✅ All approvers with access to that warehouse receive email
- ✅ 2nd level approver receives email (if 2-level approval enabled)

### When Expense is Approved:
- ✅ Approver receives confirmation email
- ✅ Expense creator receives notification email

### When Expense is Rejected:
- ✅ Approver receives confirmation email
- ✅ Expense creator receives rejection email (with reason)

---

## EmailJS Free Plan Limits
- **200 emails/month** (free)
- **Upgrade available** if you need more

---

## Testing
1. Create a test expense
2. Check approver's email inbox
3. Approve/reject the expense
4. Verify emails are received

---

## Troubleshooting

### Emails not sending?
1. ✅ Check browser console for errors
2. ✅ Verify EmailJS configuration is correct
3. ✅ Check user has email address saved
4. ✅ Verify EmailJS account is active
5. ✅ Check email service is connected in EmailJS

### EmailJS Errors?
- Check EmailJS dashboard → **Logs** for error details
- Verify template variables match exactly
- Ensure email service is properly connected

---

## Alternative: Use Your Own Email Server

If you prefer to use your own email server instead of EmailJS:

1. Set up email server (Node.js/Python/etc.)
2. Update `EMAIL_SERVER_URL` in the code
3. Uncomment the old email functions
4. Comment out EmailJS functions

---

## Support
- EmailJS Docs: https://www.emailjs.com/docs
- EmailJS Support: support@emailjs.com
