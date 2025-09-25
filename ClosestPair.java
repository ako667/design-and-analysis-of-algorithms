import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points) {
        Point[] pointsByX = points.clone();
        Point[] pointsByY = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingInt(p -> p.x));
        Arrays.sort(pointsByY, Comparator.comparingInt(p -> p.y));
        return closestPairRec(pointsByX, pointsByY, 0, points.length - 1);
    }

    private static double closestPairRec(Point[] pointsByX, Point[] pointsByY, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(pointsByX, left, right);
        }

        int mid = (left + right) / 2;
        Point midPoint = pointsByX[mid];

        Point[] leftY = new Point[mid - left + 1];
        Point[] rightY = new Point[right - mid];
        int li = 0, ri = 0;

        for (Point p : pointsByY) {
            if (p.x <= midPoint.x) {
                if (li < leftY.length) leftY[li++] = p;
            } else {
                if (ri < rightY.length) rightY[ri++] = p;
            }
        }

        // Обрезаем массивы по фактическому количеству элементов
        leftY = Arrays.copyOf(leftY, li);
        rightY = Arrays.copyOf(rightY, ri);

        double dl = closestPairRec(pointsByX, leftY, left, mid);
        double dr = closestPairRec(pointsByX, rightY, mid + 1, right);
        double d = Math.min(dl, dr);

        // Формируем полосу точек, лежащих в пределах d от midPoint.x
        Point[] strip = new Point[right - left + 1];
        int stripCount = 0;
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip[stripCount++] = p;
            }
        }

        return Math.min(d, stripClosest(strip, stripCount, d));
    }

    private static double bruteForce(Point[] points, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                min = Math.min(min, distance(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double distance(Point p1, Point p2) {
        long dx = p1.x - p2.x;
        long dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
