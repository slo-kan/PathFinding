package test_jMetal;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

public class SinglePointCrossover implements CrossoverOperator<IntegerSolution>
{

    private double crossoverProbability = 1.0;

    public SinglePointCrossover(double crossoverProbability)
    {
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    public List<IntegerSolution> execute(List<IntegerSolution> source)
    {
        if (source == null) System.out.println("Source is null!");
        if (source.size() != 2) System.out.println("There must be two parents instead of {}!" + source.size());
        if (source.get(0).variables().size() != source.get(1).variables().size()) System.out.println("Given solutions have different number of variables!");

        IntegerSolution parent1 = source.get(0);
        IntegerSolution parent2 = source.get(1);
        IntegerSolution offspring1 = (IntegerSolution) parent1.copy();
        IntegerSolution offspring2 = (IntegerSolution) parent2.copy();
        int numVariables = parent1.variables().size();

        if (Math.random() < crossoverProbability)
        {
            int cutoffPoint = (int) ((numVariables - 1)*Math.random());

            for (int i = cutoffPoint; i < numVariables; i++)
            {
                offspring1.variables().set(i, parent2.variables().get(i));
                offspring2.variables().set(i, parent1.variables().get(i));
            }
        }

        List<IntegerSolution> result = new ArrayList<>();
        result.add(offspring1);
        result.add(offspring2);

        return result;
    }

    public int getNumberOfRequiredParents()
    {
        return 2;
    }

    @Override
    public int getNumberOfGeneratedChildren()
    {
        return 2;
    }

    @Override
    public double getCrossoverProbability()
    {
        return crossoverProbability;
    }
}
