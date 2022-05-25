package ProgramFile;
//I imported the javax.swing package, which was built into the java programming language
import javax.swing.*;

//I imported the javax.swing.table package, which was built into the java programming language
import javax.swing.table.*;

//I imported the java.awt package, which was built into the java programming language
import java.awt.*;

//I imported the java.awt.event package, which was built into the java programming language
import java.awt.event.*;

//I imported the java.util package, which was built into the java programming language
import java.util.*;

//I imported the List class from the java.util package, which is built into the java programming language
import java.util.List;

//I imported the java.util.concurrent package, which was built into the java programming language
import java.util.concurrent.*;

public class BaseConversionGame extends JFrame implements ActionListener {
    private static int widthOfDisplay = 1280;
    private static int heightOfDisplay = 720;

    public static int[] randomizedLocation;

    public static int[] topLeft = {200, 70};
    public static int[] topRight = {665, 70};
    public static int[] bottomLeft = {200, 405};
    public static int[] bottomRight = {665, 405};

    private JList originalBase, convertBase, numQuestionsList;
    private JLabel conversionQuestion, statCorrect, statAccuracy, statIncorrect, statScore, onQuestionNum, correctLabel, conversionType, questionReviewTitle;
    private JTable correctQuestionTable, incorrectQuestionTable;
    private JButton answer1, answer2, answer3, answer4;
    private JPanel conversionPanel, quizPanel, welcomePanel, infoPanel, numQuestionsPanel, difficultyPanel, userStaticsPanel, questionReviewPanel;
    private JFrame frame;

    public String originalOption, convertedOption;

    public int questionNumber, numAttempts, numCorrect,  numIncorrect;
    public Random random = new Random();
    public int difficultyLevel = 0;

    public ArrayList<String> binaryArray, hexadecimalArray, octalArray, questionData, isCorrect;
    public List<ArrayList<String>> quesReviewData = new ArrayList<ArrayList<String>>();
    public DefaultTableModel tableUpdater1, tableUpdater2;

