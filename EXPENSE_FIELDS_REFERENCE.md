# Expense Form & List - Field Reference Guide

This document lists all fields/columns in the **Add Expense** form and **Expense List** for easy reference when requesting changes.

---

## 👤 USER WELCOME SECTION

### Location: Top of the page (after header, before navigation tabs)

**Section ID**: `user-info`

**Displays**:
1. **Welcome Message** (`currentUser`)
   - Shows: "Welcome, [User Name]"
   - Element ID: `currentUser`
   - Displays: User's full name (from `users[username].name`)

2. **User Role Badge** (`userRole`)
   - Shows: User's role in uppercase
   - Options: MASTER, APPROVER, WAREHOUSE
   - Element ID: `userRole`
   - CSS Class: `user-role [role]` (e.g., `user-role master`)

3. **Logout Button**
   - Button text: "Logout"
   - Function: `logout()`
   - Style: Red button (btn-danger)

---

## 📑 NAVIGATION TABS (Below Welcome Section)

### Section ID: `nav-tabs`

**Tab Buttons** (in order from left to right):

1. **➕ Add Expense** (`add`)
   - Tab ID: `add`
   - Function: `showTab('add')`
   - Always visible

2. **📋 Expense List** (`list`)
   - Tab ID: `list`
   - Function: `showTab('list')`
   - Always visible

3. **📊 Summary** (`summary`)
   - Tab ID: `summary`
   - Function: `showTab('summary')`
   - Always visible

4. **💰 Budget** (`budget`)
   - Tab ID: `budget`
   - Function: `showTab('budget')`
   - Always visible

5. **✅ Approvals** (`approvals`)
   - Tab ID: `approvals`
   - Element ID: `approvalsTab`
   - Function: `showTab('approvals')`
   - **Visibility**: Only shown for MASTER and APPROVER roles
   - Hidden by default, shown based on user role

6. **📥 Download** (`download`)
   - Tab ID: `download`
   - Function: `showTab('download')`
   - Always visible

7. **👥 Users** (`users`)
   - Tab ID: `users`
   - Element ID: `usersTab`
   - Function: `showTab('users')`
   - **Visibility**: Only shown for MASTER role
   - Hidden by default, shown only for master users

---

## 🏢 WAREHOUSE SELECTOR (Below Navigation Tabs)

### Section ID: `warehouseSelector`

**Visibility**: Only shown for MASTER and APPROVER roles (not for WAREHOUSE role)

**Elements**:
1. **Heading**: "Select Warehouse to View:"
2. **Dropdown** (`warehouseSelect`)
   - Element ID: `warehouseSelect`
   - Function: `switchWarehouse()` (on change)
   - Options:
     - "All Warehouses" (value: `all`)
     - "Warehouse 1" (value: `warehouse_1`)
     - "Warehouse 2" (value: `warehouse_2`)
     - ... (up to Warehouse 28)

---

---

## 📝 ADD EXPENSE FORM FIELDS

### Form Fields (in order):

1. **Date** (`expenseDate`)
   - Type: Date picker
   - Required: Yes
   - Field ID: `expenseDate`

2. **Time** (`expenseTime`)
   - Type: Time picker
   - Required: Yes
   - Field ID: `expenseTime`

3. **Category** (`expenseCategory`)
   - Type: Dropdown select
   - Required: Yes
   - Options:
     - Pooja
     - Tea
     - Petrol
     - Lunch
     - Travel
     - Freight
     - Maintenance
     - Utilities
     - Supplies
     - Other
   - Field ID: `expenseCategory`

4. **Amount (₹)** (`expenseAmount`)
   - Type: Number input
   - Required: Yes
   - Format: Decimal (step: 0.01)
   - Field ID: `expenseAmount`

5. **Vendor Name** (`vendorName`)
   - Type: Text input
   - Required: Yes
   - Placeholder: "Enter vendor/supplier name"
   - Field ID: `vendorName`

6. **Payment Mode** (`paymentMode`)
   - Type: Dropdown select
   - Required: Yes
   - Options:
     - Cash
     - UPI
     - Credit Card
     - Debit Card
     - Net Banking
     - Cheque
     - Bank Transfer
     - Other
   - Field ID: `paymentMode`

7. **Invoice Number** (`invoiceNumber`)
   - Type: Text input
   - Required: Yes
   - Placeholder: "Enter invoice number (e.g., INV-2024/001)"
   - Allowed characters: Letters, Numbers, Spaces, -, _, /
   - Field ID: `invoiceNumber`

