package hangmanclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class HangmanClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", Constants.PORT);
        //UI ui = new UI();
        try {
            InputConnection con = new InputConnection(socket);
            con.start();
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
        }
    }

    public static class InputConnection extends Thread {

        private final BufferedReader readerChannel;
        private final UI ui;

        InputConnection(Socket socket) throws IOException {
            readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputConnection op = new OutputConnection(socket);
            op.start();
            ui = new UI(op);
        }

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
        
        private void processCommands(String command){
            if(command.contains(Constants.COMMAND_LIVES)){
                ui.editLivesLeft(command.replace(Constants.COMMAND_LIVES, ""));
            }
            if(command.contains(Constants.COMMAND_GUESS)){
                ui.editWordToGuess(command.replace(Constants.COMMAND_GUESS, ""));
                
            }
            if(command.contains(Constants.COMMAND_LOST)){
                ui.editWordToGuess(command.replace(Constants.COMMAND_LOST, ""));
                ui.editInfoMessage("You have lost...");
            }
            if(command.contains(Constants.COMMAND_WON)){
                ui.editWordToGuess(command.replace(Constants.COMMAND_WON, ""));
                ui.editInfoMessage("You have won!");
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
    
    public static class OutputConnection extends Thread {

        private final PrintWriter writerChannel;

        OutputConnection(Socket socket) throws IOException {
            writerChannel = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {
            /*try {
                String line;
                while ((line = readerChannel.readLine()) != null) {
                    System.out.println(line);
                    processCommands(line);
                    if (line.contains("/guess") || line.contains("/restart")) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        send(reader.readLine());
                    }
                }
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
            }*/
        }

        public void send(String message) throws IOException {
            writerChannel.println(message);
        }
    }
}
