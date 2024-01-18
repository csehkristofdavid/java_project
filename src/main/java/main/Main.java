package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * The width of the primary window of the application.
     */
    private final int windowWidth = 700;

    /**
     * The height of the primary window of the application.
     */
    private final int windowHeight = 450;

    /**
     * The start function is called when the JavaFX application
     * is launched. It loads the FXML file of the primary window
     * of the application.
     *
     * @param primaryStage the primary stage of the JavaFX application
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/GUI/PrimaryWindow.fxml"));
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.show();
    }

    /**
     * The main function is the entry point of the Java application.
     * It launches the JavaFX application by calling the launch
     * function from the Application class.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
