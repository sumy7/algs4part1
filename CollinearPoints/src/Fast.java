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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fast {
    public static void main(String[] args) {
        List<Point> answerlist = new ArrayList<Point>();

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
        Point[] pointcopy = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
            pointcopy[i] = points[i];
        }

        StdDraw.setPenRadius();

        answerlist.clear();

        for (int i = 0; i < N; i++) {
            Point p = pointcopy[i];
            Arrays.sort(points, p.SLOPE_ORDER);

            double scope = Double.NEGATIVE_INFINITY;
            answerlist.clear();
            answerlist.add(p);
            for (int j = 1; j < N; j++) {
                double tempscope = p.slopeTo(points[j]);
                if (scope == tempscope) {
                    answerlist.add(points[j]);
                }
                if (scope != tempscope || j == N - 1) {
                    int size = answerlist.size();
                    if (size >= 4) {
                        Collections.sort(answerlist);
                        if (p == answerlist.get(0)) {
                            for (int k = 0; k < size; k++) {
                                if (k != 0)
                                    StdOut.print(" -> ");
                                StdOut.print(answerlist.get(k).toString());
                            }
                            StdOut.println();
                            answerlist.get(0).drawTo(answerlist.get(size - 1));
                        }
                    }
                    answerlist.clear();
                    answerlist.add(p);
                    answerlist.add(points[j]);
                    scope = tempscope;
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}
