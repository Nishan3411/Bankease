package models;

public class BankAccount {
    private int accountNumber;
    private int userId;
    private double balance;
    private String status;

    public BankAccount(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
        this.status = "ACTIVE";
    }

    public BankAccount() {}

    // Getters and setters
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    public int getUserId() { return userId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}