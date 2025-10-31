# Warehouse Expense Tracker

A comprehensive multi-warehouse expense management system available as:
- 🌐 **Web Application** (`warehouse-expense-tracker.html`) - Full-featured web app accessible from any browser
- 📱 **Android App** - Native mobile app built with Jetpack Compose and Room database

## 🌐 Web Application Features

The web version includes everything from the Android app plus:

### 🏢 Multi-Warehouse Management
- Support for up to 28 warehouses
- Warehouse-specific data isolation
- Master admin can view all warehouses

### 👥 User Management
- Role-based access (Master Admin / Warehouse Manager)
- Employee code tracking
- User creation and management

### 💰 Expense Controls & Security
- **Mandatory Fields**: Vendor name, payment mode, invoice number, invoice document
- **Invoice Tracking**: Upload PDF/JPEG invoices (max 10MB)
- **Fraud Prevention**: 
  - Duplicate invoice detection per warehouse
  - Vendor name validation
  - Date range restrictions (45 days back, 30 days forward)
  - Maximum amount limits (₹10,00,000)
  - Same-day same-vendor fraud detection
- **Employee Code**: Required field for user creation

### ✅ Approval Workflow
- All expenses require approval (no auto-approval)
- Budget remaining calculation displayed during approval
- Visual warnings for over-budget expenses
- Complete approval history tracking

### 📊 Advanced Analytics
- Real-time budget vs expense tracking
- Category-wise breakdown with pie and bar charts
- Date-wise summaries
- Budget visualization by warehouse and month

### 📥 Export & Reporting
- CSV export with all fields
- JSON export
- PDF/Text report generation
- Filtered exports by date and category

## 🚀 Quick Start - Web Version

### Option 1: Direct Access
Simply open `warehouse-expense-tracker.html` in any modern web browser.

### Option 2: Deploy Online (Recommended)
See **DEPLOYMENT_GUIDE.md** for detailed deployment instructions:
- **GitHub Pages** (Free)
- **Netlify** (Free, drag-and-drop)
- **Vercel** (Free)
- Self-hosted servers

Run `./setup-deployment.sh` to prepare for deployment.

### Default Credentials
- **Master Admin**: `admin` / `admin123`
- **Warehouse Managers**: `warehouse_1` to `warehouse_28` / `pass123`

## Features

### 📝 Expense Entry
- Add new expenses with date and time selection
- Categorize expenses (Pooja, Tea, Petrol, Lunch, Travel, Freight, Maintenance, Utilities, Supplies, Other)
- Add optional descriptions
- Real-time validation

### 📋 Expense Management
- View all expenses in a clean list format
- Filter expenses by Daily, Weekly, Monthly, or All
- Delete individual expenses
- Real-time total calculation

### 📊 Analytics & Reporting
- Expense summary with total amounts
- Category-wise breakdown with percentages
- Recent expenses overview
- Period-based filtering (Daily/Weekly/Monthly)

### 🎨 Modern UI
- Material Design 3 components
- Dark/Light theme support
- Intuitive navigation with bottom navigation bar
- Responsive design

## Technical Stack

- **UI**: Jetpack Compose
- **Database**: Room with SQLite
- **Architecture**: MVVM with Repository pattern
- **Navigation**: Navigation Compose
- **State Management**: StateFlow/Flow
- **Date/Time**: Java Time API with custom converters

## App Structure

```
app/src/main/java/com/example/myapplication/
├── data/
│   ├── ExpenseEntity.kt          # Room entity
│   ├── ExpenseDao.kt             # Database access object
│   ├── ExpenseDatabase.kt        # Room database
│   └── Converters.kt             # Type converters for dates
├── repository/
│   └── ExpenseRepository.kt      # Data repository
├── viewmodel/
│   └── ExpenseViewModel.kt       # ViewModel with business logic
├── navigation/
│   └── ExpenseNavigation.kt      # Navigation setup
├── ExpenseEntryScreen.kt         # Add expense screen
├── ExpenseListScreen.kt          # List expenses screen
├── ExpenseSummaryScreen.kt       # Analytics screen
└── MainActivity.kt               # Main activity
```

## Usage

1. **Adding Expenses**: Tap the "Add" tab, fill in the expense details, select date/time, choose category, and tap "Add Expense"

2. **Viewing Expenses**: Use the "List" tab to see all expenses. Use the filter button to switch between Daily, Weekly, Monthly, or All expenses

3. **Analytics**: Check the "Summary" tab for expense analytics, category breakdown, and recent expenses

## Database Schema

The app uses a single table `expenses` with the following structure:
- `id`: Primary key (auto-generated)
- `category`: Expense category (String)
- `amount`: Expense amount (Double)
- `description`: Optional description (String)
- `date`: Date and time of expense (LocalDateTime)
- `createdDate`: Record creation timestamp (LocalDateTime)

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

## Requirements

- Android API 36+
- Kotlin 2.0.21+
- Jetpack Compose BOM 2024.09.00

## Future Enhancements

- Export to CSV/PDF
- Data backup and restore
- Expense categories customization
- Charts and graphs
- Multi-currency support
- Receipt photo attachment



