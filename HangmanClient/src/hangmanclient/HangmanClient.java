package hangmanclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class HangmanClient {

    //Make a connection to the server and start reading in new thread
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", Constants.PORT);
        try {
            InputConnection con = new InputConnection(socket);
            con.start();
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
        }
    }

    //Thread to accept commands from the server
    public static class InputConnection extends Thread {

        private final BufferedReader readerChannel;
        private final UI ui;

        //Initialize reader and start output thread
        InputConnection(Socket socket) throws IOException {
            readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputConnection op = new OutputConnection(socket);
            op.start();
            ui = new UI(op);
        }

        //Method to read commands sent by the server
        @Override
        public void run() {
            try {
                String line;
                while ((line = readerChannel.readLine()) != null) {
                    System.out.println(line);
                    processCommands(line);
                }
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
            }
        }
        
        //Proccess parsed commands to take appropriate steps
        private void processCommands(String command){
            if(command.contains(Constants.COMMAND_LIVES)){
                ui.editLivesLeft(command.replace(Constants.COMMAND_LIVES, ""));
            }
            if(command.contains(Constants.COMMAND_GUESS)){
                ui.editWordToGuess(command.replace(Constants.COMMAND_GUESS, ""));
                
            }
            if(command.contains(Constants.COMMAND_LOST)){
                ui.InitRestartPrompt(false, command.replace(Constants.COMMAND_LOST, ""));
            }
            if(command.contains(Constants.COMMAND_WON)){
                ui.InitRestartPrompt(true, command.replace(Constants.COMMAND_WON, ""));
            }
            
            if(command.contains(Constants.COMMAND_RESTART)){
                ui.editInfoMessage("If you want to restart send \"y\".");
            }
            if(command.contains(Constants.COMMAND_LETTER_NOT_VALID)){
                ui.editInfoMessage("This is not a valid letter.");
            }
            if(command.contains(Constants.COMMAND_LETTER_REPEATED)){
                ui.editInfoMessage("You already tried to guess that letter.");
            }
            if(command.contains(Constants.COMMAND_MISSED)){
                ui.editInfoMessage("Letter you have guessed is not in the word.");
            }
        }
    }
    
    //Thread to send guesses to the server
    public static class OutputConnection extends Thread {

        private final PrintWriter writerChannel;

        OutputConnection(Socket socket) throws IOException {
            writerChannel = new PrintWriter(socket.getOutputStream(), true);
        }

        public void send(String message) throws IOException {
            writerChannel.println(message);
        }
    }
}
