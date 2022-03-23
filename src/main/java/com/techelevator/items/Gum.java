package com.techelevator.items;

public class Gum extends Item {

    public Gum(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String dispense() {
        super.dispense();
        return "Chew Chew, Yum!";
    }
}
