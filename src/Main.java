import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to run the echo wave algorithm.
 * This class also has the ability to output recorded data as a text file
 *
 * @author baizel
 */
public class Main {
    private final static int K = 10;
    private final static boolean IS_VERBOSE_OUTPUT = true;
    private final static int SAMPLE_SIZE = 1000;
    private static String nodeDataFileName = "nodes.txt";
    private static boolean isUserInput = false;

    public static void main(String[] args) throws IllegalAccessException {
        parseArgs(args);
        int counter = 1;
        for (Graph g : Graph.generateGraphFromTxt(nodeDataFileName, IS_VERBOSE_OUTPUT)) {
            runAlgorithm(g, ("Graph " + counter), IS_VERBOSE_OUTPUT);
            counter++;
        }
//        generateDataForAnalysis(SAMPLE_SIZE);
    }

    /**
     * Runs the algorithm for a single graph given. Data is collected during the execution and returned when
     * algorithm terminates as a GraphAnalysisData object
     *
     * @param graph      Graph object
     * @param graphTitle a string used for file name and for debug output
     * @param isVerbose  prints all outputs during the execution of the algorithm
     * @return GraphAnalysisData
     * @throws IllegalAccessException if the graph reaches an illegal state
     */
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
                int numberOfNodes = isUserInput ? getUserRValue(N) : getRandomIntegerBetweenRange(1, N); // The R Value
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
                getUserInput("Press enter to continue to next iteration");
            }
            iterationCounter++;
        }
        if (isVerbose) {
            System.out.println("\nOutput Tree Structure\n" + graph);
        }
        return data;
    }

    /**
     * Runs algorithm each graph in nodes.txt for a given number.
     *
     * @param sampleSize the number of iterations
     * @return returns sampleSize of data for each graph as a 2d array [NumberOfGraphs][sampleSize]
     * @throws IllegalAccessException if the graph is in a bad state
     */
    public static GraphAnalysisData[][] generateDataForAnalysis(int sampleSize) throws IllegalAccessException {
        System.out.println("Running Echo wave algorithm for " + sampleSize + " iterations");
        ArrayList<Graph> graphs = Graph.generateGraphFromTxt(nodeDataFileName, false);
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
        generateOutputFile(data);
        System.out.println("Data generated for " + sampleSize + " sets");
        return data;
    }

    //Writes data to file
    private static void generateOutputFile(GraphAnalysisData[][] data) {
        for (int i = 0; i < data.length; i++) {
            String fileName = "graph" + (i + 1) + ".txt";
            File fout = new File(fileName);
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
                System.out.println("Generating output file " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getUserInput(String prompt) {
        System.out.println(prompt);
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private static Integer convertUserStringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }

    private static int getUserRValue(int N) {
        Integer rValue = null;
        boolean isValidRval = false;
        while (!isValidRval) {
            rValue = convertUserStringToInt(getUserInput("Enter an R value between 1 and " + N));
            isValidRval = rValue != null && rValue >= 1 && rValue <= N;
        }
        return rValue;
    }

    private static void parseArgs(String[] args) {
        int count = 0;
        for (String s : args) {
            switch (s) {
                case "-n":
                    try {
                        nodeDataFileName = args[count + 1];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        throw new IllegalArgumentException("Please provide a file name for the nodes");
                    }
                    break;
                case "-r":
                    isUserInput = true;
                    break;
                case "-h":
                case "--help":
                    System.out.println("Use -r to manually choose the r value for every iteration");
                    break;
            }
            count++;
        }
    }
}

