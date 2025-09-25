import java.util.Random;

public class Benchmark {

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 50000, 100000};
        Random rand = new Random();
        Metrics timer = new Metrics();

        System.out.println("n,MergeSort(ms),QuickSort(ms),QS_MaxDepth,Select(ms),ClosestPair(ms)");

        for (int n : sizes) {
            int[] arr1 = new int[n];
            int[] arr2 = new int[n];
            int[] arr3 = new int[n];
            for (int i = 0; i < n; i++) {
                int val = rand.nextInt(1_000_000);
                arr1[i] = val;
                arr2[i] = val;
                arr3[i] = val;
            }

            // MergeSort
            int[] mergeArr = arr1.clone();
            timer.start();
            MergeSort.mergeSort(mergeArr);
            long mergeTime = timer.elapsedMillis();

            // QuickSort
            int[] quickArr = arr2.clone();
            QuickSort.resetMaxDepth();
            timer.start();
            QuickSort.quickSort(quickArr);
            long quickTime = timer.elapsedMillis();
            int quickDepth = QuickSort.getMaxDepth();

            // Select (медиана)
            int[] selectArr = arr3.clone();
            timer.start();
            int median = DeterministicSelect.select(selectArr, selectArr.length / 2);
            long selectTime = timer.elapsedMillis();

            // ClosestPair - для больших n используем только быстрый вариант
            double closestTime = 0;
            if (n <= 2000) {
                // Можно добавить медленную проверку, если надо
            }
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(rand.nextInt(1_000_000), rand.nextInt(1_000_000));
            }
            timer.start();
            double dist = ClosestPair.closestPair(points);
            closestTime = timer.elapsedMillis();

            System.out.printf("%d,%d,%d,%d,%d,%d\n", n, mergeTime, quickTime, quickDepth, selectTime, (int)closestTime);
        }
    }
}

