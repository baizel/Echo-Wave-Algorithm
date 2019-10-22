import java.io.*;
import java.util.ArrayList;

public class Test {
    private final static int K = 10;
    private final static String NODE_DATA_FILE_NAME = "nodes.txt";
    private final static boolean IS_VERBOSE_OUTPUT = true;
    private final static int SAMPLE_SIZE = 10000;

    public static void main(String[] args) throws IllegalAccessException {
//        averageIterations(NUMBER_OF_ITERATION_FOR_AVERAGE);
//        GraphAnalysisData d = runAlgorithm(Graph.GenerateGraphFromTxt(NODE_DATA_FILE_NAME, true).get(0), "Graph 0", IS_VERBOSE_OUTPUT);
//        System.out.println(d.getTimeOfExecution());
        analyseData(SAMPLE_SIZE);
    }

    private static GraphAnalysisData runAlgorithm(Graph graph, String graphTitle, boolean isVerbose) throws IllegalAccessException {
        String sum = "";
        GraphAnalysisData data = null;
        if (isVerbose) {
            System.out.println("/////////////////////// " + graphTitle + " ///////////////////////////////");
            System.out.println(graph);
        }

        int iterationCounter = 0;
        int N = graph.getNumberOfNodes();
        boolean isInitiated = false;
        boolean hasDecided = false;

        long startTime = System.nanoTime();
        outerLoop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
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
                        long endTime = System.nanoTime();

                        sum = String.format("\n%s Summary\n" +
                                        "Iterations:\t\t\t\t| %d \n" +
                                        "# of Edges:\t\t\t\t| %d \n" +
                                        "Theoretical # 2*|E|:\t| %d \n" +
                                        "Actual Message counter:\t| %d",
                                graphTitle,
                                iterationCounter,
                                graph.getNumberOfEdges(),
                                2 * graph.getNumberOfEdges(),
                                graph.getNumberOfMessagesSent());
                        data = new GraphAnalysisData(sum, (endTime - startTime), iterationCounter, graph.getNumberOfEdges(), graph.getNumberOfNodes(), graph.getNumberOfMessagesSent());
                        if (isVerbose) {
                            System.out.println(sum);
                        }
                        break outerLoop;

                    }

                }
                iterationCounter++;
            }
            iterationCounter++;
        }
        if (isVerbose) {
            System.out.println("\nOutput Tree Structure\n" + graph);
        }

        return data;
    }

    public static GraphAnalysisData[][] analyseData(int sampleSize) throws IllegalAccessException {
        ArrayList<Graph> graphs = Graph.GenerateGraphFromTxt(NODE_DATA_FILE_NAME, false);
        GraphAnalysisData[][] data = new GraphAnalysisData[graphs.size()][sampleSize];
        int graphNumber = 0;
        for (Graph g : graphs) {
            for (int i = 0; i < sampleSize; i++) {
                GraphAnalysisData d = runAlgorithm(g, "Graph " + (graphNumber + 1), false);
                data[graphNumber][i] = d;
                g.resetState();
            }
            graphNumber++;
        }
//        for (int i = 0; i < data[0].length; i++) {
//            System.out.println(data[0][i].getIterationCount());
//        }
        generateOutputFile(data);
        return data;
    }

    private static void generateOutputFile(GraphAnalysisData[][] data) {
        for (int i = 0; i < data.length; i++) {
            File fout = new File("graph" + (i + 1) + ".txt");
            try {
                fout.createNewFile();
                FileOutputStream fos = new FileOutputStream(fout);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for (int j = 0; j < data[i].length; j++) {
                    bw.write(Integer.toString(data[i][j].getIterationCount()));
                    if (j + 1 < data[i].length) {
                        bw.write(",");
                    }
                }
                bw.newLine();
                for (int j = 0; j < data[i].length; j++) {
                    bw.write(Integer.toString(data[i][j].getMessageCount()));
                    if (j + 1 < data[i].length) {
                        bw.write(",");
                    }
                }
                bw.newLine();
                for (int j = 0; j < data[i].length; j++) {
                    bw.write(Long.toString(data[i][j].getTimeOfExecution()));
                    if (j + 1 < data[i].length) {
                        bw.write(",");
                    }
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

