package com.example.graph_num_2;

import fromPershinThird.ServiceTwo;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Canvas canvas;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private VBox container;

    private List<Double> am1Points;
    private List<Double> am2Points;

    public void initialize() {
        // Генерируйте массивы точек в ServiceTwo и передавайте их контроллеру
        ServiceTwo.main(new String[0]);
        am1Points = ServiceTwo.getAm1Points();
        am2Points = ServiceTwo.getAm2Points();

        // Определите оси X и Y для графика
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        // Создайте экземпляр LineChart
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("График");

        // Создайте серии данных для am1 и am2
        XYChart.Series<Number, Number> am1Series = new XYChart.Series<>();
        XYChart.Series<Number, Number> am2Series = new XYChart.Series<>();

        for (int i = 0; i < am1Points.size(); i++) {
            am1Series.getData().add(new XYChart.Data<>(i, am1Points.get(i)));
            am2Series.getData().add(new XYChart.Data<>(i, am2Points.get(i)));
        }

        am1Series.setName("am1");
        am2Series.setName("am2");

        // Добавьте серии данных к графику
        lineChart.getData().addAll(am1Series, am2Series);

        // Добавьте график на холст
        container.getChildren().add(lineChart);
    }

    public void setAm1Points(List<Double> am1Points) {
        this.am1Points = am1Points;
    }

    public void setAm2Points(List<Double> am2Points) {
        this.am2Points = am2Points;
    }
}
