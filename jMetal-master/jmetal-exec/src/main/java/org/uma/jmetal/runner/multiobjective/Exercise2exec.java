package org.uma.jmetal.runner.multiobjective;

import org.uma.jmetal.problem.multiobjective.zdt.ZDT9;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Exercise2exec {
    public static void main(String[] args) throws IOException {
        runExe(100, "Ex2");
        runExe(50, "Ex2");
    }
    public static void runForIJ() throws IOException {
        LastExercise3.runCompare(2, 3, 1000);
        LastExercise3.runCompare(2, 4, 1000);
        LastExercise3.runCompare(3, 4, 1000);
        LastExercise3.runCompare(3, 2, 1000);
        LastExercise3.runCompare(4, 3, 1000);
        LastExercise3.runCompare(4, 2, 1000);
    }
    public static void runExe(int populationSize, String Ex) throws IOException {
        NSGAIIRunner.custNSGAIIRunner(populationSize, 100000, Ex);
        System.out.println("NSGAII");
        Exercise2exec.runForIJ();
        SPEA2Runner.custSPEA2Runner(populationSize, 1000, Ex);
        System.out.println("SPEA2");
        Exercise2exec.runForIJ();
        IBEARunner.custIBEARunner(populationSize, 100000, Ex);
        System.out.println("IBEA");
        Exercise2exec.runForIJ();
    }
}
        /*
        for (int i = 0; i < a.length; i ++){
            System.out.println(i +  "    " + a[i][0] + "    " + a[i][1]);
        }
         */