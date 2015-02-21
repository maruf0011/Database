import javax.sound.midi.MidiDevice;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phenix on 2/12/15.
 */
public class Information {
    public int totalClassRoom;
    public int totalLabRoom;
    public int totalDay;
    public int totalTimeSlot;
    public int lunchTimeSlot;

    public List<String>classList;
    public List<String>fixedTimeSlotList;


    public Information(String inputFile) throws IOException {
//        File tmpFile = new File(inputFile);

        FileReader tmpFileReader = new FileReader(inputFile);

        BufferedReader tmpBufferedReader = new BufferedReader(tmpFileReader);

        String tmpInput;

        /**
         * reading all the class information from inputFile
         * */

        tmpInput = tmpBufferedReader.readLine();

        int totalClassInfo = Integer.parseInt(tmpInput);

        classList = new ArrayList<String>();

        classList.clear();

        for (int i = 0; i <totalClassInfo ; i++) {
            tmpInput = tmpBufferedReader.readLine();
            classList.add(tmpInput);
        }

        /**
         * Reading fixed class information now.
         * */


        fixedTimeSlotList = new ArrayList<String>();
        fixedTimeSlotList.clear();

        tmpInput = tmpBufferedReader.readLine();

        int fixedTimeSlotInfo = Integer.parseInt(tmpInput);

        for (int i = 0; i <fixedTimeSlotInfo ; i++) {
            tmpInput = tmpBufferedReader.readLine();

            fixedTimeSlotList.add(tmpInput);
        }


        /**
         * reading total class
         * */

        tmpInput = tmpBufferedReader.readLine();

        totalClassRoom = Integer.parseInt(tmpInput);
        Population.totalClassRoom = totalClassRoom;

        /**
         * reading total lab
         * */


        tmpInput = tmpBufferedReader.readLine();

        totalLabRoom = Integer.parseInt(tmpInput);

        /**
         * total time slot input
         * */


        tmpInput = tmpBufferedReader.readLine();
        totalTimeSlot = Integer.parseInt(tmpInput);
        /**
         * read lunch time .
         * */


        tmpInput = tmpBufferedReader.readLine();

        lunchTimeSlot = Integer.parseInt(tmpInput);

        totalDay = 7;

        System.out.println(" calc "+ totalDay +" total room "+totalClassRoom+"total time "+totalTimeSlot);
        ProcessData();


      }

    private void ProcessData()
    {
        Population.FixedTimeFlag = new int[totalDay][totalClassRoom][totalTimeSlot];
        Population.indexOfChrosome = new int[totalDay][totalClassRoom][totalTimeSlot];
        Population.reverseIndex = new int[totalDay*totalClassRoom*totalTimeSlot][3];
        Population.fixedInChromosome = new int[totalDay*totalClassRoom*totalTimeSlot];


        for (int i = 0; i <totalDay ; i++) {
            for (int j = 0; j <totalClassRoom ; j++) {
                Arrays.fill(Population.FixedTimeFlag[i][j] , 0);
            }
        }

        Arrays.fill(Population.fixedInChromosome , 0);

        /**
         * setting fixed for friday and saturday and for the 1PM-2PM time slot;
         * */
        int count = 0;
        for (int day = 0; day <totalDay ; day++) {
            for (int room = 0; room <totalClassRoom ; room++) {
                for(int time = 0 ; time<totalTimeSlot ; time++){
                    Population.indexOfChrosome[day][room][time] = count;

                    Population.reverseIndex[count][0] = day;
                    Population.reverseIndex[count][1] = room;
                    Population.reverseIndex[count][2] = time;

                    if(day==0 || day==1 || time==lunchTimeSlot){
                        Population.FixedTimeFlag[day][room][time] = 1;
                        Population.fixedInChromosome[Population.indexOfChrosome[day][room][time]] = 1;
                    }

                    count++;

                }
            }
        }


        /**
         * setting the given fixed time
         * */

        for (String now : fixedTimeSlotList) {
//            String now = fixedTimeSlotList.get(i);

            int day = (now.charAt(0)-'0')*10+(now.charAt(1) - '0');
            int room = (now.charAt(2)-'0')*10+(now.charAt(3) - '0');
            int startTime = (now.charAt(4)-'0')*10+(now.charAt(5) - '0');
            int endTime = (now.charAt(6)-'0')*10+(now.charAt(7) - '0');

            for (int time = startTime; time <=endTime ; time++) {
                Population.FixedTimeFlag[day][room][time] = 1;
                Population.fixedInChromosome[Population.indexOfChrosome[day][room][time]] = 1;
            }
        }


        Population.totalDay = 7;
        Population.totalClassWithoutLab = totalClassRoom - totalLabRoom;
        Population.totalTimeSlot = totalTimeSlot;

        Population.classList = new ArrayList<String>();
        Population.classList.clear();

        for(String now : classList)
        {
            Population.classList.add(now);
        }
     }
}
