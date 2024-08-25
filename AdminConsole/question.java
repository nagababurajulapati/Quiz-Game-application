// Import necessary packages
package AdminConsole;

// Question class to represent quiz questions
public class question {
    private String question;
    private String[] options;
    private String correctAnswer;

    // Constructor to initialize a Question object
    public question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Setter method for updating the question text
    public void setQuestion(String question) {
        this.question = question;
    }

    // Getter method to retrieve the options
    public String[] getOptions() {
        return options;
    }

    // Setter method for updating the options
    public void setOptions(String[] options) {
        this.options = options;
    }

    // Getter method to retrieve the correct answer
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Setter method for updating the correct answer
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Getter method to retrieve the question text
    public String getQuestion() {
        return question;
    }
}
