import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This class represents the directed graph.
 * It contains methods to solve the TSP.
 * @author Umid Muzrapov.
 *
 */
public class DGraph
{
	private ArrayList<Integer> vertices=new ArrayList<Integer>();
	private ArrayList<ArrayList<Edge>> adjacentVertices=new ArrayList<>();
	
	/**
	 * This is a constructor for DGraph.
	 * @param numberVertices how many vertices the graphs has.
	 * @param edges represents the edges of the graph.
	 */
	public DGraph(int numberVertices, ArrayList<Number[]> edges)
	{	
		//Let's keep index 0 empty to make calculations easy.
		adjacentVertices.add(new ArrayList<Edge>());
		
		for (int i=1; i<numberVertices+1; i++)
		{
			addVertex(i);
		}
		
		createAdjacencyList(edges);
		
		//Sort the edges so that the vertex with a lower weight
		//is at the front.
		sortByWeight(adjacentVertices);
	}
	
	/**
	 * This method prints out the graph, printing
	 * out the vertex and its edges.
	 */
	public void printAdjacentVertices()
	{
		if (adjacentVertices.size()<1)
		{
			System.out.println("{}");
		}
		
		for (int i=1; i<adjacentVertices.size(); i++)
		{
			System.out.println("Vertex "+i+": "+adjacentVertices.get(i).toString());
		}
	}
	
	/**
	 * This method add a vertex
	 * @param vertexNumber the number of vertices.
	 */
	private void addVertex(int vertexNumber)
	{
		vertices.add(vertexNumber);
		adjacentVertices.add(new ArrayList<Edge>());
	}
	
	/**
	 * This method returns the list of vertices.
	 * @return vertices the list of vertices.
	 */
	public ArrayList<Integer> getVertices()
	{
		return vertices;
	}
	
	/**
	 * This method returns the edges of the given vertex.
	 * @param vertex the vertex whose edges we are seeking.
	 * @return edges of the vertex.
	 */
	public ArrayList<Edge> getEdges(int vertex)
	{
		return adjacentVertices.get(vertex);
	}
	
	/**
	 * This method returns the degrees of the vertex.
	 * @param vertex the degree of the vertex we are looking.
	 * @return the number of degree.
	 */
	public int getDegree(int vertex)
	{
		return adjacentVertices.get(vertex).size();
	}
	
	/**
	 * This method removes vertices and adjacency vertices.
	 */
	public void clear()
	{
		vertices=new ArrayList<Integer>();
		adjacentVertices=new ArrayList<>();
	}
	
	/**
	 * This method adds an Edge to adjacency vertices list.
	 * @param pointA
	 * @param pointB
	 * @param weight
	 */
	public void addEdge(int pointA, int pointB, int weight)
	{
		Edge edgeObject=new Edge(pointA, pointB, weight);
		adjacentVertices.get(pointA).add(edgeObject);
	}

	/***********Four methods to solve TSP problem***********/
	
	/**
	 * This methods implements heuristic algorithm to find 
	 * the approximate Hamiltonian cycle.
	 * @param showTime boolean that indicates to show time or not.
	 * @param startVertex the vertex to start.
	 */
	public void useHeuristic(int startVertex, boolean showTime)
	{
		long startTime=System.nanoTime();
		
		boolean[] visited= new boolean[vertices.size()+1];
		ArrayList<Integer> path=new ArrayList<Integer>();
		LinkedList<Integer> queue= new LinkedList<>();
		queue.add(startVertex);
		visited[startVertex]=true;
		double totalCost=0;
		
		while (queue.size()!=0)
		{
			int currentVertex=queue.pollFirst();
			path.add(currentVertex);
			
			double minWeight=Double.MAX_VALUE;
			int minVertex=-1;
			
			//find the minEdgeWeight and min vertex.
			for (Edge edge: adjacentVertices.get(currentVertex))
			{
				if (edge.getWeight()<minWeight && !visited[edge.getNeighbour()])
				{
					minWeight=edge.getWeight();
					minVertex=edge.getNeighbour();
				}
			}
			
			if (minVertex!=-1)
			{
				//if found then add minEdgeWeight to total cost and 
				//add the min vertex to the queue.
				totalCost=totalCost+minWeight;
				queue.add(minVertex);
				visited[minVertex]=true;
			}	
		}
		
		totalCost=findPathBack(path, totalCost);
		long endTime=System.nanoTime();
		showResults(showTime, startTime, endTime,
				totalCost, path, "heuristic");
	}
	
