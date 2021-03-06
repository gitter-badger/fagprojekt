package model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

public class Account {

	private String accountID, ISOCode, name;
	private LinkedList<String> owners;
	private BigDecimal balance, interest;
	private LinkedList<Transaction> transactions;
	
	public Account(LinkedList<String> owners, String accountID, String name, BigDecimal balance, BigDecimal interest, String ISOCode, LinkedList<Transaction> transactions) {
		this.owners = owners;
		this.accountID = accountID;
		this.name = name;
		this.interest = interest;
		this.ISOCode = ISOCode;
		this.transactions = new LinkedList<>();
		this.balance = balance;
		this.transactions = transactions;
	}
	
	public BigDecimal getBalance(){
		 return balance;
	}
	
	public String getBalanceString(){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("###,##0.00", symbols);
		return formatter.format(balance.doubleValue());
	}
	
	public String getAccountID(){
		return accountID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}
	
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public String getISOCode() {
		return ISOCode;
	}
	
	public LinkedList<String> getOwners() {
		return owners;
	}
	
	public void setISOCode(String ISOCode) {
		this.ISOCode = ISOCode;
	}
	
	public LinkedList<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(LinkedList<Transaction> transactions) {
		this.transactions = transactions;
	}
}