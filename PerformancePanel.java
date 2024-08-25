package QuizGame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PerformancePanel extends JPanel {
	/**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 1L;
	private int score;
	private QuizGameGUI parentFrame;

	public PerformancePanel(QuizGameGUI parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(new BorderLayout());

		JLabel heading = new JLabel("Quiz Completed!");
		heading.setFont(new Font("Arial", Font.BOLD, 24));
		add(heading, BorderLayout.NORTH);

		JLabel scoreLabel = new JLabel("Your Score: " + score);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
		add(scoreLabel, BorderLayout.CENTER);

		JButton logoutButton = new JButton("Logout");
		add(logoutButton, BorderLayout.SOUTH);

		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parentFrame.switchToPanel(new LoginPanel(parentFrame));

				// parentFrame.switchToPanel("Login");
			}
		});
	}

	public void setScore(int score) {
		this.score = score;
	}
}
