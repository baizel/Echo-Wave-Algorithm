import java.util.Objects;

/**
 * Class to represent an undirected edge
 * Used to count the number od edges in a graph
 * @author baizel
 */
public class Edge {
    private int fromNode;
    private int toNode;

    public Edge(int fromNode, int toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        //Need to have an OR to be undirected so that edge 2->4 and 4->2 is counted as the same
        return (fromNode == edge.fromNode && toNode == edge.toNode) ||
                (fromNode == edge.toNode && toNode == edge.fromNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromNode) + Objects.hash(toNode);
    }
}
