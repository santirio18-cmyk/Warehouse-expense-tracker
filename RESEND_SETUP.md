# Resend Email Setup Guide

## Quick Setup (5 minutes)

### Step 1: Set your Resend API key in Firebase

```bash
cd /Users/santhoshpremkumar/AndroidStudioProjects/MyApplication
firebase functions:config:set resend.api_key="re_YOUR_RESEND_KEY_HERE"
```

### Step 2: Install dependencies and deploy

```bash
cd functions
npm install
cd ..
firebase deploy --only functions
```

### Step 3: Verify

- The app is already configured with `USE_RESEND_EMAIL = true`
- When an expense is submitted or approved, emails will be sent via Resend

---

## Resend Setup (if you don't have a key yet)

1. Go to **https://resend.com** and sign up (free)
2. Verify your email
3. Go to **API Keys** → **Create API Key**
4. Copy the key (starts with `re_`)
5. Use it in Step 1 above

---

## Custom "From" Address (Optional)

By default, emails are sent from `onboarding@resend.dev`. To use your own domain:

1. In Resend dashboard: **Domains** → **Add Domain**
2. Add the DNS records they provide
3. Edit `functions/index.js` and change the `from` line:
   ```js
   from: 'Your App <notifications@yourdomain.com>',
   ```

---

## Firebase Blaze Plan

Cloud Functions require the **Blaze (pay-as-you-go)** plan. You get:
- 2 million invocations/month free
- 400,000 GB-seconds free
- You only pay if you exceed free tier (unlikely for email notifications)

Upgrade at: Firebase Console → Project Settings → Usage and billing
