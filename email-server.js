/**
 * Gmail Email Server for SCM Expense Tracker
 * 
 * This server uses Gmail SMTP to send emails directly from scm.dispatch@tvs.in
 * 
 * Setup:
 * 1. Install dependencies: npm install express nodemailer cors dotenv
 * 2. Create .env file with Gmail credentials
 * 3. Run: node email-server.js
 * 
 * Deploy to: Heroku, Railway, Render, or any Node.js hosting
 */

const express = require('express');
const nodemailer = require('nodemailer');
const cors = require('cors');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Gmail SMTP Configuration
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.GMAIL_USER, // scm.dispatch@tvs.in
        pass: process.env.GMAIL_APP_PASSWORD // Gmail App Password (16 characters)
    }
});

// Verify Gmail connection
transporter.verify((error, success) => {
    if (error) {
        console.error('❌ Gmail connection error:', error);
    } else {
        console.log('✅ Gmail server is ready to send emails');
    }
});

// Health check endpoint
app.get('/', (req, res) => {
    res.json({ 
        status: 'ok', 
        message: 'Gmail Email Server is running',
        service: 'SCM Expense Tracker Email Service'
    });
});

// Send User Creation Email
app.post('/api/send-user-email', async (req, res) => {
    try {
        const { to_email, user_name, username, password, role } = req.body;

        if (!to_email || !user_name || !username || !password || !role) {
            return res.status(400).json({ error: 'Missing required fields' });
        }

        const mailOptions = {
            from: {
                name: 'SCM Expense Management',
                address: process.env.GMAIL_USER
            },
            to: to_email,
            subject: `Welcome to SCM Petty Cash Expense Management System - Your Account Has Been Created`,
            html: `
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #003366;">Welcome to SCM Petty Cash Expense Management System</h2>
                    <p>Hello ${user_name},</p>
                    <p>Your account has been successfully created in the SCM Petty Cash Expense Management System.</p>
                    <div style="background: #f5f5f5; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #003366;">Account Details:</h3>
                        <ul style="line-height: 1.8;">
                            <li><strong>Username:</strong> ${username}</li>
                            <li><strong>Password:</strong> ${password}</li>
                            <li><strong>Role:</strong> ${role}</li>
                        </ul>
                    </div>
                    <p><strong>Please log in and change your password for security.</strong></p>
                    <p>Best regards,<br>SCM Expense Management Team</p>
                </div>
            `
        };

        const info = await transporter.sendMail(mailOptions);
        console.log('✅ User creation email sent:', info.messageId);
        res.json({ success: true, messageId: info.messageId });
    } catch (error) {
        console.error('❌ Error sending user creation email:', error);
        res.status(500).json({ error: 'Failed to send email', details: error.message });
    }
});

// Send Expense Submission Email
app.post('/api/send-expense-submission-email', async (req, res) => {
    try {
        const { 
            to_emails, // Array of approver emails
            submitter_name, 
            submitter_email,
            expense_amount,
            expense_category,
            expense_vendor,
            expense_warehouse,
            expense_date,
            expense_invoice
        } = req.body;

        if (!to_emails || !Array.isArray(to_emails) || to_emails.length === 0) {
            return res.status(400).json({ error: 'Missing approver emails' });
        }

        const mailOptions = {
            from: {
                name: 'SCM Expense Management',
                address: process.env.GMAIL_USER
            },
            to: to_emails.join(','), // Send to all approvers
            subject: `New Expense Submitted - ₹${expense_amount} - ${expense_category}`,
            html: `
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #003366;">New Expense Submission</h2>
                    <p>Hello,</p>
                    <p>A new expense has been submitted and requires your approval.</p>
                    <div style="background: #f5f5f5; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #003366;">Expense Details:</h3>
                        <ul style="line-height: 1.8;">
                            <li><strong>Amount:</strong> ₹${expense_amount}</li>
                            <li><strong>Category:</strong> ${expense_category}</li>
                            <li><strong>Vendor:</strong> ${expense_vendor}</li>
                            <li><strong>Warehouse:</strong> ${expense_warehouse}</li>
                            <li><strong>Date:</strong> ${expense_date}</li>
                            <li><strong>Invoice Number:</strong> ${expense_invoice}</li>
                            <li><strong>Submitted by:</strong> ${submitter_name} (${submitter_email})</li>
                        </ul>
                    </div>
                    <p><strong>Please log in to review and approve/reject this expense.</strong></p>
                    <p>Best regards,<br>SCM Expense Management</p>
                </div>
            `
        };

        const info = await transporter.sendMail(mailOptions);
        console.log('✅ Expense submission email sent:', info.messageId);
        res.json({ success: true, messageId: info.messageId });
    } catch (error) {
        console.error('❌ Error sending expense submission email:', error);
        res.status(500).json({ error: 'Failed to send email', details: error.message });
    }
});

