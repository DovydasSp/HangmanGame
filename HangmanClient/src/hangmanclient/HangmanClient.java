package hangmanclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class HangmanClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", Constants.PORT);
        try {
            Connection con = new Connection(socket);
            con.start();
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
        }
    }

    public static class Connection extends Thread {

        private final BufferedReader readerChannel;
        private final PrintWriter writerChannel;

        Connection(Socket socket) throws IOException {
            readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writerChannel = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                String line;
                while ((line = readerChannel.readLine()) != null) {
                    System.out.println(line);
                    if (line.contains("/guess") || line.contains("/restart")) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        send(reader.readLine());
                    }
                }
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
            } finally {
                System.out.println("Server crashed");
            }
        }

        public void send(String message) throws IOException {
            writerChannel.println(message);
        }
    }
}
