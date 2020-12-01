


package org.uma.jmetal.problem.multiobjective.zdt;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/** Class representing problem ZDT10 */
@SuppressWarnings("serial")
public class ZDT10 extends AbstractDoubleProblem {

    /** Constructor. Creates default instance of problem ZDT10 (30 decision variables) */
    public ZDT10() {
        this(40);
    }

    /**
     * Creates a new instance of problem ZDT10.
     *
     * @param numberOfVariables Number of variables.
     */
    public ZDT10(Integer numberOfVariables) {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);
        setName("ZDT10");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(0.0);
            upperLimit.add(1.0);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
    }

    /** Evaluate() method */
    public void evaluate(DoubleSolution solution) {
        double[] f = new double[getNumberOfObjectives()];

        f[0] = solution.getVariableValue(0);
        double g = this.evalG(solution);
        double h = this.evalH(solution);
        f[1] = h * g;
        //System.out.println(" hhhhhhhhhhhhhhhhhhhhhhhhh " + solution.getVariableValue(0) + "     " + solution.getObjective(0));
        solution.setObjective(0, h);
        solution.setObjective(1, g);
        // System.out.println(" jjjjjjlala " + solution.getObjective(0) + "     " + solution.getObjective(1));
    }

    /**
     * Returns the value of the ZDT10 function G.
     *
     * @param solution Solution
     */
    protected double evalG(DoubleSolution solution) {
        double x = 0;
        for (int i = 0; i < 20; i ++){
            x += solution.getVariableValue(i);
        }
        return x + 0.5;
    }


    protected double evalH(DoubleSolution solution) {
        double y = 0;
        for (int i = 20; i < 40; i ++){
            y += solution.getVariableValue(i);
        }
        return y + 0.5;
    }
}
