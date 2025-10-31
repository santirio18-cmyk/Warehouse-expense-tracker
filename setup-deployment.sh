#!/bin/bash

echo "🚀 Warehouse Expense Tracker - Deployment Setup"
echo "================================================"
echo ""

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "❌ Git is not installed. Please install Git first."
    exit 1
fi

echo "✅ Git is installed"
echo ""

# Initialize git repository if not already initialized
if [ ! -d ".git" ]; then
    echo "Initializing git repository..."
    git init
    echo "✅ Git repository initialized"
else
    echo "✅ Git repository already exists"
fi

# Check if .gitignore exists
if [ ! -f ".gitignore" ]; then
    echo "Creating .gitignore..."
    cat > .gitignore << EOF
# Android Studio
.gradle/
build/
*.apk
*.ap_
*.aab
local.properties
.DS_Store
.idea/
*.iml
*.hprof
.navigation/
captures/
output.json

# Keep HTML files
!*.html
!index.html
EOF
    echo "✅ .gitignore created"
else
    echo "✅ .gitignore already exists"
fi

echo ""
echo "📝 Next Steps:"
echo "=============="
echo ""
echo "1. Review the DEPLOYMENT_GUIDE.md for detailed instructions"
echo ""
echo "2. For GitHub Pages deployment:"
echo "   - Create a new repository on GitHub"
echo "   - Run these commands:"
echo "     git add warehouse-expense-tracker.html index.html"
echo "     git commit -m 'Initial commit: Warehouse Expense Tracker'"
echo "     git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git"
echo "     git push -u origin main"
echo ""
echo "3. For Netlify deployment:"
echo "   - Go to https://www.netlify.com"
echo "   - Drag and drop warehouse-expense-tracker.html"
echo ""
echo "4. For Vercel deployment:"
echo "   - Install Vercel CLI: npm install -g vercel"
echo "   - Run: vercel"
echo ""
echo "✨ Your expense tracker is ready to deploy!"
echo ""

