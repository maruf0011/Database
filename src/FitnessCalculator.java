import com.sun.org.apache.bcel.internal.generic.POP;

import java.util.List;

/**
 * Created by phenix on 2/13/15.
 */
public class FitnessCalculator {
    public int calculateFitness(Population currentPopulation)
    {
        int ret = 0;

        ret += restrictedPositionOverlapping(currentPopulation);
        ret += timeOverlapping(currentPopulation);
        ret += dayOverlapping(currentPopulation);
        ret += daySplitting(currentPopulation);
        ret += classOverlapping(currentPopulation);
        ret += classSplitting(currentPopulation);

        ret+= labAndTheoryRoomProblem(currentPopulation);
        ret += teacherTimeOverlapping(currentPopulation);

        ret += batchTimeOverlapping(currentPopulation);

        ret += subjectOverlapping(currentPopulation);

        return  ret;
    }
    /**
     * check any part of any class is in restricted time location .
     * */
    private int restrictedPositionOverlapping(Population p)
    {
        int ret = 0;

        for (int i = 0; i <p.chromosome.length ; i++) {
            String now = p.chromosome[i];

            if(now!=null)
            {
                int length = StringProcessor.getClassHour(now);

                for(int j = 0 ; j<length ; j++)
                {
                    if(i+j>=p.chromosome.length) ;
                    else if(Population.fixedInChromosome[i+j]==1) ret++;
                }
            }
        }

//        if(ret!=0)System.out.println("restricted position overlapping "+ret);

        return ret*100;
    }

    /**
     * check if two class time overlap .
     * */
    private int timeOverlapping(Population p)
    {
        int ret=  0;
        for (int curClassPos = 0; curClassPos <p.chromosome.length ; curClassPos++) {
            if(p.chromosome[curClassPos]==null) continue;

            int hourOfCurrentClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

            for (int nextClassPos = 0 ; nextClassPos <p.chromosome.length ; nextClassPos++) {
                if(p.chromosome[nextClassPos]==null) continue;

                if(curClassPos==nextClassPos) continue;

                if(nextClassPos >=curClassPos && nextClassPos<curClassPos+hourOfCurrentClass)
                {
                    ret++;
                }
            }
        }

//        if(ret!=0)System.out.println("only time overlapping "+ret);

        return  ret*80;
    }

    /**
     * check if same class is in the same day .....
     * */
    private int dayOverlapping(Population p)
    {
        int ret = 0;
        for(int curClassPos = 0 ; curClassPos < p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            int hourOfCurClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

            for(int nextClassPos = 0 ; nextClassPos<p.chromosome.length ; nextClassPos++)
            {
                if(p.chromosome[nextClassPos]==null) continue;

                if(curClassPos==nextClassPos) continue;

                if(p.chromosome[curClassPos].equals(p.chromosome[nextClassPos]))
                {
                    for(int iterator=0 ; iterator<hourOfCurClass && curClassPos+iterator<p.chromosome.length ; iterator++)
                    {
                        if(Population.reverseIndex[curClassPos+iterator][0]==Population.reverseIndex[nextClassPos][0]) ret++;
                    }
                }
            }
        }

//        if(ret!=0)System.out.println("day overlapping "+ret);
        return ret*70;
    }

    /**
     * check if same class splitted in the consecutive two days
     * */

    private int daySplitting(Population p)
    {
        int ret = 0;

        for(int curClassPos = 0;  curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            int hourOfCurClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);
//            int perDaySlot = (Population.totalClassRoom*Population.totalTimeSlot);
//            if((int)(curClassPos/perDaySlot) != (int)((curClassPos+hourOfCurClass-1)/perDaySlot)) ret++;
            if(curClassPos+hourOfCurClass >= p.chromosome.length) ret++;  //if curclass+hourofclass go out of the index
            else if(Population.reverseIndex[curClassPos][0] != Population.reverseIndex[curClassPos+hourOfCurClass-1][0]) ret++;
        }

//        if(ret!=0)System.out.println("day split "+ret);

        return  ret*60;
    }

    /**
     * check if same class splitted in the consecutive two class in same days
     * */

    private int classSplitting(Population p)
    {
        int ret = 0 ;

        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            int hourOfCurClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);
            int classIndexOfCurrentClass = Population.reverseIndex[curClassPos][0]*Population.totalClassRoom
                    + Population.reverseIndex[curClassPos][1];
            for(int iterator = 0 ; iterator<hourOfCurClass ; iterator++)
            {
                if(curClassPos+iterator>=p.chromosome.length)
                {
                    ret++; continue;
                }

                int classIndexofIterator = Population.reverseIndex[curClassPos+iterator][0]* Population.totalClassRoom
                        + Population.reverseIndex[curClassPos+iterator][1];

                if(classIndexOfCurrentClass != classIndexofIterator) ret++;
            }
        }

//        if(ret!=0)System.out.println("class splite "+ret);

        return  ret*50;
    }

    /**
     * check if the same class put in the same day same class
     * */

    private int classOverlapping(Population p)
    {
        int ret = 0;
        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            for(int nextClassPos = 0 ; nextClassPos<p.chromosome.length ; nextClassPos++)
            {
                if(curClassPos==nextClassPos) continue;
                if(p.chromosome[curClassPos].equals(p.chromosome[nextClassPos]))
                {
                    int hourOfCurClass= StringProcessor.getClassHour(p.chromosome[curClassPos]);

                    for(int iterator = 0 ; iterator<hourOfCurClass ; iterator++)
                    {
                        if(curClassPos+iterator>=p.chromosome.length) continue;

                        int classIndexOfCurClass = Population.reverseIndex[curClassPos+iterator][0]*Population.totalClassRoom
                                + Population.reverseIndex[curClassPos+iterator][1];

                        int classIndexOfNextClass = Population.reverseIndex[nextClassPos][0]*Population.totalClassRoom
                                + Population.reverseIndex[nextClassPos][1];

                        if(classIndexOfCurClass==classIndexOfNextClass) ret++;
                    }
                }
            }
        }

