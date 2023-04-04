package test_jMetal;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.Random;

public class RandomMutation implements MutationOperator<IntegerSolution>
{

    private double mutationProbability = 1.0;

    public RandomMutation(double mutationProbability)
    {
        this.mutationProbability = mutationProbability;
    }

    Random random = new Random();
    @Override
    public IntegerSolution execute(IntegerSolution solution)
    {
        //Important: Do not create a copy and modify the copy!
        //JMetal internally doesn't use the return value! It relies on the input object to be modified!

        for (int i = 0; i < solution.variables().size(); i++)
        {
            if (Math.random() < mutationProbability) continue;

            int lowerBound = solution.getBounds(i).getLowerBound();
            int upperBound = solution.getBounds(i).getUpperBound();
            solution.variables().set(i, (int) (lowerBound + (upperBound - lowerBound)*Math.random()));
        }

        return solution;
    }

    @Override
    public double getMutationProbability()
    {
        return mutationProbability;
    }
}
