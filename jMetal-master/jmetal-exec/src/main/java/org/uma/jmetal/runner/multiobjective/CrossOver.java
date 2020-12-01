package org.uma.jmetal.runner.multiobjective;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class CrossOver {

    static LinkedList<element> cache = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        TSPsolution t1 = LastExercise3.createSolution(Exercise1FromLastAss.getData("eil51.tsp"));
        TSPsolution t2 = LastExercise3.createSolution(Exercise1FromLastAss.getData("eil51.tsp"));
        LinkedList<element> ts1 = CreateCrossOverSolution(t1);
        for (int i = 0; i < 100; i ++){
            t1 = LastExercise3.mutationInsert(t1);
            t2 = LastExercise3.mutationSwap(t2);
        }
        //for (int i = 0; i < ts1.size(); i++){
        //    System.out.print(t2.order[i][0] + "   ");// + t2.order[i][1] + "   " + t2.order[i][2] + "  ,  ");
        //}
        //System.out.println(ts1.get(3).neighbor2[2]);
        TSPsolution t3 = crossoverEdgeRecombination(t1, t2);
        System.out.println(" ");
        for (int i = 0; i < ts1.size(); i++){
            //System.out.println(t3.get(i).currentPoint[0] + "   " + t3.get(i).currentPoint[1] + "   " + t3.get(i).currentPoint[2] + "  ,  " + t3.get(i).previous1 + "  ,  " + t3.get(i).previous2 + "  ,  " + t3.get(i).behind1 + "  ,  " + t3.get(i).behind2 );
            System.out.print(t3.order[i][0] + " ");// + t3.order[i][1] + "   " + t3.order[i][2]);
        }
    }

    public static LinkedList<element> CreateCrossOverSolution(TSPsolution s){
        LinkedList<element> CrossOverSolution = new LinkedList<>();

        for (int i = 0; i < s.order.length; i++){
            CrossOverSolution.add(new element(s, i));
            //System.out.println(" 3333333");
        }
        return CrossOverSolution;
    }

    public static element[] createTable(TSPsolution s1, TSPsolution s2){
        //ArrayList<element> table = new ArrayList<>(s1.order.length);
        element[] table = new element[s1.order.length];
        //System.out.println(s1.order.length + "   1 1 1   " + table.length);
        for (int i = 0; i < s1.order.length; i++){
            element e1 = new element(s1, i, (int)(s1.order[(i - 1 + s1.order.length)%s1.order.length][0]), (int)(s1.order[(i + 1 + s1.order.length)%s1.order.length][0]) );
            element e2 = new element(s1, i, (int)(s1.order[(i - 1 + s1.order.length)%s1.order.length][0]), (int)(s1.order[(i + 1 + s1.order.length)%s1.order.length][0]) );
            //System.out.println(table.length);
            //table.add((int)e1.currentPoint[0], e1);
            //System.out.println(e1.position);
            table[e1.position] = e1;
            cache.add(e2);
        }
        //for (int i = 0; i < table.length; i++){
         //   System.out.println(s1.);
        //}
        for (int i = 0; i < s1.order.length; i++){
            table[(int)s2.order[i][0]].previous2 = (int)s2.order[(i - 1 + s1.order.length)%s1.order.length][0];
            table[(int)s2.order[i][0]].behind2 = (int)s2.order[(i + 1 + s1.order.length)%s1.order.length][0];
            if (table[(int)s2.order[i][0]].previous2 == table[(int)s2.order[i][0]].previous1){
                table[(int)s2.order[i][0]].NumOfNeighbor--;
                table[(int)s2.order[i][0]].CommonNeighbor = 1010;
            }
            if (table[(int)s2.order[i][0]].previous2 == table[(int)s2.order[i][0]].behind1){
                table[(int)s2.order[i][0]].NumOfNeighbor--;
                table[(int)s2.order[i][0]].CommonNeighbor = 110;
            }
            if (table[(int)s2.order[i][0]].behind2 == table[(int)s2.order[i][0]].previous1){
                table[(int)s2.order[i][0]].NumOfNeighbor--;
                table[(int)s2.order[i][0]].CommonNeighbor = 1001;
            }
            if (table[(int)s2.order[i][0]].behind2 == table[(int)s2.order[i][0]].behind1){
                table[(int)s2.order[i][0]].NumOfNeighbor--;
                table[(int)s2.order[i][0]].CommonNeighbor = 101;
            }
        }
        /*
        System.out.println(" ");
        for (int k = 0; k < table.length; k++){
            System.out.print( table[k].position + " "  + table[k].currentPoint[0] + "  " + table[k].previous1 + "  " + table[k].behind1 + "  " + table[k].previous2 + "  " + table[k].behind2 + " /// ");
        }
        System.out.println(" ");
        */
        return table;
    }

    public static int[] edgeRecombinationSample(int[] a, int[] b){
        double[] a1 = new double[a.length];
        double[] b1 = new double[b.length];
        for (int i = 0; i < a.length; i++){
            a1[i] = (double)(a[i]);
            b1[i] = (double)(b[i]);
        }
        TSPsolution s1 = new TSPsolution(a1);
        TSPsolution s2 = new TSPsolution(b1);
        TSPsolution s3 = crossoverEdgeRecombination(s1, s2);
        int[] order = new int[a.length];
        for (int i = 0; i < a.length; i++){
            order[i] = (int)s3.order[i][0];
        }
        return order;
    }

    public static TSPsolution crossoverEdgeRecombination(TSPsolution s1, TSPsolution s2){
        int[] useWhenLoseTraction = new int[s1.order.length];
        element[] table = createTable(s1, s2);
        ArrayList<element> newSolu = new ArrayList<>(table.length);
        int current = (int)(Math.random()*table.length);
        int newCurrent;
        newSolu.add(table[current]);
        while (newSolu.size() < s1.order.length){
            newCurrent = -5;
            if (table[current].CommonNeighbor > 0){
                //System.out.print(" first if ");
                if (table[current].CommonNeighbor % 1000 > 100){   // behind1
                    //newCurrent = table[current].behind1;
                    if (table[current].behind1 == table[current].previous2){
                        table[current].CommonNeighbor -= 110;
                        newCurrent = table[current].behind1;
                    }else if (table[current].behind1 == table[current].behind2){
                        table[current].CommonNeighbor -= 101;
                        newCurrent = table[current].behind1;
                    }else {
                        newCurrent = -22;
                        //System.out.println("Error at position 0002");
                    }
                }else {
                    //newCurrent = table[current].previous1;
                    if (table[current].previous1 == table[current].behind2){
                        table[current].CommonNeighbor -= 1001;
                        newCurrent = table[current].previous1;
                    }else if (table[current].previous1 == table[current].previous2){
                        table[current].CommonNeighbor -= 1010;
                        newCurrent = table[current].previous1;
                    }else {
                        //System.out.println("Error at position 0003");
                        newCurrent = -33;
                    }
                }
                //System.out.print( newCurrent);
            }else if (table[current].NumOfNeighbor > 0){
                //System.out.print("second if ");
                int neighbors = 5;
                if (table[current].previous1 >= 0 && table[table[current].previous1].NumOfNeighbor < neighbors ){
                    newCurrent = table[current].previous1;
                    neighbors = table[current].previous1;
                }
                if (table[current].behind1 >= 0 && table[table[current].behind1].NumOfNeighbor < neighbors ){
                    newCurrent = table[current].behind1;
                    neighbors = table[current].behind1;
                }
                if (table[current].previous2 >= 0 && table[table[current].previous2].NumOfNeighbor < neighbors ){
                    newCurrent = table[current].previous2;
                    neighbors = table[current].previous2;
                }
                if (table[current].behind2 >= 0 && table[table[current].behind2].NumOfNeighbor < neighbors ){
                    newCurrent = table[current].behind2;
                    neighbors = table[current].behind2;
                }
            }else {
                //System.out.println("lose traction " + newSolu.size());
                int m = 0;
                while (m != cache.size()){
                    if (useWhenLoseTraction[cache.get(m).position] == 1){
                        cache.remove(m);
                    }else {
                        m++;
                    }
                }
                while ((newCurrent = cache.get((int)(Math.random()*cache.size())).position) == current);
                //if (newCurrent > current)
                //    newCurrent--;
            }
            //System.out.println(" " + newSolu.size());
            //deleteCorrespond(current);
            useWhenLoseTraction[current] = 1;
            //System.out.print(" " + newCurrent + " ");
            table = deleteReferConsole(table, current);
            //System.out.println("3333");
            current = newCurrent;
            newSolu.add(table[current]);
        }
        //System.out.println("  ");

        TSPsolution newTs = new TSPsolution(s1);
        //System.out.println(" size " + newSolu.size());
        for (int i = 0; i < newSolu.size(); i ++){
            newTs.order[i][0] = newSolu.get(i).currentPoint[0];
            newTs.order[i][1] = newSolu.get(i).currentPoint[1];
            newTs.order[i][2] = newSolu.get(i).currentPoint[2];
        }
        newTs = LastExercise3.calculateDistance(newTs);
        return newTs;
    }

    public static void deleteCorrespond(int m){
        int i = 0;
        //System.out.println(" m mmmmmmmmmmmmmmmmm: ");
        while(m != (cache.get(i).position)){
            //System.out.print(" cache " + cache.get(i).currentPoint[0]);
            i++;

        }
        //System.out.println(" xxxxxxxxxxxxxxxxxxxx");
        cache.remove(i);
    }

    public static element[] deleteReferConsole(element[] tableCon, int current){
        if (tableCon[current].previous1 >= 0) {
            tableCon = deleteReference(tableCon, tableCon[current].previous1, current);
        }
        if (tableCon[current].previous2 >= 0) {
            tableCon = deleteReference(tableCon, tableCon[current].previous2, current);
        }
        if (tableCon[current].behind1 >= 0) {
            tableCon = deleteReference(tableCon, tableCon[current].behind1, current);
        }
        if (tableCon[current].behind2 >= 0) {
            tableCon = deleteReference(tableCon, tableCon[current].behind2, current);
        }
        return tableCon;
    }

    public static element[] deleteReference(element[] table, int target, int current){

        if (table[target].previous1 == current){
            table[target].previous1 = -2;
            if (table[target].CommonNeighbor > 1000){
                table[target].CommonNeighbor -= 1000;
                table[target].NumOfNeighbor--;
            }else {
                table[target].NumOfNeighbor--;
            }
            //System.out.print(" x1x ");
        }
        if (table[target].behind1 == current){
            table[target].behind1 = -2;
            if (table[target].CommonNeighbor%1000 > 100){
                table[target].CommonNeighbor -= 100;
                table[target].NumOfNeighbor--;
            }else {
                table[target].NumOfNeighbor--;
            }
            //System.out.print(" x1x ");
        }
        if (table[target].previous2 == current){
            table[target].previous2 = -2;
            if (table[target].CommonNeighbor%100 >= 10){
                table[target].CommonNeighbor -= 10;
            }else {
                table[target].NumOfNeighbor--;
            }
            //System.out.print(" x1x ");
        }
        if (table[target].behind2 == current){
            table[target].behind2 = -2;
            if (table[target].CommonNeighbor%10 > 0){
                table[target].CommonNeighbor -= 1;
            }else {
                table[target].NumOfNeighbor--;
            }
            //System.out.print(" x1x ");
        }

        return table;
    }

    public static  LinkedList<element> CreateEdgeTable(LinkedList<element> s1, LinkedList<element> s2){
        double a;
        for (int i = 0; i < s1.size(); i++){
            a = s1.get(i).currentPoint[0];
            //System.out.println("2222  " + i);
            for (int j = 0; j < s2.size(); j++){
                if (s2.get(j).currentPoint[0] == a){
                    s1.get(i).neighbor3 = s2.get(j).neighbor1;
                    s1.get(i).neighbor4 = s2.get(j).neighbor2;
                    if (s1.get(i).neighbor3[0] == s1.get(i).neighbor1[0]){
                        s1.get(i).NumOfNeighbor--;
                        s1.get(i).CommonNeighbor += 1010;
                    }
                    if (s1.get(i).neighbor3[0] == s1.get(i).neighbor2[0]){
                        s1.get(i).NumOfNeighbor--;
                        s1.get(i).CommonNeighbor += 110;
                    }
                    if (s1.get(i).neighbor4[0] == s1.get(i).neighbor1[0]){
                        s1.get(i).NumOfNeighbor--;
                        s1.get(i).CommonNeighbor += 1001;
                    }
                    if (s1.get(i).neighbor4[0] == s1.get(i).neighbor2[0]){
                        s1.get(i).NumOfNeighbor--;
                        s1.get(i).CommonNeighbor += 101;
                    }
                    break;
                }
            }
        }
        return s1;
    }

    public static TSPsolution CrossOverEdgeRecomb(TSPsolution s1, TSPsolution s2){
        LinkedList<element> set = CreateEdgeTable(CreateCrossOverSolution(s1), CreateCrossOverSolution(s2));
        //System.out.println(" 1111 ");
        /*
        for (int i = 0; i < set.size(); i++){
            System.out.println(set.get(i).currentPoint[0] + "  " + set.get(i).currentPoint[1] + "  " + set.get(i).currentPoint[2] + " neighbor1 " + set.get(i).neighbor1[0] + " neighbor2 " + set.get(i).neighbor2[0] + " neighbor3 " + set.get(i).neighbor3[0] + " neighbor4 " + set.get(i).neighbor4[0]);
        }
        */
        LinkedList<element> newEdgeSolution = new LinkedList<>();
        int initial = (int)(Math.random()*set.size());
        int current = initial;
        int newCurrent = -1;
        //newEdgeSolution.add(set.get(initial));
        set = RemoveCurrentRef(set, current);
        while(newEdgeSolution.size() < s1.order.length){
            //System.out.println(newEdgeSolution.size());
            if (set.get(current).CommonNeighbor > 0){
                //System.out.println("first if");
                if (set.get(current).CommonNeighbor % 1000 > 100){
                    newCurrent = getCurrent(set, set.get(current).neighbor2[0]);
                    set.get(current).CommonNeighbor -= 100;
                    set.get(current).NumOfNeighbor --;
                    if (set.get(current).neighbor3[0] == set.get(current).neighbor2[0]){
                        set.get(current).CommonNeighbor -= 10;
                    }else {
                        set.get(current).CommonNeighbor -= 1;
                    }
                }else {
                    newCurrent = getCurrent(set, set.get(current).neighbor1[0]);
                    set.get(current).CommonNeighbor -= 1000;
                    set.get(current).NumOfNeighbor --;
                    if (set.get(current).neighbor3[0] == set.get(current).neighbor1[0]){
                        set.get(current).CommonNeighbor -= 10;
                    }else {
                        set.get(current).CommonNeighbor -= 1;
                    }
                }
            }else if (set.get(current).NumOfNeighbor > 0){
                //System.out.println("second if");
                int n1 = 4;
                newCurrent = -1;
                int[] n = new int[4];
                n[0] = getCurrent(set, set.get(current).neighbor1[0]);
                n[1] = getCurrent(set, set.get(current).neighbor2[0]);
                n[2] = getCurrent(set, set.get(current).neighbor3[0]);
                n[3] = getCurrent(set, set.get(current).neighbor4[0]);
                for (int i = 0; i < 4; i++){
                    //System.out.print(" n[" + i + "]" + n[i] + "  ");
                    if (n[i] >= 0) {
                        //System.out.print(" get in ");
                        if (set.get(n[i]).NumOfNeighbor < n1) {
                            n1 = set.get(n[i]).NumOfNeighbor;
                            newCurrent = n[i];
                        }
                    }
                }
                if (newCurrent == -1){
                    while((newCurrent = n[(int)(Math.random()*4)]) < 0);
                    //System.out.print( " newnewnenw current " + newCurrent);
                }
            }else {
                //System.out.println("lose traction");
                if ((set.size() > 1)){
                    while ((newCurrent = (int)(Math.random()*set.size())) == current){}
                }
            }
            newEdgeSolution.add(set.get(current));
            set = RemoveCurrentRef(set, current);
            set.remove(current);
            if (newCurrent > current){
                newCurrent--;
            }
            current = newCurrent;
        }
        TSPsolution ts1 = new TSPsolution(s1.order);
        for (int i = 0; i < newEdgeSolution.size(); i++){
            ts1.order[i][0] = newEdgeSolution.get(i).currentPoint[0];
            ts1.order[i][1] = newEdgeSolution.get(i).currentPoint[1];
            ts1.order[i][2] = newEdgeSolution.get(i).currentPoint[2];
        }
        ts1 = LastExercise3.calculateDistance(ts1);
        return ts1;
    }

    public static int getCurrent(LinkedList<element> set, double order){
        /*
        for (int i = 0; i < set.size(); i++){
            System.out.println(set.get(i).currentPoint[0] + "  " + set.get(i).currentPoint[1] + "  " + set.get(i).currentPoint[2] + " neighbor1 " + set.get(i).neighbor1[0] + " neighbor2 " + set.get(i).neighbor2[0] + " neighbor3 " + set.get(i).neighbor3[0] + " neighbor4 " + set.get(i).neighbor4[0]);
        }
        */
        //System.out.println("order: " + order);
        for (int i = 0; i < set.size(); i++){
            if (set.get(i).currentPoint[0] == order){
                //System.out.println(" ");
                //System.out.println(" should already found newCurrent" + order);
                return i;
            }
        }
        //System.out.println(" did nt found newCurrent");
        return -250;
    }

    public static LinkedList<element> RemoveCurrentRef(LinkedList<element> table1, int current){
        int i = 0;
        for(i = 0; i < table1.size(); i++){
            if (table1.get(i).neighbor1[0] == table1.get(current).currentPoint[0]){
                table1.get(i).neighbor1[0] = -1;
                table1.get(i).neighbor1[1] = -1;
                table1.get(i).neighbor1[2] = -1;
                if (table1.get(i).CommonNeighbor > 1000){
                    table1.get(i).CommonNeighbor -= 1000;
                }else {
                    table1.get(i).NumOfNeighbor--;
                }
            }
            if (table1.get(i).neighbor2[0] == table1.get(current).currentPoint[0]){
                table1.get(i).neighbor2[0] = -1;
                table1.get(i).neighbor2[1] = -1;
                table1.get(i).neighbor2[2] = -1;
                if (table1.get(i).CommonNeighbor % 1000 > 100){
                    table1.get(i).CommonNeighbor -= 100;
                }else {
                    table1.get(i).NumOfNeighbor--;
                }
            }
            if (table1.get(i).neighbor3[0] == table1.get(current).currentPoint[0]){
                table1.get(i).neighbor3[0] = -1;
                table1.get(i).neighbor3[1] = -1;
                table1.get(i).neighbor3[2] = -1;
                if (table1.get(i).CommonNeighbor % 100 >= 10){
                    table1.get(i).CommonNeighbor -= 10;
                    table1.get(i).NumOfNeighbor--;
                }else {
                    table1.get(i).NumOfNeighbor--;
                }
            }
            if (table1.get(i).neighbor4[0] == table1.get(current).currentPoint[0]){
                table1.get(i).neighbor4[0] = -1;
                table1.get(i).neighbor4[1] = -1;
                table1.get(i).neighbor4[2] = -1;
                if (table1.get(i).CommonNeighbor % 10 > 0){
                    table1.get(i).CommonNeighbor -= 1;
                    table1.get(i).NumOfNeighbor--;
                }else {
                    table1.get(i).NumOfNeighbor--;
                }
            }
        }
        return table1;
    }

}

