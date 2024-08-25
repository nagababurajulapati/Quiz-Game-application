package QuizGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DBConnection class for establishing a connection to the MySQL database
public class DBConnection {
	/**
	 * @author Nagababu
	 */

	public static Connection connection;

	// Method to establish a database connection
	public Connection DBConnection() throws SQLException {
		// Initialize the database connection
		String url = "jdbc:mysql://localhost:3306/javaproject";
		String username = "root";
		String password = "naga@6281";

		try {
			// Attempt to establish a connection
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// Handle any potential SQL exceptions
			e.printStackTrace();
			throw e;
		}
		// Return the established connection
		return connection;
	}

	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return connection;
	}

}
