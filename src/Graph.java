import java.util.ArrayList;

public class Graph {
    private final static int NUMBER_OF_NODES = 7;
    private ArrayList<Node> nodes = new ArrayList<>();

    public Graph() {
        for (int i = 0; i < NUMBER_OF_NODES; i++) {
            nodes.add(new Node(i));
        }
        generateNeighbours();
    }

    private void generateNeighbours() {
        //hard code for now
        int[][] neighs = {{1, 2, 5}, {0}, {0, 3, 4}, {2}, {2}, {0, 6}, {5}};
        for (int i = 0; i < neighs.length; i++) {
            for (int j = 0; j < neighs[i].length; j++) {
                getNode(i).addNeighbour(getNode(neighs[i][j]));
            }
        }
    }

    public Node getNode(int id) {
        if (id > nodes.size() || id < 0) {
            throw new IllegalArgumentException(String.format("Node Id %s out of bound", id));
        }
        return nodes.get(id);
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        for (int i =0; i < NUMBER_OF_NODES; i++){
            ret.append(String.format("Neigh Node %d: %s\n",nodes.get(i).getId(),nodes.get(i).getNeighbours().toString()));
        }
        return ret.toString();
    }

}
