package com.example.calculadora;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.EmptyStackException;

public class Controlador {


    @FXML private TextField textField;
    private CalculadoraModelo modelo;

    public Controlador() {
        this.modelo = new CalculadoraModelo();
    }

    @FXML
    public void onAction(MouseEvent mouseEvent) {
        Button btn = ((Button) mouseEvent.getSource());
        String btnText = btn.getText();
        if (isNum(btn.getText())) {
            insertToTextField(btnText);
        } else if (btnText.equals("=")) {
            resolver(textField.getText());
        } else if (btnText.equals("AC")) {
            vaciar();
        } else {
            if (!textField.getText().isEmpty()) {
                borrarUltimo();
            }
        }
    }

    public boolean isNum(String valor) {
        return "0123456789x-+/.()".contains(valor);
    }

    public void insertToTextField(String valor) {

        textField.appendText(valor);
    }


    public void resolver(String expresion) {

        String m;
        try {
            textField.setText(modelo.calcular(expresion));
            return;
        } catch (NumberFormatException e) {
            m = "No se puede introducir dos puntos seguidos";
        } catch (IllegalArgumentException e) {
            if (!isValidExpression(expresion)) {
                m = "No se puede introducir dos simbolos incompatibles seguidos.";
            } else {
                m = "Numero invalido de operandos";
            }
        } catch (ArithmeticException e) {
            m = "No se puede dividir entre 0, es infinito.";
        } catch (EmptyStackException e) {
            m = "Faltan o sobran parentesis";
        }
        instanceVentanaError(m);


/*
        String m;
        try {
            textField.setText(modelo.calcular(expresion));
            return;
        } catch (Exception e) {
            m = e.getClass().getName() + " " + e.getMessage();
        }
        instanceVentanaError(m);

 */
    }

    public void instanceVentanaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public boolean isValidExpression(String expression) {
        return !expression.contains("x/") && !expression.contains("/x") && !expression.contains("+x") && !expression.contains("-x") && !expression.contains("-/") && !expression.contains("+/") && !expression.contains("//") && !expression.contains("xx") && !expression.contains("..");
    }

    public void vaciar(){
        textField.setText("");
    }

    public void borrarUltimo() {
        textField.setText(textField.getText().substring(0, textField.getText().length()-1));

    }

    /*
    public boolean cantParentesisPar(String expresion) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (c == '(') {
                a++;
            } else if (c == ')') {
                b++;
            }
        }
        return a == b;
    }
     */
}

