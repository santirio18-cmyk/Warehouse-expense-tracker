#!/bin/bash
# One-time setup: store your GitHub token for automated pushes
# Your token stays on your machine only (file is gitignored)

cd "$(dirname "$0")"

echo "🔐 GitHub Token Setup for Auto-Push"
echo ""
echo "1. Go to: https://github.com/settings/tokens"
echo "2. Copy your 'Warehouse Tracker' token (starts with ghp_)"
echo ""
read -sp "3. Paste token here (won't show): " TOKEN
echo ""

if [ -z "$TOKEN" ]; then
    echo "❌ No token entered. Run this script again."
    exit 1
fi

echo "$TOKEN" > .github-token
chmod 600 .github-token
echo ""
echo "✅ Token saved. You can now run ./auto-push.sh anytime to push automatically."
