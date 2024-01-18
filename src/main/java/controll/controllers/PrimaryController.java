package controll.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PrimaryController {

    /**
     * The Logger instance for the PrimaryController Class.
     */
    private static final Logger LOG = LogManager
            .getLogger(PrimaryController.class);

    /**
     * A button reference to load the FXML file of the Game
     * window of the application.
     */
    @FXML
    private Button newGameButton;

    /**
     * A button reference to load the FXML file of the
     * Statistics window of the application.
     */
    @FXML
    private Button statisticsButton;

    @FXML
    private void initialize() {
        newGameButton.setOnAction(event -> newGame());
        statisticsButton.setOnAction(event -> statistics());
    }

    @FXML
    private void newGame() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/GUI/GameWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            LOG.error("An error occurred while opening the new game window.",
                    e);
        }
    }
    @FXML
    private void statistics() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/GUI/StatisticsWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            LOG.error("An error occurred while opening the statistics window.",
                    e);
        }
    }
}
