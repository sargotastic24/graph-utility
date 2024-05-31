import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs.
 * 
 * @author Sarthak Goyal
 */
public class GraphUtility {

	/*
	 * This method uses the Depth-First search algorithm to determine whether there is a cycle in the graph or not.
	 * Throws an IllegalArgumentException if number of sources and destinations are not the same.
	 */
	public static <Type> boolean isCyclic(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		if(sources.size() != destinations.size()) {
			throw new IllegalArgumentException("The number of sources and destinations do not match.");
		}
		Graph<Type> g = new Graph<Type>(sources, destinations);
		return g.isCyclic();
	}

	/*
	 * This method uses the breath-first search algorithm to determine whether there is a path from the vertex with srcData 
	 * to the vertex with dstData in the graph.  
	 * Throws an IllegalArgumentException if there does not exist a vertex in the graph with srcData, and likewise for dstData.
	 * Also throws an IllegalArgumentException if number of sources and destinations are not the same.
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) throws IllegalArgumentException {
		if(sources.size() != destinations.size()) {
			throw new IllegalArgumentException("The number of sources and destinations do not match.");
		}
		Graph<Type> g = new Graph<Type>(sources, destinations);
		return g.breathFirstSearch(srcData, dstData);
	}

	/*
	 * This method uses the topographical sort algorithm to generate a sorted ordering of the vertices in the graph.    
	 * Throws an IllegalArgumentException if the graph contains a cycle.
	 * Also throws an IllegalArgumentException if number of sources and destinations are not the same.
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		if(sources.size() != destinations.size())
			throw new IllegalArgumentException("The number of sources and destinations do not match.");
		
		Graph<Type> g = new Graph<Type>(sources, destinations);
		return g.topologicalsort();
	}

	/**
	 * Builds "sources" and "destinations" lists according to the edges
	 * specified in the given DOT file (e.g., "a -> b"). Assumes that the vertex
	 * data type is String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
	 * --accepts \\-style comments 
	 * --accepts one edge per line or edges terminated with ; 
	 * --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename - name of the DOT file
	 * @param sources - empty ArrayList, when method returns it is a valid
	 *        "sources" list that can be passed to the public methods in this
	 *        class
	 * @param destinations - empty ArrayList, when method returns it is a valid
	 *        "destinations" list that can be passed to the public methods in
	 *        this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		scan.useDelimiter(";|\n");

		// Determine if graph is directed (i.e., look for "digraph id {").
		String line = "", edgeOp = "";
		while(scan.hasNext()) {
			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");

			if(line.indexOf("digraph") >= 0) {
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				break;
			}
		}
		if(edgeOp.equals("")) {
			System.out.println("DOT graph must be directed (i.e., digraph).");
			scan.close();
			System.exit(0);

		}

		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		while(scan.hasNext()) {
			String[] substring = line.split(edgeOp);

			for(int i = 0; i < substring.length - 1; i += 2) {
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				// if string is empty, try again
				if(vertex1.equals(""))
					continue;

				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				if(vertex2.equals(""))
					continue;

				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}

			// do until the "}" has been read
			if(substring[substring.length - 1].indexOf("}") >= 0)
				break;

			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
		}

		scan.close();
	}
}