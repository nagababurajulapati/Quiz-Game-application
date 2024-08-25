package QuizGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizDisplayPanel extends JPanel {
    /**
	 * @author Nagababu
	 */
	private static final long serialVersionUID = 1L;
	private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private JTextArea questionArea;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private JButton finishButton;
    private QuizGameGUI parentFrame;

    public QuizDisplayPanel(QuizGameGUI parentFrame, List<Question> questions) {
        this.parentFrame = parentFrame;
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel heading = new JLabel("Quiz");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(heading, gbc);

        questionArea = new JTextArea(3, 30);
        questionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(questionArea);
        gbc.gridy = 1;
        add(scrollPane, gbc);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        gbc.gridy = 2;
        add(optionsPanel, gbc);

        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(nextButton, gbc);

        finishButton = new JButton("Finish");
        finishButton.setEnabled(false);
        gbc.gridx = 1;
        add(finishButton, gbc);

        loadQuestion();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadQuestion();
                } else {
                    nextButton.setEnabled(false);
                    finishButton.setEnabled(true);
                }
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                JOptionPane.showMessageDialog(null, "Your score: " + score + "/" + questions.size());
                parentFrame.switchToPanel(new QuizPanel(parentFrame, parentFrame.getConnection()));
            }
        });
    }

    private void loadQuestion() {
        if (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            JOptionPane.showMessageDialog(null, "No more questions available.");
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        questionArea.setText(question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
        }
    }

    private void checkAnswer() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return; // Or handle this case more explicitly
        }

        Question question = questions.get(currentQuestionIndex);
        String selectedOption = null;
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                selectedOption = button.getText();
                break;
            }
        }
        if (selectedOption != null && selectedOption.equals(question.getCorrectAnswer())) {
            score++;
        }
    }
}
