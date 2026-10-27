package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static double calculateTotalBalance(
            List<Transaction> transactions) {

        double balance = 0;

        for (Transaction t : transactions) {
            balance += t.getAmount();
        }

        return balance;
    }

    public static int countTransactionsByMonth(
            List<Transaction> transactions, String monthYear) {

        int count = 0;

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(
                    t.getDate(), DATE_FORMAT);

            if (date.format(DateTimeFormatter.ofPattern("MM-yyyy"))
                    .equals(monthYear)) {
                count++;
            }
        }

        return count;
    }

    public static List<Transaction> findTopExpenses(
            List<Transaction> transactions) {

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Transaction findLargestExpense(
            List<Transaction> transactions, String monthYear) {

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> getMonthYear(t).equals(monthYear))
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findSmallestExpense(
            List<Transaction> transactions, String monthYear) {

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> getMonthYear(t).equals(monthYear))
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    private static String getMonthYear(Transaction t) {
        LocalDate date = LocalDate.parse(
                t.getDate(), DATE_FORMAT);

        return date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
    }
}