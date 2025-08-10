package models;

import java.time.LocalDateTime;

    public class Transaction {
        private int transactionId;
        private int accountNumber;
        private String type; // e.g., "deposit", "withdrawal"
        private double amount;
        private LocalDateTime timestamp;

        public Transaction(int transactionId, int accountNumber, String type, double amount, LocalDateTime timestamp) {
            this.transactionId = transactionId;
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public int getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(int accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
}
