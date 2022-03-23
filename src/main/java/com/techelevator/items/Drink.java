package com.techelevator.items;

public class Drink extends Item {

    public Drink(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String dispense() {
        super.dispense();
        return "Glug Glug, Yum!";
    }
}
