#!/bin/bash
# Automated push to GitHub - no manual credential prompts
# Run setup-once.sh first to store your token

set -e
cd "$(dirname "$0")"

# Try to get token: env var > local file
TOKEN=""
if [ -n "$GITHUB_TOKEN" ]; then
    TOKEN="$GITHUB_TOKEN"
elif [ -f ".github-token" ]; then
    TOKEN=$(cat .github-token | tr -d '\n\r')
fi

if [ -z "$TOKEN" ]; then
    echo "❌ No token found. Run setup-once.sh first, or set GITHUB_TOKEN env var."
    exit 1
fi

# Commit if there are changes
if [ -n "$(git status --porcelain)" ]; then
    echo "📝 Changes detected, committing..."
    git add .
    MSG="${1:-Update warehouse expense tracker}"
    git commit -m "$MSG"
fi

# Push using token (no prompt)
echo "📤 Pushing to GitHub..."
git push https://santirio18-cmyk:${TOKEN}@github.com/santirio18-cmyk/Warehouse-expense-tracker.git main

echo "✅ Done! GitHub Pages will rebuild in 2-3 minutes."
