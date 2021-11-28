package com.example.monese.databaseRows;

import java.util.ArrayList;

public class CustomerRecord {
    private Integer customerId;
    private float balance;
    private ArrayList<Integer> transactions = new ArrayList<>();

    public CustomerRecord(Integer customerId, float balance) {
	this.setCustomerId(customerId);
	this.balance = balance;
    }

    public float getBalance() {
	return balance;
    }

    public void setBalance(float balance) {
	this.balance = balance;
    }

    public void addTransaction(TransactionRecord transaction) {
	transactions.add(transaction.getTransactionId());
    }

    public Integer getCustomerId() {
	return customerId;
    }

    public void setCustomerId(Integer customerId) {
	this.customerId = customerId;
    }

    public ArrayList<Integer> getTransactions() {
	return transactions;
    }

    public void setTransactions(ArrayList<Integer> transactions) {
	this.transactions = transactions;
    }

    @Override
    public String toString() {
	return "CustomerRecord [customerId=" + customerId + ", balance=" + balance + ", transactions=" + transactions
		+ "]";
    }
}
