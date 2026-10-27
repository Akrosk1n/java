
package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {


        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        List<Transaction> transactions;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new URL(filePath).openStream(),
                        StandardCharsets.UTF_8))) {

            transactions = TransactionCSVReader.readTransactions(br);
        }


        double totalBalance =
                TransactionAnalyzer.calculateTotalBalance(transactions);


        String monthYear = "01-2023";

        int count = TransactionAnalyzer.countTransactionsByMonth(
                transactions, monthYear);

        List<Transaction> topExpenses =
                TransactionAnalyzer.findTopExpenses(transactions);

        Transaction largest =
                TransactionAnalyzer.findLargestExpense(
                        transactions, monthYear);

        Transaction smallest =
                TransactionAnalyzer.findSmallestExpense(
                        transactions, monthYear);

        TransactionReportGenerator.printBalanceReport(totalBalance);

        TransactionReportGenerator.printTransactionsCountByMonth(
                monthYear, count);

        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        TransactionReportGenerator.printExpenseExtremesReport(
                monthYear, largest, smallest);

        TransactionReportGenerator.printExpensesByCategoryAndMonth(
                transactions);
    }
}
