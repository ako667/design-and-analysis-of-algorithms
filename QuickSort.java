import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();
    private static int maxDepth;  // для хранения максимальной глубины рекурсии

    // Метод для получения максимальной глубины
    public static int getMaxDepth() {
        return maxDepth;
    }

    // Метод для сброса максимальной глубины перед новым запуском сортировки
    public static void resetMaxDepth() {
        maxDepth = 0;
    }

    // Внешний метод сортировки — сбрасываем maxDepth и запускаем сортировку с глубиной 0
    public static void quickSort(int[] arr) {
        resetMaxDepth();
        quickSort(arr, 0, arr.length - 1, 0);
    }

    // Внутренний метод сортировки с подсчетом глубины
    private static void quickSort(int[] arr, int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        while (left < right) {
            int pivotIndex = left + rand.nextInt(right - left + 1);
            int pivotNewIndex = partition(arr, left, right, pivotIndex);

            // Рекурсия по меньшей части, итерация по большей
            if (pivotNewIndex - left < right - pivotNewIndex) {
                quickSort(arr, left, pivotNewIndex - 1, depth + 1);
                left = pivotNewIndex + 1; // итерация по большей части
            } else {
                quickSort(arr, pivotNewIndex + 1, right, depth + 1);
                right = pivotNewIndex - 1; // итерация по большей части
            }
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, i, storeIndex);
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
}
