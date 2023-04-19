package test_jMetal;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class AnotherProblem extends AbstractDoubleProblem
{


    public  AnotherProblem()
    {
        this(30);
    }
    public AnotherProblem(Integer numberOfVariables) {
        setNumberOfObjectives(2);
        setName("ZDT1");

        List<Double> lowerLimit = new ArrayList<>(numberOfVariables) ;
        List<Double> upperLimit = new ArrayList<>(numberOfVariables) ;

        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimit.add(0.0);
            upperLimit.add(1.0);
        }

        setVariableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double[] f = new double[solution.objectives().length];

        f[0] = solution.variables().get(0);
        double g = this.evalG(solution);
        double h = this.evalH(f[0], g);
        f[1] = h * g;

        solution.objectives()[0] = f[0];
        solution.objectives()[1] = f[1];

        return solution ;
    }
    protected double evalG(DoubleSolution solution) {
        double g = 0.0;
        for (int i = 1; i < solution.variables().size(); i++) {
            g += solution.variables().get(i);
        }
        double constant = 9.0 / (solution.variables().size() - 1);

        return constant * g + 1.0;
    }

    protected double evalH(double f, double g) {
        double h ;
        h = 1.0 - Math.sqrt(f / g);
        return h;
    }
}
