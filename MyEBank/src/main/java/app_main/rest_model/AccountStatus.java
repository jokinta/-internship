package app_main.rest_model;

public class AccountStatus {
	private String user_id;
	private String new_status;

	public AccountStatus() {}
	
	public AccountStatus(String user_id, String new_status) {
		this.user_id = user_id;
		this.new_status = new_status;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNew_status() {
		return new_status;
	}
	public void setNew_status(String new_status) {
		this.new_status = new_status;
	}
	
	

}
