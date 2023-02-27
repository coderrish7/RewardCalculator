package com.transaction.rewardcalculator;


import java.time.LocalDateTime;


public class CustomerTransaction {
	private String customerName;
	private LocalDateTime transactionDate;
	private double transactionAmount;
	
	public CustomerTransaction(String customerName, LocalDateTime transactionDate2, double transactionAmount2) {
		super();
		this.customerName = customerName;
		this.transactionDate = transactionDate2;
		this.transactionAmount = transactionAmount2;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public double getTransactionAmount() {
		return transactionAmount;
	}
	
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	
}
