package com.techelevator;

import com.techelevator.items.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SalesReport {
    HashMap<Item, Integer> itemsSold;

    public SalesReport(HashMap<String, Item> mapOfItems) {
        itemsSold = fillItemsSoldFromVM(mapOfItems);
    }

    private HashMap<Item, Integer> fillItemsSoldFromVM(HashMap<String, Item> mapOfItems) {
        HashMap<Item, Integer> itemsSold = new LinkedHashMap<>();

        for (String key : mapOfItems.keySet()) {
            itemsSold.put(mapOfItems.get(key), 0);
        }

        return itemsSold;
    }

    public void addToItemsSold(Item item) {
        int numberSold = itemsSold.get(item);
        numberSold += 1;

        itemsSold.put(item, numberSold);
    }

    private String formatAsCurrency(double amount) {
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        return priceFormat.format(amount);
    }

    public void createNewSalesReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy-HHmmssn");
        String filePath = LocalDateTime.now().format(formatter);
        File salesReportFile = new File(filePath);

        try {
            salesReportFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        double totalAmount = 0;

        try (PrintWriter pw = new PrintWriter(new FileWriter(salesReportFile, true))) {
            for (Item item : itemsSold.keySet()) {
                int numberSold = itemsSold.get(item);
                totalAmount += item.getPrice() * numberSold;
                pw.println(item.getName() + "|" + numberSold);
            }
            pw.println();
            pw.println("Total Sales Amount: " + this.formatAsCurrency(totalAmount));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}
