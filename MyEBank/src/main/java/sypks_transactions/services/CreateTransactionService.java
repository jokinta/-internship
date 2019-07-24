package sypks_transactions.services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;


import org.springframework.stereotype.Service;

import model.StatusInfo;
import rest_model.Transaction;
import oracle.jdbc.OracleTypes;

@Service
public class CreateTransactionService {

	StatusInfo statusInfo;

	public void create_transaction(Transaction transaction) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call create_transaction(?,?,?,?,?}");
			callStatement.setString(1, transaction.getMy_acc());
			callStatement.setString(2, transaction.getIBAN());
			callStatement.setBigDecimal(3, transaction.getAmount());
			callStatement.setString(5, transaction.getDescription());
            callStatement.registerOutParameter(5, OracleTypes.STRUCT, "STATUS_INFO");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(5);

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