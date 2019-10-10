package chaos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class chaosMain extends Application {

    private final static int WINDOW_WIDTH = 620; // px
    private final static int WINDOW_HEIGHT = 100; // px

    @Override
    public void start(Stage primaryStage) throws Exception {

        // load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chaosUI.fxml"));
        Parent root = loader.load();

        // setTime basic stage attributes
        primaryStage.setTitle("Chaos Modeling Challenge");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Montserrat:500,700&display=swap");
        primaryStage.setScene(scene);

        // setTime stage attribute in the controller
        chaosController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setScene(scene);

        // lock stage size and make it float on top
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);

        primaryStage.getIcons()
                .add(new Image(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResourceAsStream("icon/chaos_logo_1024px.png"))));

        // remove the stage title bar and background
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
