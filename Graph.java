import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a directed graph (a set of vertices and a set of edges). The graph
 * is not generic and assumes that a string name is stored at each vertex.
 * @author Sarthak Goyal
 */
public class Graph<T> {

	// the graph -- a set of vertices (String name mapped to Vertex instance)
	HashMap<T, Vertex> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<T, Vertex>();
	}
	
	/**
	 * Constructs a graph based on a given list of sources and destinations.
	 */
	public Graph(List<T> sources, List<T> destinations) {
		vertices = new HashMap<T, Vertex>();
		for(int i = 0; i < sources.size(); i++)
			addEdge(sources.get(i), destinations.get(i));
	}

	/**
	 * Adds to the graph an edge from the vertex with name "name1" to the vertex
	 * with name "name2". If either vertex does not already exist in the graph, it is added.
	 */
	public void addEdge(T name1, T name2) {
		Vertex vertex1;
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		else {
			vertex1 = new Vertex(name1);
			vertices.put(name1, vertex1);
		}

		Vertex vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex(name2);
			vertices.put(name2, vertex2);
		}

		vertex1.addEdge(vertex2);
	}

	/*
	 * Determines if there is a cycle in the graph calls the depthFirstSearch() method.
	 */
	public boolean isCyclic() {
		for(Vertex v: vertices.values()) {
			for(Vertex x: vertices.values()) {
				x.setDist(Integer.MAX_VALUE);
			}
			boolean cyclic = depthFirstSearch(v, v);
			if (cyclic) {
				return cyclic;
			}
		}
		return false;	
	}
	
	/*
	 * This method implements the DFS algorithm 
	 * @return: true if cycle is found(comes back to the start vertex). False if no cycle found.
	 */
	private boolean depthFirstSearch(Vertex x, Vertex startingVertex) {	
			x.setDist(0);
			LinkedList<Vertex> adjacencyList = x.getAdjacencyList();
			for(Vertex w : adjacencyList) {
				if (w.equals(startingVertex)) {
					return true;
				}
				if (w.getDist() == Integer.MAX_VALUE) {
					w.setDist(0);
					boolean wasACycle = depthFirstSearch(w, startingVertex);
						if(wasACycle)
							return true;
				}
			}
			return false;
		}
	
	/*
	 * This method implements the BFS algorithm 
	 * @return: true if you can get from start to destination and False if you cannot.
	 * Throws an IllegalArgumentException if the start or destination entered by user do not exist in the graph.
	 */
	public boolean breathFirstSearch(T start, T destination) {
		for(Vertex x: vertices.values()) {
			x.setDist(Integer.MAX_VALUE);
		}

		if (!vertices.containsKey(start) || !vertices.containsKey(destination))  
			throw new IllegalArgumentException("The value that was entered for start or destitation does not exist in the graph");
		
		Vertex startingVertex = vertices.get(start);
		Vertex destinationVertex = vertices.get(destination);
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.offer(startingVertex);
		while(!q.isEmpty()) {
			Vertex x = q.poll();
			x.setDist(0);
			LinkedList<Vertex> adjacencyList = x.getAdjacencyList();
			for(Vertex w : adjacencyList) {
				if (w.equals(destinationVertex))
					return true;
				if (w.getDist() == Integer.MAX_VALUE)
					q.offer(w);
			}
		}
		return false;
	}
	
	/*
	 * This method implements the Topological Sort Algorithm.
	 * @return: Returns the sorted list with items in topoplogical sorting order (There can be many correct orderings).
	 * Throws an exception if the graph is cyclic.
	 */
	public List<T> topologicalsort() {
		List<T> sortedList = new ArrayList<T>();
		Queue<Vertex> q = new LinkedList<Vertex>();
		int numberOfVertices = 0;
		
		for(Vertex x: vertices.values()) {
			LinkedList<Vertex> adjList = x.getAdjacencyList();
			numberOfVertices++;
			for (Vertex w : adjList) {
				w.incrementIndegree();
			}
		}
		
		for (Vertex x : vertices.values()) {
			if (x.getIndegree() == 0) {
				q.offer(x);
			}	
		}
		
		while(!q.isEmpty()) {
			Vertex x = q.poll();
			sortedList.add(x.name);
			LinkedList<Vertex> adjList = x.getAdjacencyList();
			for (Vertex w : adjList) {
				w.decrementIndegree();
				if (w.getIndegree() == 0)
					q.offer(w);
			}
		}
		if (sortedList.size() != numberOfVertices)
			throw new IllegalArgumentException("The graph is cyclic.");
		return sortedList;
	}
	
	/**
	 * This class represents an edge between a source vertex and a destination
	 * vertex in a directed graph.
	 * 
	 * The source of this edge is the Vertex whose object has an adjacency list
	 * containing this edge.
	 */
	public class Edge {

		// destination of this directed edge
		private Vertex dst;

		//creates the edges
		public Edge(Vertex dst) {
			this.dst = dst;
		}

		//returns the edges destination.
		public Vertex getOtherVertex() {
			return this.dst;
		}

		//Creates a String representation of the Edge.
		public String toString() {
			return (String) this.dst.getName();
		}
	}
	

/**
 * This class represents a vertex (AKA node) in a directed graph. The vertex is
 * not generic, assumes that a string name is stored there.
 */
public class Vertex {

	// used to id the Vertex
	//private String name;
	private T name;
	private int distFromStart;
	private int indegree;

	// adjacency list
	private LinkedList<Vertex> adj;

	//Creates the vertex with the value gives as it's name.
	public Vertex(T name) {
		this.name = name;
		this.adj = new LinkedList<Vertex>();
		this.distFromStart = -1;
		this.indegree = 0;                           //Number of incoming edges.
	}

	//returns the value at the vertex (it's name).
	public T getName() {
		return name;
	}

	//Adds an edge from this vertex to the other vertex.
	public void addEdge(Vertex otherVertex) {
		adj.add(otherVertex);
	}
	
	//sets the distance from the start of the vertex.
	public void setDist(int dist) {
		distFromStart = dist;
	}
	
	//returns the distance from the start.
	public int getDist() {
		return distFromStart;
	}
	
	//returns all the vertices this vertex points to.
	public LinkedList<Vertex> getAdjacencyList () {
		return adj;
	}
	
	//Adds 1 to the indegree.
	public void incrementIndegree() {
		indegree++;
	}
	
	//Subtracts 1 from the indegree.
	public void decrementIndegree() {
		indegree--;
	}
	
	//returns the indegree.
	public int getIndegree() {
		return indegree;
	}
	
	//returns and checks to see if two vertices are equal
	public boolean equals(Vertex other) {
		return this.name.equals(other.name);
	}
}


}