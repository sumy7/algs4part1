/*************************************************************************
 * Name: Sumy
 * Email: sunmingjian7@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        if (this.y == that.y)
            return +0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        if (this.y > that.y)
            return +1;
        if (this.x < that.x)
            return -1;
        if (this.x > that.x)
            return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {
            double scope1 = Point.this.slopeTo(p1);
            double scope2 = Point.this.slopeTo(p2);
            if (scope1 < scope2)
                return -1;
            if (scope1 > scope2)
                return +1;
            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        Point p = new Point(2, 8);
        Point q = new Point(9, 9);
        Point r = new Point(4, 7);

        StdOut.println(p.SLOPE_ORDER.compare(q, r));
        StdOut.println(p.slopeTo(q));
        StdOut.println(p.slopeTo(r));
    }
}