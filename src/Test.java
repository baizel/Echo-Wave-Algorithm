import java.util.ArrayList;

public class Test {
    private final static int K = 10;

    public static void main(String[] args) throws IllegalAccessException {
        ArrayList<Graph> allGraphs = Graph.GenerateGraphFromTxt("nodes.txt");
        int graphNumber = 1;
        for (Graph graph : allGraphs) {
            System.out.println("/////////////////////// Graph " + graphNumber + " ///////////////////////////////");

            int iterationCounter = 0;
            int N = graph.getNumberOfNodes();
            boolean isInitiated = false;
            boolean hasDecided = false;

            outerLoop:
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < K; j++) {
                    int numberOfNodes = getRandomIntegerBetweenRange(1, N);
                    System.out.println("----------- Iteration "+ j + " with R value of "+numberOfNodes + " -----------------");
                    for (int k = 0; k < numberOfNodes; k++) {
                        int randNodeId = getRandomIntegerBetweenRange(0, N - 1);
                        if (!isInitiated) {
                            System.out.println(String.format("Node %d has initiated", graph.getNode(randNodeId).getId()));
                            graph.getNode(randNodeId).initiateEchoWave();
                            isInitiated = true;
                        } else {
                            Node n = graph.getNode(randNodeId);
                            hasDecided = n.runEchoExecution();
                        }

                        if (hasDecided) {
                            System.out.println(String.format("\nSummary\n" +
                                            "Iterations:\t\t\t\t| %d \n" +
                                            "# of Edges:\t\t\t\t| %d \n" +
                                            "Theoretical # 2*|E|:\t| %d \n" +
                                            "Actual Message counter:\t| %d",
                                    iterationCounter,
                                    graph.getNumberOfEdges(),
                                    2 * graph.getNumberOfEdges(),
                                    graph.getNumberOfMessagesSent()));
                            break outerLoop;

                        }

                    }
                    iterationCounter++;
                }
                iterationCounter++;
            }
            System.out.println("\nGraph Structure\n" + graph);
            graphNumber++;
        }

    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