	/**
	 * This method uses backtracking the find the exact answer to TSP problem.
	 * @param start vertex to start with.
	 * @param showTime boolean that indicates to show time or not.
	 */
	public void useBacktrack(int start, boolean showTime)
	{
		long startTime=System.nanoTime();
		
		ArrayList<Integer> path=new ArrayList<Integer>();
		boolean[] visited=new boolean[vertices.size()+1];
		ArrayList<Integer> minPath=new ArrayList<Integer>();
		path.add(start);
		double totalCost=recursiveSub(start, 0, path,
				visited, Double.MAX_VALUE, minPath);
		
		long endTime=System.nanoTime();
		showResults(showTime, startTime, endTime, totalCost, minPath, "backtrack");
	}
	
	/**
	 * This is my first improvement on heuristic method.
	 * Please read readme for more details.
	 * @param showTime boolean that indicates to show time or not.
	 */
	public void mineHeuristic(int start, boolean showTime)
	{
		int currentVertex=start;
		long startTime=System.nanoTime();
		double totalWeight=0;
		
		ArrayList<Integer> path=new ArrayList<Integer>();;
		path.add(1);
		
		while (path.size()!=vertices.size())
		{
			for (int i=0; i<adjacentVertices.get(currentVertex).size();i++)
			{
				int potentialVertex=adjacentVertices.get
						(currentVertex).get(i).getNeighbour();

				if (!path.contains(potentialVertex))
				{
					path.add(potentialVertex);
					totalWeight=totalWeight+adjacentVertices.get
							(currentVertex).get(i).getWeight();
					currentVertex=potentialVertex;
					break;
				}
			}	
		}
		
		for (Edge edge: adjacentVertices.get(currentVertex))
		{
			if (edge.getNeighbour()==1) totalWeight=totalWeight+edge.getWeight();
		}
		
		long endTime=System.nanoTime();
		showResults(showTime, startTime, endTime,
				totalWeight, path, "mineHeuristic");	
	}
	
	/**
	 * This is my improvement on backtrack method.
	 * @param start the vertex to start with.
	 * @param showTime boolean that indicates if to show time.
	 */
	public void mineBacktrack(int start, boolean showTime)
	{
		long startTime=System.nanoTime();
		
		ArrayList<Integer> path=new ArrayList<Integer>();
		boolean[] visited=new boolean[vertices.size()+1];
		ArrayList<Integer> minPath=new ArrayList<Integer>();
		path.add(start);
		double totalCost=recursiveSubMine(start, 0, path,
				visited, Double.MAX_VALUE, minPath);
		
		long endTime=System.nanoTime();
		showResults(showTime, startTime, endTime,
				totalCost, minPath, "mineBacktrack");
	}
	
	/**
	 * This is my second improvement on heuristic method that 
	 * works without pre-sorting if necessary.
	 * @param showTime boolean that indicates to show time or not.
	 */
	public void mineHeuristicNoStorting(int start, boolean showTime)
	{
	    long startTime=System.nanoTime();
	    ArrayList<Integer> path= new ArrayList<Integer>();
	    int minVertex=-1;
	    double minWeight=Double.MAX_VALUE;
	    int currentVertex=start;
	    double totalWeight=0;
	    path.add(1);
	    boolean[] visited=new boolean[vertices.size()+1];
	    visited[currentVertex]=true;
	    
	    while (path.size()!=vertices.size())
	    {
	      for (Edge edge: adjacentVertices.get((int)currentVertex))
	      {
	        if (!visited[edge.getNeighbour()])
	        {
	          double weight=edge.getWeight();
	          
	          if (weight<minWeight)
	          {
	            minWeight=weight;
	            minVertex=edge.getNeighbour();
	          }
	        }
	      }
	  
	      currentVertex=minVertex;
	      path.add(currentVertex);
	      visited[currentVertex]=true;
	      totalWeight=totalWeight+minWeight;
	      minVertex=-1;
	      minWeight=Double.MAX_VALUE;
	    }
	    
	    totalWeight = findPathBack(path, totalWeight);
	    
	    long endTime=System.nanoTime();
	    showResults(showTime, startTime, endTime, 
	    		totalWeight, path, "mineHeuristickNoStorting");
	  }
	
