package test_jMetal;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.util.ArrayList;
import java.util.List;

public class TestProblem extends AbstractIntegerProblem
{
    private int evaluationCounter = 0;
    private int valueN;
    private int valueM;

    public  TestProblem()
    {
        this(20, 100, -100, -1000, +1000);
    }
    public TestProblem(int numberOfVariables, int n, int m, int lowerBound, int upperBound)
    {
        valueN = n;
        valueM = m;
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);
        setName("NMMin");

        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++)
        {
            lowerLimit.add(lowerBound);
            upperLimit.add(upperBound);
        }

        setVariableBounds(lowerLimit, upperLimit);
    }
    @Override
    public IntegerSolution evaluate(IntegerSolution solution)
    {
        evaluationCounter += 1;

        int sumDistancesToN = 0;
        int sumDistancesToM = 0;

        for (int current : solution.variables())
        {
            sumDistancesToN += Math.abs(valueN - current);
            sumDistancesToM += Math.abs(valueM - current);
        }

        solution.objectives()[0] = sumDistancesToN;
        solution.objectives()[1] = sumDistancesToM;

        return solution;
    }

    @Override
    public IntegerSolution createSolution()
    {
        return new DefaultIntegerSolution(this.getNumberOfObjectives(), this.getBoundsForVariables());
    }
}
