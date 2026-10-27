package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionReportGenerator {

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(
            String monthYear, int count) {

        System.out.println(
                "Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(
            List<Transaction> topExpenses) {

        System.out.println("\n10 найбільших витрат:");

        for (Transaction t : topExpenses) {
            System.out.println(
                    t.getDescription() + ": " + t.getAmount());
        }
    }

    public static void printExpenseExtremesReport(
            String monthYear,
            Transaction largest,
            Transaction smallest) {

        System.out.println("\nВитрати за " + monthYear + ":");

        if (largest == null) {
            System.out.println("Витрат за цей місяць немає.");
            return;
        }

        System.out.println("Найбільша витрата: "
                + largest.getDescription() + " — "
                + Math.abs(largest.getAmount()) + " грн");

        System.out.println("Найменша витрата: "
                + smallest.getDescription() + " — "
                + Math.abs(smallest.getAmount()) + " грн");
    }

    public static void printExpensesByCategoryAndMonth(
            List<Transaction> transactions) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM-yyyy");

        Map<String, Double> totals = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> t.getDescription() + " | "
                                + LocalDate.parse(
                                        t.getDate(),
                                        DateTimeFormatter.ofPattern(
                                                "dd-MM-yyyy"))
                                .format(formatter),
                        Collectors.summingDouble(
                                t -> Math.abs(t.getAmount()))
                ));

        System.out.println("\nВитрати за категоріями та місяцями:");
        System.out.println("(* = 1000 грн)");

        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            int stars = (int) (entry.getValue() / 1000);

            System.out.println(
                    entry.getKey() + ": "
                            + entry.getValue() + " грн "
                            + "*".repeat(stars));
        }
    }
}