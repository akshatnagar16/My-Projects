package com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankin.dao.CustomersDAO;
import com.bankin.dao.CustomersDAOImpl;
import com.bankin.dao.EmployeeDAO;
import com.bankin.dao.EmployeeDAOImpl;
import com.banking.model.Customers;

public class EmployeeFunctionalities {
	Logger logger = Logger.getLogger("Employee's Page");
	EmployeeDAO employeeDAO = new EmployeeDAOImpl();
	Customers customers = new Customers();
	CustomersDAO customersDAO = new CustomersDAOImpl();

	public void employeeDashboard(String employeeFirstName) {
		Scanner sc = new Scanner(System.in);

		logger.info("customer entered into his functionalities");

		System.out.println("You have LoggedIn Successfully!!");
		while (true) {
			System.out.println("Banking App");
			System.out.println();
			System.out.println();
			System.out.println("Your Functionalities:------->");
			System.out.println();
			System.out.println("1. Check Account Balance for Customer");
			System.out.println("2. Approve or Deny account");
			System.out.println("3. View Customer's Bank Accounts");
			System.out.println("4. Get Customer's Details");
			System.out.println("5. View log of all accounts");
			System.out.println("0. Logout");
			System.out.println();
			System.out.print("Please Enter Your Choice: ");
			int choice = sc.nextInt();
			
			switch (choice) {
			
			// Check Account Balance of Customer
			case 1:
				logger.info("Employee Entered check Balance section");

				System.out.print("Enter the account number of customer: ");
				int customerAccountNumber = sc.nextInt();
				String customerName = employeeDAO.isCustomer(customerAccountNumber);

				if (customerName != null) {
					customers = customersDAO.displayCustomerDetails(customers, employeeFirstName);
					int accountBalance = employeeDAO.viewAccountBalance(customerAccountNumber);
					if (accountBalance != -1) {

						logger.info(customerName + " balance retrieved " + accountBalance);

						System.out.println(customerName + " Your Balance is " + accountBalance);
					}
					break;
				} 
				else {
					logger.error("Customer does not exist");

					System.out.println("Customer with " + customerAccountNumber + " does not exist");
					break;
				}
				
			// Approve or Deny Customer's account
			case 2:

				logger.info("Employee Entered Approve or Deny Account section");

				System.out.print("Enter customer account number: ");
				int accountNumberr = sc.nextInt();
				customers = employeeDAO.getCustomerDetails(accountNumberr);
				if (customers != null) {
					Long cId = customers.getCustomerId();
					if (cId >= 10000 && cId <= 99999) {

						logger.info("Employee Retrieved customer details i.e.," + customers);

						System.out.println("The customer Details are :");
						System.out.println(customers);
						System.out.println("Approve or not(1 for Yes/ 0 for No)");
						int value = sc.nextInt();
						if (value == 1) {
							boolean res = employeeDAO.approveAccount(accountNumberr);
							if (res) {

								logger.info("Employee Approved the customer with account Number" + accountNumberr);

								System.out.println("Customer with account number " + accountNumberr + " Approved successfully");
							}
						} else {

							logger.info("Employee denied the customer with account number" + accountNumberr);

							System.out
									.println("Customer with account number " + accountNumberr + " denied successfully");
						}
						break;
					} else {

						logger.fatal("customer already approved with account number" + accountNumberr);

						System.out.println("Customer with account number " + accountNumberr + " Already Approved");
						break;
					}
				} else {

					logger.info("account number does not exist");

					System.out.println("please enter valid account number!!");
					break;
				}
				
			// View Customer's Bank Account
			case 3:
				logger.info("Employee viewed customer bank accounts");

				System.out.println("All customer Bank Account Details are: ");
				employeeDAO.getCustomerAccounts();
				break;
				
			// Get Customer's Details
			case 4:

				logger.info("Employee entered Get customer Details section ");

				System.out.print("Please enter the customer account number: ");
				int accountNumber = sc.nextInt();
				customers = employeeDAO.getCustomerDetails(accountNumber);
				if (customers != null) {

					logger.info("Employee Retrieved customer details i.e.," + customers);

					System.out.println(customers);
					break;
				} else {

					logger.info("account number does not exist");

					System.out.println("please enter valid account Number");
					break;
				}

			// Log file of all Accounts
			case 5:
				try {
					FileInputStream fstream = new FileInputStream("InsighBankingLog.log");
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
					String strLine;

					while ((strLine = br.readLine()) != null) {

						System.out.println(strLine);
					}
					fstream.close();
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
				break;
			
			// Exit
			case 0:

				logger.info("Employee entered Logged out successfully ");

				System.out.println(employeeFirstName + " You successfully Logged out");
				BankApp bankApp = new BankApp();
				bankApp.startApp();
				break;
			default:

				logger.info("Employee chosen invalid option ");
				System.out.println("you entered Invalid choice,  Please try again");
				break;
			}
		}
	}
}
