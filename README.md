# Echo-Wave-Algorithm
There are 4 graph structures used to emulate the echo wave algorithm. The first 3 graphs are connected structures but not complete, whereas graph 4 is complete. The theoretical messages needed for an echo wave algorithm is 2*|E| where |E| is the number of edges in that structure (Note: This assumes that the decision making part of the algorithm is ignored). 
From the summary table, all 4 structure uses only uses 2*|E| amount of messages. Thus it is reasonable to assume that the message sending and receiving part of the algorithm is implemented correctly. 

Structures that are fully connected will require fewer iterations than other structures since every node is connected to every other node. Therefore, in each iteration, there is a high likelihood that the node chosen for that round will either have messages in the queue or has already processed the message since it is guaranteed that in the previous round at least one node will have sent a token to the current node. This cannot be said for an incomplete structure as for each iteration, it is likely the node chosen for that round will be waiting for messages. 

## Getting started
Run the Main class to run the program.\
To produce output for data analysis uncomment line 21 in ```Main.java```

To run analysis, run ```main.py``` from the python directory\
Run the below commands (for bash)
```bash
 cd python
 pip install -r requirements.txt
 python main.py
```

Summary Table 

|                                             | Graph 1 | Graph 2 | Graph 3 | Graph 4 |
|---------------------------------------------|---------|---------|---------|---------|
| No. of rounds for 1 execution               | 17      | 38      | 19      | 9       |
| Average No. of rounds after 1000 executions | 13.11   | 13.47   | 18.44   | 10.15   |
| No. of Edges                                | 6       | 7       | 12      | 21      |
| Total numbers of messages sent              | 12      | 14      | 24      | 42      |
| No. of nodes                                | 7       | 8       | 13      | 7       |
| Standard Variance                           | 27.63   | 30.77   | 39.46   | 14.73   |

| ANOVA Main for graph 1 & 4 | F-statistic = 206.325 | Pvalue = 1.33e-44 |
|----------------------------|-----------------------|-------------------|
