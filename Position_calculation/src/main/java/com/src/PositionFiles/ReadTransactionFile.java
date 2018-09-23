package com.src.PositionFiles;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadTransactionFile {
	private int transactionId;
	private String instrument;
	private String transactionType;
	private int transactionQuantity;
	
	public ReadTransactionFile() {};
	
	public ReadTransactionFile(int transactionId, String instrument, String transactionType,
			int transactionQuantity) {
		this.transactionId = transactionId;
		this.instrument = instrument;
		this.transactionType = transactionType;
		this.transactionQuantity = transactionQuantity;
	}

	@JsonProperty("transactionId")
	public int getTransactionId() {
		return transactionId;
	}
	
	@JsonProperty("TransactionId")
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	@JsonProperty("instrument")
	public String getInstrument() {
		return instrument;
	}

	@JsonProperty("Instrument")
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	@JsonProperty("transactionType")
	public String getTransactionType() {
		return transactionType;
	}

	@JsonProperty("TransactionType")
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@JsonProperty("transactionQuantity")
	public int getTransactionQuantity() {
		return transactionQuantity;
	}

	@JsonProperty("TransactionQuantity")
	public void setTransactionQuantity(int transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}
}
