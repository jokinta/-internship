package sypks_accounts.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Types;

import org.springframework.stereotype.Service;

import model.StatusInfo;
import rest_model.Turnover;

@Service
public class GetTurnoverService {

	StatusInfo statusInfo;

	public void getTurover(Turnover turnover) {

		try {

			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");
			CallableStatement callStatement = conn.prepareCall("{call create_account(?,?,?,?,?)}");
			callStatement.setString(1, turnover.getAccount_id());
			callStatement.setDate(2, turnover.getFrom_date());
			callStatement.setDate(3, turnover.getEnd_date());
			callStatement.registerOutParameter (4,Types.DOUBLE);
			callStatement.registerOutParameter (5,Types.DOUBLE);


			callStatement.execute();
		    Double credit_sum = callStatement.getDouble(4);
		    Double debit_sum = callStatement.getDouble(5);

            System.out.println("Credit sum->" + credit_sum);
            System.out.println("Debit sum->" + debit_sum);


		}

		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}
	}



}