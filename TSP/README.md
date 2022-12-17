This text describes the time complexity for various solutions to the TSP problem. The sorting is implemented in the code. Since sorting impacts performance,
the text will analyze the performance with and without sorting.

With sorting, heuristic methods, and my improvement of the backtracking method perform better. Before the analysis of the solutions, saying a few words about the sorting would be appropriate.

The code implements the Comparable interface. The edges of the vertex are sorted by their weights(ascending order). If the weights are equal, the edge with a lower
adjacent vertex comes first. The time complexity of sorting is O(nlogn). Sorting gave the following time results:

> java PA11Main big11.mtx time: Sorting took:0.5765 milliseconds
> java PA11Main az.mtx time: Sorting took:0.4487 milliseconds
> java PA11Main tinyRW.mtx time: Sorting took:0.392 milliseconds

mineHeuristic methods heavily depend on this improvement. Instead of checking each edge, the method simply adds the first edge in the adjacency list if it is not visited. However, surprisingly, the main improvement came from not using the queue. If we keep looping while the path.size() is not equal to the number of vertices, this method is faster than the regular heuristic method. This appraoche's main disadvantage is its heavy reliance on sorting.

There is mineHeuristicNoStorting method with does not depend on sorting. Again to my huge surprise, not using a queue and just looping until path.size()!=vertices.size()
have made the algorithm pretty efficient. Lastly, the improvement of the backtracking method just relies on a simple check. If the current cost of an incomplete path is already larger than the previous minimal cost, the code can start looking at another new combination. This can can speed up the solution process significantly for certain inputs. Yet it should be mentioned that its efficiency highly relies on the nature of the input.
 
____________________________________________________________
The code has been run 4 times for each file. The time is not approximated 
to more easily see the differences between the methods.

___________________________________________________________
The average time results without sorting
(no results for mineHeuristic because it does not work without sorting):

> java PA11Main az.mtx time
heuristic: cost = 604.3, 0.3528 milliseconds
mineHeuristickNoStorting: cost = 604.3, 0.0142 milliseconds
mineBacktrack: cost = 604.3, 0.1439 milliseconds
backtrack: cost = 604.3, 0.113 milliseconds

> java PA11Main big11.mtx time
heuristic: cost = 3.4, 0.4949 milliseconds
mineHeuristickNoStorting: cost = 3.4, 0.049 milliseconds
mineBacktrack: cost = 1.4, 6.1352 milliseconds
backtrack: cost = 1.4, 372.00702 milliseconds

>java PA11Main example.mtx time
heuristic: cost = 10.0, 0.4286 milliseconds
mineHeuristickNoStorting: cost = 10.0, 0.0105 milliseconds
mineBacktrack: cost = 10.0, 0.0204 milliseconds
backtrack: cost = 10.0, 0.0245 milliseconds

> java PA11Main tinyRW.mtx time
heuristic: cost = 4.7, 0.4383 milliseconds
mineHeuristickNoStorting: cost = 4.7, 0.0193 milliseconds
mineBacktrack: cost = 4.7, 0.0229 milliseconds
backtrack: cost = 4.7, 0.0275 milliseconds

_____________________________________________________________
Average ime results with sorting

> java PA11Main az.mtx time
heuristic: cost = 604.3, 0.3393 milliseconds
mineHeuristickNoStorting: cost = 604.3, 0.0165 milliseconds
mineHeuristic: cost = 604.3, 0.0188 milliseconds
mineBacktrack: cost = 604.3, 0.1691 milliseconds
backtrack: cost = 604.3, 0.1729 milliseconds

> java PA11Main big11.mtx time
heuristic: cost = 3.4, 0.3976 milliseconds
mineHeuristickNoStorting: cost = 3.4, 0.0457 milliseconds
mineHeuristic: cost = 3.4, 0.0536 milliseconds
mineBacktrack: cost = 1.4, 3.679 milliseconds
backtrack: cost = 1.4, 417.3107 milliseconds

>java PA11Main example.mtx time
heuristic: cost = 10.0, 0.326 milliseconds
mineHeuristickNoStorting: cost = 10.0, 0.0144 milliseconds
mineHeuristic: cost = 10.0, 0.0157 milliseconds
mineBacktrack: cost = 10.0, 0.0213 milliseconds
backtrack: cost = 10.0, 0.0217 milliseconds

> java PA11Main tinyRW.mtx time
heuristic: cost = 4.7, 0.324 milliseconds
mineHeuristickNoStorting: cost = 4.7, 0.011 milliseconds
mineHeuristic: cost = 4.7, 0.0216 milliseconds
mineBacktrack: cost = 4.7, 0.0238 milliseconds
backtrack: cost = 4.7, 0.0256 milliseconds

