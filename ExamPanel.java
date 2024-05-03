import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamPanel extends JFrame {

    //FONTS
    static Font font32bold = new Font("Courier New", Font.BOLD, 32);
    static Font font20bold = new Font("Courier New", Font.BOLD, 20);
    static Font font16plain = new Font("Courier New", Font.PLAIN, 16);
    static Font getFont16MTbold = new Font("Arial Rounded MT Bold", Font.PLAIN, 16);
    static Font font14plain = new Font("Courier New", Font.PLAIN, 14);
    static Font font14bold = new Font("Courier New", Font.BOLD, 14);
    static Font font12plain = new Font("Courier New", Font.BOLD, 12);
    static int numberOfQuestions; /* specify the number of questions */
    private final String[] questions;
    private final String[][] options;
    private final Border border1 = BorderFactory.createEmptyBorder();
    private final Border border2 = BorderFactory.createEmptyBorder(20, 50, 20, 150);
    ButtonGroup buttonGroup;
    private JLabel timeLabel, timeLabel1;
    private JButton startExam;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton previousQuestionButton;
    private JButton nextQuestionButton, submitButton;
    private String userId;

    public ExamPanel() {

        initializeUI();
        Database.createConnectionForExamPanel();
        login();
        numberOfQuestions = Database.NumberOfQuestion();
        questions = new String[numberOfQuestions];
        options = new String[numberOfQuestions][4];
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamPanel());
    }

    private void initializeUI() {
        setLayout(null);
        setSize(465, 550);
        setLocation(new Point(850, 20));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //REGISTER METHOD
    private void register() {

        JLabel newUserLabel, userIdLabel, nameLabel, mobileLabel, passwordLabel, alreadyRegLabel;
        JTextField userIdField, nameField, mobileField;
        JPasswordField passwordField;
        JButton register, login;

        newUserLabel = new JLabel("New User");
        newUserLabel.setBounds(180, 50, 200, 35);
        newUserLabel.setFont(font20bold);
        this.add(newUserLabel);

        userIdLabel = new JLabel("User Id");
        userIdLabel.setBounds(100, 100, 200, 35);
        userIdLabel.setFont(font16plain);
        this.add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(100, 130, 250, 35);
        userIdField.setFont(font14plain);
        userIdField.setBorder(border1);
        this.add(userIdField);

        nameLabel = new JLabel("Name");
        nameLabel.setBounds(100, 170, 250, 35);
        nameLabel.setFont(font16plain);
        this.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 200, 250, 35);
        nameField.setFont(font14plain);
        nameField.setBorder(border1);
        this.add(nameField);

        mobileLabel = new JLabel("Mobile Number");
        mobileLabel.setBounds(100, 240, 250, 35);
        mobileLabel.setFont(font16plain);
        this.add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(100, 270, 250, 35);
        mobileField.setFont(font14plain);
        mobileField.setBorder(border1);
        this.add(mobileField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 310, 250, 35);
        passwordLabel.setFont(font16plain);
        this.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 340, 250, 35);
        passwordField.setFont(font14plain);
        passwordField.setBorder(border1);
        this.add(passwordField);


        register = new JButton("Register");
        register.setBounds(100, 390, 250, 35);
        register.setFont(font20bold);
        register.setBorder(border1);
        this.add(register);

        alreadyRegLabel = new JLabel("already registered user");
        alreadyRegLabel.setBounds(100, 430, 250, 30);
        alreadyRegLabel.setFont(font12plain);
        this.add(alreadyRegLabel);


        login = new JButton("Login");
        login.setBounds(270, 435, 80, 20);
        login.setFont(font14bold);
        login.setBackground(Color.GREEN);
        login.setForeground(Color.WHITE);
        login.setBorder(border1);
        this.add(login);


        //Lode new method
        register.addActionListener(e -> {
            String name = nameField.getText();
            String id = userIdField.getText();
            String mob = mobileField.getText();
            String pass = passwordField.getText();

            if (id.isEmpty() || name.isEmpty() || mob.isEmpty() || pass.isEmpty()) {
                System.out.println("Fill all fields");
            } else {
                System.out.println();
                String userID;
                String userName;
                String userPassword;
                String userMobile;
                userID = userIdField.getText();
                userName = nameField.getText();
                userMobile = mobileField.getText();
                userPassword = passwordField.getText();


                try {
                    Database.insertDataInTable(userID, userName, userMobile, userPassword);
                    System.out.println("Registration Successfully");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                remove(newUserLabel);
                remove(userIdLabel);
                remove(userIdField);
                remove(nameLabel);
                remove(nameField);
                remove(mobileLabel);
                remove(mobileField);
                remove(passwordLabel);
                remove(passwordField);
                remove(register);
                remove(login);
                remove(alreadyRegLabel);

                // Repaint the frame to reflect the changes
                revalidate();
                repaint();

                // Now, call the login method
                login();
            }
        });


        //Lode new method
        login.addActionListener(e -> {
            System.out.println("Loading login page");
            // Remove login components
            remove(newUserLabel);
            remove(userIdLabel);
            remove(userIdField);
            remove(nameLabel);
            remove(nameField);
            remove(mobileLabel);
            remove(mobileField);
            remove(passwordLabel);
            remove(passwordField);
            remove(register);
            remove(login);
            remove(alreadyRegLabel);

            // Repaint the frame to reflect the changes
            revalidate();
            repaint();

            // Now, call the login method
            login();

        });
    }

    //LOGIN METHOD
    public void login() {
        //Components
        JLabel loginLabel, userIdLabel, passwordLabel, newUserLabel;
        JTextField userIdField;
        JPasswordField userPasswordField;
        JButton login, register;

        //USE
        loginLabel = new JLabel("User Login");
        loginLabel.setBounds(160, 20 + 100, 200, 35);
        loginLabel.setFont(font20bold);
        this.add(loginLabel);

        userIdLabel = new JLabel("User Id");
        userIdLabel.setBounds(100, 70 + 100, 250, 35);
        userIdLabel.setFont(font16plain);
        this.add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(100, 100 + 100, 250, 35);
        userIdField.setFont(font14plain);
        userIdField.setBorder(border1);
        this.add(userIdField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 140 + 100, 250, 35);
        passwordLabel.setFont(font16plain);
        this.add(passwordLabel);

        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(100, 170 + 100, 250, 35);
        userPasswordField.setFont(font14plain);
        userPasswordField.setBorder(border1);
        this.add(userPasswordField);

        login = new JButton("Login");
        login.setBounds(100, 225 + 100, 250, 35);
        login.setFont(font20bold);
        login.setBorder(border1);
        this.add(login);

        newUserLabel = new JLabel("new user register here");
        newUserLabel.setBounds(100, 265 + 100, 250, 30);
        newUserLabel.setFont(font12plain);
        this.add(newUserLabel);

        register = new JButton("Register");
        register.setBounds(265, 270 + 100, 85, 20);
        register.setFont(font14bold);
        register.setBackground(Color.GREEN);
        register.setForeground(Color.WHITE);
        register.setBorder(border1);
        this.add(register);

        login.addActionListener(e -> {
            String id = userIdField.getText();
            String pass = userPasswordField.getText();

            if (id.isEmpty() || pass.isEmpty()) {
                System.out.println("Fill all fields");
            } else {
                userId = userIdField.getText();
                String password = userPasswordField.getText();

                if (Database.loginValidation(userId, password)) {
                    //Lode new method
                    remove(loginLabel);
                    remove(userIdLabel);
                    remove(userIdField);
                    remove(passwordLabel);
                    remove(userPasswordField);
                    remove(login);
                    remove(newUserLabel);
                    remove(register);
                    // Get the user's name from the welcome method
                    String name = Database.welcome(userId);
                    welcomeScreen(name);
                    System.out.println("Welcome");
                    System.out.println("Login Successfully");
                } else {
                    System.out.println("Invalid Login Credential");
                }
            }
        });

        //Lode new method
        register.addActionListener(e -> {
            System.out.println("Loading registration page");
            // Remove login components
            remove(loginLabel);
            remove(userIdLabel);
            remove(userIdField);
            remove(passwordLabel);
            remove(userPasswordField);
            remove(login);
            remove(newUserLabel);
            remove(register);

            // Repaint the frame to reflect the changes
            revalidate();
            repaint();

            // Now, call the register method
            register();
        });
    }

    private void welcomeScreen(String name) {
/*
        setExtendedState(JFrame.MAXIMIZED_BOTH);
*/
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
      /*  addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                // Check if the frame is in a maximized state
                if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    // If the frame is maximized, set it to maximized again if minimized
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }
        });*/
        JLabel welcomeLabel = new JLabel("Welcome " + name);
        welcomeLabel.setBounds(50, 30, getWidth(), 35);
        welcomeLabel.setFont(font32bold);
        welcomeLabel.setBackground(Color.RED);
        add(welcomeLabel);
        JLabel hrLine = new JLabel();
        hrLine.setBounds(0, 20, getWidth(), 100);
        hrLine.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.GREEN));
        add(hrLine);
        repaint();


        // Create the JLabel to display the time
        timeLabel = new JLabel();
        int xCoordinate = getWidth() - timeLabel.getWidth() - 200;
        timeLabel.setBounds(xCoordinate, 20, getWidth(), 35);
        add(timeLabel);

        // Create a Timer to update the time every second (1000 milliseconds)
        Timer timer = new Timer(1000, e -> {
            // Update the timeLabel with the current time
            updateTime();
        });
        timer.start(); // Start the timer
        // Initialize the timeLabel with the current time
        updateTime();

        // Create the JLabel to display the time
        timeLabel1 = new JLabel();
        timeLabel1.setForeground(Color.RED);
        int xCoordinate1 = getWidth() - timeLabel1.getWidth() - 200;
        timeLabel1.setBounds(xCoordinate1 + 20, 80, getWidth(), 35);


        // Create the JLabel to display the time
        startExam = new JButton("Start Exam");
        startExam.setBounds(xCoordinate1, 80, 150, 35);
        add(startExam);


        startExam.addActionListener(e -> {
            add(timeLabel1);
            startTimer();
            remove(startExam);

            welcomeScreenContent();
            displayQuiz();

            repaint();
            revalidate();
        });
    }

    private void welcomeScreenContent() {
        Database.welcomeScreenQuestion(questions, options);
        questionLabel = new JLabel();
        questionLabel.setFont(getFont16MTbold);
        questionLabel.setBounds(50, 200, getWidth(), 35);
        add(questionLabel);

        buttonGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 240 + i * 40, getWidth(), 35);
            buttonGroup.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        previousQuestionButton = new JButton("Previous");
        previousQuestionButton.setBounds(50, 450, 120, 40);
        add(previousQuestionButton);

        nextQuestionButton = new JButton("Next");
        nextQuestionButton.setBounds(200, 450, 120, 40);
        add(nextQuestionButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 450, 120, 40);

        submitButton.addActionListener(e -> {
            submitQuiz();
        });

        previousQuestionButton.addActionListener(e -> {
            prevQuestion();
        });

        nextQuestionButton.addActionListener(e -> {
            checkAnswer();
            nextQuestion();
        });
    }

    private void prevQuestion(){
        currentQuestionIndex--;
        if (currentQuestionIndex < 0) {
            currentQuestionIndex = 0;
        }
        displayQuiz();
        nextQuestionButton.setVisible(true);
        submitButton.setVisible(false);
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= questions.length) {
            currentQuestionIndex = questions.length - 1;
            nextQuestionButton.setVisible(false);
            submitButton.setVisible(true);
            add(submitButton);
        }
        displayQuiz();
    }

    private void submitQuiz(){
        questionLabel.setText("Your Exam Submitted..");

        System.out.println("Your Score: " + score + " out of " + questions.length);
        Database.result(score, userId);

        // Hide buttons
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setVisible(false);
        }
        previousQuestionButton.setVisible(false);
        nextQuestionButton.setVisible(false);
        submitButton.setVisible(false);
    }

    private void displayQuiz() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText((currentQuestionIndex + 1) + ". " + questions[currentQuestionIndex]);
            String[] questionOptions = options[currentQuestionIndex];

            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questionOptions[i]);
                optionButtons[i].setSelected(false);
                optionButtons[i].setFont(getFont16MTbold);
            }

        } else {
            // All questions have been displayed
            questionLabel.setText("Your Score: " + score + " out of " + questions.length);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setVisible(false);
            }
            previousQuestionButton.setVisible(false);
            nextQuestionButton.setVisible(false);
        }
        repaint();
        revalidate();
    }

    private void checkAnswer() {
        String correctAnswer = Database.getAnswerFromDatabase(currentQuestionIndex + 1);
        int selectedOptionIndex = getSelectedOptionIndex();
        if (selectedOptionIndex != -1) {
            String selectedAnswer = options[currentQuestionIndex][selectedOptionIndex];
            if (selectedAnswer.equals(correctAnswer)) {
                score++;
                System.out.println("Correct " + score);
            }
        }
    }

    private int getSelectedOptionIndex() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                return i;
            }
        }
        return -1; // No option selected
    }

    private void updateTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        // Update the timeLabel
        timeLabel.setText("Current Time: " + currentTime);
    }

    private void startTimer() {
        // Create a timer to display the remaining time
        Timer timer = new Timer(1000, new ActionListener() {
            int timeLeft = 5400; // 60 minutes in seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft >= 0) {
                    int hours = timeLeft / 3600;
                    int minutes = (timeLeft % 3600) / 60;
                    int seconds = timeLeft % 60;
                    timeLabel1.setText("Time Left: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                    timeLeft--;
                } else {
                    // Handle time's up logic
                    timeLabel1.setText("Quiz Exam - Time's Up!");
                    ((Timer) e.getSource()).stop(); // Stop the timer
                }
            }
        });
        timer.start();
    }


}

