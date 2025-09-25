public class DeterministicSelect {

    public static int select(int[] arr, int k) {
        if (k < 1 || k > arr.length) throw new IllegalArgumentException("k out of range");
        return select(arr, 0, arr.length - 1, k - 1);
    }

    private static int select(int[] arr, int left, int right, int k) {
        while (true) {
            if (left == right) return arr[left];

            int pivotValue = medianOfMedians(arr, left, right);
            int pivotIndex = partitionByValue(arr, left, right, pivotValue);

            int length = pivotIndex - left;
            if (k == length) {
                return arr[pivotIndex];
            } else if (k < length) {
                right = pivotIndex - 1;
            } else {
                k = k - length - 1;
                left = pivotIndex + 1;
            }
        }
    }

    // Разбиение по значению pivotValue
    private static int partitionByValue(int[] arr, int left, int right, int pivotValue) {
        int pivotIndex = left;
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }
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

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right);
            return arr[left + n / 2];
        }

        int numMedians = (n + 4) / 5;
        int[] medians = new int[numMedians];

        int medianIndex = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            insertionSort(arr, i, subRight);
            int medianIdx = i + (subRight - i) / 2;
            medians[medianIndex++] = arr[medianIdx];
        }

        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
