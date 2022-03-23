package com.techelevator.items;

public class Candy extends Item {

    public Candy(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String dispense() {
        super.dispense();
        return "Munch Munch, Yum!";
    }
}
