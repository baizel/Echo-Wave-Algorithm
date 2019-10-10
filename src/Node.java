import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Node {
    public  static int messageSentCounter =0;
    private int id;
    private ArrayList<Node> neighbours;

    //Echo wave vars
    private boolean isInitiator = false;
    private Node father = null;
    private int tokenReciviedCounter = 0;

    private final static String MESSAGE = "A_MESSAGE";

    public Deque<Token> messageQueue;


    public Node(int id) {
        this.id = id;
        neighbours = new ArrayList<>();
        messageQueue = new ArrayDeque<>();
    }

    public int getId() {
        return id;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }


    public void initiateEchoWave() {
        isInitiator = true;
        for (Node n : neighbours) {
            sendToken(this, n, MESSAGE);
        }
    }

    public boolean runEchoExecution() throws IllegalAccessException {
        if (father == null && !isInitiator && !messageQueue.isEmpty()) {
            father = messageQueue.peek().getSource();
        }
        for (Node n: neighbours){
            if (father != null && n != father){
                sendToken(this,n,MESSAGE);
            }
        }
        while (!messageQueue.isEmpty()) {
            tokenReciviedCounter++;
            messageQueue.pop();
        }
        if (tokenReciviedCounter >= neighbours.size()) {
            if (isInitiator) {
                System.out.println("Decided?");
                return true;
            } else {
                if (father != null)
                    sendToken(this, father, MESSAGE);
                else
                    throw new IllegalAccessException("Father is null for non initiator node");
            }
        }
        return false;
    }

    private static void sendToken(Node sender, Node receiver, String message) {
        messageSentCounter++;
        receiver.messageQueue.add(new Token(sender, message));
    }


    public String toString() {
        return Integer.toString(id);
    }

}
