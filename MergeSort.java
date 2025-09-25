public class MergeSort {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    public static void mergeSort(int[] arr) {
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right) {
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, left, right);
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, buffer, left, mid);
        mergeSort(arr, buffer, mid + 1, right);
        merge(arr, buffer, left, mid, right);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }
        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (k = left; k <= right; k++) {
            arr[k] = buffer[k];
        }
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
}
