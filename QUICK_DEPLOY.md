# 🚀 Quick Deployment Guide

## Fastest Way: GitHub Pages (5 minutes)

```bash
# 1. Initialize git
git init

# 2. Add files
git add warehouse-expense-tracker.html index.html DEPLOYMENT_GUIDE.md README.md

# 3. Commit
git commit -m "Warehouse Expense Tracker"

# 4. Go to GitHub.com and create a new repository, then:
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
git push -u origin main

# 5. Enable GitHub Pages:
# Go to Settings → Pages → Source: main branch
# Your app will be live at: https://YOUR_USERNAME.github.io/YOUR_REPO_NAME/
```

## Even Faster: Netlify (1 minute)

1. Go to **https://www.netlify.com**
2. Drag `warehouse-expense-tracker.html` onto the page
3. Done! Your app is live

## The Fastest: Vercel (2 minutes)

```bash
npm install -g vercel
vercel
# Follow the prompts
```

## Share Your Link

Once deployed, anyone can access your app by:
1. Opening the URL in their browser
2. Logging in with:
   - **Admin**: `admin` / `admin123`
   - **Warehouse Users**: `warehouse_1` to `warehouse_28` / `pass123`

## ⚠️ Important

The current version uses localStorage (browser storage). Each user will have their own separate data. For shared data across users, you'll need a backend database.

---

**Need help?** See `DEPLOYMENT_GUIDE.md` for detailed instructions.

