package test_jMetal;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class DoubleSinglePointCrossover implements CrossoverOperator<DoubleSolution>
{

    private double crossoverProbability = 1.0;

    public DoubleSinglePointCrossover(double crossoverProbability)
    {
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    public List<DoubleSolution> execute(List<DoubleSolution> source)
    {
        if (source == null) System.out.println("Source is null!");
        if (source.size() != 2) System.out.println("There must be two parents instead of {}!" + source.size());
        if (source.get(0).variables().size() != source.get(1).variables().size()) System.out.println("Given solutions have different number of variables!");

        DoubleSolution parent1 = source.get(0);
        DoubleSolution parent2 = source.get(1);
        DoubleSolution offspring1 = (DoubleSolution) parent1.copy();
        DoubleSolution offspring2 = (DoubleSolution) parent2.copy();
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

        List<DoubleSolution> result = new ArrayList<>();
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
