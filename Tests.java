import java.util.Arrays;
import java.util.Random;

public class Tests {

    // Для подсчета глубины рекурсии в QuickSort
    private static int currentDepth = 0;
    private static int maxDepth = 0;

    public static void testQuickSort() {
        System.out.println("Testing QuickSort...");

        int n = 100_000;
        Random rand = new Random();

        // Случайный массив
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt();

        resetDepth();
        quickSortWithDepth(arr, 0, arr.length - 1);
        checkSorted(arr, "Random array");

        // Отсортированный массив (adversarial)
        for (int i = 0; i < n; i++) arr[i] = i;
        resetDepth();
        quickSortWithDepth(arr, 0, arr.length - 1);
        checkSorted(arr, "Sorted array");

        // Обратный отсортированный массив (adversarial)
        for (int i = 0; i < n; i++) arr[i] = n - i;
        resetDepth();
        quickSortWithDepth(arr, 0, arr.length - 1);
        checkSorted(arr, "Reverse sorted array");

        // Проверка глубины рекурсии
        int expectedMaxDepth = 2 * (int) Math.floor(Math.log(n) / Math.log(2)) + 10; // O(1) ≈ 10
        System.out.println("Max recursion depth: " + maxDepth + ", expected upper bound: " + expectedMaxDepth);
        if (maxDepth <= expectedMaxDepth) {
            System.out.println("Recursion depth check PASSED.");
        } else {
            System.out.println("Recursion depth check FAILED.");
        }
    }

    private static void resetDepth() {
        currentDepth = 0;
        maxDepth = 0;
    }

    private static void quickSortWithDepth(int[] arr, int left, int right) {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;

        while (left < right) {
            int pivotIndex = left + new Random().nextInt(right - left + 1);
            int pivotNewIndex = partition(arr, left, right, pivotIndex);

            if (pivotNewIndex - left < right - pivotNewIndex) {
                quickSortWithDepth(arr, left, pivotNewIndex - 1);
                left = pivotNewIndex + 1;
            } else {
                quickSortWithDepth(arr, pivotNewIndex + 1, right);
                right = pivotNewIndex - 1;
            }
        }
        currentDepth--;
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void checkSorted(int[] arr, String desc) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                System.out.println(desc + " sorting FAILED.");
                return;
            }
        }
        System.out.println(desc + " sorting PASSED.");
    }

    // --------------------------------------

    public static void testDeterministicSelect() {
        System.out.println("Testing DeterministicSelect...");
        Random rand = new Random();

        int trials = 100;
        int n = 10_000;

        for (int t = 0; t < trials; t++) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = rand.nextInt(1_000_000);
            }
            int k = rand.nextInt(n);

            int[] copy = arr.clone();
            Arrays.sort(copy);

            int selectResult = DeterministicSelect.select(arr, k + 1); // k+1 т.к. select 1-based
            if (selectResult != copy[k]) {
                System.out.println("Trial " + t + " FAILED: expected " + copy[k] + ", got " + selectResult);
                return;
            }
        }
        System.out.println("DeterministicSelect tests PASSED.");
    }

    // --------------------------------------

    public static void testClosestPair() {
        System.out.println("Testing ClosestPair...");

        Random rand = new Random();
        int n = 2000; // <= 2000, для сравнения с O(n^2)

        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(rand.nextInt(10_000), rand.nextInt(10_000));
        }

        // Быстрый алгоритм
        double fastResult = ClosestPair.closestPair(points);

        // Наивный алгоритм O(n^2)
        double bruteForceResult = bruteForceClosestPair(points);

        System.out.println("Fast result: " + fastResult);
        System.out.println("Brute force result: " + bruteForceResult);

        if (Math.abs(fastResult - bruteForceResult) < 1e-9) {
            System.out.println("ClosestPair test PASSED.");
        } else {
            System.out.println("ClosestPair test FAILED.");
        }
    }

    private static double bruteForceClosestPair(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double distance(ClosestPair.Point p1, ClosestPair.Point p2) {
        long dx = p1.x - p2.x;
        long dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

