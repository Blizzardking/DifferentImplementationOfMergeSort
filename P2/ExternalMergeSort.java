import java.io.*;
public class ExternalMergeSort {

	/**
	 * @param args
	 */
	public static final int FILE_SIZE = 25000000;
	public static final int RAM_SIZE = 1000000;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String sourceFile = "bigrandomfile25M.dat";
		String targetFile = "sortedfile25M.dat";
		//call external merge sort;
		externalSort(sourceFile, targetFile);
		// Display the first 100 elments in the final destination file;
		displayFile(targetFile);
	}
	public static void externalSort(String sourceFile, String targetFile) throws Exception {
		String tempFile = "temp";
		int numberOfChunks = initializeChunkSortPass(RAM_SIZE, sourceFile, tempFile);
		mergePass(numberOfChunks,RAM_SIZE,tempFile,targetFile);
	}
	/*Phase 1: create initial sorted segment ;
	 * @param get the source large file sourceFile, and store sorted segment into tempFile list
	 * return the number of sorted segment 
	 */
	public static int initializeChunkSortPass(int size, String sourceFile, String tempFile) throws Exception {
		int[] array = new int[size];
		DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(sourceFile)));
		int numberOfChunks = 0;
		while(input.available()>0) {
			int i = 0;
			for(; input.available() > 0 && i < size; i++) {
				array[i] = input.readInt();
			}
			//using built-in sorting method to sort the number in memory;
			java.util.Arrays.sort(array, 0, i-1);
			DataOutputStream output = new DataOutputStream(
									  	new BufferedOutputStream(
										new FileOutputStream(tempFile + numberOfChunks + ".dat")));
			//write into tempFile;
			for(int j = 0; j < i; j++) {
				output.writeInt(array[j]);
			}
			output.close();
			numberOfChunks++;
		}
		input.close();
		return numberOfChunks;
	}
	/*Read the first k number( k = size/(numberOfChunks+1)) of each sorted chunk into input buffers in RAM
	 *and allocate the remaining k slots for an output buffer;
	 *
	 */
	public static void mergePass(int numberOfChunks, int size, String tempFile, String targetFile) throws Exception {
		int n = 0;	
		int processedChunkSize = size/(numberOfChunks+1);
		//using array buffer[i] to store the first sorted k number ready to be merged;
		int[][] buffer = new int[numberOfChunks + 1][processedChunkSize];
		DataInputStream[] input = new DataInputStream[numberOfChunks];
		//Read the first k number of each sorted chunk into buffer[i];
		for(int i = 0; i < numberOfChunks; i++) {
			input[i] = new DataInputStream(new BufferedInputStream(new FileInputStream(tempFile + i + ".dat")));
			for(int j = 0; j < processedChunkSize; j++) {
				try {
					buffer[i][j] = input[i].readInt();
					n++;
				} catch(EOFException e) {
					input[i].close();
					break;
				}
			}	
		}
		DataOutputStream output = new DataOutputStream(
				  					new BufferedOutputStream(
				  					new FileOutputStream(targetFile)));
		if(n == FILE_SIZE) {
			for(int j = 0; j < n; j++) {
				output.writeInt(buffer[0][j]);
			}
		}
		else {
			int[] counter = new int[numberOfChunks];
			int k = 0;
			while(n < FILE_SIZE){
				int min = buffer[0][counter[0]];
				int minIndex = 0;
				for(int i = 0; i < numberOfChunks; i++) {
					if(min > buffer[i][counter[i]]){
						min = buffer[i][counter[i]];
						minIndex = i;
					}
				}
				buffer[numberOfChunks][k++] = min;
				counter[minIndex]++;
				//if the output buffer is full, write the buffer to destination file;
				if(k == processedChunkSize) {
					k = 0;
					for(int j = 0; j < processedChunkSize; j++) {
						output.writeInt(buffer[numberOfChunks][j]);
					}
				}
					
					
				for(int i = 0; i < numberOfChunks; i++) {
					//the first k smallest number has been merged, read the next k smallest number 
					if(counter[i] == processedChunkSize) {
						counter[i] = 0;
						try {
							for(int j = 0; j < processedChunkSize; j++) {
								buffer[i][j] = input[i].readInt();
								n++;
							}
						}catch(IOException e) {
							//this sorted file has already been cleaned up;
							buffer[i][counter[i]] = Integer.MAX_VALUE;
							input[i].close();
						}
					}
				}
			}
			
		}
		
		output.close();
	}
	
	 public static void displayFile(String filename) {
		    try {
		      DataInputStream input = 
		        new DataInputStream(new FileInputStream(filename));
		      for (int i = 0; i < 100; i++)
		        System.out.print(input.readInt() + " ");
		      input.close();
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
		  }
}

