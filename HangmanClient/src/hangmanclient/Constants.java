package hangmanclient;

public final class Constants {
    public static final int PORT = 4000; //Port of the server
    
    public static final String COMMAND_LIVES = "/lives "; //Command to receive count of lives left
    public static final String COMMAND_GUESS = "/guess "; //Command to receive that guess is needed
    public static final String COMMAND_LOST = "/lost "; //Command to receive that user lost the game
    public static final String COMMAND_WON = "/won "; //Command to receive that user won the game
    public static final String COMMAND_RESTART = "/restart"; //Command to receive that prompts user to restart the game
    public static final String COMMAND_LETTER_NOT_VALID = "/invalid"; //Command to receive that guess was not a valid letter
    public static final String COMMAND_LETTER_REPEATED = "/repeated"; //Command to receive that guessed letter was used already
    public static final String COMMAND_MISSED = "/miss"; //Command to receive that letter guess of the user was not in the word
}