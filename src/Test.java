import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<Graph> gs = Graph.GenerateGraphFromTxt("nodes.txt");
//        for(Graph g: gs) {
//
//        }
        Graph g = gs.get(1);
        System.out.println(g);
        int N = g.getNumberOfNodes();
        int K = 10;
        int counter = 0;
        boolean isInitiated = false;
        boolean decided = false;

        g.getNode(0).initiateEchoWave();
        outerLoop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                int numberOfNodes = getRandomIntegerBetweenRange(1, N);
                for (int k = 0; k < numberOfNodes; k++) {
                    try {
                        Node n = g.getNode(getRandomIntegerBetweenRange(0, N - 1));
                        decided = n.runEchoExecution();
//                        System.out.print(" Node " + n);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (decided) {
                        System.out.println(String.format("\nIteration: %d, # of Edges: %d, Theoretical Max messages 2*|E|: %d, Actual Message counter: %d", counter, g.getNumberOfEdges(), 2 * g.getNumberOfEdges(), Node.messageSentCounter));
                        break outerLoop;

                    }

                }
                System.out.println("");

                counter++;
            }
            counter++;
        }

        System.out.println("\n"+g);
    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

