import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Node class that stimulates a worker node
 * This class has the main echo wave algorithm
 * Each node has its own message queue which can be invoked by other
 * objects using the provided methods
 * @author baizel
 */
public class Node {


    private int id;
    public int messageSentCounter = 0;
    public Deque<Token> messageQueue;
    private ArrayList<Node> neighbours;

    //Echo wave vars
    private Node father = null;
    private int tokenReceivedCounter = 0; // Keep count of the received messages
    private boolean isInitiator = false;
    private boolean hasSentTokenToFather = false; //Used to make sure message is ony sent once
    private boolean hasBroadcastedToNeighbours = false; //Used to make sure message is ony sent once
    private boolean isVerbose; // Used to decided if output should be printed

    public Node(int id, boolean isVerbose) {
        this.id = id;
        neighbours = new ArrayList<>();
        messageQueue = new ArrayDeque<>();
        this.isVerbose = isVerbose;
    }

    /**
     * Static method to send message to other nodes.
     * A token object is added to the receiver queue when invoked
     * Token contains the id of the sender node
     * @param sender Node Object
     * @param receiver Node Object
     */
    private static void sendToken(Node sender, Node receiver) {
        sender.messageSentCounter++;
        receiver.messageQueue.add(new Token(sender));
    }

    public int getId() {
        return id;
    }

    public int getMessageSentCounter() {
        return messageSentCounter;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void initiateEchoWave() {
        isInitiator = true;
        //Send a token to all neighbours
        for (Node n : neighbours) {
            sendToken(this, n);
            if (isVerbose)
                System.out.println(String.format("Node %d has sent the token to the Node %d", getId(), n.getId()));
        }
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public boolean runEchoExecution() throws IllegalAccessException {
        //If no message in queue then node will not do any work
        if (messageQueue.isEmpty()) {
            if (isVerbose)
                System.out.println(String.format("Node %d is asleep", getId()));
            return false;
        }
        //process message and set a father if needed
        while (!messageQueue.isEmpty()) {
            tokenReceivedCounter++;
            Token t = messageQueue.pop();
            if (isVerbose)
                System.out.println(String.format("Node %d has received token from node %d", getId(), t.getSource().getId()));
            if (father == null && !isInitiator) {
                father = t.getSource();
                if (isVerbose)
                    System.out.println(String.format("Node %d has set the father to node %d", getId(), father.getId()));
            }
        }
        //Send a token to all neighbours if it hasn't already
        if (!hasBroadcastedToNeighbours) {
            for (Node n : neighbours) {
                if (father != null && n != father) {
                    sendToken(this, n);
                    if (isVerbose)
                        System.out.println(String.format("Node %d has sent a token to the Node %d", getId(), n.getId()));
                    hasBroadcastedToNeighbours = true;
                }
            }
        }
        if (tokenReceivedCounter >= neighbours.size()) {
            //if initiator and all message has been received then node is in a decided state
            if (isInitiator) {
                if (isVerbose)
                    System.out.println("Initiator node has decided");
                return true;
            } else {
                //Send a token to the father if it hasn't already
                if (father != null) {
                    if (!hasSentTokenToFather) {
                        sendToken(this, father);
                        if (isVerbose)
                            System.out.println(String.format("Node %d has sent the token to the father Node %d", getId(), father.getId()));
                        hasSentTokenToFather = true;
                    }
                } else {
                    //Should never be in this state unless there is an error with the input file
                    throw new IllegalAccessException("Father is null for non initiator node");
                }
            }
        }
        return false;
    }

    public String toString() {
        return Integer.toString(id);
    }

    public Node getFather() {
        return father;
    }
}
