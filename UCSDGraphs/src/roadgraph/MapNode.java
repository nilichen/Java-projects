package roadgraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import geography.GeographicPoint;


public class MapNode {
    private GeographicPoint location;
    private double dist;
    private double predDist;
    private List<MapEdge> mapEdges;
    public static final Comparator<MapNode> BY_DIJKSTRA = new ByDijkstra();
    public static final Comparator<MapNode> BY_ASTAR = new ByAStar();
    
    public MapNode(GeographicPoint location) {
        this.location = location;
        this.dist = Double.POSITIVE_INFINITY;
        this.predDist = Double.POSITIVE_INFINITY;
        this.mapEdges = new ArrayList<>();
    }
    
    public void addEdge(MapEdge edge) {
        this.mapEdges.add(edge);
    }        
    
    public List<MapEdge> getEdges() {
        return this.mapEdges;
    }        
    
    public double getDist() {
        return dist;
    }       
    
    public void setDist(double dist) {
        this.dist = dist;
    }    
    
    public void setDist() {
        this.dist = Double.POSITIVE_INFINITY;
    }
    
    public double getPredDist() {
        return predDist;
    }
    
    public void setPredDist(GeographicPoint goal) {
        this.predDist = this.dist + this.location.distance(goal);
    }
    
    public void setPredDist() {
        this.predDist = Double.POSITIVE_INFINITY;
    }
    
    public GeographicPoint getLoc() {
        return location;
    }
    
//    public int compareTo(MapNode other) {
//        return ((Double) this.getDist()).compareTo((Double) other.getDist());
//    }
    
    private static class ByDijkstra implements Comparator<MapNode> {
        public int compare(MapNode v, MapNode w) {
            return ((Double) v.getDist()).compareTo((Double) w.getDist());
        }
    }
    
    private static class ByAStar implements Comparator<MapNode> {
        public int compare(MapNode v, MapNode w) {
            return ((Double) v.getPredDist()).compareTo((Double) w.getPredDist());
        }
    }
}
