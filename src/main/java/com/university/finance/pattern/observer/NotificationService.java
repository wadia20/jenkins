package com.university.finance.pattern.observer;

import com.university.finance.model.Transaction;
import java.text.SimpleDateFormat;

public class NotificationService implements TransactionObserver {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void update(Transaction transaction) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00", java.text.DecimalFormatSymbols.getInstance(java.util.Locale.US));

        String notification = String.format(
                "NOTIFICATION: Transaction effectuée le %s\n" +
                        "Type: %s\n" +
                        "Montant: %s\n" +   // plus de symbole € pour correspondre au test
                        "Description: %s\n",
                dateFormat.format(transaction.getTimestamp()),
                transaction.getType(),
                df.format(transaction.getAmount()),  // format 2 décimales
                transaction.getDescription()
        );

        System.out.println("=== NOTIFICATION PAR EMAIL ===");
        System.out.println(notification);
        System.out.println("==============================");
    }

}