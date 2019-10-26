import numpy
import scipy.stats as stats

if __name__ == "__main__":
    g = []
    for i in range(1, 5):
        f = open("../graph{}.txt".format(i))
        s_iteration, s_messageCounter, s_time = (f.read().split("\n"))

        iteration = [int(i) for i in s_iteration.split(",")]
        time = [int(i) for i in s_time.split(",")]
        var = numpy.var(iteration)
        averageTime = str(numpy.average(time) / 1e+6) + "ms"
        avgIteration = str(numpy.average(iteration))
        g.append(iteration)
        print("Graph {}, Standard variance: {}, Average Time: {}, Average iteration: {}".format(i, var, averageTime,avgIteration))
    print(stats.f_oneway(g[0],g[3]))
