package test_jMetal;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.Arrays;
import java.util.List;

public class AnotherExecutor
{

    public static void runExperiment()
    {
        System.out.println("--- Running Test Experiment ---");

        //Problem
        //Problem<IntegerSolution> problem = new TestProblem(2, 3, 6, 0, 10);
        Problem<DoubleSolution> problem = new AnotherProblem();


        //Crossover
        double crossoverProbability = 0.9;
        CrossoverOperator<DoubleSolution> crossover = new DoubleSinglePointCrossover(crossoverProbability);

        //Mutation
        double mutationProbability = 0.1; //1.0 / problem.getNumberOfVariables();
        MutationOperator<DoubleSolution> mutation = new DoubleRandomMutation(mutationProbability);

        //Selection
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());

        //Algorithm
        int populationSize = 2;
        NSGAIIBuilder<DoubleSolution> algorithmBuilder = new NSGAIIBuilder<>(problem, crossover, mutation, populationSize);
        algorithmBuilder.setSelectionOperator(selection);
        algorithmBuilder.setMaxEvaluations(10);
        Algorithm<List<DoubleSolution>> algorithm = algorithmBuilder.build();

        //This should be the same as using the thread method below to run it (as long as a sequential evaluator is used at least)...
        //algorithm.run();

        //Executing the algorithm
        long initTimeMilliseconds = System.currentTimeMillis();
        Thread thread = new Thread(algorithm);
        thread.start();
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            System.out.println("Error in thread.join(): {}" + e.getMessage());
        }
        long computingTimeMilliseconds = System.currentTimeMillis() - initTimeMilliseconds;
        long computingTimeSeconds = Math.round(computingTimeMilliseconds / 1000d);

        List<DoubleSolution> resultPopulation = algorithm.getResult();

        System.out.println("--- Completed Test Experiment, in seconds:" + computingTimeSeconds + " ---");

        System.out.println("Results: ...");
        for (DoubleSolution current : resultPopulation)
        {
            //System.out.println("Dec:" + current.variables().get(0) +","+ current.variables().get(1) + " Obj:" + current.objectives()[0] +","+ current.objectives()[1]);

            System.out.println("Dec:" + current.variables() + " Obj:" + Arrays.toString(current.objectives()));
        }
    }
    public static void main(String[] args){
        runExperiment();
    }
}

