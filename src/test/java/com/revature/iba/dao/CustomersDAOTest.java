package com.revature.iba.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bankin.dao.CustomersDAO;
import com.bankin.dao.CustomersDAOImpl;
import com.banking.model.Customers;


public class CustomersDAOTest {
	
	Customers customers;
	CustomersDAO customersDAO=new CustomersDAOImpl();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		customers=new Customers();
	}
	@After
	public void tearDown() throws Exception {
		customers=null;
	}
	
	
	
	@Test
	public void testAddCustomer() {
		//added ,deposited amount and cross checked the balance before and after deposit
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customers=customersDAO.depositMoney(customers, 440);
		assertEquals(customers.getAccountBalance(), 1000);
		customersDAO.deleteCustomer(-6465);
	}

	@Test
	public void testMakeWithdrawal() {
		//added ,withdrawn amount and cross checked the balance before and after Withdrawal
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customers=customersDAO.withdrawalMoney(customers, 500);
		assertEquals(customers.getAccountBalance(), 60);
		customersDAO.deleteCustomer(-6465);
	}

	@Test
	public void testMakeDeposit() {
		//added ,deposited amount and cross checked the balance before and after deposit
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customers=customersDAO.depositMoney(customers, 440);
		assertEquals(customers.getAccountBalance(), 1000);
		customersDAO.deleteCustomer(-6465);
	}

	@Test
	public void testMakeMoneyTransfer() {
		//added ,made money transfer to self account and cross checked the result
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customers=customersDAO.transferMoney(customers, (long) -6465, 440);
		//440 withdrawn and deposited to same account...
		assertEquals(customers.getAccountBalance(), 560);
		customersDAO.deleteCustomer(-6465);
	}

	@Test
	public void testLoadCustomerDetails() {
		//added,loaded details and cross checked with actual value
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customersDAO.displayCustomerDetails(customers, "Deodj");
		assertEquals(customers.getAccountBalance(), 560);
		customersDAO.deleteCustomer(-6465);
	}

	@Test
	public void testValidateCustomer() {
		//added, validated (loads details after successful validation) and cross checked the firstName
		Customers customers=new Customers((long) -999,"zyzf","lhyf","Deodj","klmn",-6465,560);
		customersDAO.addCustomer(customers);
		customers=customersDAO.checkCustomer("Deodj", "klmn");
		assertEquals(customers.getCustomerFirstName(), "zyzf");
		customersDAO.deleteCustomer(-6465);
	}

}
