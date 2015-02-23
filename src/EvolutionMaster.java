import com.sun.org.apache.bcel.internal.generic.POP;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by phenix on 2/13/15.
 */
public class EvolutionMaster {
    public Population mutationMaster(Population parent)
    {
        Population returnPopulation  = new Population();

        for (int i = 0; i< returnPopulation.chromosome.length ; i++) {
            returnPopulation.chromosome[i] = parent.chromosome[i];
            returnPopulation.endPosition[i] = parent.endPosition[i];
        }

        Random rand = new Random();

        int xPos = rand.nextInt(parent.chromosome.length);

        while(true)
        {
            if(returnPopulation.fixedInChromosome[xPos]==0)
            {
                break;
            }
            else xPos = rand.nextInt(parent.chromosome.length);
        }
        int yPos = rand.nextInt(parent.chromosome.length);

        while(true)
        {
            if(returnPopulation.fixedInChromosome[yPos]==0)
            {
                break;
            }
            else yPos = rand.nextInt(parent.chromosome.length);
        }

//        System.out.println(xPos+" --- "+yPos);

//        System.out.println("suing mutation "+returnPopulation.chromosome[xPos]+" retu "+returnPopulation.chromosome[yPos]);

        String tmp = returnPopulation.chromosome[xPos];

        returnPopulation.chromosome[xPos] = returnPopulation.chromosome[yPos];

        returnPopulation.chromosome[yPos] = tmp;

        tmp = returnPopulation.chromosome[xPos];



        if(tmp!=null)
        {
            returnPopulation.endPosition[xPos] = xPos+ StringProcessor.getClassHour(tmp) - 1;
        }

        tmp = returnPopulation.chromosome[yPos];

        if(tmp!=null)
        {
            returnPopulation.endPosition[yPos] = yPos+ StringProcessor.getClassHour(tmp) - 1;
        }

//        System.out.println("suing mutation "+returnPopulation.chromosome[xPos]+" retu "+returnPopulation.chromosome[yPos]);

        /**
         * this portion used for only checking
         *
         *
         * */

        int cnt = 0;

        for (int i = 0; i <parent.chromosome.length ; i++) {
            if(returnPopulation.chromosome[i]!=null) cnt++;
        }

//        if(cnt!=2) System.out.println(" ------------------------------------------ there is an error in mutator");

         return returnPopulation;
    }

    public List<Population> crossoverMaster(Population mother,Population father)
    {
        Population returnPopulation1 = new Population();

        Random rand = new Random(123049);

        int crossPos = rand.nextInt(father.chromosome.length);


        for (int i = 0; i <crossPos ; i++) {
            returnPopulation1.chromosome[i] = mother.chromosome[i];
            returnPopulation1.endPosition[i] = mother.endPosition[i];
        }

        for (int i = crossPos ; i <mother.chromosome.length ; i++) {
            returnPopulation1.chromosome[i] = father.chromosome[i];
            returnPopulation1.endPosition[i] = father.endPosition[i];
        }

        /**
         * this portion used for only checking
         *
         *
         * */

        int cnt = 0;

        for (int i = 0; i <mother.chromosome.length ; i++) {
            if(returnPopulation1.chromosome[i]!=null) cnt++;
        }

        if(cnt!=2) System.out.println(" ------------------------------------------ there is an error in cross over 1");



        Population returnPopulation2 = new Population();

//        Random rand = new Random(23049);

//        int crossPos = rand.nextInt(returnPopulation.chromosome.length);


        for (int i = 0; i <crossPos ; i++) {
            returnPopulation2.chromosome[i] = father.chromosome[i];
            returnPopulation2.endPosition[i] = father.endPosition[i];
        }

        for (int i = crossPos ; i <mother.chromosome.length ; i++) {
            returnPopulation2.chromosome[i] = mother.chromosome[i];
            returnPopulation2.endPosition[i] = mother.endPosition[i];
        }


        /**
         * this portion used for only checking
         *
         *
         * */

        cnt = 0;

        for (int i = 0; i <mother.chromosome.length ; i++) {
            if(returnPopulation2.chromosome[i]!=null) cnt++;
        }

        if(cnt!=2) System.out.println(" ------------------------------------------ there is an error in cross over 2");

        List<Population>ret = new ArrayList<Population>();

        ret.add(returnPopulation1);
        ret.add(returnPopulation2);

        return ret;
    }
}
