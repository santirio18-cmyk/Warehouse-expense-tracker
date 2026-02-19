#!/bin/bash
# Quick script to push changes to GitHub

echo "🚀 Pushing changes to GitHub..."
echo ""

# Check if there are changes
if [ -z "$(git status --porcelain)" ]; then
    echo "✅ No changes to commit"
    exit 0
fi

# Show what changed
echo "📝 Changes detected:"
git status --short
echo ""

# Add all changes
echo "➕ Adding changes..."
git add .

# Commit
read -p "💬 Enter commit message: " commit_msg
git commit -m "$commit_msg"

# Push
echo ""
echo "📤 Pushing to GitHub..."
git push origin main

echo ""
echo "✅ Done! GitHub Pages will rebuild in 2-3 minutes."
