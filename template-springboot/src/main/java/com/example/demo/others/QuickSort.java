import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort {


    public static void main(String[] args) {
        int[] list = {1,2,3,4,5,6,93,5};

        QuickSort.sort(list);

        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list) {
        quickSort(list, 0, list.length - 1);


    }

    private static void quickSort(int[] list, int low, int high) {
        if(low < high) {
            int pivot =  partition(list,  low, high);

            quickSort(list, low , pivot - 1);
            quickSort(list,  pivot + 1, high);

        }
    }


    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int ret = low;
        for (int i = low; i < high; i++) {
            if(arr[i] < pivot){
                swap(arr, ret, i);
                ret++;
            }
        }

        swap(arr, ret, high);
        return ret;



    }

    private static void swap(int[] arr, int ret, int i) {
        int tmp = arr[ret];
        arr[ret] = arr[i];
        arr[i] = tmp;
    }

}
