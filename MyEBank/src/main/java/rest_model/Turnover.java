package rest_model;

import java.sql.Date;

public class Turnover{
	private  String account_id;
	private Date from_date;
	private Date end_date;
	
	
	public Turnover() {}
	
	public Turnover(String account_id, Date from_date, Date end_date) {
		this.account_id = account_id;
		this.from_date = from_date;
		this.end_date = end_date;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	

}
