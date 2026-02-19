# Login Troubleshooting

## If users still can't login (e.g. K02852, K01386)

### 1. Verify user exists in Firebase
- Go to [Firebase Console](https://console.firebase.google.com) → your project → **Firestore Database**
- Open the `users` collection
- Check that the document ID matches the username (e.g. `K02852`)

### 2. Add Firebase authorized domain
- Firebase Console → **Authentication** → **Settings** → **Authorized domains**
- Add: `santirio18-cmyk.github.io`

### 3. Check Firestore rules
- Firebase Console → **Firestore** → **Rules**
- Ensure `users` collection allows read access. Example:
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if true;  // Or add proper auth rules
    }
    match /expenses/{docId} { allow read, write: if true; }
    match /budgets/{docId} { allow read, write: if true; }
  }
}
```

### 4. Ask user to click **Retry**
- When "User not found" appears, a **Retry** button is shown
- Click Retry to sync from server and try again
- Useful if first attempt failed due to slow network

### 5. Check network
- User needs internet to reach Firebase
- Corporate firewall might block Firebase – try from mobile data or different network
