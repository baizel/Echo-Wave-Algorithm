/**
 * Object to collect all the data from one run of the algorithm
 * @author baizel
 */
public class GraphAnalysisData {
    private String summary;
    private long timeOfExecution;
    private int iterationCount;
    private int numberOfEdges;
    private int numberOfNodes;
    private int messageCount;

    public GraphAnalysisData(String summary, long timeOfExecution, int iterationCount, int numberOfEdges, int numberOfNodes, int messageCount) {
        this.summary = summary;
        this.timeOfExecution = timeOfExecution;
        this.iterationCount = iterationCount;
        this.numberOfEdges = numberOfEdges;
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

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getMessageCount() {
        return messageCount;
    }


}
