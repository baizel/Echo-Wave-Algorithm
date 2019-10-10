public class Token {

    private Node source;
    private String message;

    public Token(Node source, String message) {
        this.source = source;
        this.message = message;

    }

    public Node getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

}
