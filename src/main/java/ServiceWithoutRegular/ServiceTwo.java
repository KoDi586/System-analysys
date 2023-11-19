package ServiceWithoutRegular;

import java.util.ArrayList;
import java.util.List;

public class ServiceTwo {
    static List<Double> am1Points = new ArrayList<>();
    static List<Double> am2Points = new ArrayList<>();
    public static void main(String[] args) {
        int Nx = 10;
        int Ny = 10;
        int Nz = 10;

        double[][][] dT1 = new double[Nx][Ny][Nz];
        double[][][] T1 = new double[Nx][Ny][Nz];
        double[][][] dT2 = new double[Nx][Ny][Nz];
        double[][][] T2 = new double[Nx][Ny][Nz];
        double[][][] TK = new double[Nx][Ny][Nz];
        double[][][] Tek = new double[Nx][Ny][Nz];

        double time = 0, dx = 0, dy = 0, dz1 = 0, dz2 = 0, dz3 = 0, dz4 = 0, a1 = 0, a2 = 0,
                lam1 = 0, lam2 = 0, Lx = 0, Ly = 0, Lz1 = 0, Lz2 = 0, Lz3 = 0, Lz4 = 0,
                ax1 = 0, ay1 = 0, az1 = 0, n1 = 0, Fx1 = 0, Kz1 = 0, Ts1 = 0, ax2 = 0,
                ay2 = 0, az2 = 0, n2 = 0, Fx2 = 0, Kz2 = 0, Ts2 = 0, kz0 = 0, ax = 0,
                ay = 0, az = 0, del = 0, tzz = 0, umx = 0, dmx = 0, am3 = 0, Kr = 0, k1 = 0,
                k3 = 0, ta1 = 0, ta3 = 0, f1 = 0, f3 = 0, w = 0, G1 = 0, G3 = 0, df = 0, dm = 0,
                dtau = 0, psi = 0, yt = 0, km = 0, ar = 0, Kap = 0, Kkor = 0, wc1 = 0, wc3 = 0,
                mg1 = 0, mg3 = 0, dd = 0, dw = 0, wcm1 = 0, wcm3 = 0, k = 0, ee4 = 0, dww = 0,
                G = 0, fr = 0, Mor = 0, Mo = 0, fp = 0, dfaz = 0, am1 = 0, am2 = 0, Pee4 = 0,
                Pe4 = 0, Pee2 = 0, pe2 = 0, ee2 = 0, wvv = 0, mra = 0, a3 = 0, ind2 = 0, ind4 = 0,
                indsr = 0, indd = 0, wk2 = 0, wk4 = 0, Snag = 0, b1 = 0, b2 = 0, b3 = 0, Tkk = 0,
                Si2 = 0, Lk = 0, KY = 0, Q = 0, Q1 = 0, n = 0, vF = 0, asr = 0, E1 = 0, E2 = 0,
                E4 = 0, S4 = 0, sum = 0, r1 = 0, raz = 0, Tza = 0, NB = 0, KT = 0;
        int iv = 0, tau = 0, Xs1 = 0, Ys1 = 0, Nz1 = 0, Nz2 = 0;


        NB = 18;    //вариант

        // Верхний пласт
        ax = 150.4 / 86400;
        ay = 34.64 / 86400;
        az = 24.64 / 86400;
        n = 0.000128;

        // Нижний пласт
        ax1 = 150 / 4 / 86400;
        ay1 = 34.64 / 86400;
        az1 = 24.64 / 86400;
        n1 = 0.00012;           // 0.0012
        b1 = 0.0132 / 86400;

        // Геометрия
        Lx = 2100;
        Ly = 2200;
        Lz1 = 20 + 5 * NB;    // высота гр. вод
        Lz2 = 22 + 5 * NB;

        // Расположение скважин в пласте
        Xs1 = 5;    // расположение скважин по x
        Ys1 = 5;    // расположение скважин по y

        // Скважина работает не по всей длине
        //                Nz1 = 2;
        //                Nz2 = Nz1 - 1;                        Мурат сказал
        //                for (int z = Nz1; z <= Nz2; z++)      не нужно это
        //                    Zs1[z] = 1;
        //                k1 = 0.02 + 0.01 * NB;

        // Создание массивов для сохранения точек



        // Вход
        Q1 = -100;
        Kkor = 152; // для корректировки

        // Временные циклы
        time = 0;
        iv = 50;
        tau = 200;
        dtau = 1; // 1

        for (int tt = 1; tt <= tau; tt++) {
            // Ваш цикл по времени
            // ...

            for (int i = 1; i <= iv; i++) {
                time += dtau;
                wvv = time / 3600;

                // Верхний валанжин
                for (int x = 2; x <= Nx - 1; x++)
                    for (int y = 2; y <= Ny - 1; y++)
                        for (int z = 2; z <= Nz - 1; z++)
                            dT1[x][y][z] = (1 / n) * dtau *
                                    (ax * (T1[x - 1][y][z] - 2 * T1[x][y][z] + T1[x + 1][y][z])
                                            / (dx * dx) + ay * (T1[x][y - 1][z] - 2 * T1[x][y][z] + T1[x][y + 1][z]) / (dy * dy) + az * (T1[x][y][z - 1]
                                            - 2 * T1[x][y][z] + T1[x][y][z + 1]) / (dz1 * dz1));

                // Нижний валанжин
                for (int x = 2; x <= Nx - 1; x++)
                    for (int y = 2; y <= Ny - 1; y++)
                        for (int z = 2; z <= Nz - 1; z++) {
                            del = 0;
                            if (x == Xs1 && y == Ys1)
                                del = 1;
                            dT2[x][y][z] = (1 / n1) * dtau
                                    * (ax1 * (T2[x - 1][y][z] - 2 * T2[x][y][z] + T2[x + 1][y][z]) / (dx * dx)
                                    + ay1 * (T2[x][y - 1][z] - 2 * T2[x][y][z] + T2[x][y + 1][z]) / (dy * dy) + az1 * (T2[x][y][z - 1] - 2 * T2[x][y][z]
                                    + T2[x][y][z + 1]) / (dz2 * dz2))
                                    + (Q1 / (3600 * 24)) / (Nz - 2) * Kkor * del * dtau;
                        }

                for (int x = 2; x <= Nx - 1; x++)
                    for (int y = 2; y <= Ny - 1; y++)
                        for (int z = 2; z <= Nz - 1; z++) {
                            T1[x][y][z] += dT1[x][y][z];
                            T2[x][y][z] += dT2[x][y][z];
                        }

                // Граничные условия
                for (int x = 1; x <= Nx - 1; x++)
                    for (int y = 1; y <= Ny - 1; y++)
                        T2[x][y][Nz] = T2[x][y][Nz - 1];

                // Граничные условия при переходе из среды в среду
                for (int x = 2; x <= Nx - 1; x++)
                    for (int y = 2; y <= Ny - 1; y++) {
                        T1[x][y][Nz] = T1[x][y][Nz] + b1 * dtau * (T2[x][y][1] - T1[x][y][Nz]);
                        T2[x][y][1] = T2[x][y][1] - b1 * dtau * (T2[x][y][1] - T1[x][y][Nz]);
                    }
            }

            // Сохранение точек для построения графика
            am1 = (T2[Xs1][Ys1][5] - TK[Xs1][Ys1][5]) / Q1;
            am2 = T2[Xs1][Ys1][5] - TK[Xs1][Ys1][5];

            am1Points.add(am1);
            am2Points.add(am2);
        }
    }

    public static List<Double> getAm1Points() {
        return am1Points;
    }

    public static List<Double> getAm2Points() {
        return am2Points;
    }
}

