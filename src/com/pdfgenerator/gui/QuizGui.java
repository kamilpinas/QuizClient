package com.pdfgenerator.gui;

import com.pdfgenerator.model.AnswerData;
import com.pdfgenerator.model.NetworkRequests;
import com.pdfgenerator.model.QuestionData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class QuizGui extends javax.swing.JFrame {
    private JTextField questionTextArea;
    public JPanel panel1;
    public JButton answer1Button;
    private JButton answer4Button;
    private JButton answer3Button;
    private JButton answer2Button;
    private JButton nextQuestionButton;
    private JButton endQuizButton;
    private JButton startGameButton;
    private JLabel aLabel;
    private JLabel bLabel;
    private JLabel cLabel;
    private JLabel dLabel;
    private JLabel questionPriceLabel;
    private JLabel gameOverLabel;
    private JTextField questionPriceTextField;

    ArrayList<AnswerData> list = new ArrayList<AnswerData>();
    QuestionData questionsCollection;
    String yourAnswer = "";
    int answersToCheckCount = 4;
    int id = 0;

    public void disableAnswerButtons() {
        answer1Button.setEnabled(false);
        answer2Button.setEnabled(false);
        answer3Button.setEnabled(false);
        answer4Button.setEnabled(false);
    }

    public void enableAnswerButtons() {
        answer1Button.setEnabled(true);
        answer2Button.setEnabled(true);
        answer3Button.setEnabled(true);
        answer4Button.setEnabled(true);
    }

    public void setNextQuestionTexts() {
        try {
            questionsCollection = NetworkRequests.getByGET(id);
            questionTextArea.setText(questionsCollection.getQuestion());
            answer1Button.setText(questionsCollection.getAnswers()[0]);
            answer2Button.setText(questionsCollection.getAnswers()[1]);
            answer3Button.setText(questionsCollection.getAnswers()[2]);
            answer4Button.setText(questionsCollection.getAnswers()[3]);
            questionPriceTextField.setText((questionsCollection.getPoints()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void answerButtonClickAction(int answerNumber) {
        yourAnswer = yourAnswer + answerNumber;
        if (answersToCheckCount > 1) {
            --answersToCheckCount;
            nextQuestionButton.setEnabled(true);
        } else if (answersToCheckCount == 1) {
            --answersToCheckCount;
            disableAnswerButtons();
            nextQuestionButton.setEnabled(true);
        } else if (answersToCheckCount == 3) {
            nextQuestionButton.setEnabled(true);
        } else {
            System.out.println("Negative answersToCheckCount!!!");
        }
    }

    public void sendYourAnswer(int id, String yourAnswer) {
        Integer[] yourAnswerIntArray = new Integer[yourAnswer.length()];
        char[] yourAnswerCharArray = yourAnswer.toCharArray();
        for (int i = 0; i < yourAnswer.length(); i++) {
            yourAnswerIntArray[i] = Integer.parseInt(String.valueOf(yourAnswerCharArray[i]));
        }

        AnswerData dataAnswer = new AnswerData();
        dataAnswer.setQuestionId(id);
        dataAnswer.setLastQuestion(questionsCollection.isLastQuestion());
        dataAnswer.setSelectedAnswers(yourAnswerIntArray);
        list.add(dataAnswer);
        try {
            NetworkRequests.answerData(dataAnswer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public QuizGui() throws Exception {
        answer1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(1);
                answer1Button.setEnabled(false);
            }
        });

        answer2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(2);
                answer2Button.setEnabled(false);
            }
        });

        answer3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(3);
                answer3Button.setEnabled(false);
            }
        });

        answer4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(4);
                answer4Button.setEnabled(false);
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endQuizButton.setEnabled(true);
                if (questionsCollection.isLastQuestion() == false) {
                    sendYourAnswer(id, yourAnswer);
                    id++;
                    setNextQuestionTexts();
                    enableAnswerButtons();
                    nextQuestionButton.setEnabled(false);
                    answersToCheckCount = 4;
                } else {
                    sendYourAnswer(id, yourAnswer);
                    nextQuestionButton.setText("No more questions");
                    nextQuestionButton.setEnabled(false);
                    disableAnswerButtons();
                }
                yourAnswer = "";
            }
        });

        endQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to quit?", "Warning!", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    try {
                        NetworkRequests.answerDataList(list);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    panel1.setVisible(false);
                    System.exit(0);
                }
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNextQuestionTexts();
                questionTextArea.setVisible(true);
                startGameButton.setVisible(false);
                nextQuestionButton.setVisible(true);
                nextQuestionButton.setEnabled(false);
                answer1Button.setVisible(true);
                answer2Button.setVisible(true);
                answer3Button.setVisible(true);
                answer4Button.setVisible(true);
                aLabel.setVisible(true);
                bLabel.setVisible(true);
                cLabel.setVisible(true);
                dLabel.setVisible(true);
                questionPriceLabel.setVisible(true);
                endQuizButton.setVisible(true);
                questionPriceTextField.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        JFrame GuiMain = new JFrame("Quiz");
        GuiMain.setContentPane(new QuizGui().panel1);
        GuiMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GuiMain.pack();
        GuiMain.setVisible(true);

    }
}