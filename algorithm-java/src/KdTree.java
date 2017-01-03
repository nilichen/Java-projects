import edu.princeton.cs.algs4.*;
import java.util.*;

public class KdTree {
    private static final boolean V = true;
    // private static final boolean H = false;
    private Node root;
    private int n;
    private class Node implements Comparable<Node> {
        private Point2D point;
        private Node left, right;
        private RectHV rect;
        private boolean flag;
        
        public Node(Point2D point, boolean flag, RectHV rect) {
            this.point = point;
            this.flag = flag;
            this.rect = rect;
            this.left = null;
            this.right = null;
        }
        
        @Override
        public int compareTo(Node that) {
            if (flag) return ((Double) this.point.x()).compareTo((Double) that.point.x());
            else return ((Double) this.point.y()).compareTo((Double) that.point.y());
        }
    }
// is the set empty? 
    public boolean isEmpty() {
        return n == 0;
    }
 // number of points in the set 
    public int size() {
        return n;
    }
// add the point to the set (if it is not already in the set)
    public  void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p, V, new RectHV(0, 0, 1, 1));
            n++;
        } else {
            if (insertHelper(root, p)) n++;
        }
    }
    private boolean insertHelper(Node h, Point2D p) {
        if (h.point.equals(p)) return false;
        Node newNode = new Node(p, !h.flag, null);
        if (h.compareTo(newNode) > 0) {
            if (h.left != null) return insertHelper(h.left, p);
            else {
                RectHV newRect = null;
                if (h.flag) newRect = new RectHV(h.rect.xmin(), h.rect.ymin(), h.point.x(), h.rect.ymax());
                else newRect = new RectHV(h.rect.xmin(), h.rect.ymin(), h.rect.xmax(), h.point.y());
                h.left = new Node(p, !h.flag, newRect);
            }
        } else {
            if (h.right != null) return insertHelper(h.right, p);
            else {
                RectHV newRect = null;
                if (h.flag) newRect = new RectHV(h.point.x(), h.rect.ymin(), h.rect.xmax(), h.rect.ymax());
                else newRect = new RectHV(h.rect.xmin(),  h.point.y(), h.rect.xmax(), h.rect.ymax());
                h.right = new Node(p, !h.flag, newRect);
            }
        }
        return true;
    }
 // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        return containHelper(root, new Node(p, true, null));
    }
    
   private boolean containHelper(Node h, Node p) {
       if (h.point.equals(p.point)) return true;
       if (h.compareTo(p) > 0) {
           if (h.left != null) return containHelper(h.left, p);
       } else {
           if (h.right != null) return containHelper(h.right, p);
       }
       return false;
   }
// draw all points to standard draw 
    public void draw() {
        drawHelper(root);
    }
    private void drawHelper(Node h) {
        if (h == null) return;
        h.point.draw();

        if (h.flag) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(h.point.x(), h.rect.ymin(), h.point.x(), h.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(h.rect.xmin(), h.point.y(), h.rect.xmax(), h.point.y());
        }

        drawHelper(h.left);
        drawHelper(h.right);
    }
 // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointSet = new ArrayList<>();
        rangeHelper(root, rect, pointSet);
        return pointSet;
    }
    private void rangeHelper(Node h, RectHV rect, List<Point2D> pointSet) {
        if (h == null) return;
        if (h.rect.intersects(rect)) {
            if (rect.contains(h.point)) pointSet.add(h.point);
            rangeHelper(h.left, rect, pointSet);
            rangeHelper(h.right, rect, pointSet);
        }
    }
 // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearestHelper(root, root.point, p);
    }
    private Point2D nearestHelper(Node h, Point2D nnPoint, Point2D p) {
        if (h == null) return nnPoint;
        if (h.point.distanceTo(p) < nnPoint.distanceTo(p)) {
            nnPoint = h.point;
        }
        if (h.rect.distanceTo(p) < nnPoint.distanceTo(p)) {
            if (h.compareTo(new Node(p, true, null)) > 0) {
                nnPoint = nearestHelper(h.left, nnPoint, p);
                nnPoint = nearestHelper(h.right, nnPoint, p);
            } else {
                nnPoint = nearestHelper(h.right, nnPoint, p);
                nnPoint = nearestHelper(h.left, nnPoint, p);
            }
        }
        return nnPoint;
    }
// unit testing of the methods (optional) 
    public static void main(String[] args) {
        
    }
    
    
}
