package regular;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceReg_2 {
    static List<Double> pltlist = new ArrayList<>();
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
        double[][][] Tek = new double[Nx][Ny][Nz];
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
        double usilitel,delta_H;

        NB = 9;
        // Верхний пласт, физические параметры, даны изначально
        ax = 148.4 / 86400;
        ay = 24.64 / 86400;
        az = 14.64 / 86400;
        n = 0.000128;

        // Нижний пласт
        ax1 = 148.4 / 86400;
        ay1 = 0.5 * 14.64 / 86400;
        az1 = 0.27 * 14.64 / 86400;
        n1 = 0.00012;
        b1 = 0.000012 / 86400;

        // Геометрия
        Lx = 2100;
        Ly = 2200;
        Lz1 = 20 + 5 * NB;
        Lz2 = 22 + 5 * NB;

        // Расположение скважин в пласте
        Zs1 = new double[Nz];
        for (z = 0; z < Nz; z++) {
            Zs1[z] = 0;
        }
        Xs1 = 4;
        Ys1 = 4;
        Nz1 = 2;
        Nz2 = Nz - 1;
        for (z = Nz1; z <= Nz2; z++) {
            Zs1[z] = 1;
        }
        double K1 = 0.02 + 0.1 * NB;

        dx = Lx / (Nx - 1);
        dy = Ly / (Ny - 1);
        dz1 = Lz1 / (Nz - 1);
        dz2 = (Lz2) / (Nz - 1);

        // Input
        Q1 = -100;
        Kkor = 1.91 * (0.3358 / 0.18); // Подбирать Kkor так, чтобы # 0.18 - K1  // для корректировки

        // Временные циклы
        time = 0;
        iv = 1000;
        tau = 172;
        dtau = 1;

        double Kp = 1.02964374498184;
        double Ti = 1 / 241.629736057393;
        double Td = 0.256167689129989;  // Получил из программы СПИДР

        double regular = 0;
        double s = 0;
        double d = 0;
        am2 = 0;

        vF = 50;



        for (z = 0; z < Nz; z++) {
            for (y = 0; y < Ny; y++) {
                for (x = 0; x < Nx; x++) {
                    // Верхний пласт
                    T1[x][y][z] = 193 - vF * (x - 1) / (Nx - 1);

                    //пласт
                    T2[x][y][z] = 193 - vF * (x - 1) / (Nx - 1);
                    TK[x][y][z] = T2[x][y][z];

                }
            }
        }









        // Гидролитосферные процессы
        for (tt = 1; tt <= tau; tt++) {
            for (i = 1; i <= iv; i++) {
                time += dtau;
                wvv = time / 3600;

                // Валанжины
                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        for (z = 1; z < Nz - 1; z++) {
                            //верхний
                            dT1[x][y][z] = (1 / n) * dtau * (ax * (T1[x - 1][y][z] - 2 * T1[x][y][z] + T1[x + 1][y][z]) / (dx * dx)
                                                           + ay * (T1[x][y - 1][z] - 2 * T1[x][y][z] + T1[x][y + 1][z]) / (dy * dy)
                                                           + az * (T1[x][y][z - 1] - 2 * T1[x][y][z] + T1[x][y][z + 1]) / (dz1 * dz1));

                            //нижний
                            del = 0;
                            if (x == Xs1 && y == Ys1) {
                                del = 1;
                            }
                            dT2[x][y][z] = ((1 / n1) * dtau * (ax1 * (T2[x - 1][y][z] - 2 * T2[x][y][z] + T2[x + 1][y][z]) / (dx * dx)
                                                            + ay1 * (T2[x][y - 1][z] - 2 * T2[x][y][z] + T2[x][y + 1][z]) / (dy * dy)
                                                            + az1 * (T2[x][y][z - 1] - 2 * T2[x][y][z] + T2[x][y][z + 1]) / (dz2 * dz2))
                                                            + ((-regular) / (Nz - 2)) * Kkor * del * dtau);
                        }
                    }
                }

                //Формирование условий для следующего цикла
                for (x = 1; x < Nx - 1; x++) {
                    for (y = 1; y < Ny - 1; y++) {
                        for (z = 1; z < Nz - 1; z++) {
                            T1[x][y][z] = T1[x][y][z] + dT1[x][y][z];
                            T2[x][y][z] = T2[x][y][z] + dT2[x][y][z];
                        }
                    }
                }

                // Граничные условия при переходе из среды в среду
                for (x = 2; x < Nx - 1; x++) {
                    for (y = 2; y < Ny - 1; y++) {
                        T1[x][y][7] = T1[x][y][7] + b1 * dtau * (T2[x][y][1] - T1[x][y][7]);
                        T2[x][y][1] = T2[x][y][1] - b1 * dtau * (T2[x][y][1] - T1[x][y][7]);
                    }
                }

                // Регулятор
                delta_H = am2;
                usilitel = Kp * delta_H;
                s = (s + (1 / Ti) * delta_H);
                dd = (delta_H - d) * Td;
                d = delta_H;

                regular = usilitel;  // + s + dd

                am2 = T2[Xs1][Ys1][5] - TK[Xs1][Ys1][5];

                // Сохранение точек для построения графика

                am2Points.add(am2);

            }


            for (x = 1; x < Nx - 1; x++) {
                for (y = 1; y < Ny - 1; y++) {
                    for (z = 1; z < Nz - 1; z++) {
                        Tek[x][y][z] = T2[x][y][z] - TK[x][y][z];
                    }
                }
            }

            pltlist.add(Tek[4][4][4]);

        }
        List<List<Double>> result = new ArrayList<>();
        result.add(pltlist);
        result.add(am2Points);
        //почистить от лишнего



        return result;
    }
}

