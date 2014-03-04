public class KdTree {

    private Node root = null;
    private int size = 0;

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, 0);
    }

    private Node insert(Node x, Point2D p, double xmin, double ymin,
            double xmax, double ymax, int deep) {
        if (x == null) {
            Node node = new Node();
            node.lb = null;
            node.rt = null;
            node.rect = new RectHV(xmin, ymin, xmax, ymax);
            node.p = p;
            size++;
            return node;
        }
        if (p.equals(x.p))
            return x;
        if (deep % 2 == 0) {
            if (p.x() < x.p.x())
                x.lb = insert(x.lb, p, xmin, ymin, x.p.x(), ymax, deep + 1);
            else
                x.rt = insert(x.rt, p, x.p.x(), ymin, xmax, ymax, deep + 1);
        } else {
            if (p.y() < x.p.y())
                x.lb = insert(x.lb, p, xmin, ymin, xmax, x.p.y(), deep + 1);
            else
                x.rt = insert(x.rt, p, xmin, x.p.y(), xmax, ymax, deep + 1);
        }
        return x;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int deep) {
        if (x == null)
            return false;
        if (x.p.equals(p))
            return true;
        if (deep % 2 == 0) {
            if (p.x() < x.p.x())
                return contains(x.lb, p, deep + 1);
            else
                return contains(x.rt, p, deep + 1);
        } else {
            if (p.y() < x.p.y())
                return contains(x.lb, p, deep + 1);
            else
                return contains(x.rt, p, deep + 1);
        }
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        new RectHV(0.0, 0.0, 1.0, 1.0).draw();
        draw(root, 0);

    }

    private void draw(Node x, int deep) {
        if (x == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(x.p.x(), x.p.y());
        if (deep % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        draw(x.lb, deep + 1);
        draw(x.rt, deep + 1);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        rangeSearch(root, rect, stack, 0);
        return stack;
    }

    private void rangeSearch(Node x, RectHV rect, Stack<Point2D> stack, int deep) {
        if (x == null)
            return;
        if (rect.contains(x.p))
            stack.push(x.p);
        if (deep % 2 == 0) {
            if (rect.intersects(new RectHV(x.rect.xmin(), x.rect.ymin(), x.p
                    .x(), x.rect.ymax()))) {
                rangeSearch(x.lb, rect, stack, deep + 1);
            }

            if (rect.intersects(new RectHV(x.p.x(), x.rect.ymin(), x.rect
                    .xmax(), x.rect.ymax()))) {
                rangeSearch(x.rt, rect, stack, deep + 1);

            }
        } else {
            if (rect.intersects(new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect
                    .xmax(), x.p.y()))) {
                rangeSearch(x.lb, rect, stack, deep + 1);
            }
            if (rect.intersects(new RectHV(x.rect.xmin(), x.p.y(), x.rect
                    .xmax(), x.rect.ymax()))) {
                rangeSearch(x.rt, rect, stack, deep + 1);
            }

        }
    }

    public Point2D nearest(Point2D p) {
        if (root == null)
            return null;
        return nearstSearch(root, p, root.p, 0);
    }

    private Point2D nearstSearch(Node x, Point2D p, Point2D minPoint, int deep) {
        if (x == null)
            return minPoint;
        Point2D tmpPoint = minPoint;
        if (p.distanceTo(x.p) < p.distanceTo(minPoint)) {
            tmpPoint = x.p;
        }
        if (deep % 2 == 0) {
            if (p.x() < x.p.x()) {
                RectHV rect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.lb, p, tmpPoint, deep + 1);
                }
                rect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.rt, p, tmpPoint, deep + 1);
                }
            } else {
                RectHV rect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.rt, p, tmpPoint, deep + 1);
                }
                rect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.lb, p, tmpPoint, deep + 1);
                }

            }
        } else {
            if (p.y() < x.p.y()) {
                RectHV rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
                        x.rect.xmax(), x.p.y());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.lb, p, tmpPoint, deep + 1);
                }
                rect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.rt, p, tmpPoint, deep + 1);
                }
            } else {
                RectHV rect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(),
                        x.rect.ymax());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.rt, p, tmpPoint, deep + 1);
                }
                rect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(),
                        x.p.y());
                if (p.distanceTo(tmpPoint) >= rect.distanceTo(p)) {
                    tmpPoint = nearstSearch(x.lb, p, tmpPoint, deep + 1);
                }
            }
        }
        return tmpPoint;

    }
}