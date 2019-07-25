package sypks_accounts.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;

import org.springframework.stereotype.Service;

import model.StatusInfo;
import rest_model.AccountStatus;
import oracle.jdbc.OracleTypes;

@Service
public class ChangeAccStatusService {

	StatusInfo statusInfo;

	public void change_acc_status(AccountStatus accStatus) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call sypks_accounts.change_acc_status(?,?,?)}");
			callStatement.setString(1, accStatus.getUser_id());
			callStatement.setString(2, accStatus.getNew_status());
			callStatement.registerOutParameter(3, OracleTypes.STRUCT, "STATUS_INFO");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(3);

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