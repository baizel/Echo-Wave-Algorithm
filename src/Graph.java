import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private int numberOfEdges = 0;

    //https://stackoverflow.com/questions/20626312/remove-duplicate-element-from-the-set-in-java
    private Set<Edge> edges = new HashSet<>();

    public Graph(int numOfNodes, ArrayList<ArrayList<Integer>> neighbour) {
        for (int i = 0; i < numOfNodes; i++) {
            Node n = new Node(i);
            nodes.add(n);
        }
        generateNeighbours(neighbour);
    }

    private void generateNeighbours(ArrayList<ArrayList<Integer>> neighbour) {
        for (int i = 0; i < neighbour.size(); i++) {
            for (int j = 0; j < neighbour.get(i).size(); j++) {
                Node neigh = getNode(neighbour.get(i).get(j));
                Edge edge = new Edge(i, neigh.getId());
                edges.add(edge);
                getNode(i).addNeighbour(neigh);
            }
        }
        numberOfEdges = edges.size();
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public Node getNode(int id) {
        if (id > nodes.size() || id < 0) {
            throw new IllegalArgumentException(String.format("Node Id %s out of bound", id));
        }
        return nodes.get(id);
    }

    public int getNumberOfNodes(){
        return nodes.size();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Node n : nodes) {
            ret.append(String.format("Node: %d, Father: %s, Neighbours: %s\n", n.getId(), n.getFather(), n.getNeighbours().toString()));
        }
        ret.append("\n");
        return ret.toString();
    }

    /**
     * Assumes the file is in the right format
     * format:
     * number_of_nodes
     * START OF FILE
     * neigh_1_for_node_0,neigh_2_for_node_0 ...
     * neigh_1_for_node_1,neigh_2_for_node_1 ...
     * .
     * .
     * .
     * n
     * //empty line to separate for new graph
     * <p>
     * number_of_nodes
     * neigh_1_for_node_0,neigh_2_for_node_0 ...
     * neigh_1_for_node_1,neigh_2_for_node_1 ...
     * .
     * .
     * .
     * n
     * END OF FILE
     *
     * @param filename name of the file which contains the structure of the graphs
     * @return
     */
    public static ArrayList<Graph> GenerateGraphFromTxt(String filename) {
        BufferedReader reader;
        ArrayList<Graph> graphs = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                if (!line.equals("")) {

                    int numOfNodes = Integer.parseInt(line);
                    line = reader.readLine();

                    ArrayList<ArrayList<Integer>> neighbour = new ArrayList<>();
                    while (!line.isEmpty()) {
                        ArrayList<Integer> neigh = new ArrayList<>();
                        String[] n = line.split(",");
                        for (String i : n) {
                            neigh.add(Integer.parseInt(i));
                        }
                        neighbour.add(neigh);
                        line = reader.readLine();
                    }
                    graphs.add(new Graph(numOfNodes, neighbour));
                } else {
                    line = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphs;
    }
}

