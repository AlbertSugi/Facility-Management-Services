package com.facilitymanagement.DAL;

import java.sql.*;

public class DB_Helper {

	/*public static void main(String[]args){
		getConnection();
	}*/

	@SuppressWarnings("unused")
	public static Connection getConnection() {
		System.out.println("\nDBHelper: -------- PostgreSQL " + "JDBC Connection  ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("DBHelper: Check Where  your PostgreSQL JDBC Driver exist and " + "Include in your library path!");
			e.printStackTrace();
			return null;

		}

		System.out.println("DBHelper: PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			//connection = DriverManager.getConnection("jdbc:postgresql://isilo.db.elephantsql.com:5432/", "ejlnnnlv", "rwqIf4htLN8vNMcv1fRcsTe9mP5yu3QS");
			connection = DriverManager.getConnection("jdbc:postgresql://baasu.db.elephantsql.com:5432/", "wxppvxzy", "BYCIz8Ni7PqJIcMIp9jAq7hP2MdwoCJz");

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT VERSION()");

			if (rs.next()) {
				System.out.println("DBHelper: The Database Version is " + rs.getString(1));
			}

		} catch (SQLException e) {

			System.out.println("DBHelper: Connection Failed! Check output console");
			e.printStackTrace();
			return null;

		}



		return connection;
	}

}
