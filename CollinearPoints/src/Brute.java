/*************************************************************************
 * Name: Sumy 
 * Email: sunmingjian7@gmail.com
 * 
 * Compilation: javac Brute.java 
 * Execution: 
 * Dependencies: StdDraw.java
 * 
 * Description: Examines 4 points at a time and checks whether they all lie on
 * the same line segment, printing out any such line segments to standard output
 * and drawing them using standard drawing.
 * 
 *************************************************************************/
import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.008);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        StdDraw.setPenRadius();

        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double slopeij = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < N; k++) {
                    double slopeik = points[i].slopeTo(points[k]);
                    if (slopeij != slopeik) {
                        continue;
                    }
                    for (int l = k + 1; l < N; l++) {
                        double slopeil = points[i].slopeTo(points[l]);
                        if (slopeij == slopeil) {
                            StdOut.println(points[i].toString() + " -> "
                                    + points[j].toString() + " -> "
                                    + points[k].toString() + " -> "
                                    + points[l].toString());
                            points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);

    }
}
