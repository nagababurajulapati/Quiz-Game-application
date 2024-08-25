package QuizGame;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	/**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private QuizGameGUI parentFrame;

	public LoginPanel(QuizGameGUI parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel heading = new JLabel("QUIZ GAME APPLICATION");
		heading.setFont(new Font("Arial", Font.BOLD, 24));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(heading, gbc);

		JLabel usernameLabel = new JLabel("Username:");
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(usernameLabel, gbc);

		usernameField = new JTextField(20);
		gbc.gridx = 1;
		add(usernameField, gbc);

		JLabel passwordLabel = new JLabel("Password:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(passwordLabel, gbc);

		passwordField = new JPasswordField(20);
		gbc.gridx = 1;
		add(passwordField, gbc);

		JButton loginButton = new JButton("Login");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		add(loginButton, gbc);

		JButton registerButton = new JButton("Register");
		gbc.gridy = 4;
		add(registerButton, gbc);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Handle login logic
					String username = usernameField.getText();
					String password = new String(passwordField.getPassword());
					if (authenticateUser(username, password)) {
						parentFrame.switchToPanel(new QuizPanel(parentFrame, QuizGameGUI.getConnection()));

						// parentFrame.switchToPanel("Quiz");
					} else {
						JOptionPane.showMessageDialog(null, "Invalid username or password");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parentFrame.switchToPanel(new RegistrationPanel(parentFrame));

				// parentFrame.switchToPanel("Registration");
			}
		});
	}

	private boolean authenticateUser(String username, String password) throws Exception {
		String url = "jdbc:mysql://localhost:3306/javaproject";
		String dbUsername = "root";
		String dbPassword = "naga@6281";

		Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
		String query = "SELECT * FROM credentials WHERE username = ? AND password = ?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		boolean authenticated = rs.next();
		rs.close();
		stmt.close();
		con.close();
		return authenticated;
	}
}
