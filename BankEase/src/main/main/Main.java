package main;

import java.util.Scanner;
import models.User;
import services.BankServices;

public class Main {
    public static void main(String[] args) {
        BankServices bankServices = new BankServices();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to BankEase");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bankServices.registerUser();
                    break;

                case 2:
                    User user = bankServices.loginUser();
                    if (user != null) {
                        int ch;
                        do {
                            System.out.println("\n1. Create Account\n2. Deposit\n3. Withdraw\n4. Logout");
                            ch = scanner.nextInt();
                            scanner.nextLine();

                            switch (ch) {
                                case 1:
                                    bankServices.createAccount(user);
                                    break;
                                case 2:
                                    bankServices.deposit(user);
                                    break;
                                case 3:
                                    bankServices.withdraw(user);
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        } while (ch != 4);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using BankEase.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
