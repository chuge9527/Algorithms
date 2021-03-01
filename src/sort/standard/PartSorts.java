package sort.standard;
//归并 和 快速【分治】
public class PartSorts {

    /**
     * 归并排序
     *
     * @param arr
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return; //相等时 组内只有一个元素后，开始合并
        }
        int q = (left + right) / 2;
        mergeSort(arr, left, q);
        mergeSort(arr, q + 1, right);
        merge(arr, left, q, right);//如 a[],2，2，3

    }
    //哨兵简化。未看
    private static void merge2(int[] arr, int left, int q, int right) {
        int[] leftArr = new int[q - left + 2];
        int[] rightArr = new int[right - q + 1];

        for (int i = 0; i <= q - left; i++) {
            leftArr[i] = arr[left + i];
        }
        // 第一个数组添加哨兵（最大值）
        leftArr[q - left + 1] = Integer.MAX_VALUE;

        for (int i = 0; i < right - q; i++) {
            rightArr[i] = arr[q + 1 + i];
        }
        // 第二个数组添加哨兵（最大值）
        rightArr[right - q] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;
        int k = left;
        while (k <= right) {
            // 当左边数组到达哨兵值时，i不再增加，直到右边数组读取完剩余值，同理右边数组也一样
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
    }

    private static void merge(int[] arr, int left, int q, int right) {
        int i = left;
        int j = q + 1;
        int k = 0;
        int[] tmp = new int[right - left + 1];
        while (i <= q && j <= right) { //类似链表合并，左右
            if (arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        int start = i;
        int end = q;
        if (j <= right) { //右边没放完
            start = j;
            end = right;
        }
        while (start <= end) {
            tmp[k++] = arr[start++];
        }
        for (int l = 0; l <= right - left; l++) {
            arr[l + left] = tmp[l];
        }

    }

    /**
     * 快速排序
     *
     * @param arr
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int q = partition(arr, left, right);//选切点，并分左右
        quickSort(arr, left, q - 1);
        quickSort(arr, q + 1, right);
    }

    private static int partition(int[] arr, int left, int right) {  //选择一个点分左右，类似选择排序。
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                if (i == j) {
                    ++i;//不动。i+1
                } else {
                    int tmp = arr[i];
                    arr[i++] = arr[j];//小于pot的依次排入a[i]【先赋值后加】
                    arr[j] = tmp;
                }
            }
        }
        int tmp = arr[i];
        arr[i] = arr[right];
        arr[right] = tmp;
        return i;
    }
    private static int partition2(int[] arr, int left, int right) {
        // 三数取中法 , 随机数在这里写
        int middle = (left + right) / 2;
        int pivot = arr[middle];
        // 交换到最右边
        int val = arr[right];
        arr[right] = pivot;
        arr[middle] = val;
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = arr[i];
                    arr[i++] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        int tmp = arr[i];
        arr[i] = arr[right];
        arr[right] = tmp;
        return i;
    }

}
