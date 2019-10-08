import java.util.ArrayList;

public class Node {
    private int id;
    private ArrayList<Node> neighbours;

    public Node(int id) {
        this.id = id;
        neighbours = new ArrayList<>();

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

    public String toString() {
        return Integer.toString(id);
    }
}
