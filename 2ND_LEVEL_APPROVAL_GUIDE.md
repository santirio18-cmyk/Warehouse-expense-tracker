# 2nd Level Approval System Guide

## Overview

The application now supports a **2-level approval system** for all expense transactions. This ensures that expenses go through two stages of approval before being finalized.

## How It Works

### Approval Flow

1. **Expense Submission** → Status: `pending`
   - Warehouse users submit expenses
   - Status is set to `pending`

2. **First Level Approval** → Status: `first_approved`
   - First level approvers (regular approvers) review and approve
   - Status changes to `first_approved`
   - Submitter receives email notification about first approval
   - Expense is now visible to the 2nd level approver

3. **Second Level Approval** → Status: `approved`
   - Only the designated 2nd level approver can give final approval
   - Status changes to `approved`
   - Submitter receives final approval email
   - Expense is now fully approved

### Rejection

- Expenses can be rejected at any level
- Rejection immediately sets status to `rejected`
- Submitter receives rejection email notification

## Configuration

### Setting the 2nd Level Approver

In `warehouse-expense-tracker.html`, find the configuration section (around line 1572):

```javascript
// 2ND LEVEL APPROVAL CONFIGURATION
const SECOND_LEVEL_APPROVER_ID = 'admin'; // Change this to the username of your 2nd level approver
const ENABLE_2ND_LEVEL_APPROVAL = true; // Set to false to disable 2nd level approval
```

**To configure:**
1. Set `SECOND_LEVEL_APPROVER_ID` to the username of the user who will be the final approver
2. Set `ENABLE_2ND_LEVEL_APPROVAL` to `true` to enable, or `false` to disable

### Disabling 2nd Level Approval

If you want to go back to single-level approval:
```javascript
const ENABLE_2ND_LEVEL_APPROVAL = false;
```

## User Roles and Permissions

### Warehouse Users
- Can submit expenses
- Can view their own expenses
- See status: `pending`, `first_approved`, `approved`, or `rejected`

### First Level Approvers
- Can see expenses with status: `pending`
- Can approve (first level) or reject expenses
- After first approval, expense moves to `first_approved` status
- Cannot see expenses that are `first_approved` (only 2nd level approver can)

### 2nd Level Approver (Single ID)
- Can see expenses with status: `first_approved`
- Can give final approval or reject
- After final approval, expense moves to `approved` status
- This is the only user who can see `first_approved` expenses

### Master Admin
- Can see all expenses regardless of status
- Can approve/reject at any level
- Has full access to all features

## Status Indicators

### Status Colors
- **Pending** (Yellow): Waiting for first approval
- **First Approved** (Blue): Waiting for final approval
- **Approved** (Green): Fully approved
- **Rejected** (Red): Rejected at any level

### Status Display
- Expenses show their current status in the approval panel
- Status badges are color-coded for easy identification
- Approval history shows who approved at each level

## Email Notifications

### First Level Approval
- **To Submitter**: Email notification that expense is "First Level Approved"
- **To 2nd Level Approver**: Email notification about new expense pending final approval

### Final Approval
- **To Submitter**: Email notification that expense is "Finally Approved"

### Rejection
- **To Submitter**: Email notification that expense is "Rejected"

## Approval Panel Views

### First Level Approvers See:
- Expenses with status: `pending`
- Button: "✅ First Approve" or "❌ Reject"

### 2nd Level Approver Sees:
- Expenses with status: `first_approved`
- Shows who gave first approval
- Button: "✅ Final Approve" or "❌ Reject"

## Data Fields

New fields added to expense records:
- `firstApprovedBy`: Username of first level approver
- `firstApprovedAt`: Timestamp of first approval
- `approvedBy`: Username of final approver (2nd level)
- `approvedAt`: Timestamp of final approval

## CSV/PDF Export

Exports now include:
- **Status**: Current approval status
- **First Approved By**: Name of first approver
- **First Approved At**: Timestamp of first approval
- **Final Approved By**: Name of final approver
- **Final Approved At**: Timestamp of final approval

## Example Workflow

1. **John** (warehouse user) submits an expense → Status: `pending`
2. **Sarah** (first level approver) reviews and approves → Status: `first_approved`
   - John receives: "First Level Approved" email
   - Admin receives: "New expense pending final approval" email
3. **Admin** (2nd level approver) reviews and gives final approval → Status: `approved`
   - John receives: "Finally Approved" email
4. Expense is now fully approved and appears in reports

## Troubleshooting

### "I can't see expenses to approve"
- **First level approvers**: Check that expenses have status `pending`
- **2nd level approver**: Check that expenses have status `first_approved`
- Verify your user role and warehouse access

### "2nd level approval not working"
- Check that `ENABLE_2ND_LEVEL_APPROVAL = true`
- Verify `SECOND_LEVEL_APPROVER_ID` matches your username exactly
- Check browser console for any errors

### "Emails not sending"
- Verify email server is running
- Check email server configuration
- Check browser console for email errors

## Benefits

✅ **Enhanced Security**: Two levels of review before approval
✅ **Better Control**: Final approval by designated authority
✅ **Audit Trail**: Complete history of who approved at each level
✅ **Flexible**: Can be enabled/disabled as needed
✅ **Transparent**: Clear status indicators and notifications

---

**Note**: The 2nd level approver must be a single, specific user ID. This ensures centralized final approval control.

