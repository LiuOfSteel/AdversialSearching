package org.uma.jmetal.solution.util;

import org.uma.jmetal.util.JMetalException;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RepairDoubleSolutionAtBounds implements RepairDoubleSolution {
  /**
   * Checks if the value is between its bounds; if not, the lower or upper bound is returned
   * @param value The value to be checked
   * @param lowerBound
   * @param upperBound
   * @return The same value if it is in the limits or a repaired value otherwise
   */
  public double repairSolutionVariableValue(double value, double lowerBound, double upperBound) {
    if (lowerBound > upperBound) {
      throw new JMetalException("The lower bound (" + lowerBound + ") is greater than the "
          + "upper bound (" + upperBound+")") ;
    }
    //System.out.println("in bounds' checking");
    double result = value ;
    if (value < lowerBound) {
      System.out.println(" bounds' problems1");
      result = lowerBound ;
    }
    if (value > upperBound) {
      System.out.println(" bounds' problems2");
      result = upperBound ;
    }

    return result ;
  }
}
