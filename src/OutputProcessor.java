import com.sun.org.apache.bcel.internal.generic.POP;

import java.io.*;

/**
 * Created by phenix on 2/24/15.
 */
public class OutputProcessor {
    Population solution;
    int total_solution;
    public OutputProcessor(Population solution,int total_solution) throws IOException {
        this.solution = solution;
        this.total_solution = total_solution;

        printFile();
    }

    public void printFile() throws IOException {
        File file = new File("res/out.txt");

        FileWriter fileWriter = new FileWriter("res/out.txt");

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(String.format("%d",total_solution));
        bufferedWriter.newLine();
        bufferedWriter.flush();

        for (int i = 0; i <solution.chromosome.length ; i++) {
            if(solution.chromosome[i]!=null)
            {
                String tmpString = solution.chromosome[i];
                tmpString = tmpString+ String.format("%02d%02d%02d%02d" , Population.reverseIndex[i][0],Population.reverseIndex[i][1]
                ,Population.reverseIndex[i][2] , Population.reverseIndex[i][2]+StringProcessor.getClassHour(solution.chromosome[i])-1);

                bufferedWriter.write(tmpString);

                bufferedWriter.newLine();

                bufferedWriter.flush();
            }
        }
    }
}
