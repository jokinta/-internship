package app_main.sypks_user_login.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;

import org.springframework.stereotype.Service;

import app_main.model.StatusInfo;
import app_main.rest_model.ChangePassword;
import oracle.jdbc.OracleTypes;

@Service
public class ChangePasswordService {

	StatusInfo statusInfo;

	public void pass_change(ChangePassword changePassword) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call sypks_user_login.pr_change_password(?,?,?,?)}");
			callStatement.setString(1, changePassword.getUsername());
			callStatement.setString(2, changePassword.getOld_password());
			callStatement.setString(3, changePassword.getNew_password());
			callStatement.registerOutParameter(4, OracleTypes.STRUCT, "STATUS_INFO");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(4);

			Object[] object = struct.getAttributes();
			
			statusInfo = new StatusInfo((String) object[0], (String) object[1], (String) object[2]);

		}

		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}
	}

	public StatusInfo getStatus() {
		return statusInfo;
	}

}