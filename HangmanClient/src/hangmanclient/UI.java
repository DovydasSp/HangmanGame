package hangmanclient;

import hangmanclient.HangmanClient.OutputConnection;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import static java.lang.System.exit;
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
        InitMainUI();
        con = conn;
    }

    //Initializing and adding game panels to the frame
    public void InitMainUI() {
        createInfoPanel();
        createMainPanel();
        createInputPanel();
        frame.add(infoPanel);
        frame.add(mainPanel);
        frame.add(inputPanel);
        frame.setVisible(true);
    }

    //Creating the main frame of the game
    private void createFrame() {
        frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setBackground(Color.BLACK);
        frame.setLayout(null);
    }

    //Creating top panel to show info that server sent to the player
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

    //Creating middle panel to show how many lives player has left and hint of the word
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

    //Creating bottom panel to show input textField and send button
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

    //Changng text of label to show info the server sent to the player
    public void editInfoMessage(String msg) {
        infoMessage.setText(msg);
    }

    //Changing text of label to show player how many lives he has left
    public void editLivesLeft(String lives) {
        livesLeft.setText(lives);
    }

    //Changing text of label to show the word player needs to guess
    public void editWordToGuess(String lives) {
        wordToGuess.setText(lives);
    }
    
    //Creating panel with info and buttons to restart or quit for showing after win or lose
    public void InitRestartPrompt(boolean won, String word) {
        frame.remove(infoPanel);
        frame.remove(mainPanel);
        frame.remove(inputPanel);

        JPanel restartPanel = new JPanel();
        restartPanel.setBounds(0, 0, 800, 800);
        restartPanel.setLayout(null);
        
        Font font2 = new Font("SansSerif", Font.BOLD, 40);
        Font font3 = new Font("SansSerif", Font.BOLD, 100);

        JLabel winLabel = new JLabel("", SwingConstants.CENTER);
        winLabel.setFont(font3);
        winLabel.setBounds(0, 200, 800, 100);
        winLabel.setForeground(Color.WHITE);
        restartPanel.add(winLabel);

        JLabel restartLabel = new JLabel("WORD TO GUESS WAS: " + word.toUpperCase(), SwingConstants.CENTER);
        restartLabel.setFont(font2);
        restartLabel.setBounds(0, 210, 800, 200);
        restartLabel.setForeground(Color.WHITE.darker());
        restartPanel.add(restartLabel);

        if (won) {
            restartPanel.setBackground(Color.GREEN);
            winLabel.setText("YOU WON! ☺");
        } else {
            restartPanel.setBackground(Color.RED);
            winLabel.setText("YOU LOST...");
        }

        JButton restartButton = new JButton("PLAY AGAIN");
        restartButton.setBackground(Color.WHITE);
        restartButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        restartButton.setBounds(150, 500, 200, 50);

        JButton dontRestartButton = new JButton("QUIT");
        dontRestartButton.setBackground(Color.WHITE);
        dontRestartButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        dontRestartButton.setBounds(450, 500, 200, 50);

        restartButton.addActionListener((ActionEvent e) -> {
            try {
                frame.remove(restartPanel);
                frame.repaint();
                InitMainUI();
                con.send("y");
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        restartPanel.add(restartButton);
        
        dontRestartButton.addActionListener((ActionEvent e) -> {
            try {
                con.send("n");
                exit(0);
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        restartPanel.add(dontRestartButton);
        
        frame.add(restartPanel);

        frame.setVisible(true);
        frame.repaint();
    }
}
