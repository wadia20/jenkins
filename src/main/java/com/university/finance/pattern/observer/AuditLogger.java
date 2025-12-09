package com.university.finance.pattern.observer;

import com.university.finance.model.Transaction;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class AuditLogger implements TransactionObserver {
    private static final Logger logger = Logger.getLogger(AuditLogger.class.getName());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void update(Transaction transaction) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00", java.text.DecimalFormatSymbols.getInstance(java.util.Locale.US));

        String logMessage = String.format(
                "[AUDIT] %s - User: %s, Type: %s, Amount: %s, Description: %s",
                dateFormat.format(transaction.getTimestamp()),
                transaction.getUserId(),
                transaction.getType(),
                df.format(transaction.getAmount()),
                transaction.getDescription()
        );

        logger.info(logMessage);
        System.out.println(logMessage);
    }

}