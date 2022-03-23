package com.techelevator;

import com.techelevator.items.Item;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Customer {
    private double money;
    private final AuditLog auditLog;

    public Customer(AuditLog auditLog) {
        this.money = 0;
        this.auditLog = auditLog;
    }

    public String getMoneyAsCurrency() {
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        return priceFormat.format(this.getMoney());
    }

    public boolean hasEnoughMoney(double price) {
        return this.money >= price;
    }

    public boolean buyItem(Item item, String location) {
        if (item.isSoldOut()) {
            System.out.println("Item is SOLD OUT!");
            return false;
        }

        if (this.hasEnoughMoney(item.getPrice())) {
            String previousMoney = getMoneyAsCurrency();
            this.money -= item.getPrice();

            String itemNameAndLocation = item.getName() + " " + location;
            this.formatAuditAndWrite(itemNameAndLocation, previousMoney);

            return true;
        } else {
            System.out.println("Not enough money!\n");
            return false;
        }
    }

    public HashMap<String, Integer> getChange() {
        HashMap<String, Integer> coins = new LinkedHashMap<>();

        String previousMoney = getMoneyAsCurrency();
        int changeDue = (int) Math.ceil(this.money * 100);

        int quarters = (int) Math.floor(changeDue / 25);
        changeDue = changeDue % 25;

        int dimes = (int) Math.floor(changeDue / 10);
        changeDue = changeDue % 10;

        int nickels = (int) Math.floor(changeDue / 5);

        this.money = 0;

        this.formatAuditAndWrite("GIVE CHANGE:", previousMoney);

        coins.put("Quarters", quarters);
        coins.put("Dimes", dimes);
        coins.put("Nickels", nickels);

        return coins;
    }

    public void feedMoney(double amount) {
        String previous = this.getMoneyAsCurrency();
        this.money += amount;

        this.formatAuditAndWrite("FEED MONEY:", previous);
    }

    private void formatAuditAndWrite(String typeOfAudit, String previousMoney) {
        String audit = typeOfAudit + " " + previousMoney + " " + this.getMoneyAsCurrency();
        auditLog.writeAudit(audit);
    }

    public double getMoney() {
        return money;
    }
}
