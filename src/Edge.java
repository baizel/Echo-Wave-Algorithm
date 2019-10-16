import java.util.Objects;

public class Edge {
    private int fromNode;
    private int toNode;

    public Edge(int fromNode, int toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public int getFromNode() {
        return fromNode;
    }

    public int getToNode() {
        return toNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (fromNode == edge.fromNode && toNode == edge.toNode) ||
                (fromNode == edge.toNode && toNode == edge.fromNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromNode) + Objects.hash(toNode);
    }
}