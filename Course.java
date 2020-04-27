package okh;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Course {
	
	int totalstudents;

	String fileInput;
	
	public Course(String fileInput) {
            this.fileInput = fileInput;
        }
	
	public int getTotalStudents() {
            return this.totalstudents;
        }
        
	public int getNumberOfCourses() throws IOException {
            int totalCourses = 0;

            BufferedReader readCourse = new BufferedReader(new FileReader(fileInput + ".crs"));
		while (readCourse.readLine() != null) 
                    totalCourses++; 
		readCourse.close();
		
            return totalCourses;
	}
	
	public int[][] getConflictMatrix() throws IOException {
  
            int[][] conflictMatrix = new int[getNumberOfCourses()][getNumberOfCourses()];
     	
            for (int i=0; i<conflictMatrix.length; i++)
     		for(int j=0; j<conflictMatrix.length; j++)
     			conflictMatrix[i][j] = 0;
     	
   
		BufferedReader readStudent = new BufferedReader(new FileReader(fileInput + ".stu"));
		String spasi = " ";
		while ((spasi = readStudent.readLine()) != null) {
			totalstudents++;
			String tmp [] = spasi.split(" ");
			if	(tmp.length > 1) {
				for(int i=0; i<tmp.length; i++)
					for(int j=i+1; j<tmp.length; j++) {
						conflictMatrix[Integer.parseInt(tmp[i])-1][Integer.parseInt(tmp[j])-1]++;
						conflictMatrix[Integer.parseInt(tmp[j])-1][Integer.parseInt(tmp[i])-1]++;
				}
			}
		}
		readStudent.close();	
		
		return conflictMatrix;
	}
	
	public int [][] sortingByDegree(int[][] conflictmatrix, int totalcourse) {
		int[][] course_degree = new int [totalcourse][2];
		int degree = 0;
		for (int i=0; i<course_degree.length; i++)
			for (int j=0; j<course_degree[0].length; j++)
				course_degree[i][0] = i+1; 
		
    	for (int i=0; i<totalcourse; i++) {
			for (int j=0; j<totalcourse; j++)
				if(conflictmatrix[i][j] > 0)
					degree++;
					else
						degree = degree;					
			course_degree[i][1] = degree; 
			degree=0;
		}
    
    	int[][] max = new int[1][2]; 
    	max[0][0] = -1;
		max[0][1] = -1;
		int x = 0;
		int[][] course_sorted = new int[totalcourse][2];
		
		for(int a=0; a<course_degree.length; a++) {
			for(int i=0; i<course_degree.length; i++) {
				if(max[0][1]<course_degree[i][1]) {
					max[0][0] = course_degree[i][0];
					max[0][1] = course_degree[i][1];
					x = i;
				}				
			}
			course_degree[x][0] = -2;
			course_degree[x][1] = -2;
			course_sorted[a][0] = max[0][0];
			course_sorted[a][1] = max[0][1];
			max[0][0] = -1;
			max[0][1] = -1;
		
		}
		return course_sorted;
	}
	

}