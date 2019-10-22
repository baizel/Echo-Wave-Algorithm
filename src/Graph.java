import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private int numberOfEdges;
    private int numberOfNodes;
    private boolean isVerbose;
    private ArrayList<ArrayList<Integer>> initialState;

    public Graph(int numOfNodes, ArrayList<ArrayList<Integer>> neighbour, boolean isVerbose) {
        this.isVerbose = isVerbose;
        this.numberOfNodes = numOfNodes;
        setUpNeighbours(neighbour,numOfNodes,isVerbose);
        initialState = new ArrayList<>(neighbour);
    }
    private void setUpNeighbours(ArrayList<ArrayList<Integer>> neighbour,int numOfNodes,boolean isVerbose){
        for (int i = 0; i < numOfNodes; i++) {
            nodes.add(new Node(i,isVerbose));
        }

        //Create neighbours
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < neighbour.size(); i++) {
            for (int j = 0; j < neighbour.get(i).size(); j++) {
                Node neigh = getNode(neighbour.get(i).get(j));
                edges.add(new Edge(i, neigh.getId()));
                getNode(i).addNeighbour(neigh);
            }
        }
        numberOfEdges = edges.size();
    }
    public void resetState(){
        nodes = new ArrayList<>();
        setUpNeighbours(initialState,numberOfNodes,isVerbose);
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

    public int getNumberOfNodes() {
        return nodes.size();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Node n : nodes) {
            ret.append(String.format("Node: %d, Father: %s, Neighbours: %s isInitiator: %s\n", n.getId(), n.getFather(), n.getNeighbours().toString(), n.isInitiator()));
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
    public static ArrayList<Graph> GenerateGraphFromTxt(String filename, boolean isVerbose) {
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
                    while (line != null && !line.isEmpty()) {
                        ArrayList<Integer> neigh = new ArrayList<>();
                        String[] n = line.split(",");
                        for (String i : n) {
                            neigh.add(Integer.parseInt(i));
                        }
                        neighbour.add(neigh);
                        line = reader.readLine();
                    }
                    graphs.add(new Graph(numOfNodes, neighbour, isVerbose));
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

    public int getNumberOfMessagesSent() {
        int counter = 0;
        for (Node n : nodes) {
            counter += n.getMessageSentCounter();
        }
        return counter;
    }
}

