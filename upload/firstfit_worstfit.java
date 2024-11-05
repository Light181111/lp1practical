package separate;



import java.util.*;

public class firstfit_worstfit {
 static void firstFit(int blockSize[], int m, int processSize[], int n) {
// Stores block id of the  
// block allocated to a process 
  int allocation[] = new int[n];

// Initially no block is assigned to any process 
  for (int i = 0; i < allocation.length; i++)
   allocation[i] = -1;

// pick each process and find suitable blocks 
// according to its size ad assign to it 
  for (int i = 0; i < n; i++) {
   for (int j = 0; j < m; j++) {
    if (blockSize[j] >= processSize[i]) {
     // allocate block j to p[i] process
     allocation[i] = j;

     // Reduce available memory in this block.
     blockSize[j] -= processSize[i];

     break;
    }
   }
  }

  System.out.println("\nProcess No.\tProcess Size\tBlock no.");
  for (int i = 0; i < n; i++) {
   System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
   if (allocation[i] != -1)
    System.out.print(allocation[i] + 1);
   else
    System.out.print("Not Allocated");
   System.out.println();
  }
 }

 static void worstFit(int blockSize[], int m, int processSize[], int n) {
// Stores block id of the block allocated to a 
// process 
  int allocation[] = new int[n];

// Initially no block is assigned to any process 
  for (int i = 0; i < allocation.length; i++)
   allocation[i] = -1;

// pick each process and find suitable blocks 
// according to its size ad assign to it 
  for (int i = 0; i < n; i++) {
// Find the best fit block for current process 
   int wstIdx = -1;
   for (int j = 0; j < m; j++) {
    if (blockSize[j] >= processSize[i]) {
     if (wstIdx == -1)
      wstIdx = j;
     else if (blockSize[wstIdx] < blockSize[j])
      wstIdx = j;
    }
   }

// If we could find a block for current process 
   if (wstIdx != -1) {
// allocate block j to p[i] process 
    allocation[i] = wstIdx;

// Reduce available memory in this block. 
    blockSize[wstIdx] -= processSize[i];
   }
  }

  System.out.println("\nProcess No.\tProcess Size\tBlock no.");
  for (int i = 0; i < n; i++) {
   System.out.print("   " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
   if (allocation[i] != -1)
    System.out.print(allocation[i] + 1);
   else
	   
    System.out.print("Not Allocated");
   System.out.println();
  }
 }
 
 static Scanner s = new Scanner(System.in);
 public static void main(String[] args) {
  // System.out.println("Swarup Kharat Roll No: 5");
  
  while(true) {
   
  System.out.println("Enter the number of processes:");
  int n = s.nextInt();
  int processSize[]  = new int[n];
  for(int i=0;i<n;i++){
   System.out.println("Enter the process: ");
   
   int priority = s.nextInt();
   processSize[i] = priority;
  }
  System.out.println("Enter the number of blocks:");
  int m = s.nextInt();
  int blockSize[]  = new int[m];
  for(int i=0;i<m;i++){
   System.out.println("Enter the block: ");
   
   int priority = s.nextInt();
   blockSize[i] = priority;
  }
  System.out.println("Select the type of memory management to be used:");
  System.out.println("1. First Fit");
  System.out.println("2. Worst Fit");
  System.out.println("3. Exit");
  System.out.println("Enter your choice:");
  int choice = s.nextInt();
   
  
  switch(choice){
  case 1:
   firstFit(blockSize, m, processSize, n);
   break;
  case 2:
   worstFit(blockSize, m, processSize, n);
   break;
  case 3:
   s.close();
   System.out.println("successfully exit");
   System.exit(1);
   break;
  default:
   System.out.println("Incorrect Choice");
   break;
  }
  }
 }
// s.close();

}

