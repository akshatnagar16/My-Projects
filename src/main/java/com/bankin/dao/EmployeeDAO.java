package com.bankin.dao;

import com.banking.model.Customers;

//Employee's Methods
public interface EmployeeDAO {
	public String checkEmployee(String employeeUserName,String employeePassword);
	public int viewAccountBalance(int accountNumber);
	public boolean approveAccount(int accountNumber);
	public String isCustomer(int accountNumber);
	public void getCustomerAccounts();
	public Customers getCustomerDetails(int accountNumber);
	
}
