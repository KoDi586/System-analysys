package ServiceWithoutRegular;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceFromGPT {
    static List<Double> am1Points = new ArrayList<>();
    static List<Double> am2Points = new ArrayList<>();
    public static List<List<Double>> points() throws IOException {

        final int Nx = 10;
        final int Ny = 10; // 21
        final int Nz = 10;
        double[][][] dT1 = new double[Nx][Ny][Nz];
        double[][][] T1 = new double[Nx][Ny][Nz];
        double[][][] dT2 = new double[Nx][Ny][Nz];
        double[][][] T2 = new double[Nx][Ny][Nz];
        double[][][] TK = new double[Nx][Ny][Nz];
        double[] Tek = new double[Nz];
        double[] Zs1 = new double[Nz];

        double time = 0, tmin = 0;
        double dx, dy, dz1, dz2, dz3, dz4, a1, a2, lam1,
                lam2, Lx, Ly, Lz1, Lz2, Lz3, Lz4;
        int i, iv, j, x, y, z, tau, Xs1, Ys1, tt, z1, Nz1, Nz2;
        double ax1, ay1, az1, n1, Fx1, Kz1, Ts1, ax2, ay2, az2, n2, Fx2, Kz2, Ts2, ax3, ay3, az3, n3, Fx3, Kz3, Ts3;
        double kz0, ax, ay, az, del, tzz, umx, dmx, am3, Kr;
        double k1, k3, ta1, ta3, f1, f3, w, G1, G3, df, dm, dtau, psi, yt, km, ar, Kap, Kkor;
        double wc1, wc3, mg1, mg3, dd, dw, wcm1, wcm3, k, ee4, dww, G, fr, Mor, Mo, fp, dfaz, am1 = 0, am2=0;
        double Pee4, Pe4, Pee2, pe2, ee2, wvv, mra, a3, ind2, ind4, indsr, indd, wk2, wk4, Snag, b1, b2, b3;
        double Tkk, Si2, Lk, KY, Q, Q1, n, vF = 0, asr, E1, E2, E4, S4, sum, r1, raz, Tza, NB, KT;
        FileWriter f2;

        NB = 9;
        // Верхний пласт
        ax = 150.4 / 86400;
        ay = 34.64 / 86400;
        az = 24.64 / 86400;
        n = 0.000128;

        // Нижний пласт
        ax1 = 150.4 / 86400;
        ay1 = 34.64 / 86400;
        az1 = 24.64 / 86400;
        n1 = 0.00012;
        b1 = 0.00132 / 86400;

        // Геометрия
        Lx = 2100;
        Ly = 2200;
        Lz1 = 12;
        Lz2 = 22;

        // Расположение скважин в пласте
        Zs1 = new double[Nz];
        for (z = 0; z < Nz; z++) {
            Zs1[z] = 0;
        }
        Xs1 = 5;
        Ys1 = 5;
        Nz1 = 2;
        Nz2 = Nz - 1;
        for (z = Nz1; z <= Nz2; z++) {
            Zs1[z] = 1;
        }
        double K1 = 0.02 + 0.1 * NB;

        f2 = new FileWriter("C:\\Users\\hirof\\IdeaProjects\\Graph_num_2(1)\\rez.txt");

        f2.write("*** Вариант**** " + NB + "\n");
        dx = Lx / (Nx - 1);
        dy = Ly / (Ny - 1);
        dz1 = Lz1 / (Nz - 1);
        dz2 = (Lz2) / (Nz - 1);
        f2.write("   dx= " + dx + "   dy= " + dy + " Lz2=" + Lz2 + " K1=" + K1 + "\n");

        for (z = 0; z < Nz; z++) {
            f2.write(" z=" + z + " Zs1["+z+"]=" + Zs1[z] + "\n");
        }

        for (z = 0; z < Nz; z++) {
            for (y = 0; y < Ny; y++) {
                for (x = 0; x < Nx; x++) {
                    // Верхний пласт
                    vF = 50;
                    T1[x][y][z] = 193 - vF * (x - 1) / (Nx - 1);
                }
            }
        }

        for (z = 0; z < Nz; z++) {
            for (y = 0; y < Ny; y++) {
                for (x = 0; x < Nx; x++) {
                    // Пласт
                    T2[x][y][z] = 193 - vF * (x - 1) / (Nx - 1);
                    TK[x][y][z] = T2[x][y][z];
                }
            }
        }

        for (y = 0; y < Ny; y++) {
            for (x = 0; x < Nx; x++) {
                f2.write("  ");
                for (z = 0; z < Nz; z++) {
                    f2.write(" TK["+x+"]["+y+"]["+z+"]  " + TK[x][y][z] + " ");
                }
                f2.write("\n");
            }
        }

        for (z = 0; z < Nz; z++) {
            f2.write(" z=" + z + "   T2[5][9]["+z+"]=" + T2[5][9][z] + "\n");
        }

        // Input
        Q1 = -100;
        Kkor = 1.91;  // для корректировки
        f2.write(" Kkor  " + Kkor + "\n");

        // Временные циклы
        time = 0;
        iv = 50;
        tau = 100;
        dtau = 1;

        // Гидролитосферные процессы
        for (tt = 1; tt <= tau; tt++) {
            for (i = 1; i <= iv; i++) {
                time += dtau;
                wvv = time / 3600;

                // В.валанжин
                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        for (z = 1; z < Nz - 1; z++) {
                            dT1[x][y][z] = (1 / n) * dtau * (ax * (T1[x - 1][y][z] - 2 * T1[x][y][z] + T1[x + 1][y][z]) / (dx * dx)
                                    + ay * (T1[x][y - 1][z] - 2 * T1[x][y][z] + T1[x][y + 1][z]) / (dy * dy)
                                    + az * (T1[x][y][z - 1] - 2 * T1[x][y][z] + T1[x][y][z + 1]) / (dz1 * dz1));
                        }
                    }
                }

                // Н. валанжин
                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        for (z = 1; z < Nz - 1; z++) {
                            del = 0;
                            if (x == Xs1 && y == Ys1) {
                                del = 1;
                            }
                            dT2[x][y][z] = (1 / n1) * dtau * (ax1 * (T2[x - 1][y][z] - 2 * T2[x][y][z] + T2[x + 1][y][z]) / (dx * dx)
                                    + ay1 * (T2[x][y - 1][z] - 2 * T2[x][y][z] + T2[x][y + 1][z]) / (dy * dy)
                                    + az1 * (T2[x][y][z - 1] - 2 * T2[x][y][z] + T2[x][y][z + 1]) / (dz2 * dz2))
                                    + (Q1 / (3600 * 24)) / (Nz - 2) * Kkor * del * dtau;
                        }
                    }
                }

                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        for (z = 1; z < Nz - 1; z++) {
                            T1[x][y][z] = T1[x][y][z] + dT1[x][y][z];
                            T2[x][y][z] = T2[x][y][z] + dT2[x][y][z];
                        }
                    }
                }

                // Граничные условия
                for (x = 0; x < Nx - 1; x++) {
                    for (y = 0; y < Ny - 1; y++) {
                        T2[x][y][Nz-1] = T2[x][y][Nz - 2];
                    }
                }

                // Граничные условия при переходе из среды в среду
                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        T1[x][y][Nz-1] = T1[x][y][Nz-1] + b1 * dtau * (T2[x][y][1] - T1[x][y][Nz-1]);
                        T2[x][y][1] = T2[x][y][1] - b1 * dtau * (T2[x][y][1] - T1[x][y][Nz-1]);
                    }
                }
            }
            // Сохранение точек для построения графика

            am1 = (T2[Xs1][Ys1][5] - TK[Xs1][Ys1][5]) / (Q1 / 86400);//коэф усиления
            am2 = T2[Xs1][Ys1][5] - TK[Xs1][Ys1][5];

            am1Points.add(am1);
            am2Points.add(am2);

        }
        List<List<Double>> result = new ArrayList<>();

        //почистить от лишнего

        result.add(am1Points);
        result.add(am2Points);

        f2.close();
        return result;
    }
}
