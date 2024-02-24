import java.lang.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int serverPort;
    private final List<Post> posts;

    public static void main(String[] args) {
        Server server = new Server(4141);
        server.startSever();
    }

    public Server(int serverPort) {
        this.serverPort = serverPort;
        this.posts = new ArrayList<>();
    }

    public void startSever() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server listening on port: " + serverPort);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Connection accepted: " + client.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(client, this);
                clientHandler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addPost(String message) {
        posts.add(new Post(posts.size(), message));
    }

    public synchronized boolean addCommentToPost(int postID, String comment) {
        if (postID < posts.size()) {
            posts.get(postID).addComment(comment);
            return (true);
        }
        return (false);
    }

    public List<Post> getLatestFivePost() {
        int postSize = posts.size();
        int indexFrom = Math.max(postSize - 5, 0);
        return (posts.subList(indexFrom, postSize));
    }
}