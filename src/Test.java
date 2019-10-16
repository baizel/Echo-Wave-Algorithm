import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<Graph> gs = Graph.GenerateGraphFromTxt("nodes.txt");
        Graph g = gs.get(0);
        System.out.println(gs.get(0));
        int N = 7;
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
                        System.out.println(String.format("\nIteration: %d, Maximum Edges: %d, Theoretical Max messages 2*|E|: %d, Actual Message counter: %d", counter, g.getMaximumNumberOfEdges(), 2 * g.getMaximumNumberOfEdges(), Node.messageSentCounter));
                        break outerLoop;

                    }

                }
                System.out.println("");

                counter++;
            }
            counter++;
        }

        System.out.println(gs.get(0));
    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

