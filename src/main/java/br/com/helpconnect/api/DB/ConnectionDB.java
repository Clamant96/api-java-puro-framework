package br.com.helpconnect.api.DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
	
public static Connection getConnection() {
		
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lista_pessoas", "root", "Edkaike1");
		
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return connection;
	}

}
