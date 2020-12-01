
package org.uma.jmetal.runner.multiobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.ExerciseCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.Exercise2Mutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.*;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Class for configuring and running the SPEA2 algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class SPEA2Runner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws java.io.IOException
   * @throws SecurityException
   * @throws ClassNotFoundException Invoking command:
   *                                java org.uma.jmetal.runner.multiobjective.SPEA2BinaryRunner problemName [referenceFront]
   */
  public static void main(String[] args) throws JMetalException, FileNotFoundException {
    Problem<DoubleSolution> problem;
    Algorithm<List<DoubleSolution>> algorithm;
    CrossoverOperator<DoubleSolution> crossover;
    MutationOperator<DoubleSolution> mutation;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

    String referenceParetoFront = "";

    String problemName;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0];
      referenceParetoFront = args[1];
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT2";
      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT2.pf";
    }

    problem = ProblemUtils.loadProblem(problemName);

    double crossoverProbability = 0.9;
    double crossoverDistributionIndex = 20.0;
    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0;
    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

    algorithm = new SPEA2Builder<>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxIterations(250)
            .setPopulationSize(100)
            .setK(1)
            .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute();

    List<DoubleSolution> population = algorithm.getResult();

    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront);
    }
  }

  public static void custSPEA2Runner(int populationSize, int MaxEvaluations, String ex)throws JMetalException, FileNotFoundException{
    int[][] occupy = new int[20][20];
    int times = (int)(Math.random()*10) + 20;
    Problem<DoubleSolution> problem;
    Algorithm<List<DoubleSolution>> algorithm;
    CrossoverOperator<DoubleSolution> crossover;
    MutationOperator<DoubleSolution> mutation;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

    String referenceParetoFront = "";

    String problemName;
    problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT2";
    referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT2.pf";


    problem = ProblemUtils.loadProblem(problemName);

    double crossoverProbability = 0.9;
    double crossoverDistributionIndex = 20.0;
    crossover = new ExerciseCrossover(crossoverProbability, crossoverDistributionIndex);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0;
    mutation = new Exercise2Mutation(mutationProbability, mutationDistributionIndex);

    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

    algorithm = new SPEA2Builder<>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxIterations(MaxEvaluations)
            .setPopulationSize(populationSize)
            .setK(1)
            .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute();

    List<DoubleSolution> population = algorithm.getResult();
    if (ex == "Ex2") {
      for (int i = 0; i < population.size(); i++) {
        int a = (int) (population.get(i).getObjective(0) * 20 % 20);
        int b = (int) (population.get(i).getObjective(1) * 20 % 20);
        population.get(i).setObjective(0, (int) (population.get(i).getObjective(0) * 20 % 20) + 0.5);
        population.get(i).setObjective(1, (int) (population.get(i).getObjective(1) * 20 % 20) + 0.5);
        while(occupy[a][b] == 1){
          if (Math.random() < 0.5){
            a = (a + (int)(Math.random()*20)) % 20;
          } else {
            b = (b + (int)(Math.random()*20)) % 20;
          }
        }
        occupy[a][b] = 1;
        population.get(i).setObjective(0, a + 0.5);
        population.get(i).setObjective(1, b + 0.5);
      }
    }else if (ex == "Ex3") {
      for (int i = 0; i < population.size(); i++) {
        population.get(i).setObjective(0, (population.get(i).getObjective(0) * 20 % 20) + 0.5);
        population.get(i).setObjective(1, (population.get(i).getObjective(1) * 20 % 20) + 0.5);
      }
    }else if (ex == "Ex4"){
      for (int i = 0; i < population.size(); i++) {
        double a = (population.get(i).getObjective(0) * 20 % 20);
        double b = (population.get(i).getObjective(1) * 20 % 20);
        double a1 = (int)a + 0.5;
        double b1 = (int)b + 0.5;
        population.get(i).setObjective(0, a - 1.5 * (a - a1));
        population.get(i).setObjective(1, b - 1.5 * (b - b1));
      }
      for (int i = 0; i < times; i++){
        doAMicro();
      }
    }
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront);
    }
  }
  public static void doAMicro() throws FileNotFoundException {
    Problem<DoubleSolution> problem;
    Algorithm<List<DoubleSolution>> algorithm;
    CrossoverOperator<DoubleSolution> crossover;
    MutationOperator<DoubleSolution> mutation;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

    String referenceParetoFront = "";

    String problemName;

    problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT2";
    referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT2.pf";

    problem = ProblemUtils.loadProblem(problemName);

    double crossoverProbability = 0.9;
    double crossoverDistributionIndex = 20.0;
    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0;
    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

    algorithm = new SPEA2Builder<>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxIterations(250)
            .setPopulationSize(10)
            .setK(1)
            .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute();

    List<DoubleSolution> population = algorithm.getResult();

    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    CustmizedPrintFinalSolutionSet(population, 10, 250, "ZDT2");
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront);
    }
  }

}
