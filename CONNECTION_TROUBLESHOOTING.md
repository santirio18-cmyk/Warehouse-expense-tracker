# "Server is Not Running" / Connection Errors

## For users like Ashok getting connection errors

### 1. Use the correct app URL
The app is hosted on GitHub Pages. Use:
```
https://santirio18-cmyk.github.io/Warehouse-expense-tracker/warehouse-expense-tracker.html
```
**Do NOT** use `file://` or `localhost` – the app needs to run from a web server.

### 2. Check internet connection
- Ensure you have working internet
- Try opening the URL in a different browser
- If on office WiFi, try **mobile data** – corporate firewalls often block Firebase

### 3. Firebase may be blocked
The app uses Firebase (Google). Some networks block:
- `firebase.google.com`
- `firestore.googleapis.com`

**Fix:** Use mobile data, home WiFi, or ask IT to allow these domains.

### 4. Clear cache and retry
- Clear browser cache (Ctrl+Shift+Delete)
- Or try in Incognito/Private mode
- Reload the page

### 5. Browser support
Use Chrome, Edge, Safari, or Firefox (latest version).
