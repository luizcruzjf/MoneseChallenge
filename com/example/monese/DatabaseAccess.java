package com.example.monese;

import java.util.HashMap;
import java.util.Map;

import com.example.monese.databaseRows.CustomerRecord;
import com.example.monese.databaseRows.TransactionRecord;

/*
 * Dummy database class, used to simulate database queries, with records stored as maps instead of rows in a table.
 */
public class DatabaseAccess {

    private static Map<Integer, CustomerRecord> customers = new HashMap<Integer, CustomerRecord>();
    private static Map<Integer, TransactionRecord> transactions = new HashMap<Integer, TransactionRecord>();

    private static int nextTransactionId = 0;
    private static int nextCustomerId = 0;

    public static boolean customerExists(Integer customerId) {
	if (customers.containsKey(customerId)) {
	    return true;
	}
	return false;
    }

    public static float getBalance(Integer originId) {
	return customers.get(originId).getBalance();
    }

    public static void reduceBalance(Integer originId, float value) {
	CustomerRecord customerRecord = customers.get(originId);
	float balance = customerRecord.getBalance();
	balance = balance - value;
	customerRecord.setBalance(balance);
    }

    public static void increaseBalance(Integer originId, float value) {
	CustomerRecord customerRecord = customers.get(originId);
	float balance = customerRecord.getBalance();
	balance = balance + value;
	customerRecord.setBalance(balance);
    }

    public static CustomerRecord insertCustomer(float balance) {
	int customerId = nextCustomerId++;
	CustomerRecord newCustomer = new CustomerRecord(customerId, balance);
	customers.put(customerId, newCustomer);
	return newCustomer;
    }

    public static TransactionRecord insertTransaction(Integer originId, Integer destinationId, float value) {
	int transactionId = nextTransactionId++;
	TransactionRecord newTransaction = new TransactionRecord(transactionId, originId, destinationId, value);
	transactions.put(transactionId, newTransaction);
	return newTransaction;
    }

    public static CustomerRecord getCustomer(Integer originId) {
	return customers.get(originId);
    }

    public static TransactionRecord getTransaction(Integer transactionId) {
	return transactions.get(transactionId);
    }
}
