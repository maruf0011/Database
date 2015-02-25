import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.*;

/**
 * Created by phenix on 2/12/15.
 */

public class Population implements Comparable{
    public static int [][][] FixedTimeFlag;
    public static int [][][] indexOfChrosome;
    public static int [][] reverseIndex;
    public String [] chromosome;
    public int [] endPosition;
    public static int [] fixedInChromosome;
    public static int totalDay,totalClassRoom,totalClassWithoutLab,totalTimeSlot;
    public static List<String>classList;
    public static String [] fixedPositionChromosomeStringStorage;

    public Population()
    {
        chromosome = new String[fixedInChromosome.length];
        endPosition = new int[fixedInChromosome.length];

        Arrays.fill(chromosome , null);

    }

    public void RandomGenerator(){


//        System.out.println("-> "+fixedInChromosome.length);
        chromosome = new String[fixedInChromosome.length];
        endPosition = new int[fixedInChromosome.length];

        Arrays.fill(chromosome , null);

        List<String>tmpList = new ArrayList<String>();

        for(String now : classList){
            tmpList.add(now);
        }
        Random rand = new Random();

        for (int i = 0; i <1000 ; i++) {
            int index_x = rand.nextInt(tmpList.size());
            int index_y = rand.nextInt(tmpList.size());


            String now = tmpList.get(index_x);

            tmpList.set(index_x , tmpList.get(index_y));

            tmpList.set(index_y , now);
        }
//        System.out.println(" randomized ");
        for(String now : tmpList)
        {

                while(true)
                {
//                    System.out.println(" cholse for "+now);
                    int Position = rand.nextInt(chromosome.length);
                    if(fixedInChromosome[Position]==0 && chromosome[Position]==null)
                    {
//                        System.out.println("length "+now.length());
//                        System.out.println("here  "+now.charAt(6)+" and "+now.charAt(7));
//                        System.out.println(Position);
//                        System.out.println("class hour "+StringProcessor.getClassHour(now));
//                        System.out.println(Position +" to "+(Position+ StringProcessor.getClassHour(now)) );
                        chromosome[Position] = now;
                        endPosition[Position] = Position+ StringProcessor.getClassHour(now) - 1 ;
                        break;
                    }
                }
        }

        /**
         * assign the fixed class in the position of chromosome . f
         * */

        for(int iteratorForFixed = 0 ; iteratorForFixed<chromosome.length ; iteratorForFixed++)
        {
            if(Population.fixedInChromosome[iteratorForFixed]==1 && Population.fixedPositionChromosomeStringStorage[iteratorForFixed]!=null)
            {
                chromosome[iteratorForFixed] = Population.fixedPositionChromosomeStringStorage[iteratorForFixed];
                endPosition[iteratorForFixed] = iteratorForFixed+StringProcessor.getClassHour(chromosome[iteratorForFixed])-1;
            }
        }

        //System.out.println("sesh");

    }

    @Override
    public int compareTo(Object o) {
        FitnessCalculator fit =new FitnessCalculator();
        int value1 = fit.calculateFitness(this , 0);
        int value2 = fit.calculateFitness((Population)o , 0);

//        System.out.println("in comparator "+value1+" "+value2);

        return (value1 < value2) ? -1 : ((value1>value2) ? 1 : 0);
        //return 0;
    }
}
