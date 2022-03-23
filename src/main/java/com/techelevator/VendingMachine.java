package com.techelevator;

import com.techelevator.items.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class VendingMachine {
    private final HashMap<String, Item> mapOfItems;
    private final AuditLog auditLog;
    private Customer customer;
    private SalesReport salesReport;

    public VendingMachine(File inputFile, AuditLog auditLog) {
        this.mapOfItems = new LinkedHashMap<>();
        this.auditLog = auditLog;
        this.customer = new Customer(auditLog);

        readCSVFile(inputFile);

        this.salesReport = new SalesReport(this.getMapOfItems());
    }

    private void readCSVFile(File inputFile) {
        try (Scanner readLine = new Scanner(inputFile)){
            while (readLine.hasNext()) {
                String nextLine = readLine.nextLine();
                handleCSVLine(nextLine);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not read file!");
            System.exit(1);
        }
    }

    private void handleCSVLine(String nextLine) {
        String[] lineSplit = nextLine.split("\\|");

        if (lineSplit.length < 4) {
            return;
        }

        String location = lineSplit[0];
        if (!validateLocation(location)) {
            return;
        }

        String itemName = lineSplit[1];
        String itemPriceAsString = lineSplit[2];
        double itemPrice = validatePrice(itemPriceAsString);
        String itemType = lineSplit[3];

        try {
            Item item = validateTypeOfItem(itemName, itemPrice, itemType);
            if (item != null) {
                this.mapOfItems.put(location, item);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private boolean validateLocation(String location) {
        return location.length() == 2;
    }

    private Item validateTypeOfItem(String itemName, double itemPrice, String itemType) {
        switch (itemType) {
            case "Chip":
                return new Chip(itemName, itemPrice, 5);
            case "Candy":
                return new Candy(itemName, itemPrice, 5);
            case "Drink":
                return new Drink(itemName, itemPrice, 5);
            case "Gum":
                return new Gum(itemName, itemPrice, 5);
            default:
                return null;
        }
    }

    private double validatePrice(String priceAsString) {
        try {
            return Double.parseDouble(priceAsString);
        } catch (NumberFormatException e) {
            System.out.println("Price is not a number");
        }

        return -1;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSalesReport(SalesReport salesReport) {
        this.salesReport = new SalesReport(this.getMapOfItems());
    }

    public HashMap<String, Item> getMapOfItems() {
        return mapOfItems;
    }

    public AuditLog getAuditLog() {
        return auditLog;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SalesReport getSalesReport() {
        return salesReport;
    }
}
