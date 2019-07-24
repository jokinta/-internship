package rest_model;

import java.math.BigDecimal;

public class Transaction {
	private String my_acc;
	private String IBAN;
	private BigDecimal amount;
	private String description;
	
	
	public Transaction(String my_acc, String iBAN, String amount, String description) {
		this.my_acc = my_acc;
		this.IBAN = iBAN;
		this.amount = new BigDecimal(amount);
		this.description = description;
	}
	
	public String getMy_acc() {
		return my_acc;
	}
	public void setMy_acc(String my_acc) {
		this.my_acc = my_acc;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
