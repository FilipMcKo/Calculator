package sample;

import javafx.scene.input.KeyCode;

import java.math.BigDecimal;

public class Model {


    public static BigDecimal calculate(BigDecimal nr1, BigDecimal nr2, Equasion op) {
        switch (op) {
            case ADD:
                return nr1.add(nr2);
            case SUBTRACT:
                return nr1.subtract(nr2);
            case MULTIPLY:
                return nr1.multiply(nr2);
            case DIVIDE:
                if (nr2.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
                return nr1.divide(nr2, 7, BigDecimal.ROUND_HALF_UP);
            default:
                System.out.println("Unknown operator - " + op);
        }
        return BigDecimal.ZERO;
    }

    public static KeyCode aquireKeyCode(String value) {
        switch (value) {
            case "+":
                return KeyCode.ADD;
            case "-":
                return KeyCode.SUBTRACT;
            case "/":
                return KeyCode.DIVIDE;
            case "X":
                return KeyCode.MULTIPLY;
            case "=":
                return KeyCode.ENTER;
            case "CE":
                return KeyCode.DELETE;
            case "1":
                return KeyCode.NUMPAD1;
            case "2":
                return KeyCode.NUMPAD2;
            case "3":
                return KeyCode.NUMPAD3;
            case "4":
                return KeyCode.NUMPAD4;
            case "5":
                return KeyCode.NUMPAD5;
            case "6":
                return KeyCode.NUMPAD6;
            case "7":
                return KeyCode.NUMPAD7;
            case "8":
                return KeyCode.NUMPAD8;
            case "9":
                return KeyCode.NUMPAD9;
            case "0":
                return KeyCode.NUMPAD0;
            case ",":
            case ".":
                return KeyCode.COMMA;
        }
        return null;
    }
}
