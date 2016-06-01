package model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.sql.*;

public class Database {
	private Connection connection;
	private Statement statement;

	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";

	public Database() throws ClassNotFoundException, SQLException {
		connect();
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public ArrayList<User> searchFor(String keyword) {
		ResultSet resultSet = null;
		LinkedList<String> IDs = new LinkedList<>();
		ArrayList<User> users = new ArrayList<>();
		
		try {
			resultSet = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"CUSTOMERS\" WHERE \"CPRNo\" LIKE '%" + keyword
					+ "%' OR \"Phone\" LIKE '%" + keyword + "%' OR LOWER(\"FullName\") LIKE '%" + keyword
					+ "%'");
			while (resultSet.next()) {
				IDs.add(resultSet.getString("CPRNo"));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String ID : IDs){
			users.add(getUser(ID));
		}
		return users;
	}

	public LinkedList<String> getStrings(String query, String[] columns) {
		LinkedList<String> results = new LinkedList<>();

		try {
			ResultSet resultset = statement.executeQuery(query);
			while (resultset.next()) {
				for (int i = 0; i < columns.length; i++) {
					results.add(resultset.getString(columns[i]));
				}
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	public LinkedList<Account> getAccounts(User user) {
		String cpr = user.getCPR();
		LinkedList<Account> accounts = new LinkedList<>();

		try {
			ResultSet resultset = statement.executeQuery(
					"select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = '"
							+ cpr + "' ");
			while (resultset.next()) {
				Account acc = new Account(user, resultset.getString("AccID"), resultset.getString("AccName"),
						resultset.getBigDecimal("Balance"), resultset.getBigDecimal("Interest"),
						resultset.getString("ISOCode"));
				accounts.add(acc);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public LinkedList<Transaction> getTransactions(String accountID) {
		LinkedList<Transaction> transactions = new LinkedList<>();

		try {
			ResultSet resultset = statement
					.executeQuery("select * from \"DTUGRP05\".\"TRANSACTIONS\" WHERE \"AccID\" = '" + accountID
							+ "' OR \"AccIDTracing\" = '" + accountID + "' ");
			while (resultset.next()) {
				BigDecimal amount = resultset.getBigDecimal("Amount");
				if (resultset.getString("AccID").equals(accountID) && !resultset.getString("AccIDTracing").equals(accountID)
						|| resultset.getString("TransName").equals("Withdraw")) {
					amount = amount.negate();
				}
				Transaction trans = new Transaction(resultset.getString("TransName"), resultset.getDate("TransDate"),
						amount, resultset.getString("ISOCode"), resultset.getString("AccID"),
						resultset.getString("AccIDTracing"), resultset.getBigDecimal("ResultBalance"));
				transactions.add(trans);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactions;
	}

	public String newAccount(Account account) throws SQLException {
		String cpr = account.getOwner().getCPR();
		String ID = account.getAccountID();
		String name = account.getName();
		BigDecimal balance = account.getBalance();
		BigDecimal interest = account.getInterest();
		String ISOCode = account.getISOCode();

		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".CreateAccount(?, ?, ?, ?, ?, ?, ?) }");
		call.setString("vCPRNo", cpr);
		call.setString("vAccID", ID);
		call.setBigDecimal("vAmount", balance);
		call.setBigDecimal("vInterest", interest);
		call.setString("vAccName", name);
		call.setString("vISOCode", ISOCode);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String closeAccount(String accountID) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".DeleteAccount(?, ?) }");
		call.setString("vAccID", accountID);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String editAccount(Account account) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".EditAccount(?, ?, ?, ?) }");
		call.setString("vAccID", account.getAccountID());
		call.setString("vAccName", account.getName());
		call.setBigDecimal("vInterest", account.getInterest());
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String processTransaction(String type, String accountID, String accountID2, BigDecimal amount,
			String ISOCode, String transactionName) throws SQLException {
		CallableStatement call;
		switch (type) {
		case "Deposit":
			System.out.println("accID: " + accountID + ", amount: " + amount + ", ISO: " + ISOCode);
			call = connection.prepareCall("{call \"DTUGRP05\".Deposit(?, ?, ?, ?) }");
			call.setString("vAccID", accountID);
			call.setBigDecimal("vAmount", amount);
			call.setString("vISOCode", ISOCode);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		case "Withdraw":
			call = connection.prepareCall("{call \"DTUGRP05\".withdraw(?, ?, ?, ?) }");
			call.setString("vAccID", accountID);
			call.setBigDecimal("vAmount", amount);
			call.setString("vISOCode", ISOCode);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		case "Transfer":
			call = connection.prepareCall("{call \"DTUGRP05\".MoneyTransfer(?, ?, ?, ?, ?, ?) }");
			System.out.println(amount + " " + transactionName + " " + accountID + " " + accountID2 + " " + ISOCode);
			call.setBigDecimal("vTransfer", amount);
			call.setString("vTransName", transactionName);
			call.setString("vAccID1", accountID);
			call.setString("vAccID2", accountID2);
			call.setString("vISOCode", ISOCode);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return "";
	}

	public String getOwner(String accountID) {
		String cpr = "";
		try {
			ResultSet resultset = statement
					.executeQuery("select * from \"DTUGRP05\".\"OWNERSHIPS\" WHERE \"AccID\" = '" + accountID + "'");
			if (resultset.next()) {
				cpr = resultset.getString("CPRNo");
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cpr;
	}
	
	public String getCity(String zipcode) {
		String city = "";
		try {
			ResultSet resultset = statement
					.executeQuery("select * from \"DTUGRP05\".\"CITIES\" WHERE \"Postcode\" = '" + zipcode + "'");
			if (resultset.next()) {
				city = resultset.getString("CityName");
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return city;
	}
	
	public Account getAccount(String accountID) {
		try {
			ResultSet resultset = statement.executeQuery("select * from \"DTUGRP05\".\"ACCOUNTS\" WHERE \"AccID\" = '" + accountID + "'");
			if (resultset.next()) {
				BigDecimal balance = resultset.getBigDecimal("Balance");
				BigDecimal interest = resultset.getBigDecimal("Interest");
				String AccName = resultset.getString("AccName");
				String ISOCode = resultset.getString("ISOCode");
				return new Account(getUser(getOwner(accountID)), accountID, AccName, balance, interest, ISOCode);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void register(String cpr, String email, String password, String name, String address, String zipcode,
			Date date, String phone) {
		System.out.println(date);
		try {
			statement.executeUpdate("INSERT INTO \"DTUGRP05\".\"USERS\"" + " VALUES('" + cpr + "', '" + email + "', '" + 
								  password + "', 'c', '" + name + "', '" + phone + "', '" + address + "', '" + date + "', '" + zipcode + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public User getUser(String cpr) {
		User user = null;
		try {
			ResultSet resultset = statement.executeQuery("select * from \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "'");
			if (resultset.next()) {
				user = new User(this, cpr);
				String email = resultset.getString("Email");
				String name = resultset.getString("FullName");
				String password = resultset.getString("Password");
				String phone = resultset.getString("Phone");
				String address = resultset.getString("Address");
				Date dateOfBirth = resultset.getDate("DateOfBirth");
				String postCode = resultset.getString("PostCode");
				String role = resultset.getString("RoleID");
				user.setInfo(email, password, name, phone, address, dateOfBirth, postCode, role);
				
				LinkedList<Account> accounts = getAccounts(user);
				user.setAccounts(accounts);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public String editUser(String cpr, String email, String password, String name, String address, String zipcode,
			Date date, String phone) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".EditUser(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
		call.setString("vCPRNo", cpr);
		call.setString("vEmail", email);
		call.setString("vPassword", password);
		call.setString("vFullName", name);
		call.setString("vAddress", address);
		call.setString("vPostcode", zipcode);
		call.setDate("vDateOfBirth", date);
		call.setString("vPhone", phone);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}
}