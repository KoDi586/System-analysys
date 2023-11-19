package com.example.graph_num_2;

import workingServices.ServiceWithoutR;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML
    private LineChart<Number, Number> lineChart1;
    @FXML
    private LineChart<Number, Number> lineChart2;
    @FXML
    private NumberAxis xAxis1;
    @FXML
    private NumberAxis yAxis1;
    @FXML
    private NumberAxis xAxis2;
    @FXML
    private NumberAxis yAxis2;
    @FXML
    private VBox container;

    public List<List<Double>> doubless;

    public void initialize() {
        // Генерируйте массивы точек в ServiceTwo и передавайте их контроллеру
//        List<List<Double>> doubless;
//        try {
//            doubless = ServiceFromGPT.points();       // график
//        } catch (IOException e) {                     //без регулятора
//            throw new RuntimeException(e);
//        }
//
//        List<Double> am1Points = doubless.get(0);
//        List<Double> am2Points = doubless.get(1);   //без регулятора


        List<List<Double>> doubless;
        try {
            doubless = ServiceWithoutR.points();       //график
        } catch (IOException e) {                 //с регулятором
            throw new RuntimeException(e);
        }
        List<Double> am1Points = doubless.get(0);
        List<Double> am2Points = doubless.get(0);   //с регулятором



        // Определите оси X и Y для графиков
        xAxis1 = new NumberAxis();
        yAxis1 = new NumberAxis();

        xAxis2 = new NumberAxis();
        yAxis2 = new NumberAxis();

        // Создайте экземпляры LineChart для каждой плоскости
        lineChart1 = new LineChart<>(xAxis1, yAxis1);
        lineChart1.setTitle("График am1");

        lineChart2 = new LineChart<>(xAxis2, yAxis2);
        lineChart2.setTitle("График am2");

        // Создайте серии данных для am1 и am2
        XYChart.Series<Number, Number> am1Series = new XYChart.Series<>();
        XYChart.Series<Number, Number> am2Series = new XYChart.Series<>();

        for (int i = 0; i < am1Points.size(); i++) {
            am1Series.getData().add(new XYChart.Data<>(i, am1Points.get(i)));
            am2Series.getData().add(new XYChart.Data<>(i, am2Points.get(i)));
        }

        am1Series.setName("am1");
        am2Series.setName("am2");

        // Добавьте серии данных к графикам
        lineChart1.getData().addAll(am1Series);
        lineChart2.getData().addAll(am2Series);

        // Добавьте графики на холст
        container.getChildren().add(lineChart1);
        container.getChildren().add(lineChart2);
    }
}