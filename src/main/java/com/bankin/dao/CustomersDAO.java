package com.bankin.dao;

import java.util.Scanner;

import com.banking.model.Customers;

// Customer's Methods
public interface CustomersDAO {
	public boolean addCustomer(Customers customers);
	public Customers withdrawalMoney(Customers customers,int debitAmount);
	public Customers depositMoney(Customers customers,int creditAmount);
	public Customers transferMoney(Customers customers,Long customerAccountNumber,int amount);
	public Customers checkCustomer(String customerUserName,String customerPassword);
	public Customers displayCustomerDetails(Customers customers,String customerUserName);
	public void appInfo();
	public boolean deleteCustomer(int accountNumber);//for test cases
}
