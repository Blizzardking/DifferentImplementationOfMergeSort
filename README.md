I.Author: Renkai Ji
-------------------
II.Purpose
----------
1. Implement 3 versions of Merge Sort, and compare their running times
   on large values of N.  Assume that N is an even power of 2.
   Submit statistics for different values of N, up to N = 2^20 = 1048576.

   a. Allocate dynamic memory in each call to Merge for L and R
   b. Use an auxiliary array B[ ] (same size as A).  In each call to
      Merge, copy contents from A to B and then merge them back
      to A, in sorted order.
   c. Use an auxiliary array B[ ] (same size as A).  When data is
      in A[p..q..r], it is merged into B[p..r].  When data is in
      B[p..q..r], it is merged into A[p..r].


2. Compare performance of Merge Sort and Quick Sort for sorting large
   amount of data that does not fit within memory.  This is called external
   sorting.  For more details, contact the instructor.


III. Note
---------

1. The directory P1 contains the JAVA source code, as well as the statistics of comparison of performance of three implementations of merge sort. The source code is well self-explained with a litte comment.


2. The directory P2 contians only the source code of external sort by merge-sort approach combined with in-memory quick sort. 

Step1: Using "Create25Mfile" program to create a data set(actually you can use this program to create as large dataset as you want by change the constant FILE_SIZE(number of integer) in the source file). Ouput data will be stored in a binary file named "bigrandomfile25M.dat".

Step2: "ExternalMergeSort" program will sort the the binary file named "bigrandomfile25M.dat". Store the sorted data into "sortedfile25M.dat". At the same time, there will be numbers of temp files whose number depends on the ratio of RAM_SIZE and FILE_SIZE. (RAM_SIZE and FILE_SIZE are two constants indicating the number of integer in the second file)  

***Please note that if you change the FILE_SIZE in the first program, it is necessary for you accommodated to the same FILE_SIZE in the second program. If you increase the RAM_SIZE, you will make the performance much better***
  
