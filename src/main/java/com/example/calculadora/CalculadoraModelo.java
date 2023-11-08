package com.example.calculadora;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class CalculadoraModelo {
    private String mensajeError;
    public String calcular(String expression) {
        try {
            expression = expression.replace("x", "*");
            // Crear un objeto ExpressionBuilder para construir la expresión
            ExpressionBuilder builder = new ExpressionBuilder(expression);

            // Crear un objeto Expression
            Expression expr = builder.build();

            double resultado = 0;
            // Evaluar la expresión y obtener el resultado
            resultado = expr.evaluate();
            // Manejar excepciones, por ejemplo, división por cero
            return String.valueOf(resultado);
        } catch (Exception e) {
            throw e;
        }

    }

    public String getMensajeError() {
        return mensajeError;
    }
}
