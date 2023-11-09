package com.example.calculadora;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Vista extends Application {
    @FXML
    private TextField textField = new TextField();

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Vista.class.getResource("Calculadora.fxml"));
        //Image icon = new Image(getClass().getResourceAsStream("/imgs/miniatura.png"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Calculadora");
        stage.setScene(scene);
        stage.show();
    }

    public TextField getTextField() {
        return textField;
    }

}





