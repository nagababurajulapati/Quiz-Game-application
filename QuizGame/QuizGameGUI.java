package QuizGame;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuizGameGUI extends JFrame {
    /**
	 * @author Nagababu
	 */
	
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
    private JPanel mainPanel;
    static Connection connection;
    

    public QuizGameGUI() {
        setTitle("Quiz Game Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        JPanel loginPanel = new LoginPanel(this);
        JPanel registrationPanel = new RegistrationPanel(this);
        JPanel quizPanel = new QuizPanel(this, connection);
        JPanel performancePanel = new PerformancePanel(this);

        // Add panels to main panel
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(quizPanel, "Quiz");
        mainPanel.add(performancePanel, "Performance");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
        cardLayout.show(mainPanel, "LogOut"); // Show login panel first
    }

 

    public static void main(String[] args) throws SQLException {
    	 // Initialize the database connection
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String username = "root";
        String password = "naga@6281";
         connection = DriverManager.getConnection(url, username, password);
        SwingUtilities.invokeLater(() -> {
            QuizGameGUI frame = new QuizGameGUI();
            frame.setVisible(true);
        });
    }
    public void switchToPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }


	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return connection;
	}



	

	
}
