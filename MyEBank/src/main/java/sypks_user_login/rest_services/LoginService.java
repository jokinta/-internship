package sypks_user_login.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;

import org.springframework.stereotype.Service;

import model.Login;
import rest_model.User;
import oracle.jdbc.OracleTypes;

@Service
public class LoginService {

	Login log;
	// clas login request ();

	public void login(User user) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call sypks_user_login.pr_login(?,?,?)}");
			callStatement.setString(1, user.getUsername());
			callStatement.setString(2, user.getPassword());
			callStatement.registerOutParameter(3, OracleTypes.STRUCT, "LOGIN_DATA");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(3);

			Object[] object = struct.getAttributes();
			log = new Login((String) object[0], (String) object[1], (String) object[2]);

		}

		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}
	}

	public Login getStatus() {
		return log;
	}

}