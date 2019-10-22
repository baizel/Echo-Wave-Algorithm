public class GraphAnalysisData {
    private String summary;
    private long timeOfExecution;
    private int iterationCount;
    private int numberOFEdges;
    private int numberOfNodes;
    private int messageCount;

    public GraphAnalysisData(String summary, long timeOfExecution, int iterationCount, int numberOFEdges, int numberOfNodes, int messageCount) {
        this.summary = summary;
        this.timeOfExecution = timeOfExecution;
        this.iterationCount = iterationCount;
        this.numberOFEdges = numberOFEdges;
        this.numberOfNodes = numberOfNodes;
        this.messageCount = messageCount;
    }

    public String getSummary() {
        return summary;
    }

    public long getTimeOfExecution() {
        return timeOfExecution;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public int getNumberOFEdges() {
        return numberOFEdges;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getMessageCount() {
        return messageCount;
    }


}
