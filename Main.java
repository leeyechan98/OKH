package okh;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String folderDataset = "C:/Users/Lee Ye Chan/Documents/Kuliah/Semester 6/OKH/fpokh/cap2/Toronto/";
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
	
	private static Scanner scanner;
	
    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        for	(int i=0; i< fileName.length; i++)
        	System.out.println(i+1 + ". Penjadwalan " + fileName[i][1]);
        
        System.out.print("\nSilahkan pilih file untuk dijadwalkan : ");
        int pilih = scanner.nextInt();
        
        String filePilihanInput = fileName[pilih-1][0];
        String filePilihanOutput = fileName[pilih-1][1];
        
        String file = folderDataset + filePilihanInput;
        	
        Course course = new Course(file);
        int jumlahexam = course.getNumberOfCourses();
        
        conflictMatrix = course.getConflictMatrix();
        int jumlahmurid = course.getTotalStudents();
        
		courseSorted = course.sortingByDegree(conflictMatrix, jumlahexam);
		
		long starttimeLargestDegree = System.nanoTime();
		Schedule schedule = new Schedule(file, conflictMatrix, jumlahexam);
		timeslot = schedule.schedulingByDegree(courseSorted);
		int[][] timeslotByLargestDegree = schedule.getSchedule();
		long endtimeLargestDegree = System.nanoTime();

		Optimizer optimization = new Optimizer(file, conflictMatrix, courseSorted, jumlahexam, jumlahmurid, 10000);

		long starttimeHC = System.nanoTime();
		optimization.getTimeslotByHillClimbing();
		long endtimeHC = System.nanoTime();
		
                long starttimeSA = System.nanoTime();
		optimization.getTimeslotBySimulatedAnnealing(100.0);
		long endtimeSA = System.nanoTime();
                
		System.out.println("PENJADWALAN UNTUK " + filePilihanOutput + "\n");
		
		System.out.println("Jumlah Timeslot (menggunakan \"Constructive Heuristics\")     : " + schedule.getTotalTimeslots(schedule.getSchedule()));
		System.out.println("Penalti \"Constructive Heuristics\"                           : " + Evaluator.getPenalty(conflictMatrix, schedule.getSchedule(), jumlahmurid));
		System.out.println("Waktu Eksekusi \"Constructive Heuristics\"                    : " + ((double) (endtimeLargestDegree - starttimeLargestDegree)/1000000000) + " detik.\n");
		
		System.out.println("Jumlah Timeslot (menggunakan Hill Climbing)                 : " + optimization.getJumlahTimeslotHC());
		System.out.println("Penalti Hill Climbing                                       : " + Evaluator.getPenalty(conflictMatrix, optimization.getTimeslotHillClimbing(), jumlahmurid));
		System.out.println("Waktu Eksekusi Hill Climbing                                : " + ((double) (endtimeHC - starttimeHC)/1000000000) + " detik.\n");
		
                System.out.println("Jumlah Timeslot (menggunakan Simulated Annealing)           : " + optimization.getJumlahTimeslotSimulatedAnnealing());
                System.out.println("Penalti Simulated Annealing                                 : " + Evaluator.getPenalty(conflictMatrix, optimization.getTimeslotSimulatedAnnealing(), jumlahmurid));
                System.out.println("Waktu Eksekusi Simmulated Annealing                         : " + ((double) (endtimeSA - starttimeSA)/1000000000) + " detik.\n");
		
    }
    
}

/*
         * Scheduling from largest degree (default timesloting)
         */
        /*course = new Course(file);
        jumlahexam = course.getJumlahCourse();
        
        conflict_matrix = new int[jumlahexam][jumlahexam];  
        conflict_matrix = course.getConflictMatrix();
        
        // print dataset array
		/*for (int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				System.out.print(conflict_matrix[i][j] + " ");
			}
			System.out.println();
		}
		
		// sort exam by degree
		course_sorted = course.sortingByDegree(conflict_matrix, jumlahexam);
		//for (int i=0; i<jumlahexam; i++)
			//System.out.println("Degree of course " + course_sorted[i][0] + " is " + course_sorted[i][1]);
		
		schedule = new Schedule(filePilihanOutput, conflict_matrix, jumlahexam);
		timeslot = new int[jumlahexam];
		
		
		// start time
		long starttime = System.nanoTime();
		timeslot = schedule.schedulingByDegree(course_sorted, timeslot);
		long endtime = System.nanoTime();
		// end time
		double runningtime = (double) (endtime - starttime)/1000000000;
		
		int jumlahMurid = course.getJumlahMurid();
		
		schedule.printSchedule(timeslot);
		
		System.out.println("Waktu eksekusi yang dibutuhkan adalah selama " + runningtime + " detik.");
		
		// write to sol file
		writeSolFile(hasil_timeslot, filePilihanOutput);
		System.out.println("Penalti : " + Evaluator.getPenalty(conflict_matrix, hasil_timeslot, jumlahMurid));
		*/
