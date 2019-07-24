package rest_model;

import java.math.BigDecimal;

public class Account {
	private String user_id;
	private String currency;
	private BigDecimal deposit;
	
	public Account() {}
	
	public Account(String user_id, String currency, String deposit) {
		this.user_id = user_id;
		this.currency = currency;
		this.deposit = new BigDecimal(deposit) ;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getDeposit() {
		return deposit;
	}
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}
	
	

}
