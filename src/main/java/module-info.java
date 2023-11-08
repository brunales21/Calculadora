module com.example.calculadora {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.example.calculadora to javafx.fxml;
    exports com.example.calculadora;
}