package hangmanserver;

import hangmanserver.HangmanServer.Connection;
//import java.util.Scanner;

public class GameLogic {

    private String word;
    private String secretWord;
    private int livesLost;
    private String guessedChars = "";
    private boolean doRestart = true;
    private final Connection con;

    public GameLogic(Connection conn) {
        con = conn;
        play();
    }
    
    /*private void play(){
        Scanner scanner = new Scanner(System.in);
        while (doRestart) {
            doRestart = false;
            initialize();

            //play while player has lives and word is not guessed
            while (livesLost < Constants.LIVES && secretWord.contains("*")) {
                System.out.println("Guess any letter in the word: " + secretWord);
                char guess = scanner.next().charAt(0);
                hang(guess);
            }

            //check if player won or lost
            if (secretWord.contains("*")) {
                System.out.println("You lost :( \nThe secret word was: " + word + "\n");
            } else {
                System.out.println("You won with " + (Constants.LIVES - livesLost) + " lives left :) \nThe secret word was: " + word + "\n");
            }

            //check if player wants to play again
            System.out.println("If you want to restart type \"yes\".");
            if (scanner.next().equals("yes")) {
                doRestart = true;
            }
        }
        scanner.close();
    }*/
    
    private void play(){
        //Scanner scanner = new Scanner(System.in);
        while (doRestart) {
            doRestart = false;
            initialize();

            //play while player has lives and word is not guessed
            while (livesLost < Constants.LIVES && secretWord.contains("*")) {
                //System.out.println("Guess any letter in the word: " + secretWord);
                //char guess = scanner.next().charAt(0);
                con.send("Guess any letter in the word: " + secretWord);
                char guess = con.read().charAt(0);
                hang(guess);
            }

            //check if player won or lost
            if (secretWord.contains("*")) {
                //System.out.println("You lost :( \nThe secret word was: " + word + "\n");
                con.send("You lost :( \nThe secret word was: " + word + "\n");
            } else {
                //System.out.println("You won with " + (Constants.LIVES - livesLost) + " lives left :) \nThe secret word was: " + word + "\n");
                con.send("You won with " + (Constants.LIVES - livesLost) + " lives left :) \nThe secret word was: " + word + "\n");
            }

            //check if player wants to play again
            //System.out.println("If you want to restart type \"yes\".");
            con.send("If you want to restart type \"y\".");
            if(con.read().equals("y")){
                doRestart = true;
            }
        }
        //scanner.close();
    }

    //initialize variables needed to play the game
    private void initialize() {
        word = Constants.WORDS[(int) (Math.random() * Constants.WORDS.length)];
        secretWord = new String(new char[word.length()]).replace("\0", "*");
        livesLost = 0;
        guessedChars = "";
    }

    //logic of the game
    private void hang(char guess) {
        //check if guessed letter is valid
        if (!Character.isLetter(guess)) {
            //System.out.println("This is not a valid letter.");
            con.send("This is not a valid letter.");
            return;
        }

        //check if letter was guessed
        if (guessedChars.indexOf(guess) != -1) {
            //System.out.println("You already tried to guess that letter.");
            con.send("You already tried to guess that letter.");
            return;
        } else {
            guessedChars += guess;
        }

        //check if letter is in the word
        if (word.indexOf(guess) == -1) {
            livesLost++;
            //System.out.println("Lives lost: " + livesLost);
            con.send("Lives lost: " + livesLost);
            return;
        }

        //if letter is in the word, rebuild word hint
        String newSecretWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                newSecretWord += guess;
            } else if (secretWord.charAt(i) != '*') {
                newSecretWord += word.charAt(i);
            } else {
                newSecretWord += "*";
            }
        }
        secretWord = newSecretWord;
    }
}
