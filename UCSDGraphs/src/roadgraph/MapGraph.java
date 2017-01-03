/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashMap;
import java.util.Comparator;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
    HashMap<GeographicPoint, MapNode> mapNodes;
    List<MapEdge> mapEdges;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		mapNodes = new HashMap<GeographicPoint, MapNode>();
		mapEdges = new ArrayList<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return mapNodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return mapNodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return mapEdges.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{	
	    if (location != null && !mapNodes.containsKey(location)) {
		    MapNode node = new MapNode(location);
		    mapNodes.put(location, node);
		    return true;
		}
		return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {	
	    if (from == null || to == null || length < 0 
	            || !mapNodes.containsKey(from) || !mapNodes.containsKey(to))
	        throw new IllegalArgumentException();
	    MapEdge edge = new MapEdge(from, to, roadName, roadType, length);
	    mapNodes.get(from).addEdge(edge);
		mapEdges.add(edge);		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		HashMap<GeographicPoint, GeographicPoint> parentNodes = new HashMap<>();
		List<GeographicPoint> queue = new LinkedList<>();
		List<GeographicPoint> visited = new ArrayList<>();
		queue.add(start);
		visited.add(start);
		while (!queue.isEmpty()) {
		    GeographicPoint curr = queue.remove(0);
		    for (MapEdge edge: mapNodes.get(curr).getEdges()) {
		        GeographicPoint next = edge.getTo();
		        if (!visited.contains(next)) {
		            queue.add(next);
		            visited.add(next);
		            nodeSearched.accept(next);
		            parentNodes.put(next, curr);
		            if (next.equals(goal)) {
		                List<GeographicPoint> path = buildPath(start, next, parentNodes);
		                return path;
		            }
		        }
		    }
		}
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		return null;
	}
	
    private List<GeographicPoint> buildPath(GeographicPoint start, GeographicPoint goal, 
            HashMap<GeographicPoint, GeographicPoint> parentNodes) {
        List<GeographicPoint> path = new LinkedList<>();
        GeographicPoint curr = goal;
        while (!curr.equals(start)) {
            path.add(0, curr);
            curr = parentNodes.get(curr);
        }
        path.add(0, curr);
        return path;
    }

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	private void reset() {
	    for (Map.Entry<GeographicPoint, MapNode> entry: mapNodes.entrySet()) {
	        MapNode node = entry.getValue();
	        node.setDist();
	        node.setPredDist();
	    }
	}
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{ 
	    reset();
	    HashMap<GeographicPoint, GeographicPoint> parentNodes = new HashMap<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(MapNode.BY_DIJKSTRA);
        List<GeographicPoint> visited = new ArrayList<>();
        MapNode startNode = mapNodes.get(start);
        startNode.setDist(0);
        startNode.setPredDist(goal);
        queue.add(startNode);
        
        while (!queue.isEmpty()) {
            MapNode currNode = queue.remove();            
            GeographicPoint currLoc = currNode.getLoc();
//            System.out.println(currLoc);
//            System.out.println(currNode.getDist());
            if (!visited.contains(currLoc)) {
                visited.add(currLoc);
                nodeSearched.accept(currLoc);
                if (currLoc.equals(goal)) {
                    List<GeographicPoint> path = buildPath(start, currLoc, parentNodes);
                    System.out.println(visited.size());
                    return path;
                }
                for (MapEdge edge: currNode.getEdges()) {
                    GeographicPoint nextLoc = edge.getTo();
                    if (!visited.contains(nextLoc)) {
                        MapNode nextNode = mapNodes.get(nextLoc);
                        double currDist = currNode.getDist() + edge.getLength();
                        if (currDist < nextNode.getDist()) {
                            nextNode.setDist(currDist); 
                            queue.add(nextNode);   
                            parentNodes.put(nextLoc, currLoc);                           
                        }
                    }
                }
            }
        }
        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());
        return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
	    reset();
        HashMap<GeographicPoint, GeographicPoint> parentNodes = new HashMap<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(MapNode.BY_ASTAR);
        List<GeographicPoint> visited = new ArrayList<>();
        MapNode startNode = mapNodes.get(start);
        startNode.setDist(0);
        queue.add(startNode);
        
        while (!queue.isEmpty()) {
            MapNode currNode = queue.remove();            
            GeographicPoint currLoc = currNode.getLoc();
            if (!visited.contains(currLoc)) {
                visited.add(currLoc);
                nodeSearched.accept(currLoc);
                if (currLoc.equals(goal)) {
                    List<GeographicPoint> path = buildPath(start, currLoc, parentNodes);
                    System.out.println(visited.size());
                    return path;
                }
                for (MapEdge edge: currNode.getEdges()) {
                    GeographicPoint nextLoc = edge.getTo();
                    if (!visited.contains(nextLoc)) {
                        MapNode nextNode = mapNodes.get(nextLoc);
                        double currDist = currNode.getDist() + edge.getLength();
                        if (currDist < nextNode.getDist()) {
                            nextNode.setDist(currDist);
                            nextNode.setPredDist(goal);
                            queue.add(nextNode);   
                            parentNodes.put(nextLoc, currLoc);                           
                        }
                    }
                }
            }
        }
        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());
        return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		List<GeographicPoint> testrouteBFS = simpleTestMap.bfs(testStart, testEnd);
		System.out.println("Test 1 using simpletest: BFS");
		System.out.println(testrouteBFS);
		
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		System.out.println(testroute);
		
		List<GeographicPoint> testrouteAstar = simpleTestMap.aStarSearch(testStart,testEnd);
		System.out.println(testrouteAstar);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = testMap.aStarSearch(testStart,testEnd);
		System.out.println(testroute);
		System.out.println(testroute2);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		System.out.println(testroute);
		System.out.println(testroute2);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
