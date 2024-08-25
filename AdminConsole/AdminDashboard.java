package AdminConsole;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import QuizGame.DBConnection;

public class AdminDashboard extends JFrame {
	/**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 1L;
	private JTextField quizTypeField;
	private JTextField questionField;
	private JTextField option1Field;
	private JTextField option2Field;
	private JTextField option3Field;
	private JTextField option4Field;
	private JTextField correctAnswerField;
	private JButton addButton;
	private JButton logoutButton;

	public AdminDashboard() {
		setTitle("Admin Dashboard");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titleLabel = new JLabel("Add New Question", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(titleLabel, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JLabel("Quiz Type: "), gbc);

		quizTypeField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(quizTypeField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(new JLabel("Question: "), gbc);

		questionField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(questionField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(new JLabel("Option 1: "), gbc);

		option1Field = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(option1Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(new JLabel("Option 2: "), gbc);

		option2Field = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panel.add(option2Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		panel.add(new JLabel("Option 3: "), gbc);

		option3Field = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(option3Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		panel.add(new JLabel("Option 4: "), gbc);

		option4Field = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 6;
		panel.add(option4Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		panel.add(new JLabel("Correct Answer: "), gbc);

		correctAnswerField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 7;
		panel.add(correctAnswerField, gbc);

		addButton = new JButton("Add Question");
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		addButton.addActionListener(new AddButtonListener());
		panel.add(addButton, gbc);

		logoutButton = new JButton("Logout");
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		logoutButton.addActionListener(e -> {
			// Close the admin dashboard and reopen the login page
			dispose();
			Loginadmin loginPage = new Loginadmin();
			loginPage.setVisible(true);
		});
		panel.add(logoutButton, gbc);

		add(panel);
	}

	// Action listener for the Add Question button
	private class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String quizType = quizTypeField.getText();
			String questionText = questionField.getText();
			String option1 = option1Field.getText();
			String option2 = option2Field.getText();
			String option3 = option3Field.getText();
			String option4 = option4Field.getText();
			String correctAnswer = correctAnswerField.getText();

			// Check if any field is empty
			if (quizType.isEmpty() || questionText.isEmpty() || option1.isEmpty() || option2.isEmpty()
					|| option3.isEmpty() || option4.isEmpty() || correctAnswer.isEmpty()) {

				JOptionPane.showMessageDialog(null, "All fields are mandatory. Please fill in all fields.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			question newQuestion = new question(questionText, new String[] { option1, option2, option3, option4 },
					correctAnswer);

			addQuestion(quizType, newQuestion);
		}
	}

	// Method to add a new question to the database
	private void addQuestion(String quizType, question question) {
		try {
			// Create a database connection
			DBConnection con = new DBConnection();
			Connection connection = con.DBConnection();

			// Insert a new question into the database for the specified quiz type
			String query = "INSERT INTO questions (quiz_type, question, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, quizType);
			preparedStatement.setString(2, question.getQuestion());
			String[] options = question.getOptions();
			for (int i = 0; i < options.length; i++) {
				preparedStatement.setString(i + 3, options[i]);
			}
			preparedStatement.setString(7, question.getCorrectAnswer());
			preparedStatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "Question added successfully!");

			// Clear fields after successful addition
			quizTypeField.setText("");
			questionField.setText("");
			option1Field.setText("");
			option2Field.setText("");
			option3Field.setText("");
			option4Field.setText("");
			correctAnswerField.setText("");

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to add question.");
		}
	}
}
