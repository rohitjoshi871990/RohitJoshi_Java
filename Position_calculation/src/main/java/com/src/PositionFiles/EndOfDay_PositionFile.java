package com.src.PositionFiles;

public class EndOfDay_PositionFile {
	private String instrument;
	private int account;
	private String accountType;
	private int quantity;
	private int delta;
	
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
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}
}
