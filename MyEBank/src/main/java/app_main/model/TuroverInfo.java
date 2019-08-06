package app_main.model;

import java.math.BigDecimal;
import java.util.Date;

public class TuroverInfo {
	private String account_id;
	private String to_account_id;
	private BigDecimal trn_id;
	private BigDecimal lcy_amount;
	private String drcr_indicator;
	private Date trn_date;
	private String trn_description;

	
	public TuroverInfo() {}
	
	public TuroverInfo(String account_id, String to_account_id, BigDecimal trn_id, BigDecimal lcy_amount,
			String drcr_indicator, Date trn_date, String trn_description) {
		this.account_id = account_id;
		this.to_account_id = to_account_id;
		this.trn_id = trn_id;
		this.lcy_amount = lcy_amount;
		this.drcr_indicator = drcr_indicator;
		this.trn_date = trn_date;
		this.trn_description = trn_description;
	}


	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getTo_account_id() {
		return to_account_id;
	}
	public void setTo_account_id(String to_account_id) {
		this.to_account_id = to_account_id;
	}
	public BigDecimal getTrn_id() {
		return trn_id;
	}
	public void setTrn_id(BigDecimal trn_id) {
		this.trn_id = trn_id;
	}
	public BigDecimal getLcy_amount() {
		return lcy_amount;
	}
	public void setLcy_amount(BigDecimal lcy_amount) {
		this.lcy_amount = lcy_amount;
	}
	public String getDrcr_indicator() {
		return drcr_indicator;
	}
	public void setDrcr_indicator(String drcr_indicator) {
		this.drcr_indicator = drcr_indicator;
	}
	public Date getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(Date trn_date) {
		this.trn_date = trn_date;
	}
	public String getTrn_description() {
		return trn_description;
	}
	public void setTrn_description(String trn_description) {
		this.trn_description = trn_description;
	}


	
	
	

}
