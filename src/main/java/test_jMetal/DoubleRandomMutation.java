package test_jMetal;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.Random;

public class DoubleRandomMutation implements MutationOperator<DoubleSolution>
{

    private double mutationProbability = 1.0;

    public DoubleRandomMutation(double mutationProbability)
    {
        this.mutationProbability = mutationProbability;
    }

    Random random = new Random();
    @Override
    public DoubleSolution execute(DoubleSolution solution)
    {
        //Important: Do not create a copy and modify the copy!
        //JMetal internally doesn't use the return value! It relies on the input object to be modified!

        for (int i = 0; i < solution.variables().size(); i++)
        {
            if (Math.random() < mutationProbability) continue;

            double lowerBound = solution.getBounds(i).getLowerBound();
            double upperBound = solution.getBounds(i).getUpperBound();
            solution.variables().set(i, (lowerBound + (upperBound - lowerBound)*Math.random()));
        }

        return solution;
    }

    @Override
    public double getMutationProbability()
    {
        return mutationProbability;
    }
}
