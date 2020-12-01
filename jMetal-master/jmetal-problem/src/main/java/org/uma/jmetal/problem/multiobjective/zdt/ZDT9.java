//  ZDT9.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//




//




//



package org.uma.jmetal.problem.multiobjective.zdt;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/** Class representing problem ZDT9 */
@SuppressWarnings("serial")
public class ZDT9 extends AbstractDoubleProblem {

    /** Constructor. Creates default instance of problem ZDT9 (30 decision variables) */
    public ZDT9() {
        this(40);
    }

    /**
     * Creates a new instance of problem ZDT9.
     *
     * @param numberOfVariables Number of variables.
     */
    public ZDT9(Integer numberOfVariables) {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(2);
        setName("ZDT9");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(0.0);
            upperLimit.add(2.0);
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
     * Returns the value of the ZDT9 function G.
     *
     * @param solution Solution
     */
    protected double evalG(DoubleSolution solution) {
        int x = 0;
        for (int i = 0; i < 20; i ++){
            x += solution.getVariableValue(i);
        }
        return x + 0.5;
    }


    protected double evalH(DoubleSolution solution) {
        int y = 0;
        for (int i = 20; i < 40; i ++){
            y += solution.getVariableValue(i);
        }
        return y + 0.5;
    }
}
