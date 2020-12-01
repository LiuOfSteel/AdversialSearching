package org.uma.jmetal.operator.impl.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.util.RepairDoubleSolution;
import org.uma.jmetal.solution.util.RepairDoubleSolutionAtBounds;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;


@SuppressWarnings("serial")
public class Exercise3Mutation implements MutationOperator<DoubleSolution> {
    private static final double DEFAULT_PROBABILITY = 0.01 ;
    private static final double DEFAULT_DISTRIBUTION_INDEX = 20.0 ;
    private double distributionIndex ;
    private double mutationProbability ;
    private RepairDoubleSolution solutionRepair ;

    private RandomGenerator<Double> randomGenerator ;

    /** Constructor */
    public Exercise3Mutation() {
        this(DEFAULT_PROBABILITY, DEFAULT_DISTRIBUTION_INDEX) ;
    }

    /** Constructor */
    public Exercise3Mutation(DoubleProblem problem, double distributionIndex) {
        this(1.0/problem.getNumberOfVariables(), distributionIndex) ;
    }

    /** Constructor */
    public Exercise3Mutation(DoubleProblem problem,
                             double distributionIndex,
                             RandomGenerator<Double> randomGenerator) {
        this(1.0/problem.getNumberOfVariables(), distributionIndex) ;
        this.randomGenerator = randomGenerator ;
    }

    /** Constructor */
    public Exercise3Mutation(double mutationProbability, double distributionIndex) {
        this(mutationProbability, distributionIndex, new RepairDoubleSolutionAtBounds()) ;
    }

    /** Constructor */
    public Exercise3Mutation(double mutationProbability,
                             double distributionIndex,
                             RandomGenerator<Double> randomGenerator) {
        this(mutationProbability, distributionIndex, new RepairDoubleSolutionAtBounds(), randomGenerator) ;
    }

    /** Constructor */
    public Exercise3Mutation(double mutationProbability, double distributionIndex,
                             RepairDoubleSolution solutionRepair) {
        this(mutationProbability, distributionIndex, solutionRepair, () -> JMetalRandom.getInstance().nextDouble());
    }

    /** Constructor */
    public Exercise3Mutation(double mutationProbability, double distributionIndex,
                             RepairDoubleSolution solutionRepair, RandomGenerator<Double> randomGenerator) {
        if (mutationProbability < 0) {
            throw new JMetalException("Mutation probability is negative: " + mutationProbability) ;
        } else if (distributionIndex < 0) {
            throw new JMetalException("Distribution index is negative: " + distributionIndex) ;
        }
        this.mutationProbability = mutationProbability;
        this.distributionIndex = distributionIndex;
        this.solutionRepair = solutionRepair ;

        this.randomGenerator = randomGenerator ;
    }

    /* Getters */
    public double getMutationProbability() {
        return mutationProbability;
    }

    public double getDistributionIndex() {
        return distributionIndex;
    }

    /* Setters */
    public void setMutationProbability(double probability) {
        this.mutationProbability = probability ;
    }

    public void setDistributionIndex(double distributionIndex) {
        this.distributionIndex = distributionIndex ;
    }

    /** Execute() method */
    @Override
    public DoubleSolution execute(DoubleSolution solution) throws JMetalException {
        if (null == solution) {
            throw new JMetalException("Null parameter") ;
        }

        doMutation(mutationProbability, solution);
    /*
    System.out.println(" getLowerBound(0) : " + solution.getLowerBound(0));
    System.out.println(" getUpperBound(0) : " + solution.getUpperBound(0));
    System.out.println(" toString : " + solution.toString());
    System.out.println(" getVariableValue(0) : " + solution.getVariableValue(0));
    System.out.println(" getAttributes : " + solution.getAttributes());
    System.out.println(" getObjective(0) : " + solution.getObjective(0));
*/
        return solution;
    }

    /** Perform the mutation operation */

    private void doMutation(double probability, DoubleSolution solution) {
        double rnd, delta1, delta2, mutPow, deltaq;
        double y, yl, yu, val, xy;
        // System.out.println(" in Exercise2Mutation solution.getNumberVariables() : " + solution.getNumberOfVariables());
        // System.out.println("solution.getNumberObjectives() : " + solution.getNumberOfObjectives());
        int permutationLength = solution.getNumberOfObjectives();
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (1 > 0) {

                y = solution.getVariableValue(i);
                yl = solution.getLowerBound(i) ;
                yu = solution.getUpperBound(i) ;
                yl = 0;
                yu = 1;
                /*
                if (yl == yu) {
                    y = yl ;
                } else {
                    delta1 = (y - yl) / (yu - yl);
                    delta2 = (yu - y) / (yu - yl);
                    rnd = randomGenerator.getRandomValue();
                    mutPow = 1.0 / (distributionIndex + 1.0);
                    if (rnd <= 0.5) {
                        xy = 1.0 - delta1;
                        val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, distributionIndex + 1.0));
                        deltaq = Math.pow(val, mutPow) - 1.0;
                    } else {
                        xy = 1.0 - delta2;
                        val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, distributionIndex + 1.0));
                        deltaq = 1.0 - Math.pow(val, mutPow);
                    }
                    y = y + deltaq * (yu - yl);
                    // System.out.println("y: " + y + ",  yl: " + yl + ",  yu: " + yu);
                    y = solutionRepair.repairSolutionVariableValue(y, yl, yu);
                    // System.out.println("y: " + y);
                }
                solution.setVariableValue(i, y);
                 */
                double pos1 ;
                double pos2 ;

                pos1 = Math.random();
                // System.out.println("pos1 : " + pos1 + "   i  " + i);
                pos2 = (int)Math.random()*(permutationLength-1);

                solution.setVariableValue(i, pos1 );
                // System.out.println(" lala " + solution);
                // System.out.println(" lalaalalalalalalaal " + solution.getVariableValue(i) );

                /*
                while (pos1 == pos2) {
                    if (pos1 == (permutationLength - 1))
                        pos2 = (int)Math.random()*(permutationLength-2);
                    else
                        pos2 = (int)Math.random()*(permutationLength-1);
                } // while
                // swap
                /*
                int temp = permutation[pos1];
                permutation[pos1] = permutation[pos2];
                permutation[pos2] = temp;

                BitSet city1 = cityItems[permutation[pos1]+1];
                BitSet city2 = cityItems[permutation[pos2]+1];

                BitSet items1 = ((BitSet)items.clone());
                items1.and(city1);
                BitSet items2 = ((BitSet)items.clone());
                items2.and(city2);
                int i = items1.nextSetBit(0);
                int j = items2.nextSetBit(0);

                if(i>0 && j<0){
                    items.set(j,true);
                    items.set(i,false);
                }else if(j>0 && i<0){
                    items.set(i,true);
                    items.set(j,false);
                }
                */
            }
        }
    }

    public static void main(String args[]){


    }
}

