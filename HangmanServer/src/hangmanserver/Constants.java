package hangmanserver;

public final class Constants {
    public static final int PORT = 4000; //Port of the server
    public static final int LIVES = 10; //Int to define how many lives user has
    
    public static final String COMMAND_LIVES = "/lives "; //Command to send count of lives left
    public static final String COMMAND_GUESS = "/guess "; //Command to send that guess is needed
    public static final String COMMAND_USER_EXITED = "/user_exited"; //Command that user exited the game
    public static final String COMMAND_LOST = "/lost "; //Command to send that user lost the game
    public static final String COMMAND_WON = "/won "; //Command to send that user won the game
    public static final String COMMAND_RESTART = "/restart"; //Command to send that prompts user to restart the game
    public static final String COMMAND_LETTER_NOT_VALID = "/invalid"; //Command to send that guess was not a valid letter
    public static final String COMMAND_LETTER_REPEATED = "/repeated"; //Command to send that guessed letter was used already
    public static final String COMMAND_MISSED = "/miss"; //Command to send that letter guess of the user was not in the word
    
    //Word dictionary
    public static final String[] WORDS = {
        "computer",
        "science",
        "phone",
        "robot",
        "android",
        "console"
    };
}