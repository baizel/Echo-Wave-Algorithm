public class Test {

    public static void main(String[] args) {
        Graph g = new Graph();
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
                        decided = g.getNode(getRandomIntegerBetweenRange(0, N - 1)).runEchoExecution();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (decided) {
                        System.out.println("iteration " + counter + " " + Node.messageSentCounter);
                        break outerLoop;

                    }
                }

                counter++;
            }
            counter++;
        }

    }

    //https://dzone.com/articles/random-number-generation-in-java
    public static int getRandomIntegerBetweenRange(double min, double max) {
        return (int) ((int) (Math.random() * ((max - min) + 1)) + min);
    }
}

