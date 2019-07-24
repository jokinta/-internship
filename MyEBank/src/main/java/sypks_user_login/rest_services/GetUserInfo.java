package sypks_user_login.rest_services;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Struct;

import org.springframework.stereotype.Service;

import model.UserInfo;
import rest_model.User;
import oracle.jdbc.OracleTypes;

@Service
public class GetUserInfo {

	UserInfo userInfo;
	// clas login request ();

	public void getInfo(User user) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call sypks_user_login.pr_get_user_info(?,?)}");
			callStatement.setString(1, user.getUsername());
			callStatement.registerOutParameter(2, OracleTypes.STRUCT, "USER_INFO");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(2);

			Object[] object = struct.getAttributes();
			userInfo = new UserInfo((String) object[0], (String) object[1], (String) object[2],(String) object[3],(String) object[4],
					(String) object[5]);

		}

		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}
	}

	public UserInfo getStatus() {
		return userInfo;
	}

}