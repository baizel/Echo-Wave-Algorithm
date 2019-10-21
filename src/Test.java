import java.util.ArrayList;

public class Test {
    private final static int K = 10;
    private final static String NODE_DATA_FILE_NAME = "nodes.txt";
    private final static boolean IS_VERBOSE_OUTPUT = true;
    private final static int NUMBER_OF_ITERATION_FOR_AVERAGE = 1000;

    public static void main(String[] args) throws IllegalAccessException {
        averageIterations(NUMBER_OF_ITERATION_FOR_AVERAGE);
//        runAlgorithm(IS_VERBOSE_OUTPUT);
    }

    private static int[] runAlgorithm(boolean isVerbose) throws IllegalAccessException {
        ArrayList<Graph> allGraphs = Graph.GenerateGraphFromTxt(NODE_DATA_FILE_NAME, isVerbose);
        ArrayList<String> graphSummary = new ArrayList<>();
        int[] iterationCounterForAllGraphs = new int[allGraphs.size()];
        int graphNumber = 1;
        for (Graph graph : allGraphs) {
            if (isVerbose)
                System.out.println("/////////////////////// Graph " + graphNumber + " ///////////////////////////////");

            int iterationCounter = 0;
            int N = graph.getNumberOfNodes();
            boolean isInitiated = false;
            boolean hasDecided = false;

            outerLoop:
            for (int i = 0; i < N; i++) {
                iterationCounter++;
                for (int j = 0; j < K; j++) {
                    iterationCounter++;
                    int numberOfNodes = getRandomIntegerBetweenRange(1, N);
                    if (isVerbose) {
                        System.out.println("----------- Iteration " + iterationCounter + " with R value of " + numberOfNodes + " -----------------");
                    }
                    for (int k = 0; k < numberOfNodes; k++) {
                        int randNodeId = getRandomIntegerBetweenRange(0, N - 1);
                        if (!isInitiated) {
                            if (isVerbose) {
                                System.out.println(String.format("Node %d has initiated", graph.getNode(randNodeId).getId()));
                            }
                            graph.getNode(randNodeId).initiateEchoWave();
                            isInitiated = true;
                        } else {
                            Node n = graph.getNode(randNodeId);
                            hasDecided = n.runEchoExecution();
                        }

                        if (hasDecided) {
                            if (isVerbose) {
                                String sum = String.format("\nGraph %d Summary\n" +
                                                "Iterations:\t\t\t\t| %d \n" +
                                                "# of Edges:\t\t\t\t| %d \n" +
                                                "Theoretical # 2*|E|:\t| %d \n" +
                                                "Actual Message counter:\t| %d",
                                        graphNumber,
                                        iterationCounter,
                                        graph.getNumberOfEdges(),
                                        2 * graph.getNumberOfEdges(),
                                        graph.getNumberOfMessagesSent());
                                graphSummary.add(sum);
                                System.out.println(sum);
                            }
                            break outerLoop;

                        }

                    }
                }
            }
            if (isVerbose) {
                System.out.println("\nGraph Structure\n" + graph);
            }
            iterationCounterForAllGraphs[graphNumber - 1] = iterationCounter;
            graphNumber++;
        }
        if (isVerbose) {
            System.out.println("---------------- All Summary ----------------------------");
            for (String i : graphSummary) {
                System.out.println(i);
            }
        }
        return iterationCounterForAllGraphs;
    }

    private static void averageIterations(int numberOfTimesToRun) throws IllegalAccessException {
        ArrayList<Integer> averages = new ArrayList<>();
        for (int i = 0; i < numberOfTimesToRun; i++) {
            int[] res = runAlgorithm(false);
            for (int q = 0; q < res.length; q++) {
                if (averages.size() < res.length) {
                    averages.add(res[q]);
                } else {
                    averages.set(q, averages.get(q) + res[q]);
                }
            }
        }
        for (int i : averages) {
            System.out.print(i / numberOfTimesToRun + " ");
        }
    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

