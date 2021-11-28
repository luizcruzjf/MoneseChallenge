package com.example.monese.databaseRows;

public class TransactionRecord {
    private int transactionId;
    private int custOriginId;
    private int custDestId;
    private float value;

    public TransactionRecord(int transactionId, int custOriginId, int custDestId, float value) {
	this.setTransactionId(transactionId);
	this.custOriginId = custOriginId;
	this.custDestId = custDestId;
	this.value = value;
    }

    public int getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(int transactionId) {
	this.transactionId = transactionId;
    }

    public int getCustOriginId() {
	return custOriginId;
    }

    public void setCustOriginId(int custOriginId) {
	this.custOriginId = custOriginId;
    }

    public int getCustDestId() {
	return custDestId;
    }

    public void setCustDestId(int custDestId) {
	this.custDestId = custDestId;
    }

    public float getValue() {
	return value;
    }

    public void setValue(float value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return "TransactionRecord [transactionId=" + transactionId + ", custOriginId=" + custOriginId + ", custDestId="
		+ custDestId + ", value=" + value + "]";
    }

}
