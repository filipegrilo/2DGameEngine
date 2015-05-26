package org.test.Sorting;

public class Sort {
	public static void insertionSort(int[] arr, int start, int end){
		for(int i=start+1; i < end; i++){
			int j;
			int v = arr[i];
			
			for(j = i-1; j >= start && arr[j] > v; j--)
				arr[j+1] = arr[j];
			
			arr[j+1] = v;
		}
	}
	
	public static void mergeSort(int[] arr, int start, int end){
		if(end - start > 1){
			int middle = (end + start) / 2;
			mergeSort(arr, start, middle);
			mergeSort(arr, middle, end);
			mergeSubArrays(arr, start, middle, end);
		}
	}
	
	private static void mergeSubArrays(int[] arr, int start, int middle, int end){
		int[] b = new int[end - start];
		int i1 = start;
		int i2 = middle;
		int j = 0;
		
		while(i1 < middle && i2 < end){
			if(arr[i1] < arr[i2]) b[j++] = arr[i1++];
			else b[j++] = arr[i2++];
		}
		
		while(i1 < middle) b[j++] = arr[i1++];
		while(i2 < end) b[j++] = arr[i2++];
		
		System.arraycopy(b, 0, arr, start, end - start);
	}
}
