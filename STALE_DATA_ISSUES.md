# Stale Data & Sync Issues – Audit Report

## ✅ FIXED

### 1. Login – User not found (stale localStorage)
**Issue:** User exists in Firebase (admin created them) but gets "User not found" when logging in from another device/browser.  
**Cause:** `getUsers(false)` returns cached localStorage and skips Firebase when local has any data.  
**Fix:** When user not found, force sync from Firebase (`getUsers(true)`) and retry.  
**Status:** Fixed in warehouse-expense-tracker.html (lines 3044–3064)

---

## ⚠️ POTENTIAL ISSUES (Lower Priority)

### 2. Initial page load – Firebase not ready
**Issue:** `initializeData()` runs immediately; Firebase initializes 1 second later. On first load:
- `getUsers()` → localStorage only (db is null)
- `getExpenses()` → localStorage only
- `getBudgets()` → localStorage only

**Impact:** Low. User must log in; at login we now retry from Firebase if user not found. Expenses get real-time updates via Firebase listener once it connects.

**Possible improvement:** After Firebase initializes, trigger a one-time sync of users and budgets into the global variables.

---

### 3. After add/edit/delete user – redundant getUsers(false)
**Issue:** After add user, delete user, or save warehouses:
```javascript
users = await getUsers();  // Returns localStorage – may not have latest
loadUsers();               // Does getUsers(true) – fetches from Firebase
```
**Impact:** Low. `loadUsers()` corrects the data. The first `getUsers()` is redundant and can briefly show stale data.

**Possible improvement:** Use only `loadUsers()` after these operations.

---

### 4. User import – getUsers() without forceReload
**Issue:** Import flow uses `getUsers()` (no forceReload) to get current users before merge.  
**Impact:** Low. Import is done by admin in Users tab; they likely have recent data. If not, merge could overwrite with stale data.  
**Possible improvement:** Use `getUsers(true)` before import to ensure fresh data.

---

### 5. Data recovery – db check
**Issue:** `attemptUserRecovery()` checks `if (db)` before fetching from Firebase. If Firebase is not initialized, Firebase recovery is skipped.  
**Impact:** Low. Recovery runs from Users tab; admin has already logged in, so Firebase is usually ready.  
**Possible improvement:** Call `await initializeFirebase()` before recovery if `db` is null.

---

### 6. Budgets – no real-time listener
**Issue:** Expenses have a Firebase `onSnapshot` listener; users and budgets do not. Budget changes on one device are not pushed to others until they reload.  
**Impact:** Low. Budgets change less often; users typically reload or navigate, which triggers a refresh.  
**Possible improvement:** Add Firebase listeners for `users` and `budgets` collections (similar to expenses).

---

## Summary

| Issue                    | Severity | Status   |
|--------------------------|----------|----------|
| Login user not found     | High     | Fixed    |
| Initial load timing      | Low      | Documented |
| Redundant getUsers()     | Low      | Documented |
| User import              | Low      | Documented |
| Data recovery            | Low      | Documented |
| Budget real-time sync    | Low      | Documented |

The main user-facing issue (login with stale cache) is fixed. The rest are minor and can be improved later if needed.
