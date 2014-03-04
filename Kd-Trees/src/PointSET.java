import java.util.HashSet;
import java.util.Set;

public class PointSET {
    private Set<Point2D> mSet = null;

    public PointSET() {
        this.mSet = new HashSet<Point2D>();
    }

    public boolean isEmpty() {
        return mSet.isEmpty();
    }

    public int size() {
        return mSet.size();
    }

    public void insert(Point2D p) {
        mSet.add(p);
    }

    public boolean contains(Point2D p) {
        return mSet.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D point : mSet) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> tmpSet = new HashSet<Point2D>();
        for (Point2D point : mSet) {
            if (rect.contains(point)) {
                tmpSet.add(point);
            }
        }
        return tmpSet;
    }

    public Point2D nearest(Point2D p) {
        double minDis = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        for (Point2D point : mSet) {
            double dis = distanceTo(p, point);
            if (dis < minDis) {
                minDis = dis;
                minPoint = point;
            }
        }
        return minPoint;
    }

    private double distanceTo(Point2D p, Point2D q) {
        return Math.sqrt((p.x() - q.x()) * (p.x() - q.x()) + (p.y() - q.y())
                * (p.y() - q.y()));
    }
}