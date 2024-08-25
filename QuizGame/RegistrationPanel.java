package QuizGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationPanel extends JPanel {
	
    /**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private QuizGameGUI parentFrame;

    public RegistrationPanel(QuizGameGUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel heading = new JLabel("Registration Page");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(heading, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(registerButton, gbc);
    	JButton logoutButton = new JButton("Back");
		gbc.gridy = 6;
		gbc.gridwidth = 4;
		add(logoutButton, gbc);
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Call the method to handle logout
				handleLogout();
			}
		});
	


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Handle registration logic
                    String email = emailField.getText();
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    if (isValidEmail(email) && !isEmailExists(email) && isValidUsername(username) && !isUsernameExists(username) && isValidPassword(password)) {
                        registerUser(username, password, email);
                        parentFrame.switchToPanel(new LoginPanel(parentFrame));
                        
                       // parentFrame.switchToPanel("Login");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input or email/username already exists");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$") && email.toLowerCase().endsWith("@gmail.com");
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$") && (username.length() >= 6 && username.length() <= 27);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 27;
    }

    private boolean isEmailExists(String email) throws Exception {
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String dbUsername = "root";
        String dbPassword = "naga@6281";
        Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
        String query = "SELECT * FROM credentials WHERE email = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next();
        rs.close();
        stmt.close();
        con.close();
        return exists;
    }

    private boolean isUsernameExists(String username) throws Exception {
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String dbUsername = "root";
        String dbPassword = "naga@6281";
        Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
        String query = "SELECT * FROM credentials WHERE username = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next();
        rs.close();
        stmt.close();
        con.close();
        return exists;
    }

    private void registerUser(String username, String password, String email) throws Exception {
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String dbUsername = "root";
        String dbPassword = "naga@6281";
        Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
        String query = "INSERT INTO credentials (username, password, email) VALUES (?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, email);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    private void handleLogout() {
		// Switch to the LoginPanel or initial panel
		// Assuming there's a LoginPanel class available
		LoginPanel loginPanel = new LoginPanel(parentFrame);
		parentFrame.switchToPanel(loginPanel);
	}
}
