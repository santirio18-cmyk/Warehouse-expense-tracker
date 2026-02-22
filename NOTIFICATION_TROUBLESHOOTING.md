# Why Users Are Not Getting Notifications

## Email Notifications – Fix Checklist

### Step 1: Deploy Firebase Cloud Functions
```bash
cd /Users/santhoshpremkumar/AndroidStudioProjects/MyApplication
cd functions
npm install
cd ..
firebase deploy --only functions
```

### Step 2: Set Resend API key
```bash
firebase functions:config:set resend.api_key="re_YOUR_ACTUAL_KEY"
firebase deploy --only functions
```

### Step 3: Firebase Blaze plan
- Firebase Console → Project Settings → Usage and billing
- Upgrade to **Blaze** (pay-as-you-go) – required for Cloud Functions
- Free tier included (2M invocations/month)

### Step 4: Add email to users
- Login as **admin** → **Users** tab
- Each approver (K02815, K01706) and expense creator **must have email**
- Edit user → add email address → Save
- No email = no notification

### Step 5: Verify
- Submit or approve an expense
- Check browser console (F12) for `✅ Approval email sent` or errors
- If you see "Notification email could not be sent" toast → follow Steps 1–4

---

## Sound
Toasts play a short beep when they appear. Ensure device volume is on.
