package app_main.rest_model;



public class Turnover{
	private  String account_id;
	private String from_date;
	private String end_date;
	
	public Turnover() {}
	
	public Turnover(String account_id, String from_date, String end_date) {
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
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
    
	
    
	

}
