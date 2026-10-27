package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    public static List<Transaction> readTransactions(BufferedReader br)
            throws IOException {

        List<Transaction> transactions = new ArrayList<>();
        String line;

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            String date = data[0].trim();
            double amount = Double.parseDouble(data[1].trim());
            String description = data[2].trim();

            transactions.add(new Transaction(date, amount, description));
        }

        return transactions;
    }
}