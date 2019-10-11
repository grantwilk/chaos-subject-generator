package chaos;

import chaos.settings.chaosSettingsController;
import chaos.util.chaosTimer;
import chaos.util.subjectGenerator;
import chaos.util.windowController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.*;

public class chaosController extends windowController {

    // FXML elements
    @FXML
    private Label labelTimer;
    @FXML
    private Button buttonStartStop;
    @FXML
    private Label labelSubject;

    // subject generator variables
    private chaos.util.subjectGenerator subjectGenerator = new subjectGenerator();
    private Stack<String> subjectStack = new Stack<>();
    private static final int MAX_SUBJECT_STACK_SIZE = 20;

    // timer variables
    private chaosTimer timer;

    /**
     * FXML initialization
     * Loads adjective and noun lists, generates a subject, and binds the timer label to the timer's
     * text property
     */
    @FXML
    private void initialize() {
        timer = new chaosTimer(600, labelTimer);
        generateSubject();

        labelTimer.textProperty().bind(timer.getTimeText());
    }

    /**
     * Exits the application
     */
    @FXML
    private void exitApplication(){
        Platform.exit();
    }

    /**
     * Shows the settings window
     */
    @FXML
    private void showSettings() {
        try {
            final int SETTINGS_WINDOW_WIDTH = 300; // px
            final int SETTINGS_WINDOW_HEIGHT= 500; // px

            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings/chaosSettingsUI.fxml"));
            Parent root = loader.load();

            Stage settingsStage = new Stage();
            Scene settingsScene = new Scene(root, SETTINGS_WINDOW_WIDTH, SETTINGS_WINDOW_HEIGHT);
            settingsScene.setFill(Color.TRANSPARENT);

            // configure scene
            settingsScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Montserrat:500,700&display=swap");

            // configure stage
            settingsStage.setScene(settingsScene);
            settingsStage.setTitle("Chaos Settings");
            settingsStage.setResizable(false);
            settingsStage.setAlwaysOnTop(true);
            settingsStage.initStyle(StageStyle.UNDECORATED);
            settingsStage.initStyle(StageStyle.TRANSPARENT);
            settingsStage.getIcons()
                    .add(new Image(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResourceAsStream("icon/chaos_logo_1024px.png"))));

            // configure controller
            chaosSettingsController controller = loader.getController();
            controller.setStage(settingsStage);
            controller.setScene(settingsScene);
            controller.setTimer(timer);
            controller.setSubjectGenerator(subjectGenerator);
            controller.setChaosController(this);

            settingsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the timer and changes the start-stop button to use its stop appearance
     * @param actionEvent - the action event
     */
    @FXML
    public void startTimer(ActionEvent actionEvent) {
        timer.start();

        buttonStartStop.setText("STOP");
        buttonStartStop.getStyleClass().remove("btn-seascape");
        buttonStartStop.getStyleClass().add("btn-flamingo");
        buttonStartStop.setOnAction(this::stopTimer);
    }

    /**
     * Stops the timer and changes the start-stop button to use its start appearance
     * @param actionEvent - the action event
     */
    @FXML
    public void stopTimer(ActionEvent actionEvent) {
        timer.stop();

        buttonStartStop.setText("START");
        buttonStartStop.getStyleClass().remove("btn-flamingo");
        buttonStartStop.getStyleClass().add("btn-seascape");
        buttonStartStop.setOnAction(this::startTimer);
    }

    /**
     * Stops the timer and sets it back to its original value
     */
    @FXML
    public void resetTimer() {
        stopTimer(null);
        timer.reset();
    }

    /**
     * Generates a adjective noun combo and displays it on the subject labels
     */
    @FXML
    private void generateSubject() {

        // if stack is at max size, remove the bottom most element
        if (subjectStack.size() >= MAX_SUBJECT_STACK_SIZE) {
            subjectStack.remove(0);
        }

        // push existing text onto stack
        subjectStack.push(labelSubject.getText());

        // generate new text
        String subject = subjectGenerator.generateRandom();

        // update label font size
        updateSubjectLabelFontSize(subject);

        // display text on subject labels
        labelSubject.setText(subject);

    }

    /**
     * Reverts the subject back to its previous value
     */
    @FXML
    private void revertSubject() {
        if (subjectStack.size() > 1) {
            String subject = subjectStack.pop();
            updateSubjectLabelFontSize(subject);
            labelSubject.setText(subject);
        }
    }

    private void updateSubjectLabelFontSize(String subject) {
        if (subject.length() < 10) {
            labelSubject.setStyle("-fx-font-size: 24px");
        } else if (subject.length() < 14) {
            labelSubject.setStyle("-fx-font-size: 22px;");
        } else if (subject.length() < 20) {
            labelSubject.setStyle("-fx-font-size: 18px");
        } else if (subject.length() < 30) {
            labelSubject.setStyle("-fx-font-size: 16px");
        } else {
            labelSubject.setStyle("-fx-font-size: 14px;");
        }
    }


}
