package tree.heap;
//王峥，用数组存储
public class Heap1 {

    private int[] a; // 数组，从下标1开始存储数据
    private int n; // 堆可以存储的最大数据个数
    private int count; // 堆中已经存储的数据个数

    public Heap1(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }
    //插入，从下往上
    public void insert(int data) {
        if (count >= n) return; // 堆满了
        ++count;
        a[count] = data;
        int i = count;
        while (i/2 > 0 && a[i] > a[i/2]) { // 自下往上堆化，父节点是i/2
            swap(a, i, i/2); // swap()函数作用：交换下标为i和i/2的两个元素
            i = i/2;
        }
    }
    //删除堆顶
    public void removeMax() {
        if (count == 0) return ; // -1 堆中没有数据
        a[1] = a[count];
        --count;
        heapify(a, count, 1);
    }
    //建推，从上往上堆化
    private  void buildHeap(int[] b, int n) {
        for (int i = n/2; i >= 1; --i) {
            heapify(b, n, i);
        }
    }

    // 堆排序
    public  void sort(int[] a, int n) {//n表示数据的个数，数组a中的数据从下标1到n的位置。
        buildHeap(a, n);
        int k = n; while (k > 1) {
            swap(a, 1, k);  //最大的放入最后
            --k;
            heapify(a, k, 1);
        }
    }

    // 自上往下堆化
    private void heapify(int[] a, int n, int i) {
        while (true) {
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if (maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    private void swap(int[] a,int i,int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
