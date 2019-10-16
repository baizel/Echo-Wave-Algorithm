import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Node {
    public static int messageSentCounter = 0;
    private int id;
    private ArrayList<Node> neighbours;

    //Echo wave vars
    private boolean isInitiator = false;
    private Node father = null;
    private int tokenReceivedCounter = 0;
    private boolean hasSendTokenToFather = false;
    private boolean hasBroadcastedToNeighbours = false;

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
            System.out.println(String.format("Node %d has send the token to the Node %d", getId(), n.getId()));
        }
    }

    public boolean runEchoExecution() throws IllegalAccessException {
        while (!messageQueue.isEmpty()) {
            tokenReceivedCounter++;
            Token t = messageQueue.pop();
            System.out.println(String.format("Node %d has received token from node %d", getId(), t.getSource().getId()));
            if (father == null && !isInitiator) {
                father = t.getSource();
                System.out.println(String.format("Node %d has set the father to node %d", getId(), father.getId()));
            }
        }
        if (!hasBroadcastedToNeighbours) {
            for (Node n : neighbours) {
                if (father != null && n != father) {
                    sendToken(this, n, MESSAGE);
                    System.out.println(String.format("Node %d has send a token to the Node %d", getId(), n.getId()));
                }
            }
            hasBroadcastedToNeighbours = true;
        }
        if (tokenReceivedCounter >= neighbours.size()) {
            if (isInitiator) {
                System.out.println("Initiator node has decided");
                return true;
            } else {
                if (father != null) {
                    if (!hasSendTokenToFather) {
                        sendToken(this, father, MESSAGE);
                        System.out.println(String.format("Node %d has send the token to the father Node %d", getId(), father.getId()));
                        hasSendTokenToFather = true;
                    }
                } else {
                    throw new IllegalAccessException("Father is null for non initiator node");
                }
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

    public Node getFather() {
        return father;
    }
}
