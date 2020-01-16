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

    private final static int PORT_NO = 4000;
    private final ServerSocket listener;
    private final List<Connection> clients;

    HangmanServer() throws IOException {
        listener = new ServerSocket(PORT_NO);
        clients = new ArrayList<>();
        System.out.println("Listening on port: " + PORT_NO);
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

    private class Connection extends Thread {

        private BufferedReader br;
        private PrintWriter pw;

        Connection(Socket s) throws IOException {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream(), true);
        }

        @Override
        public void run() {
            send("Welcome!");
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    //send(line);
                }
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
            } finally {
                System.out.println("Client removed");
                clients.remove(this);
            }
        }

        private void send(String message) {
            pw.println(message);
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
