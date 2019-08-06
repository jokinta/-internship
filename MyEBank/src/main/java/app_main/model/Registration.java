package app_main.model;

public class Registration {
	private  String status ;
	private  String err_message ;
	private  String err_code ;
	
	
	public Registration() {
		
	}
	public Registration(String status, String err_message, String err_code) {
		this.status = status;
		this.err_message = err_message;
		this.err_code = err_code;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErr_message() {
		return err_message;
	}
	public void setErr_message(String err_message) {
		this.err_message = err_message;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	
	

}