8. **Invoice Document** (`invoiceFile`)
   - Type: File upload
   - Required: Yes
   - Accepted formats: PDF, JPG, JPEG, PNG
   - Max size: 750KB (Firebase) / 10MB (local storage)
   - Field ID: `invoiceFile`

9. **Description** (`expenseDescription`)
   - Type: Textarea (multi-line)
   - Required: No (Optional)
   - Placeholder: "Add any additional details..."
   - Field ID: `expenseDescription`

---

## 📋 EXPENSE LIST COLUMNS

### Displayed Information (in order):

1. **Category Badge**
   - Shows expense category (e.g., "Pooja", "Travel")
   - Color: Orange badge
   - Field: `expense.category`

2. **Amount**
   - Shows expense amount in ₹
   - Format: ₹XX.XX
   - Field: `expense.amount`

3. **Date & Time**
   - Shows: Date and Time
   - Format: "Date: YYYY-MM-DD, Time: HH:MM"
   - Fields: `expense.date`, `expense.time`

4. **Vendor Name**
   - Shows vendor/supplier name
   - Field: `expense.vendorName`

5. **Payment Mode**
   - Shows payment method used
   - Field: `expense.paymentMode`

6. **Invoice Number**
   - Shows invoice number
   - Field: `expense.invoiceNumber`

7. **Warehouse Badge**
   - Shows warehouse name (e.g., "Warehouse 1")
   - Color: Dark blue badge
   - Field: `expense.warehouse`

8. **Status**
   - Shows approval status:
     - "Pending" (orange)
     - "Approved" (green)
     - "Rejected" (red)
   - Field: `expense.status`

9. **Description** (if provided)
   - Shows additional description
   - Only displayed if description exists
   - Field: `expense.description`

10. **Created By**
    - Shows username who created the expense
    - Field: `expense.createdBy`

11. **Approved By** (if approved/rejected)
    - Shows username who approved/rejected
    - Field: `expense.approvedBy`

12. **Approved At** (if approved/rejected)
    - Shows approval/rejection timestamp
    - Field: `expense.approvedAt`

13. **Action Buttons**
    - **View Invoice**: View invoice document
    - **Download Invoice**: Download invoice file
    - **Delete**: Delete expense (if pending)
    - **Approve/Reject**: For approvers (in Approvals tab)

---

## 🔧 INTERNAL DATA FIELDS

These fields are stored but may not always be displayed:

- **ID** (`expense.id`)
  - Unique identifier for each expense
  - Format: Timestamp string

- **Invoice File** (`expense.invoiceFile`)
  - Object containing:
    - `filename`: File name
    - `type`: File MIME type
    - `size`: File size in bytes
    - `data`: Base64 encoded file data

- **Created At** (`expense.createdAt`)
  - ISO timestamp of creation
  - Format: ISO 8601 string

- **Warehouse** (`expense.warehouse`)
  - Warehouse identifier
  - Format: "warehouse_1", "warehouse_2", etc.

---

## 📊 HOW TO REFERENCE FIELDS

When requesting changes, you can use:

### For Form Fields:
- Use the **Field ID** (e.g., `expenseDate`, `vendorName`)
- Or use the **Label Name** (e.g., "Date", "Vendor Name")

### For List Columns:
- Use the **Field Name** (e.g., `expense.amount`, `expense.category`)
- Or describe the **Display Name** (e.g., "Amount column", "Category badge")

### Examples:
- ✅ "Change the Date field to show calendar icon"
- ✅ "Make Vendor Name field wider"
- ✅ "Add a new field called 'Reference Number' after Invoice Number"
- ✅ "Show Amount in bold in the Expense List"
- ✅ "Add a filter by Category in Expense List"
- ✅ "Change the Category dropdown to include 'Stationery'"

---

## 🎨 VISUAL ELEMENTS

### Badges:
- **Category Badge**: Orange background, white text
- **Warehouse Badge**: Dark blue background, white text
- **Status Badges**: 
  - Pending: Orange
  - Approved: Green
  - Rejected: Red

### Buttons:
- **Add Expense**: Orange button
- **View Invoice**: Blue button
- **Download Invoice**: Teal button
- **Delete**: Red button
- **Approve**: Green button
- **Reject**: Red button

---

## 📝 NOTES

- All fields marked with `*` are **required**
- Fields without `*` are **optional**
- Date and Time are auto-filled with current date/time
- Invoice file is validated for size and format
- Invoice number is validated for allowed characters

---

**Last Updated**: Based on current implementation
**File**: `warehouse-expense-tracker.html`

