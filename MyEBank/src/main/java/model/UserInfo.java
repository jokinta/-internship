package model;

public class UserInfo {
      private String status;
      private String username;
      private String user_id;
      private String email;
      private String user_status;
      private String err_message;
      
      public UserInfo() {}
      
	public UserInfo(String status, String username, String user_id, String email, String user_status,String err_message) {
		this.status = status;
		this.username = username;
		this.user_id = user_id;
		this.email = email;
		this.user_status = user_status;
		this.err_message = err_message;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	public String getErr_message() {
		return err_message;
	}
	public void setErr_message(String err_message) {
		this.err_message = err_message;
	}
      
      
}
