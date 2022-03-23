package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class UserInterfaceTest {
    public VendingMachine vendingMachine;
    public UserInterface ui;

    @Before
    public void setup() {
        Scanner input = new Scanner(System.in);

        String filePath = "./vendingmachine.csv";
        File inputFile = new File(filePath);

        AuditLog auditLog = new AuditLog();

        vendingMachine = new VendingMachine(inputFile, auditLog);
        ui = new UserInterface(input, vendingMachine);
    }




    @Test
    public void ui_test() {
        Assert.assertTrue(ui.displayVendingMachineItems(vendingMachine.getMapOfItems()));
    }

    @Test
    public void user_enters_10_dollars() {
        boolean fm = ui.feedMoney("4");
        Assert.assertTrue(fm);
    }

    @Test
    public void verify_customer_balance() {
        Customer customer = vendingMachine.getCustomer();
        boolean fm = ui.feedMoney("4");
        double customerBalance = customer.getMoney();
        double expectedBalance = 10.00;
        Assert.assertEquals(expectedBalance, customerBalance, 0);
    }

    @Test
    public void verify_customer_purchase_balance() {
        Customer customer = vendingMachine.getCustomer();
        boolean fm = ui.feedMoney("4");
        double customerBalance = customer.getMoney();
        double expectedBalance = 9.25;
        boolean sp = ui.selectProduct("D3");
        Assert.assertEquals(expectedBalance, customerBalance,1);

    }

    @Test
    public void user_enters_invalid_money() {
        boolean fm = ui.feedMoney("5");
        Assert.assertFalse(fm);
    }

    @Test
    public void user_selects_A4() {
        boolean sp = ui.selectProduct("A4");
        Assert.assertTrue(sp);
    }
    @Test
    public void user_selects_z19() {
        boolean sp = ui.selectProduct("z19");
        Assert.assertFalse(sp);
    }


}
