package com.example.graph_num_2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Canvas canvas;

    public void initialize() {
        // Вызывается при инициализации контроллера
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawPoints(gc);
    }

    private void drawPoints(GraphicsContext gc) {
        // Здесь вы можете рисовать точки на холсте
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillOval(50, 50, 5, 5); // Пример рисования красной точки в координатах (50, 50)
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
