import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id;
    private String postMessage;
    private List<String> comments;

    public Post(int id, String postMessage) {
        this.id = id;
        this.postMessage = postMessage;
        this.comments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }
}