	/*************Private Helper Methods************/

	/**
	 * This is an auxiliary recursive method for the original backtrack method.
	 * @param currentVertex current vertex
	 * @param currentCost the total cost of the path so far.
	 * @param path the list of visited vertices.
	 * @param visited boolean list to see which of the vertices have been visited.
	 * @param totalCost totalCost of the minimal Path.
	 * @param minPath the list of vertices visited for minimal path.
	 * @return
	 */
	private double recursiveSub(int currentVertex, double currentCost, ArrayList<Integer> path,
			boolean[] visited, double totalCost, ArrayList<Integer> minPath)
	{
		visited[currentVertex]=true;
		
		if (path.size()==vertices.size())
		{
			totalCost = checkMinPathBacktrack(currentCost, path, totalCost, minPath);
		}
		
		else 
		{	
			for (Edge edge: adjacentVertices.get(currentVertex))
			{
				if (!visited[edge.getNeighbour()])
				{
					path.add(edge.getNeighbour());
					currentCost=currentCost+edge.getWeight();
					totalCost=recursiveSub(edge.getNeighbour(),
							currentCost, path, visited, totalCost, minPath);
					path.remove(path.size()-1);
					currentCost=currentCost-edge.getWeight();
					visited[edge.getNeighbour()]=false;
				}
			}
		}
		
		return totalCost;
	}
	
	/**
	 * This is an auxiliary recursive method 
	 * for the backtrack method improvement.
	 * @param currentVertex current vertex
	 * @param currentCost the total cost of the path so far.
	 * @param path the list of visited vertices.
	 * @param visited boolean list to see which of the vertices have been visited.
	 * @param totalCost totalCost of the minimal Path.
	 * @param minPath the list of vertices visited for minimal path.
	 * @return
	 */
	private double recursiveSubMine(int currentVertex, double currentCost, ArrayList<Integer> path,
			boolean[] visited, double totalCost, ArrayList<Integer> minPath)
	{
		visited[currentVertex]=true;
		
		//if the currentCost is already larger than the current total minimal
		//cost there is no reason to continue.
		if (currentCost>totalCost)
		{
			return totalCost;
		}
		
		
		if (path.size()==vertices.size())
		{
			totalCost = checkMinPathBacktrack(currentCost, path, totalCost, minPath);
		}
		
		else 
		{
			for (Edge edge: getEdges(currentVertex))
			{
				if (!visited[edge.getNeighbour()])
				{
					path.add(edge.getNeighbour());
					currentCost=currentCost+edge.getWeight();
					totalCost=recursiveSubMine(edge.getNeighbour(),
							currentCost, path, visited, totalCost, minPath);
					path.remove(path.size()-1);
					currentCost=currentCost-edge.getWeight();
					visited[edge.getNeighbour()]=false;
				}
			}
		}
		
		return totalCost;
	}
	
	/**
	 * This helper function finds the minimal total cost for
	 * backtrack method, considering the back path to vertex 1.
	 * @param currentCost the cost of the path so far.
	 * @param path the list of vertices visited.
	 * @param totalCost the total minimal cost.
	 * @param minPath the list of vertices for minimal path.
	 * @return total cost.
	 */
	private double checkMinPathBacktrack(double currentCost, ArrayList<Integer> path,
			double totalCost, ArrayList<Integer> minPath) 
	{
		currentCost = findPathBack(path, currentCost);
			
		if (currentCost<totalCost || (currentCost==totalCost
				&& pathHasLowerVertex(path, minPath)))
		{	
			minPath.clear();
			totalCost=currentCost;
			
			for (int i=0; i<path.size(); i++)
			{
				minPath.add(path.get(i));
			}
		}
		
		return totalCost;
	}
	
	private boolean pathHasLowerVertex(ArrayList<Integer> path, ArrayList<Integer> minPath)
	{
		String minPathString="";
	    String pathString="";
	    
	    for (int i=0; i<minPath.size(); i++)
	    {
	        pathString=pathString+path.get(i);
	        minPathString=minPathString+minPath.get(i);
	    }
	    
	    return (Integer.parseInt(pathString)<Integer.parseInt(minPathString));
	}
	