//        if(ret!=0)System.out.println("class overlapping "+ret);
        return  ret*40;
    }

    private int labAndTheoryRoomProblem(Population p)
    {
        int ret = 0;

        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            int hourOfCurrentClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

            int islabFlag = StringProcessor.getIsLabOrTheory(p.chromosome[curClassPos]);

            for(int iterator = 0 ; iterator<hourOfCurrentClass ; iterator++)
            {
                if(curClassPos+iterator>=p.chromosome.length) continue;

                if(islabFlag==1 && Population.reverseIndex[curClassPos+iterator][1]<Population.totalClassWithoutLab) ret++;
                else if(islabFlag==0 && Population.reverseIndex[curClassPos+iterator][1] >= Population.totalClassWithoutLab) ret++;
            }
        }
//        if(ret!=0)System.out.println("lab and theory overlapping");
        return ret*30;
    }
    private int teacherTimeOverlapping(Population p)
    {
        int ret = 0;


        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            for (int nextClassPos = 0 ; nextClassPos<p.chromosome.length ; nextClassPos++)
            {
                if(p.chromosome[nextClassPos]==null) continue;

                if(curClassPos==nextClassPos) continue;

                int teacherIdOfCurrentClass = StringProcessor.getTeacherId(p.chromosome[curClassPos]);
                int teacherIdOfNextClass = StringProcessor.getTeacherId(p.chromosome[nextClassPos]);

                if(teacherIdOfCurrentClass == teacherIdOfNextClass)
                {
                    int hourOfCurrentClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

                    for(int iterator = 0 ; iterator<hourOfCurrentClass ; iterator++)
                    {
                        if(curClassPos+iterator>=p.chromosome.length) continue;

                        if(Population.reverseIndex[curClassPos+iterator][0] == Population.reverseIndex[nextClassPos][0])
                        {
                            if(Population.reverseIndex[curClassPos+iterator][2] == Population.reverseIndex[nextClassPos][2]) ret++;
                        }
                    }
                }
            }
        }

//        if(ret!=0)System.out.println("teacher overlapping "+ret);

        return ret*20;
    }

    private int batchTimeOverlapping(Population p)
    {
        int ret = 0;


        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            for (int nextClassPos = 0 ; nextClassPos<p.chromosome.length ; nextClassPos++)
            {
                if(p.chromosome[nextClassPos]==null) continue;

                if(curClassPos==nextClassPos) continue;

//                System.out.println("for i and j" + curClassPos+" "+nextClassPos+" -------- "+p.chromosome[curClassPos]+" -> "+p.chromosome[nextClassPos]);

                int batchIdOfCurrentClass = StringProcessor.getBatchId(p.chromosome[curClassPos]);
                int batchIdOfNextClass = StringProcessor.getBatchId(p.chromosome[nextClassPos]);

                if(batchIdOfCurrentClass == batchIdOfNextClass)
                {
//                    System.out.println("Batch id same for "+batchIdOfCurrentClass+" "+batchIdOfNextClass);
                    int hourOfCurrentClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

//                    System.out.println(" hour of class "+hourOfCurrentClass+" for "+p.chromosome[curClassPos]);
                    for(int iterator = 0 ; iterator<hourOfCurrentClass ; iterator++)
                    {
                        if(curClassPos+iterator>=p.chromosome.length) continue;

                        if(Population.reverseIndex[curClassPos+iterator][0] == Population.reverseIndex[nextClassPos][0])
                        {
//                            System.out.println("This put in same day ");
                            if(Population.reverseIndex[curClassPos+iterator][2] == Population.reverseIndex[nextClassPos][2])
                            {
                                ret++;
//                                System.out.println("this put same class :) :)");
                            }
                        }
                    }
                }
            }
        }

//        if(ret!=0)System.out.println("batch overlapping "+ret);

        return ret*10;
    }

    private int subjectOverlapping(Population p)
    {
        int ret = 0;


        for(int curClassPos = 0 ; curClassPos<p.chromosome.length ; curClassPos++)
        {
            if(p.chromosome[curClassPos]==null) continue;

            for (int nextClassPos = 0 ; nextClassPos<p.chromosome.length ; nextClassPos++)
            {
                if(p.chromosome[nextClassPos]==null) continue;

                if(curClassPos==nextClassPos) continue;

                int subjectIdOfCurrentClass = StringProcessor.getSubjectId(p.chromosome[curClassPos]);
                int subjectIdOfNextClass = StringProcessor.getSubjectId(p.chromosome[nextClassPos]);

                if(subjectIdOfCurrentClass == subjectIdOfNextClass)
                {
                    int hourOfCurrentClass = StringProcessor.getClassHour(p.chromosome[curClassPos]);

                    for(int iterator = 0 ; iterator<hourOfCurrentClass ; iterator++)
                    {
                        if(curClassPos+iterator>=p.chromosome.length) continue;

                        if(Population.reverseIndex[curClassPos+iterator][0] == Population.reverseIndex[nextClassPos][0])
                        {
                            if(Population.reverseIndex[curClassPos+iterator][2] == Population.reverseIndex[nextClassPos][2]) ret++;
                        }
                    }
                }
            }
        }

//        if(ret!=0)System.out.println("subject overlapping "+ret);
        return ret*10;
    }
}
