package com.example.calculadora;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controlador {


    @FXML
    private TextField textField;
    private CalculadoraModelo modelo;
    @FXML
    private Button cierraParentesis;


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
            resolver(fixExpression(textField.getText()));
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

    public void enableButton(Button btn) {
        btn.setDisable(false);
    }

    public void disableButton(Button b) {
        b.setDisable(true);
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
        return String.valueOf(valor.charAt(valor.length() - 1));
    }

    public int ocurrenciasDe(String exp, String c) {
        int contador = 0;
        for (int i = 0; i < exp.length(); i++) {
            String elemento = String.valueOf(exp.charAt(i));
            if (elemento.equals(c)) {
                contador++;
            }
        }
        return contador;
    }


    public void insertToTextField(String input) {
        if (!textField.getText().isEmpty() && isOperador(input) && isOperador(getLastPos(textField.getText()))) {
            textField.setText(textField.getText().substring(0, textField.getText().length() - 1).concat(input));
            return;
        }

        textField.appendText(input);
        Button cierraParentesisBtn = (Button) this.cierraParentesis.getScene().lookup("#cierraParentesis");

        if (input.equals(")") && ocurrenciasDe(textField.getText(), ")") == ocurrenciasDe(textField.getText(), "(")) {
            disableButton(cierraParentesisBtn);
        } else if (input.equals("(") && ocurrenciasDe(textField.getText(), "(") > ocurrenciasDe(textField.getText(), ")")) {
            enableButton(cierraParentesisBtn);
        }
    }


    public String fixExpression(String expresion) {
        if (getCantParentesisRestantes(expresion) != 0) {
            expresion = fillWithParentesis(expresion);
        }
        return expresion;
    }

    public void resolver(String expresion) {
        String m;
        try {
            String resultado = modelo.calcular(expresion);
            String decimalPart = resultado.substring(resultado.indexOf(".") + 1);
            if (isEntero(decimalPart)) {
                textField.setText(getParteEntera(resultado));
            } else {
                textField.setText(resultado);
            }
            return;
        } catch (ArithmeticException e) {
            m = "No se puede dividir entre 0, es infinito.";
        } catch (Exception e) {
            m = "Esta expresion no tiene sentido.\nRevisa el numero de operandos.";
        }
        instanceVentanaError(m);
    }

    public int getCantParentesisRestantes(String expresion) {
        return ocurrenciasDe(expresion, "(") - ocurrenciasDe(expresion, ")");
    }

    public boolean endsWith(String expression) {
        return "(/-+x.".indexOf(expression.charAt(expression.length() - 1)) != -1;
    }

    public String fillWithParentesis(String expresion) {
        if (endsWith(expresion)) {
            expresion = expresion.concat("0");
        }
        int cant = getCantParentesisRestantes(expresion);
        for (int i = 0; i < cant; i++) {
            expresion = expresion.concat(")");
        }
        return expresion;
    }

    public void instanceVentanaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void vaciar() {
        textField.setText("");
    }

    public void borrarUltimo() {
        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
    }

    public boolean isEntero(String decPart) {
        for (int i = 0; i < decPart.length(); i++) {
            if (decPart.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    public String getParteEntera(String num) {
        return num.substring(0, num.indexOf("."));
    }

}

