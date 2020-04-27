package okh;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
	
	static final String DIREKTORI = "C:/Users/Lee Ye Chan/Downloads/Toronto/";
        static String fileName[][] = {
            {"car-f-92", "Carleton92"}, {"car-s-91", "Carleton91"},
            {"ear-f-83", "EarlHaig83"}, {"hec-s-92", "EdHEC92"}, 
            {"kfu-s-93", "KingFahd93"}, {"lse-f-91", "LSE91"},
            {"pur-s-93", "pur93"}, {"rye-s-93", "rye92"},
            {"sta-f-83", "St.Andrews83"}, {"tre-s-92", "Trent92"}, 
            {"uta-s-92", "TorontoAS92"}, {"ute-s-92", "TorontoE92"},
            {"yor-f-83", "YorkMills83"}
        };
        static int timeslot[]; 
        static int[][] conflictMatrix, courseSorted, timeslotResult;
	
	public static void main(String[] args) throws IOException {
		
		for(int i=0; i< fileName.length; i++)
        	System.out.println(i+1 + ". Penjadwalan " + fileName[i][1]);
                
		System.out.print("\nDataset yang ingin dijadwalkan : ");
		Scanner input = new Scanner(System.in);
		int dataset = input.nextInt();
                
                String filePilihanInput = fileName[dataset-1][0];
                String filePilihanOutput = fileName[dataset-1][1];
                System.out.println("\n================================================\n");
        
            String file = DIREKTORI + filePilihanInput;
        	
            Course course = new Course(file);
            int totalExams = course.getNumberOfCourses();
        
            conflictMatrix = course.getConflictMatrix();
            int totalStudents = course.getTotalStudents();
        
            conflictMatrix = new int[totalExams][totalExams];  
            conflictMatrix = course.getConflictMatrix();
        
            for (int i=0; i<10; i++) {
		for(int j=0; j<10; j++) {
			System.out.print(conflictMatrix[i][j] + " ");
                    }
		System.out.println();
            }
		
          
            courseSorted = course.sortingByDegree(conflictMatrix, totalExams);
               
            System.out.println("\n================================================\n");
                
  
            for (int i=0; i<totalExams; i++)
                System.out.println("Degree of course " + courseSorted[i][0] + " = " + courseSorted[i][1]);
               
       
            long starttimeLD = System.nanoTime();
            Schedule schedule = new Schedule(file, conflictMatrix, totalExams);
            timeslot = schedule.schedulingByDegree(courseSorted);
            int[][] timeslotLD = schedule.getSchedule();
            long endtimeLD = System.nanoTime();
               
            System.out.println("\n================================================\n");
            for (int i = 0; i < totalExams; i++)
            System.out.println("Timeslot course "+ (i+1) +  " = " + timeslot[i]);
		
            System.out.println("\n================================================\n");
            System.out.println("Schedule for " + filePilihanOutput );
            System.out.println("Timeslots  	: " + schedule.getTotalTimeslots(schedule.getSchedule()));
            System.out.println("Penalty  	: " + Evaluator.getPenalty(conflictMatrix, schedule.getSchedule(), totalStudents));
            System.out.println("Execution Time  : " + ((double) (endtimeLD - starttimeLD)/1000000000) + " s.\n");
	}
}