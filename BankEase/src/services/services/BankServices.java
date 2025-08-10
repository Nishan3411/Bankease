package services;

import database.DBConnection;
import exceptions.*;
import java.sql.*;
import java.util.Scanner;
import models.User;

public class BankServices {
    private Scanner scanner = new Scanner(System.in);

    public void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, false)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loginUser() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                return user;
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createAccount(User user) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts (user_id, balance) VALUES (?, 0.0)");
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            System.out.println("Bank account created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deposit(User user) {
        System.out.print("Enter account number: ");
        int acc = scanner.nextInt();
        System.out.print("Amount to deposit: ");
        double amt = scanner.nextDouble();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ? AND user_id = ?");
            stmt.setDouble(1, amt);
            stmt.setInt(2, acc);
            stmt.setInt(3, user.getId());
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                PreparedStatement txn = conn.prepareStatement("INSERT INTO transactions (account_number, type, amount) VALUES (?, 'DEPOSIT', ?)");
                txn.setInt(1, acc);
                txn.setDouble(2, amt);
                txn.executeUpdate();
                conn.commit();
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Account not found or unauthorized.");
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(User user) {
        System.out.print("Enter account number: ");
        int acc = scanner.nextInt();
        System.out.print("Amount to withdraw: ");
        double amt = scanner.nextDouble();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement check = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number=? AND user_id=?");
            check.setInt(1, acc);
            check.setInt(2, user.getId());
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getDouble(1) >= amt) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                stmt.setDouble(1, amt);
                stmt.setInt(2, acc);
                stmt.executeUpdate();

                PreparedStatement txn = conn.prepareStatement("INSERT INTO transactions (account_number, type, amount) VALUES (?, 'WITHDRAW', ?)");
                txn.setInt(1, acc);
                txn.setDouble(2, amt);
                txn.executeUpdate();

                conn.commit();
                System.out.println("Withdrawal successful.");
            } else {
                throw new InsufficientFundsException("Not enough balance.");
            }
        } catch (SQLException | InsufficientFundsException e) {
            e.printStackTrace();
        }
    }
}
