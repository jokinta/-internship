package sypks_accounts.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;

import org.springframework.stereotype.Service;

import model.StatusInfo;
import rest_model.Account;
import oracle.jdbc.OracleTypes;

@Service
public class CreateAccountService {

	StatusInfo statusInfo;

	public void create_acc(Account account) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call create_account(?,?,?,?)}");
			callStatement.setString(1, account.getUser_id());
			callStatement.setString(2, account.getCurrency());
		     callStatement.setBigDecimal(3,account.getDeposit());
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