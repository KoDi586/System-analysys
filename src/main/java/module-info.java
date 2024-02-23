module com.example.graph_num_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graph_num_2 to javafx.fxml;
    exports com.example.graph_num_2;
}