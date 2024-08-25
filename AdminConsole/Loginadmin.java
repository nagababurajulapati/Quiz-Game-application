package AdminConsole;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import QuizGame.DBConnection;

public class Loginadmin extends JFrame {
	/**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 5178944843292705583L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton logoutButton;
	private boolean isLoggedIn = false;

	public Loginadmin() {
		setTitle("Admin Login");
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Username: "), gbc);

		usernameField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(usernameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JLabel("Password: "), gbc);

		passwordField = new JPasswordField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(passwordField, gbc);

		loginButton = new JButton("Login");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		loginButton.addActionListener(new LoginButtonListener());
		panel.add(loginButton, gbc);

		logoutButton = new JButton("Logout");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		logoutButton.addActionListener(new LogoutButtonListener());
		logoutButton.setVisible(true); // Initially hidden
		panel.add(logoutButton, gbc);

		add(panel);
	}

	// Action listener for the Login button
	private class LoginButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());

			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username and Password fields are mandatory.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (validateAdminCredentials(username, password)) {
					JOptionPane.showMessageDialog(null, "Login Successful");

					// Show the logout button after successful login
					logoutButton.setVisible(true);
					loginButton.setVisible(false);
					usernameField.setEnabled(false);
					passwordField.setEnabled(false);

					isLoggedIn = true;

					// Open the admin dashboard
					AdminDashboard adminDashboard = new AdminDashboard();
					adminDashboard.setVisible(true);
					// No need to close the login window here
					// Dispose login window will be handled in logout action
				} else {
					JOptionPane.showMessageDialog(null, "Login failed. Incorrect username or password.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	// Action listener for the Logout button
	private class LogoutButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (isLoggedIn) {
				JOptionPane.showMessageDialog(null, "Logged out successfully.");
			}

			// Close the application when logout is clicked
			dispose();
		}
	}

	// Method to validate admin credentials against the database
	private boolean validateAdminCredentials(String username, String password) {
		try {
			// Create a database connection
			DBConnection con = new DBConnection();
			Connection connection = con.DBConnection();

			// Query to check admin credentials
			String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				connection.close();
				return true;
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Loginadmin loginPage = new Loginadmin();
			loginPage.setVisible(true);
		});
	}
}
