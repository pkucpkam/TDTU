import java.util.NoSuchElementException;

public class maxHeap {
    int heap[];
    int heapSize;
    int maxSize;

    public maxHeap(int capity) {
        heapSize = 0;
        this.maxSize = capity + 1;
        heap = new int[maxSize];
        heap[0] = - 1;
    }

    private int parent(int i) {
        return i/2;
    }

    private int left(int i) {
        return i*2;
    }

    private int right(int i) {
        return i*2 + 1;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void insert(int key) throws NoSuchElementException {
        if (heapSize == maxSize) {
            throw new NoSuchElementException("Overflow Exception");
        }
        heapSize++;
        heap[heapSize] = key;
        shiftUp(heapSize);
    }

    private void shiftUp(int i) {
        while ( i > 1 && heap[parent(i)] < heap[i]) {
            swap(parent(i), i);
            i = parent(i);
        }
    }
    
    public int extractMax() throws NoSuchElementException {
        if (heapSize == 0) {
            throw new NoSuchElementException("Underflow Exception");
        }
        int max = heap[1];
        heap[1] = heap[heapSize];
        heapSize--;
        shiftDown(1);
        return max;
    }

    private void shiftDown(int i) {
        while ( i <= heapSize) {
            int max = heap[i];
            int max_id = left(i);
            if (left(i) <= heapSize && max < heap[left(i)]) {
                max = heap[left(i)];
                max_id = left(i);
            }
            if (right(i) <= heapSize && max < heap[right(i)]) {
                max = heap[right(i)];
                max_id = right(i);
            }
            if (max_id != i) {
                swap(max_id, i);
                i = max_id;
            }
            else {
                break;
            }
        }
    }

    public void print() 
    { 
        for (int i = 1; i <= heapSize / 2; i++) { 
            System.out.print(" PARENT : " + heap[i] + " LEFT CHILD : " + 
                      heap[2 * i] + " RIGHT CHILD :" + heap[2 * i + 1]); 
            System.out.println(); 
        } 
    } 

    public static maxHeap createHeap(int[] arr) {
        int n = arr.length;
        maxHeap heap = new maxHeap(n);
        for (int i = 0; i < n; i++) {
            heap.insert(arr[i]);
        }
        return heap;
    }
    
    public static void heapSort(int[] arr) {
        int n = arr.length;
        maxHeap heap = createHeap(arr);
        for (int i = n - 1; i >= 0; i--) {
            arr[i] = heap.extractMax();
        }
    }
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7, 3};
        maxHeap heap = createHeap(arr);
        heap.print();
        heapSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
    

}
