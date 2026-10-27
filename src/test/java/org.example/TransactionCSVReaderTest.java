package org.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionCSVReaderTest {

    @Test
    public void testReadTransactions() throws Exception {

        String csv = "date,amount,description\n"
                + "01-02-2023,50.0,Dohid\n"
                + "15-02-2023,-20.0,Vytrata\n";

        BufferedReader reader = new BufferedReader(
                new StringReader(csv));

        List<Transaction> transactions =
                TransactionCSVReader.readTransactions(reader);

        assertEquals(2, transactions.size());
        assertEquals("01-02-2023",
                transactions.get(0).getDate());
        assertEquals(50.0,
                transactions.get(0).getAmount());
        assertEquals("Vytrata",
                transactions.get(1).getDescription());
    }
}