    public BaseConversionGame() {
        frame = new JFrame("Base Conversion Game");
        frame.setSize(widthOfDisplay, heightOfDisplay);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conversionPanel = new JPanel();
        quizPanel = new JPanel();
        welcomePanel = new JPanel();
        infoPanel = new JPanel();
        numQuestionsPanel = new JPanel();
        userStaticsPanel = new JPanel();
        questionReviewPanel = new JPanel();
        difficultyPanel = new JPanel();

        conversionPanel.setLayout(null);
        quizPanel.setLayout(null);
        welcomePanel.setLayout(null);
        infoPanel.setLayout(null);
        numQuestionsPanel.setLayout(null);
        userStaticsPanel.setLayout(null);
        questionReviewPanel.setLayout(null);
        difficultyPanel.setLayout(null);

        JLabel welcome = new JLabel("Base Conversion Game"); //The name of the program is displayed on the welcome menu as a title
        welcome.setFont(new Font("Serif", Font.PLAIN, 60));
        welcome.setBounds(350, 0, 600, 60);
        welcomePanel.add(welcome);

        JButton start = new JButton("Start"); //The button starts the program/allows the user to continue on to the next panel
        start.setFont(new Font("Serif", Font.PLAIN, 40));
        start.setBounds(200, 100, 300, 400);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(welcomePanel);
                frame.setContentPane(conversionPanel);
                frame.validate();
                frame.setVisible(true);
            }
        });
        welcomePanel.add(start);

        JButton infoButton = new JButton("How to Play"); //This button opens the panel with the directions of the game
        infoButton.setFont(new Font("Serif", Font.PLAIN, 40));
        infoButton.setBounds(700, 100, 300, 400);
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(welcomePanel);
                frame.setContentPane(infoPanel);
                frame.validate();
                frame.setVisible(true);
            }
        });
        welcomePanel.add(infoButton);

        JButton infoBack = new JButton("Back"); //This button brings the user back to the welcome panel from the infoPanel
        infoBack.setFont(new Font("Serif", Font.PLAIN, 40));
        infoBack.setBounds(300, 500, 250, 45);
        infoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(infoPanel);
                frame.setContentPane(welcomePanel);
                frame.validate();
                frame.setVisible(true);
            }
        });
        infoPanel.add(infoBack);

        JLabel infoTitle = new JLabel("HOW TO PLAY"); //The title of the info panel is displayed
        infoTitle.setFont(new Font("Serif", Font.PLAIN, 40));
        infoTitle.setBounds(300, 0, 320, 60);
        infoPanel.add(infoTitle);

        String howToPlay = "1. Press start <br>" +
                "2. Choose the base conversion you want to practice <br>" +
                "3. Press next <br>" +
                "4. Choose how many questions you want and then press next <br>" +
                "5. After you finish all the questions, you'll see how well you did/your statics <br>" +
                "6. After checking your statics, press next to continue to review your questions <br>" +
                "7. After you to review your questions you got correct/incorrect, you can choose to return to the base conversion menu or go back to the statics panel <br>" +
                "TIP: <br>- The maximum value for the bases is " + difficultyLevel + " (in decimal values)";
        JLabel info = new JLabel("<html>" + howToPlay + "</html>"); //Displays the directions of the game/howToPlay (shown above)
        info.setFont(new Font("Serif", Font.PLAIN, 23));
        info.setBounds(300, 80, 700, 400);
        infoPanel.add(info);

        JLabel baseConversionTitle = new JLabel("Base Conversion"); //Displays the base conversion title on the conversionPanel
        baseConversionTitle.setFont(new Font("Serif", Font.PLAIN, 60));
        baseConversionTitle.setBounds(435, 0, 800, 80);
        conversionPanel.add(baseConversionTitle);

        JLabel origBaseLabel = new JLabel("Original Base"); //Displays the title for the list of the original base that the user wants
        origBaseLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        origBaseLabel.setBounds(235, 130, 180, 40);
        conversionPanel.add(origBaseLabel);

        JLabel convertNumBaseLabel = new JLabel("Converted Base"); //Displays the title for the list of bases the user wants to convert to
        convertNumBaseLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        convertNumBaseLabel.setBounds(870, 130, 200, 40);
        conversionPanel.add(convertNumBaseLabel);

        String[] numberBaseArray = {"Binary", "Octal", "Decimal", "Hexadecimal"};

        originalBase = new JList(numberBaseArray); //The list of original bases that the user wants
        originalBase.setFont(new Font("Serif", Font.PLAIN, 30));
        JScrollPane originalNumBaseGUI = new JScrollPane(originalBase); //Displays the originalBase list
        originalNumBaseGUI.setBounds(100, 170, 440, 370);
        conversionPanel.add(originalNumBaseGUI);

        convertBase = new JList(numberBaseArray); //The list of bases that the user wants to convert to
        convertBase.setFont(new Font("Serif", Font.PLAIN, 30));
        JScrollPane convertNumBaseGUI = new JScrollPane(convertBase); //Displays the convertBase list
        convertNumBaseGUI.setBounds(740, 170, 440, 370);
        conversionPanel.add(convertNumBaseGUI);

        JLabel originalToConvertText = new JLabel("To"); //Displays the word "To" to show that the original base selected on the left is being converted to the selected base on the right
        originalToConvertText.setFont(new Font("Serif", Font.PLAIN, 60));
        originalToConvertText.setBounds(605, 300, 70, 50);
        conversionPanel.add(originalToConvertText);

        JButton conversionNext = new JButton("Next"); //This button changes the panel to the numQuestionPanel
        conversionNext.setFont(new Font("Serif", Font.PLAIN, 30));
        conversionNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List selectedOrigBase = originalBase.getSelectedValuesList();
                List selectedConvertBase = convertBase.getSelectedValuesList();

                if ((selectedOrigBase.toArray().length == 1 && selectedConvertBase.toArray().length == 1) && originalBase.getSelectedValue() != convertBase.getSelectedValue()) {
                    String origBase = selectedOrigBase.toString().substring(1, selectedOrigBase.toString().length() - 1);
                    String convertBase = selectedConvertBase.toString().substring(1, selectedConvertBase.toString().length() - 1);

                    conversionType.setText(origBase + " to " + convertBase);
                    questionReviewTitle.setText(origBase + " to " + convertBase + " Question Review");

                    frame.remove(conversionPanel);
                    frame.setContentPane(difficultyPanel);
                    frame.validate();
                } else { //An error message on the next line of code will show if certain conditions in the if statement above was not met
                    String warningMessage = "Please selected a value/one value for the original number <br>" +
                            "base system and/or for the number base system to convert to.<br>" +
                            "Also make sure the two values are not the same.";
                    JOptionPane.showMessageDialog(frame, "<html>" + warningMessage + "</html>", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        conversionNext.setBounds(680, 580, 200, 80);
        conversionPanel.add(conversionNext);

        JLabel difficultyLabel = new JLabel("Select The Difficulty");
        difficultyLabel.setFont(new Font("Serif", Font.BOLD, 60));
        difficultyLabel.setBounds(435, 0, 800, 80);
        difficultyPanel.add(difficultyLabel);

         // Button difficulty (Making sure that you have the appropriate value for the oringalBase (ex. gettting 4, 8, and 16 binary places for the easy, medium, and hard difficulities respectively)).
        JButton difficultyEasy = new JButton("Easy");
        difficultyEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(originalBase.getSelectedValue() == "Binary") {
                    difficultyLevel = 16;
                } else if(originalBase.getSelectedValue() == "Octal") {
                    difficultyLevel = 64;
                } else if(originalBase.getSelectedValue() == "Decimal") {
                    difficultyLevel = 100;
                } else {
                    difficultyLevel = 256;
                }
            }
        });
        difficultyEasy.setFont(new Font("Serif", Font.PLAIN, 30));
        difficultyEasy.setBounds(400, 120, 600, 120);
        difficultyPanel.add(difficultyEasy);

        JButton difficultyMedium = new JButton("Medium");
        difficultyMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                if(originalBase.getSelectedValue() == "Binary") {
                    difficultyLevel = 128;
                } else if(originalBase.getSelectedValue() == "Octal") {
                    difficultyLevel = 512;
                } else if(originalBase.getSelectedValue() == "Decimal") {
                    difficultyLevel = 1000;
                } else {
                    difficultyLevel = 4096;
                }
            }
        });
        difficultyMedium.setFont(new Font("Serif", Font.PLAIN, 30));
        difficultyMedium.setBounds(400, 250, 600, 120);
        difficultyPanel.add(difficultyMedium);

        JButton difficultyHard = new JButton("Hard");
        difficultyHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(originalBase.getSelectedValue() == "Binary") {
                    difficultyLevel = 65536;
                } else if(originalBase.getSelectedValue() == "Octal") {
                    difficultyLevel = 4096;
                } else if(originalBase.getSelectedValue() == "Decimal") {
                    difficultyLevel = 10000;
                } else {
                    difficultyLevel = 65536;
                }
            }
        });
        difficultyHard.setFont(new Font("Serif", Font.PLAIN, 30));
        difficultyHard.setBounds(400, 380, 600, 120);
        difficultyPanel.add(difficultyHard);


        JButton difficultyNextButton = new JButton("Next");
        difficultyNextButton.setFont(new Font("Serif", Font.BOLD, 60));
        difficultyNextButton.setBounds(580, 550, 250, 100);
        difficultyNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(difficultyPanel);
                frame.setContentPane(numQuestionsPanel);
                frame.validate();
            }
        });
        difficultyPanel.add(difficultyNextButton);

        JButton conversionBack = new JButton("Back"); //This button brings the user back to the welcome panel from the conversionPanel
        conversionBack.setFont(new Font("Serif", Font.PLAIN, 30));
        conversionBack.setBounds(400, 580, 200, 80);
        conversionBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(conversionPanel);
                frame.setContentPane(welcomePanel);
                frame.validate();
            }
        });
        conversionPanel.add(conversionBack);

        JLabel numQuestions = new JLabel("Selected How Many Questions You Want"); //Displays a question to ask how many questions the user wants
        numQuestions.setFont(new Font("Serif", Font.PLAIN, 40));
        numQuestions.setBounds(270, 0, 800, 80);
        numQuestionsPanel.add(numQuestions);

        ArrayList<Integer> listOfNumberOfQuestions = NumberOfMathQuestions(); //Calls the NumberOfMathQuestion function to setup the different numbers of question created that it created
        numQuestionsList = new JList(listOfNumberOfQuestions.toArray()); //
        numQuestionsList.setFont(new Font("Serif", Font.PLAIN, 31));
        JScrollPane numMathQuestionsListGUI = new JScrollPane(numQuestionsList); //Displays the numQuestionsList list
        numMathQuestionsListGUI.setBounds(240, 100, 800, 450);
        numQuestionsPanel.add(numMathQuestionsListGUI);

        JButton play = new JButton("Play"); //This button starts the quiz with the options that the user has selected
        play.setFont(new Font("Serif", Font.PLAIN, 30));
        play.setBounds(690, 580, 240, 80);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Binary();
                Octal();
                Hexadecimal();

                questionNumber = 1;
                numCorrect = 0;
                numIncorrect = 0;
                List numberOfQuestions = numQuestionsList.getSelectedValuesList();
                if (numberOfQuestions.toArray().length > 1 || numberOfQuestions == null) { //An error message on the next line of code will show if certain conditions are true
                    JOptionPane.showMessageDialog(frame, "<html> Please selected a value/one value </html>", "Warning", JOptionPane.ERROR_MESSAGE);
                } else {
                    quesReviewData.clear();
                    onQuestionNum.setText("Question #: " + questionNumber);
                    correctLabel.setText("Correct: " + numCorrect);
                    frame.remove(numQuestionsPanel);
                    questionScreen();
                }
            }
        });
        numQuestionsPanel.add(play);

        JButton numQuestionsBack= new JButton("Back"); //This button will bring the user back to the conversion panel from the numQuestionsPanel
        numQuestionsBack.setFont(new Font("Serif", Font.PLAIN, 30));
        numQuestionsBack.setBounds(350, 580, 240, 80);
        numQuestionsBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(numQuestionsPanel);
                frame.setContentPane(conversionPanel);
                frame.validate();
            }
        });
        numQuestionsPanel.add(numQuestionsBack);

        conversionType = new JLabel(); //The type of base conversion the user selected to practice is displayed above the number in original base/question
        conversionType.setFont(new Font("Serif", Font.PLAIN, 20));
        conversionType.setBounds(200, 0, 500, 30);
        quizPanel.add(conversionType);

        conversionQuestion = new JLabel(); //The question/the number in the original base is displayed
        conversionQuestion.setFont(new Font("Serif", Font.PLAIN, 30));
        conversionQuestion.setBounds(200, 0, 300, 80);
        quizPanel.add(conversionQuestion);

        answer1 = new JButton(); //This creates one button for one of the answer choices to be on
        answer1.setFont(new Font("Serif", Font.PLAIN, 30));
        answer1.setSize(415, 285);
        answer1.addActionListener(this);
        quizPanel.add(answer1);

        answer2 = new JButton(); //This creates one button for one of the answer choices to be on
        answer2.setFont(new Font("Serif", Font.PLAIN, 30));
        answer2.setSize(415, 285);
        answer2.addActionListener(this);
        quizPanel.add(answer2);

        answer3 = new JButton(); //This creates one button for one of the answer choices to be on
        answer3.setFont(new Font("Serif", Font.PLAIN, 30));
        answer3.setSize(415, 285);
        answer3.addActionListener(this);
        quizPanel.add(answer3);

        answer4 = new JButton(); //This creates one button for one of the answer choices to be on
        answer4.setFont(new Font("Serif", Font.PLAIN, 30));
        answer4.setSize(415, 285);
        answer4.addActionListener(this);
        quizPanel.add(answer4);

        onQuestionNum = new JLabel(); //The question that the user is on is displayed on the right hand side of the GUI
        onQuestionNum.setFont(new Font("Serif", Font.PLAIN, 25));
        onQuestionNum.setBounds(900, 40, 200, 30);
        quizPanel.add(onQuestionNum);

        correctLabel = new JLabel(); //The number of questions the user got correct (the value is updated every time the correct answer is pressed) is displayed right above the onQuestionNum label
        correctLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        correctLabel.setBounds(900, 0, 200, 30);
        quizPanel.add(correctLabel);

        JLabel userStaticsTitle = new JLabel("Statics"); //Displays the statics panel title
        userStaticsTitle.setFont(new Font("Serif", Font.PLAIN, 60));
        userStaticsTitle.setBounds(500, 0, 210, 150);
        userStaticsPanel.add(userStaticsTitle);

        statCorrect = new JLabel(); //Displays the number of questions the user got correct at the end
        statCorrect.setFont(new Font("Serif", Font.PLAIN, 30));
        statCorrect.setBounds(370, 150, 600, 80);
        userStaticsPanel.add(statCorrect);

        statIncorrect = new JLabel(); //Displays the number of questions the user got incorrect at the end
        statIncorrect.setFont(new Font("Serif", Font.PLAIN, 30));
        statIncorrect.setBounds(370, 230, 600, 80);
        userStaticsPanel.add(statIncorrect);

        statAccuracy = new JLabel(); //Displays the accuracy (# of correct questions/# of questions) of the user
        statAccuracy.setFont(new Font("Serif", Font.PLAIN, 30));
        statAccuracy.setBounds(370, 310, 600, 80);
        userStaticsPanel.add(statAccuracy);

        statScore = new JLabel(); //Displays the score (accuracy (as a whole number (e.g., 36 for 36%)) times 1000) the user received
        statScore.setFont(new Font("Serif", Font.PLAIN, 30));
        statScore.setBounds(370, 390, 600, 80);
        userStaticsPanel.add(statScore);

        JButton backToMenu = new JButton("Return to Menu"); //This button brings the user back to the conversion menu from the questionReviewPanel
        backToMenu.setFont(new Font("Serif", Font.PLAIN, 30));
        backToMenu.setBounds(690, 600, 250, 60);
        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(questionReviewPanel);
                frame.setContentPane(conversionPanel);
                frame.validate();
            }
        });
        questionReviewPanel.add(backToMenu);

        JButton questionReviewBack = new JButton("Back"); //This button brings the user back to the userStaticsPanel from the questionReviewPanel
        questionReviewBack.setFont(new Font("Serif", Font.PLAIN, 30));
        questionReviewBack.setBounds(340, 600, 250, 60);
        questionReviewBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(questionReviewPanel);
                frame.setContentPane(userStaticsPanel);
                frame.validate();
            }
        });
        questionReviewPanel.add(questionReviewBack);

        questionReviewTitle = new JLabel(); //This displays the title of the question review, which includes the type of base conversion
        questionReviewTitle.setFont(new Font("Serif", Font.PLAIN, 30));
        questionReviewTitle.setSize(1280, 50);
        questionReviewTitle.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
        questionReviewPanel.add(questionReviewTitle);

        frame.setContentPane(welcomePanel);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        new BaseConversionGame();
    }

    //Creates the ArrayList of the number of questions the user can choose from ranging from 10 to 100 in increments of 10
    public static ArrayList<Integer> NumberOfMathQuestions() {
        ArrayList<Integer> numOfQuestions = new ArrayList<>();
        for (int i = 10; i < 101; i += 10) {
            numOfQuestions.add(i);
        }
        return numOfQuestions;
    }

    //Creates the binaryArray and fills it with binary values from 1 to maxValue (512)
    public void Binary() {
        binaryArray = new ArrayList<>();
        for (int i = 1; i <= difficultyLevel; i++) {
            binaryArray.add(Integer.toBinaryString(i));
        }        
    }

    //Creates the octalArray and fills it with octal values from 1 to maxValue (512)
    public void Octal() {
        octalArray = new ArrayList<>();
        for (int i = 1; i <= difficultyLevel; i++) {
            octalArray.add(Integer.toOctalString(i));
        }
    }

    //Creates the hexadecimalArray and fills it with hexadecimal values from 1 to difficultyLevel's value
    public void Hexadecimal() {
        hexadecimalArray = new ArrayList<>();
        for (int i = 1; i <= difficultyLevel; i++) {
            hexadecimalArray.add(Integer.toHexString(i));
        }
    }

    /*
    * The wrongAnswerGenerator procedure takes in the parameter values of numBase (a string value) and correctAnswer (also
    * a string value). It then uses the numBase to select which base array to use to generate the random wrong questions.
    * If two answer choices are the same, either that be one of the wrong answers or the correct answer, then it restarts
    * the procedure before it is saved to the array.
    */
