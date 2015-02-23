import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by phenix on 2/12/15.
 */
public class Generation {
    public List<Population> populationList;
    EvolutionMaster evolutor;
    public int generationNo;
    FitnessCalculator fitnessChecker;

    public Generation()
    {
        generationNo = 0;
        evolutor = new EvolutionMaster();
        fitnessChecker = new FitnessCalculator();
    }
    public void mutateProcessor() {

        //System.out.println(" pop list befor mutate "+populationList.size());
        List<Population>tmpList = new ArrayList<Population>();


        for (int i = 0; i <populationList.size() ; i++) {
            tmpList.add(evolutor.mutationMaster(populationList.get(i)));
        }

        for (int i = 0; i <tmpList.size() ; i++) {
            populationList.add(tmpList.get(i));
        }

        //System.out.println(" pop list befor mutate "+populationList.size());
    }

    public void crossoverProcessor() {
        //System.out.println(" pop list befor cross "+populationList.size());

        List<Population>tmpList = new ArrayList<Population>();

        for (int i = 0; i <populationList.size() ; i++) {
            for (int j = 0 ; j <populationList.size() ; j++) {
                if(i==j)continue;
                List<Population>forNow = new ArrayList<Population>();
                forNow = evolutor.crossoverMaster(populationList.get(i) , populationList.get(j));

                for (int k = 0; k <forNow.size() ; k++) {
                    tmpList.add(forNow.get(k));
                }
            }
        }

        for(Population p: tmpList)
        {
            populationList.add(p);
        }

//        //System.out.println(" pop list after "+populationList.size());

    }

    public void evolutionProcessor(){

        //System.out.println("Size of Pop list before mutate "+populationList.size());
        Collections.sort(populationList);

        while(populationList.size()> 10)
        {
            int idx= populationList.size();

            populationList.remove(idx-1);
        }

        //System.out.println(" pop list after "+populationList.size());

        generationNo++;
    }


    public boolean haveSolution()
    {
        for(Population p : populationList)
        {
            if(fitnessChecker.calculateFitness(p,1)==0) return true;
        }
        return false;
    }

    public Population getSolution()
    {
        for(Population p : populationList)
        {
            if(fitnessChecker.calculateFitness(p,1)==0) return p;
        }
        return  null;
    }

    public void printFitness(int value)
    {
        for(Population p : populationList)
        {
            System.out.printf(" %d", fitnessChecker.calculateFitness(p , value));
        }
        System.out.println(":");
    }
}
