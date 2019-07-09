/**
 * 
 */
package com.anncode.amazonviewer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.anncode.amazonviewer.db.DataBase.*;

/**
 * @author Usuario
 *
 */
public interface IDBConnection {
	
	default Connection connectToDB() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL + DB, USER, PASSWORD);
			if (connection != null) {
				System.out.println("Se estableció la conexión...");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		return connection;
		
	}

}
