package com.techelevator.items;

public class Chip extends Item {

    public Chip(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String dispense() {
        super.dispense();
        return "Crunch Crunch, Yum!";
    }
}
