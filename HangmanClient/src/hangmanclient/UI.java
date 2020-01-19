package hangmanclient;

import hangmanclient.HangmanClient.OutputConnection;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class UI {

    private final OutputConnection con;

    private JFrame frame;
    private JPanel infoPanel;
    private JPanel mainPanel;
    private JPanel inputPanel;

    private JLabel infoMessage;
    private JLabel livesLeft;
    private JLabel wordToGuess;
    private JButton button;

    public UI(OutputConnection conn) {
        createFrame();
        createInfoPanel();
        createMainPanel();
        createInputPanel();
        frame.add(infoPanel);
        frame.add(mainPanel);
        frame.add(inputPanel);
        frame.setVisible(true);
        con = conn;
    }

    private void createFrame() {
        frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setBackground(Color.BLACK);
        frame.setLayout(null);
    }

    private void createInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setBounds(0, 0, 800, 100);
        infoPanel.setBackground(Color.BLACK);
        infoPanel.setLayout(null);

        infoMessage = new JLabel("Enter your guess and click \"SEND\" button.", SwingConstants.CENTER);
        infoMessage.setFont(new Font("SansSerif", Font.BOLD, 25));
        infoMessage.setBounds(0, 0, 800, 100);
        infoMessage.setForeground(Color.WHITE);
        infoPanel.add(infoMessage);
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 100, 800, 500);
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(null);

        Font font2 = new Font("SansSerif", Font.BOLD, 40);
        Font font3 = new Font("SansSerif", Font.BOLD, 100);

        JLabel livesLeftText = new JLabel("LIVES LEFT", SwingConstants.CENTER);
        livesLeftText.setFont(font2);
        livesLeftText.setBounds(0, 0, 800, 100);
        livesLeftText.setForeground(Color.WHITE.darker());
        mainPanel.add(livesLeftText);

        livesLeft = new JLabel("", SwingConstants.CENTER);
        livesLeft.setFont(font3);
        livesLeft.setBounds(0, 10, 800, 200);
        livesLeft.setForeground(Color.WHITE);
        mainPanel.add(livesLeft);

        JLabel wordToGuessText = new JLabel("WORD TO GUESS", SwingConstants.CENTER);
        wordToGuessText.setFont(font2);
        wordToGuessText.setBounds(0, 300, 800, 100);
        wordToGuessText.setForeground(Color.WHITE.darker());
        mainPanel.add(wordToGuessText);

        wordToGuess = new JLabel("", SwingConstants.CENTER);
        wordToGuess.setFont(font3);
        wordToGuess.setBounds(0, 310, 800, 200);
        wordToGuess.setForeground(Color.WHITE);
        mainPanel.add(wordToGuess);
    }

    private void createInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setBounds(0, 600, 800, 200);
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setLayout(null);

        JTextField textField = new JTextField(1);
        textField.setBounds(275, 50, 140, 50);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        inputPanel.add(textField);

        button = new JButton("SEND");
        button.setBackground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.PLAIN, 20));
        button.setBounds(425, 50, 120, 50);

        button.addActionListener((ActionEvent e) -> {
            String input = textField.getText();
            if (!input.isEmpty()) {
                textField.setText("");
                try {
                    con.send(input);
                } catch (IOException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
                editInfoMessage("Guess sent!");
            } else {
                editInfoMessage("Enter your guess before sending it!");
            }
        });
        inputPanel.add(button);
    }

    public void editInfoMessage(String msg) {
        infoMessage.setText(msg);
    }

    public void editLivesLeft(String lives) {
        livesLeft.setText(lives);
    }

    public void editWordToGuess(String lives) {
        wordToGuess.setText(lives);
    }
}

