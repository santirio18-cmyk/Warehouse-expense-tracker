# Why Notifications & Sound Aren't Working

## Email Notifications – Checklist

### 1. Firebase Cloud Functions deployed?
```bash
cd functions && npm install && cd ..
firebase deploy --only functions
```

### 2. Resend API key set?
```bash
firebase functions:config:set resend.api_key="re_YOUR_KEY"
```

### 3. Firebase Blaze plan?
- Cloud Functions need Blaze (pay-as-you-go)
- Firebase Console → Settings → Usage and billing

### 4. Users have email addresses?
- Admin → Users tab → Edit each user
- Approvers (K02815, K01706) and creators must have email
- Emails are only sent when `user.email` is set

### 5. Check browser console
- Open DevTools (F12) → Console
- Look for: `✅ Email notifications enabled (Resend via Firebase)`
- Or errors: `❌ Error sending approval email`

---

## Sound

The app uses **toast notifications** (visual popups). Sound has been added – toasts now play a short beep when they appear.
