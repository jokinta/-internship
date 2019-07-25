package rest_model;

import java.math.BigDecimal;

public class Transaction {
	private String my_acc;
	private String iban;
	private BigDecimal amount;
	private String description;
	
	public Transaction() {}
	
	public Transaction(String my_acc, String iban, String amount,String description) {
		this.my_acc = my_acc;
		this.iban = iban;
		this.amount = new BigDecimal(amount);
		this.description = description;
	}
	
	public String getMy_acc() {
		return my_acc;
	}
	public void setMy_acc(String my_acc) {
		this.my_acc = my_acc;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
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