// Send Approval/Rejection Email
app.post('/api/send-approval-email', async (req, res) => {
    try {
        const { 
            to_email,
            submitter_name,
            approver_name,
            action, // 'approved' or 'rejected'
            expense_amount,
            expense_category,
            expense_vendor,
            expense_warehouse,
            expense_date,
            expense_invoice
        } = req.body;

        if (!to_email || !submitter_name || !action) {
            return res.status(400).json({ error: 'Missing required fields' });
        }

        // Handle different approval actions
        let actionText, actionColor, actionMessage;
        if (action === 'first_approved') {
            actionText = 'First Level Approved ✅';
            actionColor = '#17a2b8';
            actionMessage = `Your expense has been approved at the first level. It is now pending final approval from ${req.body.second_level_approver || 'the final approver'}.`;
        } else if (action === 'approved') {
            actionText = 'Approved ✅';
            actionColor = '#28a745';
            actionMessage = 'Your expense has been approved!';
        } else {
            actionText = 'Rejected ❌';
            actionColor = '#dc3545';
            actionMessage = 'Your expense has been rejected. Please review and resubmit if needed.';
        }

        const mailOptions = {
            from: {
                name: 'SCM Expense Management',
                address: process.env.GMAIL_USER
            },
            to: to_email,
            subject: `Expense ${action === 'first_approved' ? 'First Level Approved' : action} - ₹${expense_amount} - ${expense_category}`,
            html: `
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #003366;">Expense ${actionText}</h2>
                    <p>Hello ${submitter_name},</p>
                    <p>Your expense submission has been <strong style="color: ${actionColor};">${action === 'first_approved' ? 'first level approved' : action}</strong>.</p>
                    <div style="background: #f5f5f5; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <h3 style="margin-top: 0; color: #003366;">Expense Details:</h3>
                        <ul style="line-height: 1.8;">
                            <li><strong>Amount:</strong> ₹${expense_amount}</li>
                            <li><strong>Category:</strong> ${expense_category}</li>
                            <li><strong>Vendor:</strong> ${expense_vendor}</li>
                            <li><strong>Warehouse:</strong> ${expense_warehouse}</li>
                            <li><strong>Date:</strong> ${expense_date}</li>
                            <li><strong>Invoice Number:</strong> ${expense_invoice}</li>
                            <li><strong>Status:</strong> <strong style="color: ${actionColor};">${action === 'first_approved' ? 'First Level Approved' : action}</strong></li>
                            <li><strong>Reviewed by:</strong> ${approver_name}</li>
                        </ul>
                    </div>
                    <div style="background: ${action === 'approved' ? '#d4edda' : action === 'first_approved' ? '#d1ecf1' : '#f8d7da'}; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid ${actionColor};">
                        <p style="margin: 0; font-weight: bold; color: ${action === 'approved' ? '#155724' : action === 'first_approved' ? '#0c5460' : '#721c24'};">
                            ${actionMessage}
                        </p>
                    </div>
                    <p>Best regards,<br>SCM Expense Management</p>
                </div>
            `
        };

        const info = await transporter.sendMail(mailOptions);
        console.log(`✅ ${action} email sent:`, info.messageId);
        res.json({ success: true, messageId: info.messageId });
    } catch (error) {
        console.error(`❌ Error sending ${action} email:`, error);
        res.status(500).json({ error: 'Failed to send email', details: error.message });
    }
});

// Start server
app.listen(PORT, () => {
    console.log(`🚀 Gmail Email Server running on port ${PORT}`);
    console.log(`📧 Gmail User: ${process.env.GMAIL_USER || 'Not configured'}`);
});


