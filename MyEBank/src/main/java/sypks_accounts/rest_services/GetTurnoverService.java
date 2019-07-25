package sypks_accounts.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;


import org.springframework.stereotype.Service;

import model.TuroverInfo;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import rest_model.Turnover;

@Service

public class GetTurnoverService {
	ArrayList<TuroverInfo> turovers = new ArrayList< TuroverInfo>();

	TuroverInfo turoverInfo;

	public void getTurover(Turnover turnover) {

		try {

			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");
			CallableStatement callStatement = conn.prepareCall("{call getTurnover2(?,?,?,?)}");
			callStatement.setString(1, turnover.getAccount_id());
			callStatement.setString(2, turnover.getFrom_date());
			callStatement.setString(3, turnover.getEnd_date());
			callStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callStatement.execute();
		      ResultSet rs = ((OracleCallableStatement)callStatement).getCursor(4);
		      while (rs.next()) {
		    	  turoverInfo = new TuroverInfo(rs.getString(1),rs.getString(2),rs.getBigDecimal(3),rs.getBigDecimal(4),rs.getString(5),rs.getDate(6),rs.getString(7)) ;
		    	  turovers.add(turoverInfo);
		      }
		      rs.close();

       
		}
		  

	
		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}
	}
	
	public ArrayList<TuroverInfo> getStatus() {
			return turovers;

		}
	
	}



