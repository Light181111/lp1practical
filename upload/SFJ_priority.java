package separate;

import java.io.Closeable;
import java.util.Scanner;

class Process{
 int pid;
    int waitingTime;
    int arrivalTime;
    int burstTime;
 int turnAroundTime;
 int timeToComplete;
 int completionTime = 0;
 int priority;
    Process(int pid, int sub,int bur){
     this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.timeToComplete = burstTime;
    }
    
    Process(int pid, int sub,int bur, int priority){
     this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.priority = priority;
        this.timeToComplete = burstTime;
    }

}
public class SFJ_priority{
 static Scanner s = new Scanner(System.in);
    public static void main(String[] args){
   //   System.out.println("Swarup Kharat");
   //   System.out.println("Roll No: 5");
  System.out.println("Enter the number of processes:");
  int n = s.nextInt();
        Process[] myProcess = new Process[n];
  for(int i=0;i<n;i++){
   System.out.println("Enter Arrival time,  Burst Time, and Priority: ");
   int sub  = s.nextInt();
   int bur = s.nextInt();
   int priority = s.nextInt();
   myProcess[i] = new Process(i+1,sub,bur,priority);
  }
  
  System.out.println("Select the type of scheduler to be used:");
  System.out.println("1. SJF (Preemptive)");
  System.out.println("2. Priority (Non-preemptive");
  System.out.println("3. Exit");
  System.out.println("Enter your choice:");
  int choice = s.nextInt();  
  
  switch(choice){
  case 1:
   SJF(myProcess);
   break;
  case 2:
   PriorityScheduling(myProcess);
   break;
  case 3:
   s.close();
   System.exit(1);
   break;
  default:
   System.out.println("Incorrect Choice");
   break;
  }
  s.close();
    }  
    
    static void SJF(Process myProcess[]){
     int curTimeInterval = 0, completedProcesses = 0;
     Process curProcess;
     
     //Traverse until all process gets completely executed.
     curProcess = myProcess[0];
     while(completedProcesses < myProcess.length){
      for(int i=0; i < myProcess.length; i++){
       if(myProcess[i].timeToComplete > 0){
        curProcess = myProcess[i];
        break;
       }
      }
      System.out.println("Current Time Interval = " + curTimeInterval);
      System.out.println("No of Processes Completed = " + completedProcesses);
      //Find process with minimum remaining time at every single time lap
      
      for(int i=0; i < myProcess.length; i++){
       if(myProcess[i].arrivalTime > curTimeInterval || myProcess[i].timeToComplete == 0 ){
        continue;
       }
       if(myProcess[i].timeToComplete < curProcess.timeToComplete){
        curProcess = myProcess[i];
       }
      }
      //Reduce its time by 1
      curProcess.timeToComplete -= 1;
      //Check if its remaining time becomes 0 
      if(curProcess.timeToComplete == 0){
       //Increment the counter of process completion.
       completedProcesses++;
       //Completion time of current process = current_time +1;
       curProcess.completionTime = curTimeInterval +1;
      }
      curTimeInterval++;
     }
     
  for(int i=0;i<myProcess.length;i++){
   //Calculate waitingTimeing time for each process
   //waitingTimeing Time = Completion time - arrival_time - burst_time
   myProcess[i].waitingTime = myProcess[i].completionTime - myProcess[i].arrivalTime - myProcess[i].burstTime;
   
   //Find turnAroundTime time (waitingTimeing_time+burst_time)
   myProcess[i].turnAroundTime = myProcess[i].waitingTime + myProcess[i].burstTime;
   System.out.println("Process "+ myProcess[i].pid +":");
   System.out.println("turnAroundTime\tCompletion\twaitingTimeing");
   System.out.println(myProcess[i].turnAroundTime+"\t\t\t"+myProcess[i].completionTime+"\t\t"+myProcess[i].waitingTime);
        }
        
 }
    
    static void PriorityScheduling(Process myProcess[]){
     //Arrange processes according to their priority in the descending order
  Process temp;
  for(int i = 0; i < myProcess.length; i++){
   for(int j = i; j < myProcess.length; j++){
    if(myProcess[i].priority > myProcess[j].priority){
     temp = myProcess[j];
     myProcess[j] = myProcess[i];
     myProcess[i] = temp;
    }
   }
  }
     
  int x = 0;
  for(int i=0;i<myProcess.length;i++){
            x = x+myProcess[i].burstTime;
   myProcess[i].completionTime = x;
   myProcess[i].turnAroundTime = myProcess[i].completionTime - myProcess[i].arrivalTime;
   myProcess[i].waitingTime = myProcess[i].turnAroundTime - myProcess[i].burstTime;
   System.out.println("Process "+ myProcess[i].pid +":");
   System.out.println("turnAroundTime\tCompletion\twaitingTimeing");
   System.out.println(myProcess[i].turnAroundTime+"\t\t\t"+myProcess[i].completionTime+"\t\t"+myProcess[i].waitingTime);
        }
     
    }
    
}

