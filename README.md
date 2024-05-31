# graph-utility

Graph Utility

This project provides a generic implementation for directed, unweighted, and sparse graphs, along with several utility functions:

- Cycle Detection: This function determines if a cycle (circular path) exists within the graph using depth-first search.
- Connectivity Check: This function verifies whether a path exists between two specific vertices in the graph.
- Vertex Sorting (To be implemented): This function will sort the graph's vertices based on a chosen criteria.
Requirements

The provided GraphUtility.java file contains incomplete implementations for the aforementioned functions. Users are encouraged to download and complete these methods as described below.

Graph Representation

(Provide details on the specific graph data structure used in the implementation. Examples include adjacency lists, adjacency matrices, or custom structures.)

Methods

1) isCyclic(List<Type> sources, List<Type> destinations)

This method takes two lists as input: sources and destinations.
Each pair (sources[i], destinations[i]) represents a directed edge from a vertex with data sources[i] to a vertex with data destinations[i].
If the lengths of sources and destinations differ, an IllegalArgumentException is thrown.
The function employs depth-first search to determine if the graph contains a cycle.
It returns true if a cycle is found, and false otherwise.

2) areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)

Similar to isCyclic, this method takes two lists as input.
If the lengths of sources and destinations differ, an IllegalArgumentException is thrown.
The function determines if there exists a path from a vertex with data srcData to a vertex with data dstData in the graph.
It returns true if a path exists, and false otherwise. (Describe the chosen path finding algorithm here.)

3) vertexSorting(List<Type> sources, List<Type> destinations) (To be implemented)

This method takes the same input as isCyclic and areConnected.
It will sort the vertices of the graph based on a chosen criteria (e.g., topological sorting for directed acyclic graphs).
The specific sorting algorithm is left for implementation.
