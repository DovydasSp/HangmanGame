package hangmanserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

public class HangmanServer {

    private final ServerSocket listener;
    private final List<Connection> clients;

    HangmanServer() throws IOException {
        listener = new ServerSocket(Constants.PORT);
        clients = new ArrayList<>();
        System.out.println("Listening on port: " + Constants.PORT);
    }

    void runServer() {
        try {
            while (true) {
                Socket socket = listener.accept();
                System.out.println("Accepted new connection");
                Connection con = new Connection(socket);
                clients.add(con);
                con.start();
            }
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
        }
    }

    public class Connection extends Thread {

        private final BufferedReader br;
        private final PrintWriter pw;

        Connection(Socket s) throws IOException {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream(), true);
        }

        @Override
        public void run() {
            //send("Welcome!");
            GameLogic gl = new GameLogic(this);
        }

        public void send(String message) {
            pw.println(message);
        }

        public String read() {
            try {
                String line = br.readLine();
                return line;
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
                clients.remove(this);
                System.out.println("Client removed");
            }
            return "-";
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Server starting.");
            new HangmanServer().runServer();
        } catch (IOException ioe) {
            System.err.println("Unable to create server socket. " + ioe.getMessage());
        }
    }
}
