import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        // Пример массива для сортировок и выбора
        int n = 100_000;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        int[] arr3 = new int[n];

        for (int i = 0; i < n; i++) {
            int val = rand.nextInt(1_000_000);
            arr1[i] = val;
            arr2[i] = val;
            arr3[i] = val;
        }

        // Metrics для замеров времени
        Metrics timer = new Metrics();

        // 1. MergeSort
        int[] arrMergeSort = arr1.clone();
        timer.start();
        MergeSort.mergeSort(arrMergeSort);
        System.out.println("MergeSort time: " + timer.elapsedMillis() + " ms");

        // 2. QuickSort
        int[] arrQuickSort = arr2.clone();
        timer.start();
        QuickSort.quickSort(arrQuickSort);
        System.out.println("QuickSort time: " + timer.elapsedMillis() + " ms");

        // 3. Deterministic Select (найдем медиану)
        int[] arrSelect = arr3.clone();
        timer.start();
        int median = DeterministicSelect.select(arrSelect, arrSelect.length / 2);
        System.out.println("Deterministic Select median: " + median + ", time: " + timer.elapsedMillis() + " ms");

        // 4. Closest Pair of Points
        int pointsCount = 100_000;
        ClosestPair.Point[] points = new ClosestPair.Point[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new ClosestPair.Point(rand.nextInt(1_000_000), rand.nextInt(1_000_000));
        }
        timer.start();
        double closestDistance = ClosestPair.closestPair(points);
        System.out.println("Closest Pair distance: " + closestDistance + ", time: " + timer.elapsedMillis() + " ms");

        // Запуск тестов из класса Tests
        System.out.println("\n--- Running Tests ---");
        Tests.testQuickSort();
        Tests.testDeterministicSelect();
        Tests.testClosestPair();
        System.out.println("--- Tests Finished ---");
    }
}