public ArrayList<String> wrongAnswerGenerator(String numBase, String correctAnswer) {
        ArrayList<String> randWrongAnsArray = new ArrayList<>();
        String wrongAnswer;
        for(int i = 0; i < 3; i++) {

            while(true) {
                if(numBase == "binary") {
                    wrongAnswer = binaryArray.get(random.nextInt(difficultyLevel - 1));
                } else if(numBase == "octal") {
                    wrongAnswer = octalArray.get(random.nextInt(difficultyLevel - 1));
                } else if (numBase == "decimal") {
                    wrongAnswer = String.valueOf(random.nextInt(difficultyLevel));
                } else {
                    wrongAnswer = hexadecimalArray.get(random.nextInt(difficultyLevel - 1));
                }

                if(!correctAnswer.equals(wrongAnswer)){
                    if(!(randWrongAnsArray.contains(wrongAnswer))) {
                        randWrongAnsArray.add(wrongAnswer);
                        break;
                    }
                }
            }
        }
        return randWrongAnsArray;
    }

    /*
    * Generates random original base questions and then generates the correct answer in the converted base. It also
    * generates the wrong question choices by calling wrongAnswerGenerator and inputting the originalAnswer and the base
    * the user choice to convert to. It then sets the text on the answer choice buttons and adds the original base number/question
    * and the correctAnswer into an array for review.
    */
    public void questionScreen() {
        questionNumber++;
        randomizeButton();
        String originalAnswer = "";
        String correctAnswer = "";
        int numBase = 0;
        ArrayList<String> wrongAnswer = new ArrayList<>();
        questionData = new ArrayList<>();
        originalOption = (String) originalBase.getSelectedValue();
        convertedOption = (String) convertBase.getSelectedValue();

        Binary();
        Octal();
        Hexadecimal();

        if (originalOption.contains("Binary")) {
            originalAnswer = binaryArray.get(random.nextInt(difficultyLevel - 1));
            numBase = 2;
        } else if (originalOption.contains("Octal")) {
            originalAnswer = octalArray.get(random.nextInt(difficultyLevel - 1));
            numBase = 8;
        } else if (originalOption.contains("Decimal")) {
            originalAnswer = String.valueOf(random.nextInt(difficultyLevel));
            numBase = 10;
        } else if (originalOption.contains("Hexadecimal")) {
            originalAnswer = hexadecimalArray.get(random.nextInt(difficultyLevel - 1));
            numBase = 16;
        }

        if (convertedOption.contains("Binary")) {
            correctAnswer = binaryArray.get(Integer.parseInt(originalAnswer, numBase) - 1);
            wrongAnswer = wrongAnswerGenerator("binary", correctAnswer);
        } else if (convertedOption.contains("Octal")) {
            correctAnswer = octalArray.get(Integer.parseInt(originalAnswer, numBase) - 1);
            wrongAnswer = wrongAnswerGenerator("octal", correctAnswer);
        } else if (convertedOption.contains("Decimal")) {
            correctAnswer = String.valueOf(Integer.parseInt(originalAnswer, numBase));
            wrongAnswer = wrongAnswerGenerator("decimal", correctAnswer);
        } else if (convertedOption.contains("Hexadecimal")) {
            correctAnswer = hexadecimalArray.get(Integer.parseInt(originalAnswer, numBase) - 1);
            wrongAnswer = wrongAnswerGenerator("hexadecimal", correctAnswer);
        }

        conversionQuestion.setText(originalAnswer);
        answer1.setText(correctAnswer); //the answer1 button always contains the correct answer
        answer2.setText(String.valueOf(wrongAnswer.get(0)));
        answer2.setEnabled(true);
        answer3.setText(String.valueOf(wrongAnswer.get(1)));
        answer3.setEnabled(true);
        answer4.setText(String.valueOf(wrongAnswer.get(2)));
        answer4.setEnabled(true);
        questionData.add(originalAnswer);
        questionData.add(correctAnswer);

        frame.setContentPane(quizPanel);
        frame.validate();
    }

    /*
    * Has an array from 0 to 3 and randomly shuffles them so that the answer buttons setLocation
    * calls the buttonArray at a different location to get the coordinates to a different location, which
    * shuffles the buttons in a random order.
    */
    public void randomizeButton() {
        Random random = ThreadLocalRandom.current();
        randomizedLocation = new int[]{0, 1, 2, 3};
        /*
         * The following 6 lines of code came from https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
         * by PhiLho (who posted the code on October 5, 2009) and leventov (who edited it on October 2, 2015). This code
         * was a comment to give the user Hubert a solution on how to randomly shuffle values in a list. I've retrieved
         * this code on March 24, 2021 and have modified it to work with my code.
         */
        for (int i = randomizedLocation.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = randomizedLocation[index];
            randomizedLocation[index] = randomizedLocation[i];
            randomizedLocation[i] = a;
        }

        int[][] buttonLocation = new int[][]{topLeft, topRight, bottomLeft, bottomRight};
        answer1.setLocation(buttonLocation[randomizedLocation[0]][0], buttonLocation[randomizedLocation[0]][1]);
        answer2.setLocation(buttonLocation[randomizedLocation[1]][0], buttonLocation[randomizedLocation[1]][1]);
        answer3.setLocation(buttonLocation[randomizedLocation[2]][0], buttonLocation[randomizedLocation[2]][1]);
        answer4.setLocation(buttonLocation[randomizedLocation[3]][0], buttonLocation[randomizedLocation[3]][1]);
    }

    /*
    * Gets the statics (the numCorrect, the value of the number of questions the user got right, and the
    * numIncorrect, the value of the number of questions the user got incorrect) and uses it to calculate the accuracy and
    * score (takes the accuracy as a whole number (e.g., 90 to represent 90%) and multiplies it by 1000). It then displays
    * it along with numCorrect and numIncorrect.
    */
    public void UserStatics() {
        String aLotOfSpaces = "";
        double accuracy = Math.round(((double) numCorrect / (int) numQuestionsList.getSelectedValue()) * 10000)/100;
        int score = 1000 * (int) accuracy;
        for (int i = 0; i < 25; i++) {
            aLotOfSpaces += " ";
        }
        statScore.setText("   Score:   " + aLotOfSpaces + score);
        statAccuracy.setText("Accuracy:" + aLotOfSpaces + accuracy + "%");
        statCorrect.setText(" Correct:   " + aLotOfSpaces + numCorrect);
        statIncorrect.setText("Incorrect:  " + aLotOfSpaces + numIncorrect);

        JButton toQuestionReview = new JButton("Next");
        toQuestionReview.setFont(new Font("Serif", Font.PLAIN, 30));
        toQuestionReview.setBounds(450, 550, 250, 60);
        toQuestionReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionReviewMethod();
                frame.remove(userStaticsPanel);
                frame.setContentPane(questionReviewPanel);
                frame.validate();
            }
        });
        userStaticsPanel.add(toQuestionReview);

        frame.remove(quizPanel);
        frame.setContentPane(userStaticsPanel);
        frame.validate();
    }

    /*
    * Sets up the table for the correct and incorrect question reviews.
    */
    private void QuestionReviewMethod() {
        String[] columnName = {"Original #", "Correct #", "User Input Was..."};

        JLabel correctQuestionTableTitle = new JLabel("Answered Correctly");
        correctQuestionTableTitle.setFont(new Font("Serif", Font.PLAIN, 30));
        correctQuestionTableTitle.setBounds(200, 100, 270, 40);
        questionReviewPanel.add(correctQuestionTableTitle);

        correctQuestionTable = new JTable(tableUpdater1) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JScrollPane correctQuesReviewGUI = new JScrollPane(correctQuestionTable);
        correctQuesReviewGUI.setBounds(100, 150, 440, 400);
        questionReviewPanel.add(correctQuesReviewGUI);

        JLabel incorrectQuestionTableTitle = new JLabel("Answered Incorrectly");
        incorrectQuestionTableTitle.setFont(new Font("Serif", Font.PLAIN, 30));
        incorrectQuestionTableTitle.setBounds(835, 100, 270, 40);
        questionReviewPanel.add(incorrectQuestionTableTitle);

        incorrectQuestionTable = new JTable(tableUpdater2) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JScrollPane incorrectQuesReviewGUI = new JScrollPane(incorrectQuestionTable);
        incorrectQuesReviewGUI.setBounds(740, 150, 440, 400);
        questionReviewPanel.add(incorrectQuesReviewGUI);

        tableUpdater1 = (DefaultTableModel) correctQuestionTable.getModel();
        tableUpdater1.setColumnIdentifiers(columnName);
        tableUpdater2 = (DefaultTableModel) incorrectQuestionTable.getModel();
        tableUpdater2.setColumnIdentifiers(columnName);
        updateTable();
    }

    /*
    * Takes the quesReviewData, which holds all the questions the user had with its correct answer and whether or not
    * the user got it correct, and converts it into an array. It then uses the array to determine whether or not the
    * user got the question correct or incorrect and adds the questions to the corresponding tables.
    */
    public void updateTable() {
        tableUpdater1 = (DefaultTableModel) correctQuestionTable.getModel();
        tableUpdater1.setRowCount(0);
        tableUpdater2 = (DefaultTableModel) incorrectQuestionTable.getModel();
        tableUpdater2.setRowCount(0);

        for(int i = 0; i < quesReviewData.size(); i++) {
            if(quesReviewData.get(i).get(2) == "Correct") {
                tableUpdater1.addRow(quesReviewData.get(i).toArray());
                tableUpdater1.fireTableDataChanged();
            } else {
                tableUpdater2.addRow(quesReviewData.get(i).toArray());
                tableUpdater2.fireTableDataChanged();
            }
        }
        correctQuestionTable.setModel(tableUpdater1);
        incorrectQuestionTable.setModel(tableUpdater2);
    }

    /*
    * When the answer buttons are pressed in the quizPanel, it determines whether answer1 was pressed (which is the
    * correct answer). If it wasn't, the number of attempts (numAttempts) and incorrectly answered questions (numIncorrect)
    * on the question goes up. After answer1 is pressed, the question will go in as "Incorrect" and numAttempts will be
    * reset to 0. If the question was correct on the first try, the number of correct questions (numCorrect) will increase
    * and the question will go in as "Correct." Whether the question was answered correctly or not, the question # display
    * will update and so will the # of correct answers. It will also run UserStatics() after the user finishes answering
    * all of their questions.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPress = (JButton) e.getSource();
        isCorrect = new ArrayList<>();
        if (questionNumber <= (int) numQuestionsList.getSelectedValue() + 1) {
            if (e.getSource() == answer1) {
                if (numAttempts == 0) {
                    numCorrect++;
                    questionData.add("Correct");
                } else
                    questionData.add("Incorrect");
                quesReviewData.add(questionData);
                numAttempts = 0;
                onQuestionNum.setText("Question #: " + questionNumber);
                correctLabel.setText("Correct: " + numCorrect);
                questionScreen();
                if(questionNumber == (int) numQuestionsList.getSelectedValue() + 2) {
                    UserStatics();
                }
            } else if(e.getSource() != answer1) {
                if(numAttempts == 0)
                    numIncorrect++;
                numAttempts++;
                buttonPress.setEnabled(false);
                buttonPress.setText("X");
            }
        }
    }
}