const functions = require('firebase-functions');
const { Resend } = require('resend');

exports.sendExpenseEmail = functions.https.onCall(async (data, context) => {
  const { to, subject, html } = data;

  if (!to || !subject || !html) {
    throw new functions.https.HttpsError('invalid-argument', 'Missing required fields: to, subject, html');
  }

  const apiKey = functions.config().resend?.api_key || process.env.RESEND_API_KEY;
  if (!apiKey) {
    throw new functions.https.HttpsError('failed-precondition', 'Resend API key not configured. Run: firebase functions:config:set resend.api_key="re_your_key"');
  }

  const resend = new Resend(apiKey);

  try {
    const { data: result, error } = await resend.emails.send({
      from: 'Warehouse Expense Tracker <onboarding@resend.dev>',
      to: [to],
      subject: subject,
      html: html,
    });

    if (error) {
      console.error('Resend error:', error);
      throw new functions.https.HttpsError('internal', error.message);
    }

    return { success: true, id: result?.id };
  } catch (err) {
    console.error('Email send error:', err);
    throw new functions.https.HttpsError('internal', err.message || 'Failed to send email');
  }
});