class element{
    double[] currentPoint = new double[3];
    double[] neighbor1 = new double[3];
    double[] neighbor2 = new double[3];
    double[] neighbor3 = new double[3];
    double[] neighbor4 = new double[3];
    int previous1;
    int previous2;
    int behind1;
    int behind2;
    int position;
    int NumOfNeighbor;
    int CommonNeighbor;
    public element(TSPsolution s, int m){
        this.currentPoint[0] = s.order[m][0];
        this.currentPoint[1] = s.order[m][1];
        this.currentPoint[2] = s.order[m][2];
        this.neighbor1[0] = s.order[(m + s.order.length - 1) % s.order.length][0];
        this.neighbor1[1] = s.order[(m + s.order.length - 1) % s.order.length][1];
        this.neighbor1[2] = s.order[(m + s.order.length - 1) % s.order.length][2];
        this.neighbor2[0] = s.order[(m + s.order.length + 1) % s.order.length][0];
        this.neighbor2[1] = s.order[(m + s.order.length + 1) % s.order.length][1];
        this.neighbor2[2] = s.order[(m + s.order.length + 1) % s.order.length][2];
        this.NumOfNeighbor = 4;
        this.CommonNeighbor = 0;
    }

    public element(TSPsolution s, int m, int previous1, int behind1){
        this.position = (int)s.order[m][0];
        this.previous1 = previous1;
        this.behind1 = behind1;
        this.currentPoint[0] = s.order[m][0];
        this.currentPoint[1] = s.order[m][1];
        this.currentPoint[2] = s.order[m][2];
        this.NumOfNeighbor = 4;
        this.CommonNeighbor = 0;
    }
}
