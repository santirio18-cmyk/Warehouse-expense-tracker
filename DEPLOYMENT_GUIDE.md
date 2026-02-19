# Deployment Guide for Warehouse Expense Tracker

This guide explains how to make the `warehouse-expense-tracker.html` application accessible to others.

## Quick Start Options

### Option 1: Using GitHub Pages (Recommended - Free)

1. **Create a GitHub Repository:**
   ```bash
   cd /Users/santhoshpremkumar/AndroidStudioProjects/MyApplication
   git init
   git add warehouse-expense-tracker.html
   git commit -m "Initial commit: Warehouse Expense Tracker"
   ```

2. **Push to GitHub:**
   - Go to https://github.com and create a new repository
   - Connect your local repo:
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   git branch -M main
   git push -u origin main
   ```

3. **Enable GitHub Pages:**
   - Go to your repository on GitHub
   - Click **Settings** → **Pages**
   - Under "Source", select **"Deploy from a branch"**
   - Choose **"main"** branch and **"/ (root)"**
   - Click **Save**
   - Your app will be live at: `https://YOUR_USERNAME.github.io/YOUR_REPO_NAME/warehouse-expense-tracker.html`

### Option 2: Using Netlify (Free & Easy)

1. Go to https://www.netlify.com and sign up (free)

2. Drag and drop the `warehouse-expense-tracker.html` file onto Netlify

3. Your app will be live instantly with a random URL

4. You can customize the URL in Settings → Domain

### Option 3: Using Vercel (Free)

1. Install Vercel CLI:
   ```bash
   npm install -g vercel
   ```

2. Deploy:
   ```bash
   cd /Users/santhoshpremkumar/AndroidStudioProjects/MyApplication
   vercel
   ```

3. Follow the prompts and your app will be deployed

### Option 4: Self-Hosted Server

If you have your own web server:

1. Upload `warehouse-expense-tracker.html` to your server

2. Access it via: `http://your-server.com/warehouse-expense-tracker.html`

### Option 5: Local Network Sharing (For Testing)

For sharing within your office/network:

1. Make sure the file is accessible via a local web server
2. Find your IP address: `ifconfig` (Mac/Linux) or `ipconfig` (Windows)
3. Share the URL: `http://YOUR_IP_ADDRESS/warehouse-expense-tracker.html`

## Important Notes

⚠️ **Data Storage:** This app uses browser localStorage, meaning:
- Each user's data is stored in THEIR browser
- Data is NOT shared between users
- If someone clears their browser cache, all data is lost

📝 **For Production Use:**
Consider migrating to a backend database for:
- Centralized data storage
- Multi-user collaboration
- Data backup and recovery
- Server-side validation

## Current Features Summary

✅ Multi-warehouse support (28 warehouses)
✅ User management with roles
✅ Invoice tracking with file uploads
✅ Budget management and visualization
✅ Approval workflow
✅ Comprehensive validations and fraud controls
✅ Export capabilities (CSV, JSON, PDF)
✅ Employee code tracking
✅ Vendor and payment mode tracking

## Support

For issues or questions, check the console logs in your browser developer tools (F12).


