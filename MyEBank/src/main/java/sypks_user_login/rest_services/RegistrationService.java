package sypks_user_login.rest_services;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.Struct;

import model.Registration;
import rest_model.UserRegistration;
import oracle.jdbc.OracleTypes;

public class RegistrationService {

	Registration reg;

	public void registration(UserRegistration userRegistration) {

		// public static void main(String[] args) {

		try {

			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jokinta", "root2");

			CallableStatement callStatement = conn.prepareCall("{call sypks_user_login.pr_registration(?,?,?,?)}");
			callStatement.setString(1, userRegistration.getUsername());
			callStatement.setString(2, userRegistration.getEmail());
			callStatement.setString(3, userRegistration.getPassword());
			callStatement.registerOutParameter(4, OracleTypes.STRUCT, "REGISTRATION_DATA");

			callStatement.execute();

			Struct struct = (Struct) callStatement.getObject(4);

			Object[] object = struct.getAttributes();
			reg = new Registration((String) object[0], (String) object[1], (String) object[2]);

		}

		catch (Exception e)

		{

			System.out.println(e.getMessage());

		}

	}

	public Registration getStatus() {
		return reg;
	}

}