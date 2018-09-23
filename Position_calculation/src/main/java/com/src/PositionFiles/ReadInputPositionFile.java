package com.src.PositionFiles;

public class ReadInputPositionFile {
	private String instrument;
	private int account;
	private String accountType;
	private int quantity;
	
	public ReadInputPositionFile() {};
	
	public ReadInputPositionFile(String instrument,int account,String accountType,int quantity) {
		this.instrument = instrument;
		this.account = account;
		this.accountType = accountType;
		this.quantity = quantity;
	}
	
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
