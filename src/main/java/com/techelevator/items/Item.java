package com.techelevator.items;

import java.text.NumberFormat;

public abstract class Item {
    private final String name;
    private final double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean isSoldOut() {
        return this.quantity == 0;
    }

    public String getPriceAsCurrency() {
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        return priceFormat.format(this.price);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String dispense() {
        this.setQuantity(this.quantity - 1);
        return "";
    }
}