	/**
	 * This helped method finds the total cost for heuristic method,
	 * including the path back to vertex 1.
	 * @param path the list of visited vertices.
	 * @param totalCost the total cost of minimal cycle.
	 * @return total cost.
	 */
	private double findPathBack(ArrayList<Integer> path, double totalCost)
	{
		for (Edge edge: adjacentVertices.get(path.get(path.size()-1)))
		{
			if(edge.getNeighbour()==1)
			{
				totalCost=totalCost+edge.getWeight();
			}
		}
		return totalCost;
	}
	
	/**
	 * This method prints out the results of the search,
	 * configuring it based on the user input.
	 * @param showTime boolean that indicates to show time or not.
	 * @param startTime start time
	 * @param endTime end time
	 * @param totalCost the minimal cost.
	 * @param path the list of visited vertices.
	 * @param method name of the method used.
	 */
	private void showResults(boolean showTime, long startTime,
			long endTime, double totalCost, ArrayList<Integer> path, String method )
	{
		if (!showTime)
		{
			System.out.println("cost = "+String.format("%.1f", totalCost)
			+", visitOrder = "+path.toString());
		}
		
		if (showTime)
		{
			System.out.println(method+": cost = "+String.format("%.1f", totalCost)
			+", "+(endTime-startTime)/1000000.0f+" milliseconds");
		}
	}
	
	/**
	 * This method sorts the adjacency list of edges.
	 * In read me file, I will consider the cases with and without sorting.
	 * @param adjacentVertices
	 */
	private void sortByWeight(ArrayList<ArrayList<Edge>> adjacentVertices)
	{
		for (ArrayList<Edge> list: adjacentVertices)
		{
			Collections.sort(list);
		}
	}
	
	/**
	 * This method creates the adjacency list from the 
	 * the list of edges (the edge is not an Edge object.).
	 * @param edges represents the edges of the graph.
	 */
	private void createAdjacencyList(ArrayList<Number[]> edges)
	{
		for (int i=1; i<edges.size(); i++)
		{
			Number[] edge=edges.get(i);
			Edge edgeObject=new Edge((int)edge[0], (int)edge[1], (double)edge[2]);
			adjacentVertices.get((int)edge[0]).add(edgeObject);
		}
	}
	
}

/**
 * This class represents an edge of the graph.
 * @author Umid Muzrapov
 *
 */
class Edge implements Comparable<Edge>
{
	private int pointA; //from vertex
	private int pointB; // to vertex
	private double weight;
	
	/**
	 * This is a constructor for the Edge class.
	 * @param pointA from vertex
	 * @param pointB to vertex
	 * @param weight the weight of the edge.
	 */
	public Edge(int pointA, int pointB, double weight) 
	{
		this.pointA=pointA;
		this.pointB=pointB;
		this.weight=weight;
	}
	
	/**
	 * This is copy constructor for the Edge class.
	 * @param edge
	 */
	public Edge(Edge edge)
	{
		pointA=edge.getVertex();
		pointB=edge.getNeighbour();
		weight=edge.getWeight();
	}
	
	/**
	 * This method constructs the string the represents the edge.
	 * @return String
	 */
	public String toString()
	{
		String string="{"+pointA+" "+pointB+" "+weight+"}";
		return string;
	}
	
	/**
	 * This method gets the weight of the edge.
	 * @return weight
	 */
	public double getWeight()
	{
		return weight;
	}
	
	/**
	 * This methods return to-vertex.
	 * @return the adjacent vertex.
	 */
	public int getNeighbour()
	{
		return pointB;
	}
	
	/**
	 * This method returns from-vertex.
	 * @return vertex.
	 */
	public int getVertex()
	{
		return pointA;
	}

	@Override
	/**
	 * This methods compares two edges and is used for sorting.
	 */
	public int compareTo(Edge anotherEdge) 
	{
		//if the weights are the same, put the the one with 
		// a lower vertex number at the front.
		if (weight==anotherEdge.getWeight())
		{
			return pointB-anotherEdge.getNeighbour();
		}
		
		return Double.compare(weight, anotherEdge.getWeight());
	}
	
}