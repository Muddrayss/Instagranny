import java.lang.*;
import java.io.*;
import java.net.*;

public class Client {
    private final String serverName;
    private final int serverPort;

    public static void main(String[] args) {
        Client client = new Client("localhost", 4141);
        client.startClient();
    }

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void startClient() {
        try {
            Socket socket = new Socket(serverName, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String readLine;
            while (true) {
                System.out.print(Colors.DEFAULT + "$> ");
                readLine = br.readLine();
                if (readLine == null) {
                    break;
                }

                String command = readLine.split(" ")[0].toLowerCase();
                switch (command) {
                    case "get":
                    case "post":
                    case "comment":
                        out.println(readLine);
                        out.flush();
                        break;
                    default:
                        System.out.println(Colors.RED + "Command not found!" + Colors.DEFAULT);
                        printCommands();
                        continue;
                }

                String response;
                while (!(response = in.readLine()).equals("\0")) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printCommands() {
        System.out.println(Colors.YELLOW + "Available commands: ");
        System.out.println("GET - get the latest 5 messages posted to the server");
        System.out.println("POST <message_to_post> - post a message to the server");
        System.out.println("COMMENT <post_id> <message> - add a comment to a post" + Colors.DEFAULT);
    }
}
