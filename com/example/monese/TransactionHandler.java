package com.example.monese;

import java.util.ArrayList;
import java.util.List;

import com.example.monese.databaseRows.CustomerRecord;
import com.example.monese.databaseRows.TransactionRecord;

public class TransactionHandler {
    public void createTransaction(Integer originId, Integer destinationId, float value) throws Exception {
	if (!DatabaseAccess.customerExists(destinationId)) {
	    throw new Exception("Destination customer id does not exist: " + destinationId);
	}
	if (!DatabaseAccess.customerExists(originId)) {
	    throw new Exception("Origin customer id does not exist: " + originId);
	}
	if (DatabaseAccess.getBalance(originId) < value) {
	    throw new Exception("Transaction value is higher than customer balance");
	}
	TransactionRecord transaction = DatabaseAccess.insertTransaction(originId, destinationId, value);
	DatabaseAccess.reduceBalance(originId, value);
	DatabaseAccess.increaseBalance(destinationId, value);

	CustomerRecord originCustomer = DatabaseAccess.getCustomer(originId);
	originCustomer.addTransaction(transaction);

	CustomerRecord destinationCustomer = DatabaseAccess.getCustomer(destinationId);
	destinationCustomer.addTransaction(transaction);
    }

    public List<TransactionRecord> getCustomerTransactions(Integer customerId) {
	if (!DatabaseAccess.customerExists(customerId)) {
	    System.out.println("Customer id " + customerId + " does not exist");
	    return null;
	}
	CustomerRecord customer = DatabaseAccess.getCustomer(customerId);
	ArrayList<TransactionRecord> transactions = new ArrayList<>();
	for (Integer transactionId : customer.getTransactions()) {
	    TransactionRecord transaction = DatabaseAccess.getTransaction(transactionId);
	    transactions.add(transaction);
	}
	return transactions;
    }
}
