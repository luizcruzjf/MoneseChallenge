package com.example.monese;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.monese.databaseRows.TransactionRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestHandler {
    private static final String DESTINATION_ID = "toCustomerId";
    private static final String CUSTOMER_ID = "customerId";
    static private TransactionHandler transactionHandler = new TransactionHandler();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean handleFileRequest(File file) {
	try {
	    Map<?, ?> jsonMap = objectMapper.readValue(file, Map.class);
	    for (Object requestType : jsonMap.keySet()) {
		if (!(requestType instanceof String)) {
		    throw new Exception("unknown json file format");
		} else {
		    switch ((String) requestType) {
		    case "transaction":
			handleTransactionRequest(jsonMap, requestType, file);
			break;
		    case "statement":
			handleStatementRequest(jsonMap, requestType, file);
			break;
		    default:
			throw new Exception("unknown request: " + requestType);
		    }

		}
	    }
	} catch (Exception e) {
	    writeResponseFile("failed", file, e);
	    return false;
	}
	return true;
    }

    private static void handleStatementRequest(Map<?, ?> map, Object key, File file) throws Exception {
	Map<?, ?> requestValues = (Map<?, ?>) map.get(key);
	if (!requestValues.containsKey(CUSTOMER_ID)) {
	    throw (new Exception("Invalid json file format"));
	}
	Integer originId = Integer.parseInt((String) requestValues.get(CUSTOMER_ID));
	if (!DatabaseAccess.customerExists(originId)) {
	    throw (new Exception("Customer id " + originId + " does not exist"));
	}

	List<TransactionRecord> customerTransactions = transactionHandler.getCustomerTransactions(originId);

	LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<String, Object>();
	jsonMap.put("result", "success");
	jsonMap.put(CUSTOMER_ID, originId);

	LinkedHashMap<String, Object> transactionList = new LinkedHashMap<String, Object>();
	for (TransactionRecord transactionRecord : customerTransactions) {
	    LinkedHashMap<String, Object> transactions = new LinkedHashMap<String, Object>();
	    transactions.put("fromCustomerId", transactionRecord.getCustOriginId());
	    transactions.put("value", transactionRecord.getValue());
	    transactions.put(DESTINATION_ID, transactionRecord.getCustDestId());
	    transactionList.put(transactionRecord.getTransactionId() + "", transactions);
	}
	ObjectMapper o = new ObjectMapper();
	File responseFile = new File("responses/response_" + file.getName());
	jsonMap.put("balance", DatabaseAccess.getBalance(originId));
	jsonMap.put("transactions", transactionList);
	try {
	    o.writeValue(responseFile, jsonMap);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static void handleTransactionRequest(Map<?, ?> map, Object key, File file) throws Exception {
	Map<?, ?> requestValues = (Map<?, ?>) map.get(key);
	if (!requestValues.containsKey(CUSTOMER_ID) || !requestValues.containsKey(DESTINATION_ID)
		|| !requestValues.containsKey("value")) {
	    throw (new Exception("Invalid json file format"));
	}
	Integer originId = Integer.parseInt((String) requestValues.get(CUSTOMER_ID));
	Integer destinationId = Integer.parseInt((String) requestValues.get(DESTINATION_ID));
	float value = Float.parseFloat((String) requestValues.get("value"));
	try {
	    transactionHandler.createTransaction(originId, destinationId, value);
	    writeResponseFile("success", file);
	} catch (IOException e) {
	    e.printStackTrace();
	    writeResponseFile("failed", file, e);
	}

    }

    private static void writeResponseFile(String response, File file) {
	writeResponseFile(response, file, null);
    }

    private static void writeResponseFile(String response, File file, Exception exception) {
	HashMap<String, String> resultMap = new HashMap<String, String>();
	File responseFile = new File("responses/response_" + file.getName());
	resultMap.put("result", response);
	if (exception != null) {
	    resultMap.put("errorMessage", exception.getMessage());
	}
	try {
	    objectMapper.writeValue(responseFile, resultMap);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
