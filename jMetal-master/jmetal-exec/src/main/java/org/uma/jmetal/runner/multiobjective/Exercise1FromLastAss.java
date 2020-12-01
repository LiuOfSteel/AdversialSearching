package org.uma.jmetal.runner.multiobjective;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Exercise1FromLastAss {
    static boolean dimGot = false;
    static int dimension = 2;
    static double[][] data;          // all nodes' coordinates data would be saved here
    public static void main(String[] args) throws IOException {
        double[][] a = getData("usa13509.tsp");
        for (int i = 0; i < a.length; i ++){
            System.out.println(a[i][0] + "    " + a[i][1]);
        }
    }

    public static double[][] getData(String fileName) throws IOException {
        int lineNum = 0;
        String line;
        BufferedReader br0 = new BufferedReader(new FileReader(fileName));
        BufferedReader br1 = new BufferedReader(new FileReader(fileName));
        while ((line = br0.readLine()) != null){
            lineNum++;
        }
        data = new double[lineNum][2];
        lineNum = 0;
        while ((line = br1.readLine()) != null){
            if(line.length() != 0)
                getEveryCord(line, lineNum);
            //System.out.println(line);
            lineNum++;
        }
        /*
        for(int i = 0; i < data.length; i ++){
            System.out.println(i+ "  " + data[i][0] + "  " + data[i][1]);
        }
        */
        return data;
    }

    public static boolean getDim(String line){
        int r = 0;
        while (line.charAt(r) == "DIMENSION1".charAt(r)){
            r++;
        }
        if(r >= 8){
            for(int i = 12; i < line.length(); i++){
                dimension = dimension* 10 + (int)line.charAt(i) - 48;
            }
            return true;
        }else {
            return false;
        }
    }
    static int position = 0;

    public static void getEveryCord(String line, int lineNum){
        position = 0;
        //System.out.println(line);
        int a = (int)line.charAt(0);
        if (a >= 48 && a < 58){
            // System.out.println(line + " --- " + lineNum + "    ");
            data[lineNum][0] = (double)Math.round(getNumber(line)*1000)/1000;
            position++;
            data[lineNum][1] = (double)Math.round(getNumber(line)*1000)/1000;
        }
    }

    public static double getNumber(String line){
        double a = 0;
        int getPoint = 0;
        while (line.charAt(position) != ' '){
            if (getPoint == 0){
                if (line.charAt(position) != '.') {
                    a = a * 10 + line.charAt(position) - 48;
                }else {
                    getPoint = 1;
                }
            }else {
                a += (line.charAt(position) - 48) / Math.pow(10, getPoint);
                getPoint++;
            }
            position++;
            if (position >= line.length())
                break;
        }
        return a;
    }

}
