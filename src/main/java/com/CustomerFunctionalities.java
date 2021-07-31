package com;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankin.dao.CustomersDAO;
import com.bankin.dao.CustomersDAOImpl;
import com.banking.model.Customers;

public class CustomerFunctionalities {

	Logger logger=Logger.getLogger("Customer's Page");

	Customers customers=new Customers();
	Scanner sc=new Scanner(System.in);
	CustomersDAO customerDAO=new CustomersDAOImpl();
	
	public void customerDashboard(Customers customers) {
		String customerUserName=customers.getCustomerUserName();

		logger.info("customer entered into his functionalities");

		System.out.println("You have LoggedIn Successfully!!");
		while (true) {
			System.out.println("Banking App");
			System.out.println();
			System.out.println();
			System.out.println("Your Functionalities:------->");
			System.out.println();
			System.out.println("1. Check Account Balance");
			System.out.println("2. Deposit money");
			System.out.println("3. Withdrawal money");
			System.out.println("4. Transfer money");
			System.out.println("0. Logout");
			System.out.print("Please Enter Your Choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			
			// Check Account Balance
			case 1:
				logger.info("customer entered check account balance section");


				customers=customerDAO.displayCustomerDetails(customers, customerUserName);
				System.out.println(customers.getCustomerFirstName()+" Your account balance is :"+customers.getAccountBalance());
				break;
				
			// Deposit Money
			case 2:
				logger.info("customer entered deposit money section");

				customers=customerDAO.displayCustomerDetails(customers, customerUserName);
				System.out.print("Enter the amount you want to deposit: ");
				int creditAmount=sc.nextInt();
				
				if(creditAmount <= 0) {	
					logger.fatal("customer entered negative value money or 0 i.e., "+creditAmount);

					System.out.println("Invalid amount, try again");
					break;
				}
				else if(creditAmount < 500) {

					logger.fatal("customer entred less than 500");

					System.out.println("Minimum deposit money is 500 INR!!");
					break;
				}
				else if(creditAmount > 50000) {

					logger.fatal("customer entred greater than 50000");

					System.out.println("You can only deposit 50000 INR in a day");
					break;
				}
				else {
					customers = customerDAO.depositMoney(customers, creditAmount);

					if (customers != null) {

						logger.info("customer deposited "+creditAmount + " successfully and new balance is "+ customers.getAccountBalance());

						System.out.println("Rs."+creditAmount + " deposited to your account successfully, your current account balance is "+ customers.getAccountBalance());
						break;
					} else

						logger.error("deposit failed");

						System.out.println("Deposit failed!!");
					break;
				}
			
			// Withdrawal Money
			case 3:
				logger.info("customer entered withdraw money section");

				customers=customerDAO.displayCustomerDetails(customers, customerUserName);
				System.out.print("Enter the amount you want to Withdraw: ");
				int debitAmount=sc.nextInt();
				
				if(debitAmount <= 0) {

					logger.fatal("customer entered negative value money or 0");
					System.out.println("Invalid amount, try again");
					break;
				}
				else if(debitAmount < 500) {

					logger.fatal("customer entred less than 500");

					System.out.println("Minimum withdrawal is 500 INR!!");
					break;
				}
				else if(debitAmount > 25000) {

					logger.fatal("customer entred greater than 25000");

					System.out.println("You can only withdraw 25000 INR in a day");
					break;
				}
				else {
					customers =customerDAO.withdrawalMoney(customers,debitAmount);
					if(customers!=null) {

						logger.info("customer withdrawn "+debitAmount + " successfully and new balance is "+ customers.getAccountBalance());

						System.out.println("Rs."+debitAmount+" withdrawn from your account, your current balance is "+customers.getAccountBalance());
						break;
					}
					else {

						logger.error("Withdraw failed ");

						System.out.println("Withdraw failed!!");
					break;
					}
				}
			
			// Transfer Money
			case 4:

				logger.info("Customer entered TRANSFER MONEY section ");

				customers=customerDAO.displayCustomerDetails(customers, customerUserName);
				System.out.print("Enter the receiver's account numbery: ");
				Long customerAccountNumber=sc.nextLong();
				System.out.print("Enter the amount you want to transfer: ");
				int amount=sc.nextInt();
				
				if(amount <= 0) {

					logger.fatal("customer entred negative amount or 0 amount");

					System.out.println("Invalid Amount, try again");
					break;
				}
				else if(amount > 30000) {

					logger.fatal("customer entred amount greater than 30000");

					System.out.println("You can only transfer upto 30000 INR");
					break;
				}
				else {
					customers=customerDAO.transferMoney(customers,customerAccountNumber,amount);
					if(customers!=null) {

						logger.info("customer transfered Rs."+amount + " to "+customerAccountNumber+"  successfully, current balance is "+ customers.getAccountBalance());

						System.out.println("Rs."+amount+" transfered to "+customerAccountNumber+" successfully your current account balance is "+customers.getAccountBalance());
					}
					else {

						logger.error("transfer failed ");

						System.out.println("Transfer failed, please try again");
					}
					break;
				}
				
			case 0:
				customers=customerDAO.displayCustomerDetails(customers, customerUserName);
				System.out.println(customers.getCustomerFirstName()+" You successfully logged out");

				logger.info("customer click on logout and logged out successfully ");

				BankApp bankApp=new BankApp();
				bankApp.startApp();
				break;
			default:

				logger.fatal("customer chosen wrong option");
				System.out.println("Invalid Option..Please try again");

			}
		}
		
	}
}
