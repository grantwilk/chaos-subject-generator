package chaos.util;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class windowController {

    // window control
    private Stage stage;
    private Scene scene;
    private Delta dragDelta = new Delta();

    /**
     * Sets the stage associated with the controller
     * @param stage - the stage associated with the controller
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the scene associated with the controller
     * @param scene - the scene associated with the controller
     */
    public void setScene(Scene scene) { this.scene = scene;}

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Changes the cursor to the hand cursor
     * @param event - the mouse event
     */
    @FXML
    private void cursorToHand(MouseEvent event) {
        scene.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor to the default cursor
     * @param event - the mouse event
     */
    @FXML
    private void cursorToDefault(MouseEvent event) {
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Sets the original position of the mouse when a drag occurs
     * @param event - the mouse event
     */
    @FXML
    private void dragSetOrigin(MouseEvent event) {
        dragDelta.x = stage.getX() - event.getScreenX();
        dragDelta.y = stage.getY() - event.getScreenY();
    }

    /**
     * Moves the window from its original position to the terminating point of the mouse drag
     * @param event - the mouse event
     */
    @FXML
    private void dragMoveStage(MouseEvent event) {
        stage.setX(event.getScreenX() + dragDelta.x);
        stage.setY(event.getScreenY() + dragDelta.y);
    }

    /**
     * Drag delta for window drag updates
     */
    private class Delta {
        private double x, y;
    }
}
