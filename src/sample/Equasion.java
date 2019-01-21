package sample;

public enum Equasion {
    ADD("+"), SUBTRACT("-"), MULTIPLY("X"), DIVIDE("/"), NULL("");

    public String symbol;

    Equasion(String symbol) {
        this.symbol = symbol;
    }

    public static Equasion getOperator(String value) {
        switch (value) {
            case "+":
                return Equasion.ADD;
            case "-":
                return Equasion.SUBTRACT;
            case "X":
                return Equasion.MULTIPLY;
            case "/":
                return Equasion.DIVIDE;
        }
         return Equasion.NULL;

    }
}
