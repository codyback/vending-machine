package com.techelevator;

import com.techelevator.items.Item;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private final Scanner input;
    private final VendingMachine vm;

    private final String[] MAIN_MENU_PROMPTS;
    private final String[] PURCHASE_MENU_PROMPTS;
    private final String[] DENOMINATION_PROMPTS;

    public UserInterface(Scanner input, VendingMachine vm) {
        this.input = input;
        this.vm = vm;

        MAIN_MENU_PROMPTS = new String[] {"Display Vending Machine Items", "Purchase", "Exit"};
        PURCHASE_MENU_PROMPTS = new String[] {"Feed Money", "Select Product", "Finish Transaction"};
        DENOMINATION_PROMPTS = new String[] {"$1.00", "$2.00", "$5.00", "$10.00"};
    }

    public void mainMenu() {
        while (true) {
            this.promptUser(MAIN_MENU_PROMPTS);

            String userInput = input.nextLine();
            switch (userInput) {
                case "1":
                    displayVendingMachineItems(vm.getMapOfItems());
                    break;
                case "2":
                    this.purchaseMenu();
                    break;
                case "3":
                    System.exit(1);
                    break;
                case "4":
                    vm.getSalesReport().createNewSalesReport();
                    break;
                default:
                    System.out.println("Not an option!\n");
            }
        }
    }

    public void purchaseMenu() {
        boolean correctSelection = false;
        while (!correctSelection) {
            System.out.println();
            this.promptUser(PURCHASE_MENU_PROMPTS);

            System.out.println("\nCurrent money provided: " + this.vm.getCustomer().getMoneyAsCurrency());

            String userInput = input.nextLine();
            switch (userInput) {
                case "1":
                    this.feedMoneyInput();
                    break;
                case "2":
                    this.selectProductInput();
                    break;
                case "3":
                    resetCustomer();
                    correctSelection = true;
                    break;
            }
        }
    }

    public void feedMoneyInput() {
        boolean correctChoice = false;
        while (!correctChoice) {
            System.out.println();
            this.promptUser(DENOMINATION_PROMPTS);

            String userInput = input.nextLine();

            correctChoice = feedMoney(userInput);
        }
    }

    public boolean feedMoney(String userInput) {
        HashMap<String, Double> feedAmount = new HashMap<>();
        feedAmount.put("1", 1.0);
        feedAmount.put("2", 2.0);
        feedAmount.put("3", 5.0);
        feedAmount.put("4", 10.0);

        if (feedAmount.containsKey(userInput)) {
            this.vm.getCustomer().feedMoney(feedAmount.get(userInput));
            return true;
        } else {
            System.out.println("Invalid choice! Must select 1, 2, 3 or 4\n");
            return false;
        }
    }

    public void selectProductInput() {
        boolean selectedProduct = false;
        while (!selectedProduct) {
            if (!displayVendingMachineItems(vm.getMapOfItems())) {
                System.out.println("Vending Machine is EMPTY!");
                selectedProduct = true;
                continue;
            }

            System.out.print("\nPlease select an item >>> ");
            String userInput = input.nextLine().trim().toUpperCase();

           selectedProduct = selectProduct(userInput);
        }
    }

    public boolean selectProduct(String userInput) {
        if (vm.getMapOfItems().containsKey(userInput)) {
            Item item = vm.getMapOfItems().get(userInput);

            if (this.vm.getCustomer().buyItem(item, userInput)) {
                System.out.println(item.getName() + " - " + item.getPriceAsCurrency() + "\n");
                System.out.println(item.dispense() + "\n");
                vm.getSalesReport().addToItemsSold(item);
            }

            return true;
        } else {
            System.out.println("Product code does not exist! Try again...\n");
        }

        return false;
    }

    public void resetCustomer() {
        System.out.println();
        HashMap<String, Integer> coins = this.vm.getCustomer().getChange();
        for (String coin : coins.keySet()) {
            System.out.printf("%10s : %-3s\n", coin, coins.get(coin));
        }

        System.out.println();
        this.vm.setCustomer(new Customer(vm.getAuditLog()));
    }

    public void promptUser(String[] prompts) {
        System.out.println("Please select an item:");
        for (int i = 0; i < prompts.length; i++) {
            System.out.println("(" + (i + 1) + ") " + prompts[i]);
        }
    }

    public boolean displayVendingMachineItems(HashMap<String, Item> mapOfItems) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%9s | %-30s %8s | %-12s\n","LOCATION", "NAME", "PRICE", "QUANTITY");
        System.out.println("---------------------------------------------------------------");
        boolean atLeastOneItem = false;
        for (String location : mapOfItems.keySet()) {
            Item item = mapOfItems.get(location);
            String generalItemString = String.format("%9s | %-30s %8s", location, item.getName(), item.getPriceAsCurrency());
            if (item.isSoldOut()) {
                System.out.println(generalItemString + " | SOLD OUT");
            } else {
                System.out.println(generalItemString + " | " + item.getQuantity());
                atLeastOneItem = true;
            }
        }
        System.out.println("---------------------------------------------------------------");

        return atLeastOneItem;
    }

}
