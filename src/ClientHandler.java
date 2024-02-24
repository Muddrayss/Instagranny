import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String readLine;
            String[] splittedReadLine;
            while ((readLine = in.readLine()) != null) {
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                splittedReadLine = readLine.split(" ");
                switch (splittedReadLine[0].toLowerCase()) {
                    case "get":
                        System.out.println("GET action called by: " + socket.getInetAddress());

                        List<Post> latestPosts = server.getLatestFivePost();
                        if (latestPosts.isEmpty()) {
                            out.println(Colors.YELLOW + "There are still no posts!" + Colors.DEFAULT);
                            out.flush();
                        } else {
                            for (Post post : latestPosts) {
                                List<String> latestPostComments = post.getComments();

                                out.println(Colors.YELLOW + "(ID: " + post.getId() + ") " + post.getPostMessage() + Colors.DEFAULT);
                                for (String latestPostComment : latestPostComments) {
                                    out.println(Colors.CYAN + "\t(Anonymous) " + latestPostComment + Colors.DEFAULT);
                                }
                            }
                            out.flush();
                        }
                        break;
                    case "post":
                        System.out.println("POST action called by: " + socket.getInetAddress());

                        server.addPost(Utils.removeFirstNWords(readLine, 1));
                        out.println(Colors.GREEN + "Post added successfully!" + Colors.DEFAULT);
                        break;
                    case "comment":
                        System.out.println("COMMENT action called by: " + socket.getInetAddress());

                        try {
                            if (server.addCommentToPost(Integer.parseInt(splittedReadLine[1]), Utils.removeFirstNWords(readLine, 2))) {
                                out.println(Colors.GREEN + "Comment added successfully!" + Colors.DEFAULT);
                            } else {
                                out.println(Colors.RED + "There is no post with ID: " + splittedReadLine[1] + Colors.DEFAULT);
                            }
                        } catch (NumberFormatException e) {
                            out.println(Colors.RED + "Invalid post ID: " + splittedReadLine[1] + Colors.DEFAULT);
                        }

                        break;
                    default:
                        break;
                }
                out.println("\0");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
