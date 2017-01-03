import edu.princeton.cs.algs4.*;
import java.util.*;

public class PointSET {
    private TreeSet<Point2D> pointSet;
 // construct an empty set of points 
    public PointSET() {
        pointSet = new TreeSet<>();
    }
// is the set empty? 
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
// number of points in the set 
    public int size() {
        return pointSet.size();
    }
// add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointSet.add(p);
    }
// does the set contain point p? 
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }
// draw all points to standard draw 
    public void draw() {
        for (Point2D p: pointSet)
            p.draw();
    }
// all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> insideRect = new ArrayList<>();
        for (Point2D p: pointSet) {
            if (rect.contains(p))
                insideRect.add(p);
        }
        return insideRect;
    }
// a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        double minDist = Double.MAX_VALUE;
        Point2D nnPoint = null;
        for (Point2D q: pointSet) {
            double dist = q.distanceTo(p);
            if (dist < minDist) {
                minDist = dist;
                nnPoint = q;
            }
        }
        return nnPoint;
    }
// unit testing of the methods (optional) 
    public static void main(String[] args) {
        
    }
}

