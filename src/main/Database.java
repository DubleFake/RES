package main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Database {

	
	
	public static void addEntry(String data, int n) {
		try {
		Scanner r = new Scanner(new File("password.dat"));
		final String password = r.nextLine();
		r.close();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", password);
		String query = "INSERT INTO `res`.`saves` (`Encrypted_text`, `N_value`) VALUES (?,?)";
		PreparedStatement preparedStmnt = con.prepareStatement(query);
		preparedStmnt.setString(1, data);
		preparedStmnt.setInt(2, n);
		preparedStmnt.execute();
		con.close();
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
		}


	}
	
	public static String[] loadEntry() {
		
		String[] result = new String[2];
		
		try {
			
			Scanner r = new Scanner(new File("password.dat"));
			final String password = r.nextLine();
			r.close();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", password);
			String query = "SELECT * FROM `res`.`saves` ORDER BY `ID` DESC LIMIT 1";
			PreparedStatement preparedStmnt = con.prepareStatement(query);
			ResultSet rs = preparedStmnt.executeQuery();
			if(rs.next()) {
			
				result[0] = rs.getString("Encrypted_text");
				result[1] = Integer.toString(rs.getInt("N_value"));
				
			}
			con.close();
			return result;
			}catch(Exception ex) {
			
				ex.printStackTrace();
			
		}
		return result;
		
	}
	
}
