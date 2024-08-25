package QuizGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizPanel extends JPanel {
	 /**
		 * @author Nagababu
		 */
		
	private JComboBox<String> quizTypeComboBox;
	private JTextField numberOfQuestionsField;
	private JButton startQuizButton;
	private JButton logoutButton;
	private QuizGameGUI parentFrame;
	private Connection connection;
	private List<Question> questions;
	private int numQuestions;

	public QuizPanel(QuizGameGUI parentFrame, Connection connection) {
		this.parentFrame = parentFrame;
		this.connection = connection;
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel heading = new JLabel("Select Quiz Type and Number of Questions");
		heading.setFont(new Font("Arial", Font.BOLD, 24));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(heading, gbc);

		JLabel quizTypeLabel = new JLabel("Quiz Type:");
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		add(quizTypeLabel, gbc);

		quizTypeComboBox = new JComboBox<>();
		gbc.gridx = 1;
		add(quizTypeComboBox, gbc);

		JLabel numQuestionsLabel = new JLabel("Number of Questions:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(numQuestionsLabel, gbc);

		numberOfQuestionsField = new JTextField(5);
		gbc.gridx = 1;
		add(numberOfQuestionsField, gbc);

		startQuizButton = new JButton("Start Quiz");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		add(startQuizButton, gbc);

		logoutButton = new JButton("Logout");
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		add(logoutButton, gbc);

		// Populate quiz types from database
		populateQuizTypes();

		startQuizButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String quizType = (String) quizTypeComboBox.getSelectedItem();
					numQuestions = Integer.parseInt(numberOfQuestionsField.getText());
					questions = getQuestionsFromDatabase(quizType);

					if (questions.size() >= numQuestions) {
						List<Question> selectedQuestions = questions.subList(0, numQuestions);
						QuizDisplayPanel quizDisplayPanel = new QuizDisplayPanel(parentFrame, selectedQuestions);
						parentFrame.switchToPanel(quizDisplayPanel);
					} else {
						JOptionPane.showMessageDialog(null, "Not enough questions available.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number of questions.");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Call the method to handle logout
				handleLogout();
			}
		});
	}

	private void populateQuizTypes() {
		quizTypeComboBox.removeAllItems(); // Clear existing items
		try {
			String query = "SELECT DISTINCT quiz_type FROM questions";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				quizTypeComboBox.addItem(resultSet.getString("quiz_type"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Question> getQuestionsFromDatabase(String quizType) {
		List<Question> questions = new ArrayList<>();
		try {
			String query = "SELECT question, option1, option2, option3, option4, correct_answer FROM questions WHERE quiz_type = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, quizType);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String questionText = resultSet.getString("question");
				String[] options = new String[] { resultSet.getString("option1"), resultSet.getString("option2"),
						resultSet.getString("option3"), resultSet.getString("option4") };
				String correctAnswer = resultSet.getString("correct_answer");
				questions.add(new Question(questionText, options, correctAnswer));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}

	private void handleLogout() {
		// Switch to the LoginPanel or initial panel
		// Assuming there's a LoginPanel class available
		LoginPanel loginPanel = new LoginPanel(parentFrame);
		parentFrame.switchToPanel(loginPanel);
	}
}
