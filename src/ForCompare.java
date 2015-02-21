/**
 * Created by phenix on 2/14/15.
 */

import java.util.*;

public class ForCompare implements Comparator<Population>{
    @Override
    public int compare(Population o1, Population o2) {
        FitnessCalculator fit =new FitnessCalculator();
        int value1 = fit.calculateFitness(o1);
        int value2 = fit.calculateFitness(o2);

//        System.out.println("in comparator "+value1+" "+value2);

        return (value1 > value2) ? 1 : 0;
    }
}
