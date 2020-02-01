package chaos.settings;

import chaos.chaosController;
import chaos.util.chaosTimer;
import chaos.util.subjectGenerator;
import chaos.util.windowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import static java.lang.String.format;


public class chaosSettingsController extends windowController {

    // timer settings
    @FXML
    private TextField hoursField;
    @FXML
    private TextField minutesField;
    @FXML
    private TextField secondsField;

    // generator settings
    @FXML
    private TextField seedField;

    // other settings
    @FXML
    private CheckBox toggleAlwaysOnTop;

    // objects affected by settings
    private chaos.util.subjectGenerator subjectGenerator;
    private chaosTimer timer;
    private chaosController callingController;

    /**
     * Initialization method
     */
    @FXML
    private void initialize() {
        // only allow integers less than 24 or an empty string
        hoursField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") && (!newValue.matches("[\\d]{0,2}") || Integer.parseInt(newValue) >= 24)) {
                    hoursField.setText(oldValue);
                }
            }
        });

        // only allow integers less than 60 or an empty string
        minutesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("")
                        && (!newValue.matches("[\\d]{0,2}")
                        || Integer.parseInt(newValue) >= 60)) {
                    minutesField.setText(oldValue);
                }
            }
        });

        // only allow integers less than 60 or an empty string
        secondsField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("")
                        && (!newValue.matches("[\\d]{0,2}")
                        || Integer.parseInt(newValue) >= 60)) {
                    secondsField.setText(oldValue);
                }
            }
        });
    }

    /**
     * Sets the timer that will be altered with the settings
     * @param timer the timer object
     */
    public void setTimer(chaosTimer timer) {
        this.timer = timer;

        int hours = (int) timer.getHours();
        int minutes = (int) timer.getMinutes();
        int seconds = (int) timer.getSeconds();

        hoursField.setText(format("%02d", hours));
        minutesField.setText(format("%02d", minutes));
        secondsField.setText(format("%02d", seconds));
    }

    /**
     * Sets the subject generator that will be altered with the settings
     * @param subjectGenerator - the subject generator object
     */
    public void setSubjectGenerator(subjectGenerator subjectGenerator) {
        this.subjectGenerator = subjectGenerator;
    }

    /**
     * Sets the chaos controller object that called this settings dialog
     * @param callingController the chaos controller object that called this settings dialog
     */
    public void setChaosController(chaosController callingController) {
        this.callingController = callingController;
    }

    /**
     * Closes the settings dialog without applying settings
     */
    @FXML
    private void closeSettings() { super.getStage().hide(); }

    /**
     * Applys the settings in the settings dialog, then closes it
     */
    @FXML
    private void applySettings() {
        callingController.resetTimer();

        // apply timer settings
        double hours = 3600 * Integer.parseInt(hoursField.getText());
        double minutes = 60 * Integer.parseInt(minutesField.getText());
        double seconds = Integer.parseInt(secondsField.getText());

        timer.setTime(hours + minutes + seconds);

        // apply seed settings
        String seedString = seedField.getText();
        long seed = 0;
        for (int i = 0; i < seedString.length(); i++) {
            char ch = seedString.charAt(i);
            seed += ch;
        }
        subjectGenerator.setSeed(seed);

        // close settings dialog
        closeSettings();
    }

    /**
     * Increments the amount of hours in the settings field by 1
     */
    @FXML
    private void incrementHours() {
        int increment = Integer.parseInt(hoursField.getText()) + 1;

        if (increment == 24) {
            hoursField.setText("00");
        } else {
            hoursField.setText(format("%02d", increment));
        }
    }

    /**
     * Increments the amount of minutes in the settings field by 1
     */
    @FXML
    private void incrementMinutes() {
        int increment = Integer.parseInt(minutesField.getText()) + 1;
        if (increment == 60) {
            minutesField.setText("00");
        } else {
            minutesField.setText(format("%02d", increment));
        }
    }

    /**
     * Increments the amount of seconds in the settings field by 1
     */
    @FXML
    private void incrementSeconds(){
        int increment = Integer.parseInt(secondsField.getText()) + 1;

        if (increment == 60) {
            secondsField.setText("00");
        } else {
            secondsField.setText(format("%02d", increment));
        }
    }

    /**
     * Decrements the amount of hours in the settings field by 1
     */
    @FXML
    private void decrementHours() {
        int increment = Integer.parseInt(hoursField.getText()) - 1;

        if (increment < 0) {
            hoursField.setText("23");
        } else {
            hoursField.setText(format("%02d", increment));
        }
    }

    /**
     * Decrements the amount of minutes in the settings field by 1
     */
    @FXML
    private void decrementMinutes() {
        int increment = Integer.parseInt(minutesField.getText()) - 1;

        if (increment < 0) {
            minutesField.setText("59");
        } else {
            minutesField.setText(format("%02d", increment));
        }
    }

    /**
     * Decrements the amount of seconds in the settings field by 1
     */
    @FXML
    private void decrementSeconds() {
        int increment = Integer.parseInt(secondsField.getText()) - 1;

        if (increment < 0) {
            secondsField.setText("59");
        } else {
            secondsField.setText(format("%02d", increment));
        }
    }
}
