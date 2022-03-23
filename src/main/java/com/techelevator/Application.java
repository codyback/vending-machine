package com.techelevator;

import java.io.File;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		String filePath = "./vendingmachine.csv";
		File inputFile = new File(filePath);

		AuditLog auditLog = new AuditLog();

		VendingMachine vendingMachine = new VendingMachine(inputFile, auditLog);
		UserInterface ui = new UserInterface(input, vendingMachine);


		ui.mainMenu();
	}
}
