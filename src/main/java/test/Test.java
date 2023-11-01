package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Double> doubles = new ArrayList<>();
        for (Double i = 0d; i < 100d; i++) {
            doubles.add(i);
        }
        System.out.println(doubles);

        int Nx = 10;
        int Ny = 10;
        int Nz = 10;

//        double[][][] dT1 = new double[Nx][Ny][Nz];
//        System.out.println(Arrays.stream(dT1).toString());

        double d = 1/0;
        System.out.println(d);
    }


}
