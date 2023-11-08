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

        if (isPartOfExpression(btn.getText())) {
            insertToTextField(btnText);
        } else if (presionoResolver(btnText)) {
            resolver(textField.getText());
        } else if (presionoLimpiar(btnText)) {
            vaciar();
        } else {
            //borrar ultima posicion
            if (!textField.getText().isEmpty()) {
                //entra solo si hay algo que borrar
                borrarUltimo();
            }
        }
    }

    public boolean presionoResolver(String btnText) {
        return btnText.equals("=");
    }

    public boolean presionoLimpiar(String btnText) {
        return btnText.equals("AC");
    }

    public boolean isPartOfExpression(String valor) {
        return "0123456789x-+/.()".contains(valor);
    }

    public boolean isOperador(String valor) {
        return "+-/.x".contains(valor);
    }
    public String getLastPos(String valor) {
        return String.valueOf(valor.charAt(valor.length()-1));
    }

    public void insertToTextField(String input) {
        if (isOperador(input) && isOperador(getLastPos(textField.getText()))) {
            textField.setText(textField.getText().substring(0, textField.getText().length()-1).concat(input));
            return;
        }
        textField.appendText(input);
    }



    public void resolver(String expresion) {
        String m;
        try {
            textField.setText(modelo.calcular(expresion));
            return;
        } catch (ArithmeticException e) {
            m = "No se puede dividir entre 0, es infinito.";
        } catch (EmptyStackException | IllegalArgumentException e) {
            m = "Faltan o sobran parentesis";
        }
        instanceVentanaError(m);
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

