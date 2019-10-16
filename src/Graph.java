import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {
    private static int numberOfNodes = 7;
    private ArrayList<Node> nodes = new ArrayList<>();
    private int maximumNumberOfEdges = 0;


    public Graph(int numOfNodes, ArrayList<ArrayList<Integer>> neighbour) {
        numberOfNodes = numOfNodes;
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new Node(i));
        }
        generateNeighbours(neighbour);
        maximumNumberOfEdges = (numberOfNodes * (numberOfNodes - 1)) / 2;
    }

    private void generateNeighbours(ArrayList<ArrayList<Integer>> neighbour) {
        //hard code for now
        for (int i = 0; i < neighbour.size(); i++) {
            for (int j = 0; j < neighbour.get(i).size(); j++) {
                getNode(i).addNeighbour(getNode(neighbour.get(i).get(j)));
            }
        }
    }

    public int getMaximumNumberOfEdges() {
        return maximumNumberOfEdges;
    }

    public Node getNode(int id) {
        if (id > nodes.size() || id < 0) {
            throw new IllegalArgumentException(String.format("Node Id %s out of bound", id));
        }
        return nodes.get(id);
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
