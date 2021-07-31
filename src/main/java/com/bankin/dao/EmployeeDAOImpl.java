package com.bankin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.banking.model.Customers;
import com.banking.model.Employee;
import com.banking.util.DbConnection;

public class EmployeeDAOImpl implements EmployeeDAO{
	
	//connecting to the database
	Connection connection=DbConnection.getDbConnection();
	Customers customers=new Customers();
	Employee employee=new Employee();
	
	//Sql Queries
	private String CHECK_EMPLOYEE="select employeeUserName,employeePassword from bank.employee where employeeUserName=? and employeePassword=? ";
	private String VIEW_CUSTOMER_BALANCE="call bank.getCustomerBalance(?,?)";
	private String VIEW_CUSTOMER_ACCOUNTS="select * from bank.customers";
	private String VIEW_CUSTOMER_DETAILS="select * from bank.customers where accountNumber=?";
	private String VIEW_NAME="select employeeFirstName,employeeLastName from bank.employee where employeeUserName=?";
	
	private String CHECK_CUSTOMER="select customerFirstName from bank.customers where accountNumber=?";
	private String GET_CID="select customerId from bank.customers where accountNumber=?";
	private String UPDATE_CID="update bank.customers set customerId=? where accountNumber=?";
	//calling stored procedure 
	
	//method to view account balance using account Number ,return balance
	public int viewAccountBalance(int accountNumber) {
		int accountBalance=-1;
		try {
;
			CallableStatement statement=connection.prepareCall(VIEW_CUSTOMER_BALANCE);

			statement.setInt(1, accountNumber);
			statement.registerOutParameter(2, Types.INTEGER);
			statement.setInt(2, accountBalance);
			statement.execute();
			accountBalance=statement.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountBalance;
	}

	public void getCustomerAccounts() {
		// TODO Auto-generated method stub
		ResultSet res=null;
		try {
			Statement statement=connection.createStatement();
			 res=statement.executeQuery(VIEW_CUSTOMER_ACCOUNTS);
			 ResultSetMetaData rsmd=res.getMetaData();
			 int columnCount=rsmd.getColumnCount();

			 while(res.next()) {
				 for(int i=1;i<=columnCount;i++) {
					 System.out.print(res.getString(i)+"\t");
				 }
				 System.out.println();
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//method to get particular customer details using account number,return customers object with all the details
	public Customers getCustomerDetails(int accountNumber) {
		// TODO Auto-generated method stub
		ResultSet res=null;
		
		try {
			PreparedStatement statement=connection.prepareStatement(VIEW_CUSTOMER_DETAILS);
			statement.setInt(1, accountNumber);
			res=statement.executeQuery();
			if(res.next()) {
			 customers=new Customers(res.getLong(1),res.getString(2),
					res.getString(3),res.getString(4),res.getString(5),res.getLong(6),res.getInt(7));
			}
			else {
				customers=null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Account Number does not exist, Please try again");
		}
		


		return customers;
		
		
	}
	//method to validate the employee ,if exists in the database then return  employee full name
	public String checkEmployee(String employeeUserName,String employeePassword) {
		// TODO Auto-generated method stub
		ResultSet res;
		int res1=1;
		try {
			PreparedStatement statement=connection.prepareStatement(CHECK_EMPLOYEE);
			statement.setString(1, employeeUserName);
			statement.setString(2, employeePassword);
			res=statement.executeQuery();
			if(res.next())
				res1=1;
			else 
				res1=0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("You entered Wrong UserName or Password, Please try again");
		}
		if(res1==1) {

			ResultSet res2=null;
			String employeeName=null;
			try {
				PreparedStatement statement=connection.prepareStatement(VIEW_NAME);
				statement.setString(1, employeeUserName);
				res2=statement.executeQuery();
				if(res2.next()) {
					 employeeName=res2.getString(1)+" "+res2.getString(2);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return employeeName;
		}
		else {
			return null;
		}
	}
	//method to check the customer existence using account number ,return customer name
	public String isCustomer(int accountNumber) {
		// TODO Auto-generated method stub
		ResultSet res=null;
		String res1=null;
		try {
			PreparedStatement statement=connection.prepareStatement(CHECK_CUSTOMER);
			statement.setInt(1, accountNumber);
			res=statement.executeQuery();
			if(res.next())
				 res1=res.getString(1);
			else
				res1=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res1;
	}
	public boolean approveAccount(int accountNumber) {
		// TODO Auto-generated method stub
		ResultSet res=null;
		int res1=0;
		int cif=0,newCif=0;
		try {
			PreparedStatement statement=connection.prepareStatement(GET_CID);
			statement.setInt(1, accountNumber);
			res=statement.executeQuery();
			if(res.next()) {
				cif=res.getInt(1);
			}
			newCif=cif+100000;
		PreparedStatement stat=connection.prepareStatement(UPDATE_CID);
		stat.setInt(1, newCif);
		stat.setInt(2, accountNumber);
		res1 =stat.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res1!=1) {
			return false;
		}
		else {
			return true;
		}
		
	}
}
