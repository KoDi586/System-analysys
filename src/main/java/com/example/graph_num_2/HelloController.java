package com.example.graph_num_2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Canvas canvas;

    public void initialize() {
        // Вызывается при инициализации контроллера
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawCoordinatePlane(gc);
    }

    private void drawCoordinatePlane(GraphicsContext gc) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // Рисуем ось X
        gc.strokeLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2);

        // Рисуем ось Y
        gc.strokeLine(canvasWidth / 2, 0, canvasWidth / 2, canvasHeight);

        // Добавляем метки на осях
        for (int i = -10; i <= 10; i++) {
            double x = canvasWidth / 2 + i * 20;
            double y = canvasHeight / 2;

            gc.strokeText(Integer.toString(i), x - 5, y + 15);
//            gc.strokeText(Integer.toString(-i), x - 5, y - 5); // Добавляем метки для вертикальной оси
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
