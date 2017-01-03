package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
    private GeographicPoint from; 
    private GeographicPoint to; 
    private String roadName;
    private String roadType;
    private double length;
    
    public MapEdge(GeographicPoint from, GeographicPoint to, String roadName,
            String roadType, double length) {
        this.from = from;
        this.to = to;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }
    
    public GeographicPoint getTo() {
        return to;
    }
    
    public double getLength() {
        return length;
    }
}
