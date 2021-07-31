package com;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankin.dao.CustomersDAO;
import com.bankin.dao.CustomersDAOImpl;
import com.bankin.dao.EmployeeDAO;
import com.bankin.dao.EmployeeDAOImpl;
import com.banking.model.Customers;
import com.banking.model.Employee;
import com.banking.util.DbConnection;

public class BankApp {
	Logger logger = Logger.getLogger("Insight Banking App");
	public int passwordMissmatchValue = 0;

	public void startApp() {

		CustomersDAO customersDAO = new CustomersDAOImpl();
		Customers customers = new Customers();
		Employee employee = new Employee();
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		Scanner sc = new Scanner(System.in);
		boolean res = false;
		while (true) {

			System.out.println("Welcome to Banking App");
			System.out.println();
			System.out.println();
			System.out.println("Enter choice :------->");
			System.out.println("1.Customer Login");
			System.out.println("2.Employee Login");
			System.out.println("3.Create an Account");
			System.out.println("4.About bank");
			System.out.println("0.exit");
			System.out.print("Please Enter your Choice: ");
			int choice = sc.nextInt();
			
			switch (choice) {
			
			// Customer Login
			case 1:
				System.out.print("Enter your User Name : ");
				String customerUserName = sc.next();
				System.out.print("Enter your Password : ");
				String customerPassword = sc.next();

				logger.info("customer "+ customerUserName+" entered into CUSTOMER LOGIN section" );

				customers = customersDAO.checkCustomer(customerUserName, customerPassword);
				if (customers != null) {
					Long cifId = customers.getCustomerId();
					if (cifId >= 100000 && cifId <= 999999) {
						CustomerFunctionalities customerFunctionalities = new CustomerFunctionalities();

						logger.info("customer validated and sending to CustomerPage class with userName"
								+ customerUserName);

						customerFunctionalities.customerDashboard(customers);
						break;
					} else {
						System.out.println("Your account not Approved yet(or Denied)");
						System.out.println(
								"It will take 24 to 48 hours for approval,Contact our customer support for further assistance...");
						break;
					}
				} else {
					logger.error("customer username or password did not match and entered values are UserName: "
							+ customerUserName + " Password: " + customerPassword);

					System.out.println("UserName or Password does not match, Please try again");
					break;
				}
			case 2:
				System.out.println("Enter your UserName : ");
				String employeeUserName = sc.next();
				System.out.println("Enter your Password : ");
				String employeePassword = sc.next();

				logger.info("Employee entered into EMPLOYEE LOGIN section with userName" + employeeUserName);

				String employeeName = employeeDAO.checkEmployee(employeeUserName, employeePassword);
				if (employeeName != null) {
					EmployeeFunctionalities emploteePage = new EmployeeFunctionalities();

					logger.info(
							"Employee validated and sending to EmployeePage class with UserName " + employeeUserName);

					emploteePage.employeeDashboard(employeeName);
				} else {

					logger.error("Employee username or password did not match and entered values are userName: "
							+ employeeUserName + " Password: " + employeePassword);

					System.out.println("UserName or Password does not match, Please try again");
				}
				break;
			case 3:

				logger.info("new customer entered into CREATE ACCOUNT section");

				customers = getCustomerDetails();
				if (customers != null) {
					res = customersDAO.addCustomer(customers);
					if (res == true) {

						logger.info("customer has successfully created account with cif Id: "
								+ customers.getCustomerId());

						System.out.println("Congratulations " + customers.getCustomerFirstName()
								+ " Your Account Successfully created");
						System.out.println("Your account number is " + customers.getAccountNumber() + " and CIF is "
								+ customers.getCustomerId());
						System.out
								.println("It will be approved soon , Kindly check your mail frequently for an update");
					} else {

						logger.error("Customer account not created as entered invalid details");

						System.out.println("Something went wrong,Please try again");
					}
					break;
				}
				break;
			case 4:

				logger.info("customer entered about app section");

				customersDAO.appInfo();
				break;
			case 0:

				logger.info("customer choosen the exit option");

				System.exit(0);
				System.out.println("Thank you for using my App,Visit Again");
				break;
			default:

				logger.fatal("Customer selected invalid option");

				System.out.println("Invalid Option..Please try again");
			}

		}
	}

	public Customers getCustomerDetails() {

		logger.info("customer came to getCustomerDetails method to enter inputs manually");

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your First Name : ");
		String customerFirstName = sc.nextLine();
		System.out.print("Enter your last name : ");
		String customerLastName = sc.next();
		System.out.print("Enter Username : ");
		String customerUserName = sc.next();
		System.out.print("Enter password : ");
		String customerPassword = sc.next();
		System.out.print("Re-enter password : ");
		String customerReEnteredPassword = sc.next();
		System.out.print("Enter the amount you want to open with : ");
		int accountBalance = sc.nextInt();
		if (customerPassword.equals(customerReEnteredPassword)) {
			Long accountNumber = (long) (100000000 + Math.random() * 900000000);
			Long customerCifId = (long) (10000 + Math.random() * 90000);

			logger.info("account number of 9 digits and cifid of 5 degits generated using random method");

			Customers customers = new Customers(customerCifId, customerFirstName, customerLastName, customerUserName,customerPassword, accountNumber, accountBalance);

			return customers;
		} else {

			logger.error("Customer entered different passwords ...password mismatch. Entered passwords are:"+ customerPassword + " and " + customerReEnteredPassword);

			System.out.println("Password mismatch...Please try again");
			return null;
		}
	}
}
