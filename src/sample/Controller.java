package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.math.BigDecimal;


public class Controller {

    @FXML
    private  Text output;
    @FXML
    private  Text operatorBox;
    @FXML
    private  GridPane gridPane;
    @FXML
    private  ToggleButton toggleButton;
    @FXML
    private  StackPane stackPane;
    @FXML
    private  Button ceButton;
    @FXML
    private  Rectangle operatorRec;

    private BigDecimal number1 = null;
    private BigDecimal number2 = null;
    static Equasion operator = Equasion.NULL;
    private static boolean turnedOn = true;
    private boolean beginNewNumber = false;
    static String value = "";
    private String previousValue = "";


    @FXML
    private void processNumPad(KeyEvent key) {
        /**
         * Collects key pressed by user and supplies it to function responsible for calculation logic
         */
        if (turnedOn) {
            respondToUserAction(key.getCode());
        }
    }

    @FXML
    private void processNumClick(ActionEvent event) {
        /**
         * Collects button pressed by user and supplies it to function responsible for calculation logic
         */
        String value = ((Button) event.getSource()).getText();
        respondToUserAction(Model.aquireKeyCode(value));
    }


    @FXML
    private void processOnOffButton() {
        /**
         * Turns off/on the calculator
         */
        if (toggleButton.isSelected()) {
            this.turnOnCalculator();
        } else {
            this.turnOffCalculator();
        }
    }

    private static void setFontSize(Text output) {
        /**
         * Changes the font-size depending on how many digits output has
         */
        output.setFont(new Font(21));
        if (output.getText().length() > 34) output.setFont(new Font(14));
        if (output.getText().length() > 75) output.setFont(new Font(11));
    }

    private void turnOnCalculator(){
        /**
         * Enables calculator's modules when turned on is pressed
         */
        gridPane.setDisable(false);
        output.setDisable(false);
        stackPane.setDisable(false);
        ceButton.setDisable(false);
        turnedOn = true;
        output.setOpacity(1);
        operatorRec.setOpacity(1);
        operatorBox.setOpacity(1);
    }

    private void turnOffCalculator(){
        /**
         * Disables calculator's modules when turned off is pressed
         */
        gridPane.setDisable(true);
        output.setDisable(true);
        stackPane.setDisable(true);
        ceButton.setDisable(true);
        turnedOn = false;
        output.setOpacity(0.5);
        operatorRec.setOpacity(0.5);
        operatorBox.setOpacity(0.5);
    }

    private boolean outputIsTooLong() {
        return this.output.getText().length() > 127;
    }

    private void setOperatorSettings(Equasion op) {
        /**
         * Manages operator-related variables
         */
        operator = op;
        value = "op";
        operatorBox.setText(op.symbol);
    }

    private void deleteData() {
        /**
         * Clears all variables when delete button is pressed
         */
        output.setText("");
        operatorBox.setText("");
        operator = Equasion.NULL;
        value = "";
        number1 = null;
        number2 = null;
    }

    private void backSpace() {
        /**
         * Sets output to new value that is one(last) character less than old value when the back space is pressed
         */
        output.setText((String) output.getText().subSequence(0, (output.getText().length() - 1)));
    }

    private void calculate() {
        /**
         * Sends data for calculation when it is necessary
         */
        number2 = BigDecimal.valueOf(Double.parseDouble(output.getText()));
        output.setText(String.valueOf(Model.calculate(number1, number2, operator)));
        number2 = null;
    }

    private void updateOutput() {
        /**
         * Concatenates value (users input) to the output and sets font size to smaller/bigger when necessary
         */
        output.setText(output.getText() + (value.equals("op") ? "" : value));
        setFontSize(output);
    }

    private void updateValue() {
        /**
         * Saves previous value for further evaluations and resets the value variable for further user inputs
         */
        previousValue = String.valueOf(value);
        value = "";
    }

    private void respondToUserAction(KeyCode key) {

        if (beginNewNumber && key.isDigitKey()) {
            output.setText("");
            beginNewNumber = false;
        }

        switch (key) {
            case NUMPAD0:
            case NUMPAD1:
            case NUMPAD2:
            case NUMPAD3:
            case NUMPAD4:
            case NUMPAD5:
            case NUMPAD6:
            case NUMPAD7:
            case NUMPAD8:
            case NUMPAD9:
                value = String.valueOf(key.getName().charAt(7));
                break;
            case COMMA:
            case PERIOD:
            case DECIMAL:
                value = ".";
                break;
            case DELETE:
                deleteData();
                break;
            case BACK_SPACE:
                if (!output.getText().isEmpty())
                    backSpace();
                break;
            case ADD:
                applyLogic(Equasion.ADD);
                break;
            case SUBTRACT:
                applyLogic(Equasion.SUBTRACT);
                break;
            case MULTIPLY:
                applyLogic(Equasion.MULTIPLY);
                break;
            case DIVIDE:
                applyLogic(Equasion.DIVIDE);
                break;
            case ENTER:
                if (number1 != null && operator != Equasion.NULL && !output.getText().equals("")) {
                    calculate();
                    operatorBox.setText("");
                    operator = Equasion.NULL;
                    beginNewNumber = true;
                }
        }
        if (value.equals(".") && output.getText().contains(".")) value = "";
        if (this.outputIsTooLong()) return;
        updateOutput();
        updateValue();
    }

    private void applyLogic(Equasion operation){
        /**
         * If nothing is present on the output then the function does nothing
         * If previous value was an operator then the function updates the operator and does nothing
         * If operator variable is not null and number1 variable is not null and 
         */
        if (output.getText().equals("")) return;
        if (previousValue.equals("op")) {
            setOperatorSettings(operation);
            return;
        }
        if (operator != Equasion.NULL &&number1 != null && !output.getText().equals("")) {
            calculate();
        }
        setOperatorSettings(operation);
        number1 = BigDecimal.valueOf(Double.parseDouble(output.getText()));
        beginNewNumber = true;
    }
}
