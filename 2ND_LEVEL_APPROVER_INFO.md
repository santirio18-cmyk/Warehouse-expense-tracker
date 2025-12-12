# 2nd Level Approver Configuration

## Current 2nd Level Approver

**Username**: `K01706`  
**Name**: Varadarajan Krishnamachari  
**Email**: varadarajan.krishnamachari@tvs.in  
**Emp ID**: K01706

## How to Create This User

1. **Log in as Master Admin**
2. **Go to "Users" tab**
3. **Click "Add New User"**
4. **Fill in the details**:
   - **Username**: `K01706` (must match exactly)
   - **Name**: `Varadarajan Krishnamachari`
   - **Email**: `varadarajan.krishnamachari@tvs.in`
   - **Password**: Set a secure password
   - **Role**: Select `Master` or `Approver` (both can be 2nd level approvers)
   - **Warehouse**: Can be left empty or select specific warehouses

5. **Save the user**

## Important Notes

- The username **MUST** be exactly `K01706` (case-sensitive)
- This user will see all expenses with status `first_approved`
- Only this user can give final approval to expenses
- The user will receive email notifications when expenses need final approval

## Verification

After creating the user:
1. Log in with username `K01706`
2. Go to "Approvals" tab
3. You should see expenses with status "FIRST APPROVED"
4. You can approve/reject them for final approval

---

**Configuration Location**: `warehouse-expense-tracker.html` line ~1583
```javascript
const SECOND_LEVEL_APPROVER_ID = 'K01706';
```

