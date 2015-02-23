import com.sun.org.apache.bcel.internal.generic.POP;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by phenix on 2/12/15.
 */
public class MainClass {
    public static void main(String[]Args) throws IOException {
//        System.out.println("this is cool");
        Information infoClass = new Information("res/in.txt");

//        System.out.println(" fixed "+ Population.fixedInChromosome.length);

        Generation gen = new Generation();


        gen.populationList = new ArrayList<Population>();

        for (int i = 0; i <10 ; i++) {
//            System.out.println(" now for "+i);
            Population p = new Population();
            p.RandomGenerator();
            gen.populationList.add(p);
        }

//        System.out.println("size of cur pop list "+gen.populationList.size());
        while(gen.haveSolution()==false)
        {
//            gen.printFitness(10);
            gen.mutateProcessor();
            //gen.crossoverProcessor();
            gen.evolutionProcessor();
//            System.out.println("cur gen "+gen.generationNo);
//            gen.printFitness(10);
        }

//        System.out.println("----------------------------------------------------------------------------------------------------------------- ");

        Population solution = gen.getSolution();
        int totalOutput = 0;
//        System.out.println("solution of "+(new FitnessCalculator()).calculateFitness(solution , 100));

        for (int i = 0; i <solution.chromosome.length ; i++) {
            //System.out.println("obkkor");
            if(solution.chromosome[i]!=null)
            {
//                System.out.println("day : "+Population.reverseIndex[i][0] +" room "+Population.reverseIndex[i][1]+" time slot : "+ Population.reverseIndex[i][2]+" to "+Population.reverseIndex[solution.endPosition[i]][2]+" class "+solution.chromosome[i] );
                totalOutput++;
            }
        }

        OutputProcessor outputProcessor = new OutputProcessor(solution , totalOutput);


    }
}
