package test_jMetal;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.List;

public class Test
{


    public static void runTest()
    {
        System.out.println("--- Running JMetal Test ---");

        Problem<DoubleSolution> problem;
        Algorithm<List<DoubleSolution>> algorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
        String referenceParetoFront = "";
        String problemName;

        problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
        referenceParetoFront = "resources/referenceFrontsCSV/ZDT1.csv";

        problem = new ZDT1(); //ProblemUtils.<DoubleSolution>loadProblem(problemName);

        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 20.0;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

        selection = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());

        int populationSize = 100;
        algorithm = new NSGAIIBuilder<>(problem, crossover, mutation, populationSize)
                .setSelectionOperator(selection)
                .setMaxEvaluations(100000)
                .build();

        long initTimeMilliseconds = System.currentTimeMillis();
        Thread thread = new Thread(algorithm);
        thread.start();
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            System.out.println("Error in thread.join(): {}" + e.getMessage());
            //throw new JMetalException("Error in thread.join()", e);
        }
        long computingTimeMilliseconds = System.currentTimeMillis() - initTimeMilliseconds;
        long computingTimeSeconds = Math.round(computingTimeMilliseconds / 1000d);

        List<DoubleSolution> population = algorithm.getResult();

        System.out.println("--- Completed JMetal Test, in seconds: " + computingTimeSeconds + "---");
    }

    public static void main(String[] args){
        runTest();
    }

}
