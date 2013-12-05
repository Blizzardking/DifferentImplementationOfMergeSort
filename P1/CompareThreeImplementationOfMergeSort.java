import java.util.*;
import java.io.*;
import java.lang.*;
public class CompareThreeImplementationOfMergeSort {

	/**
	 * @param args
	 */
	public static String newline = System.getProperty("line.separator");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
		int n = 1;
		File f = new File("compareThreeImplementationOfMergeSort.txt");
		PrintWriter pwf = null;
		try{
			pwf = new PrintWriter(new FileWriter(f, true));
			while(i < 21) {
				int[] arr1 = new int[n];
				int[] arr2 = new int[n];
				int[] arr3 = new int[n];
				Random randNumGenerator = new Random();
				for (int j=0; j < n; j++) {
					arr1[j] = randNumGenerator.nextInt();
					arr2[j] = randNumGenerator.nextInt();
					arr3[j] = randNumGenerator.nextInt();
				}
				long time1 = System.currentTimeMillis();
				MergeSort.dynamicAllocate(arr1);
				long time2 = System.currentTimeMillis();
				MergeSort.auxiliaryAllocate(arr2);
				long time3 = System.currentTimeMillis();
				MergeSort.alternativeAllocate(arr3);
				long time4 = System.currentTimeMillis();
				pwf.printf("%d\t%d\t%d\t%d\t%d%s",i,n, (time2-time1),(time3-time2),(time4-time3),newline);
				i++;
				n = n * 2;
				System.out.println(i);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pwf.close ();
		}
	}
}

class MergeSort {
	//Method1: Helper method
	public static void dynamicAllocate(int[] array) {
		dynamicMergeSort(array,0,array.length - 1);
	}
	public static void dynamicMergeSort(int[] array, int low, int high) {
		int mid = (low + high)/2;
		if (high > low) {
			dynamicMergeSort(array,low,mid);
			dynamicMergeSort(array,mid + 1,high);
			dynamicMerge(array,low,mid,high);
		}
	}
	public static void dynamicMerge(int[] array, int low, int mid, int high) {
		int[] L = new int[mid - low + 2];
		int[] R = new int[high - mid + 1];
		System.arraycopy(array, low, L, 0, mid - low + 1); // copy array[low...mid] into L;
		System.arraycopy(array, mid + 1, R, 0, high - mid); // copy array[mid+1...high] into R;
		L[mid - low + 1] = Integer.MAX_VALUE;
		R[high - mid] = Integer.MAX_VALUE;
		int i = 0, j = 0;
		for(int k = low; k <=high; k++) {
			if (L[i] <= R[j]) {
				array[k] = L[i++];
			}
			else {
				array[k] = R[j++];
			}
		}	
	}
	public static void auxiliaryAllocate(int[] array) {
		int[] auxiliaryArray = new int[array.length];
		auxiliaryMergeSort(array,auxiliaryArray, 0,array.length - 1);
	}
	public static void auxiliaryMergeSort(
			int[] array, int[] auxiliaryArray, int low, int high) {
		int mid = (low + high)/2;
		if (high > low) {
			auxiliaryMergeSort(array,auxiliaryArray, low,mid);
			auxiliaryMergeSort(array,auxiliaryArray, mid + 1,high);
			auxiliaryMerge(array, auxiliaryArray, low, mid, high);
		}
	}
	public static void auxiliaryMerge(int[] array, int[] auxiliaryArray, int low, int mid, int high) {
		
	//	System.out.printf("%d %d %d%n",low,  mid, high);
		System.arraycopy(array, low, 
				auxiliaryArray, low, mid - low + 1); // copy array[low...mid] into auxiliary;
		System.arraycopy(array, mid + 1, 
				auxiliaryArray, mid + 1, high - mid); // copy array[mid+1...high] into auxiliary;
		int i = low, j = mid + 1, k = low;
		while(k <= high){
			if (auxiliaryArray[i] <= auxiliaryArray[j]) {
				array[k++] = auxiliaryArray[i++];
			}
			else {
				array[k++] = auxiliaryArray[j++];
			}
			if(i == mid + 1){
				while(j < high+1) 
					array[k++] = auxiliaryArray[j++];
			}
			if(j == high + 1) {
				while(i < mid+1) 
					array[k++] = auxiliaryArray[i++];
			}
		}	
	}
	// Method 3: auxiliary array, alternative copy;
    public static void alternativeAllocate(int[] array) {
            int[] alternativeArray = new int[array.length];
            int times = (int)Math.log((double)array.length) + 1;
            int parity = times % 2;
            System.arraycopy(array, 0, alternativeArray, 0, array.length);
            alternativeMergeSort(array,alternativeArray,0,array.length - 1, times, parity);
    }
    public static void alternativeMergeSort(
                    int[] array, int[] alternativeArray, int low, int high,
                    int times, int parity) {
            int mid = (low + high)/2;
            if (high > low) {
                    alternativeMergeSort(array,alternativeArray, low, mid, times - 1, parity);
                    alternativeMergeSort(array,alternativeArray, mid + 1, high, times -1, parity);
                    if(times % 2 == parity)
                        alternativeMerge(array, alternativeArray, low, mid, high);
                    else
                    	alternativeMerge(alternativeArray, array, low, mid, high);
            }
    }
    public static void alternativeMerge(int[] array, int[] alternativeArray, int low, int mid, int high) {
//          System.out.printf("%d %d %d%n",low,  mid, high);
            int i = low, j = mid + 1, k = low;
            while(k <= high){
                    if (alternativeArray[i] <= alternativeArray[j]) {
                            array[k++] = alternativeArray[i++];
                    }
                    else {
                            array[k++] = alternativeArray[j++];
                    }
                    if(i == mid + 1){
                            while(j < high+1)
                                    array[k++] = alternativeArray[j++];
                    }
                    if(j == high + 1) {
                            while(i < mid+1)
                                    array[k++] = alternativeArray[i++];
                    }
            }
    }

}
