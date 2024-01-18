import controll.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UnitTest extends ApplicationTest {
    private static final Logger LOG = LogManager
            .getLogger(UnitTest.class);
    private GameController gameController;
    private GridPane board;
    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/GUI/GameWindow.fxml"));
            Parent root = fxmlLoader.load();
            gameController = fxmlLoader.getController();
            board = gameController.getBoard();
            Stage scene = new Stage();
            scene.setScene(new Scene(root));
            scene.show();

        } catch (IOException e) {
            LOG.error("An error occurred while opening the test game window.",
                    e);
        }
    }

    @Test
    @DisplayName("Testing createSquare method")
    public void testCreateSquare() {
        StackPane square = gameController.createSquare();
        Circle piece = (Circle) square.getChildren().get(0);

        assertEquals(Circle.class, piece.getClass());
    }

    @Test
    @DisplayName("Testing selectBlue method")
    public void testSelectBlue() {
        Circle[][] discMatrix = gameController.discMatrix;
        discMatrix[0][0].setFill(Color.BLUE);
        gameController.setSwitchPlayer(true);
        gameController.setSelectDisc(true);
        gameController.setSelectGrid(false);

        assertTrue(gameController.selectBlue(discMatrix[0][0]));

        discMatrix[0][1].setFill(Color.RED);
        assertFalse(gameController.selectBlue(discMatrix[0][1]));

        gameController.setSwitchPlayer(false);
        assertFalse(gameController.selectBlue(discMatrix[0][0]));

        gameController.setSelectGrid(true);
        assertFalse(gameController.selectBlue(discMatrix[0][0]));

        gameController.setSelectGrid(false);
        gameController.setSelectDisc(false);
        assertFalse(gameController.selectBlue(discMatrix[0][0]));
    }

    @Test
    @DisplayName("Testing selectRed method")
    public void testSelectRed() {
        Circle[][] discMatrix = gameController.discMatrix;
        discMatrix[0][0].setFill(Color.RED);
        gameController.setSwitchPlayer(false);
        gameController.setSelectDisc(true);
        gameController.setSelectGrid(false);

        assertTrue(gameController.selectRed(discMatrix[0][0]));

        discMatrix[0][1].setFill(Color.BLUE);
        assertFalse(gameController.selectRed(discMatrix[0][1]));

        gameController.setSwitchPlayer(true);
        assertFalse(gameController.selectRed(discMatrix[0][0]));

        gameController.setSelectGrid(true);
        assertFalse(gameController.selectBlue(discMatrix[0][0]));

        gameController.setSelectDisc(false);
        gameController.setSelectDisc(false);
        assertFalse(gameController.selectBlue(discMatrix[0][0]));
    }

    @Test
    public void testCanBlueMoveHere() {
        Circle clickedDisc = new Circle();
        clickedDisc.setFill(Color.TRANSPARENT);
        int row = 0;
        int col = 0;
        gameController.discMatrix[row][col] = new Circle();
        gameController.discMatrix[row][col].setFill(Color.RED);
        gameController.setSelectDisc(false);
        gameController.setSelectGrid(true);
        gameController.setSwitchPlayer(true);

        boolean result = gameController.canBlueMoveHere(clickedDisc, row, col);

        assertTrue(result);
    }

    @Test
    public void testCanRedMoveHere() {
        Circle clickedDisc = new Circle();
        clickedDisc.setFill(Color.TRANSPARENT);
        int row = 0;
        int col = 0;
        gameController.discMatrix[row][col] = new Circle();
        gameController.discMatrix[row][col].setFill(Color.BLUE);
        gameController.setSelectDisc(false);
        gameController.setSelectGrid(true);
        gameController.setSwitchPlayer(false);

        boolean result = gameController.canRedMoveHere(clickedDisc, row, col);

        assertTrue(result);
    }

    @Test
    public void testSelectDisk() {
        StackPane square = new StackPane();
        GridPane.setRowIndex(square, 1);
        GridPane.setColumnIndex(square, 2);

        gameController.selectDisk(square);

        assertEquals(1, gameController.getSelectedRow());
        assertEquals(2, gameController.getSelectedCol());
    }
    @Test
    public void testMoveBlue() {
        gameController.setSwitchPlayer(true);

        clickOn(gameController.discMatrix[5][0]);
        clickOn(gameController.discMatrix[4][0]);

        assertEquals(Color.BLUE, gameController.discMatrix[4][0].getFill());
        assertEquals(Color.TRANSPARENT, gameController.discMatrix[5][0].getFill());
        assertFalse(gameController.getSwitchPlayer());
    }

    @Test
    public void testMoveRed() {
        gameController.setSwitchPlayer(false);

        clickOn(gameController.discMatrix[0][0]);
        clickOn(gameController.discMatrix[1][0]);

        assertEquals(Color.RED, gameController.discMatrix[1][0].getFill());
        assertEquals(Color.TRANSPARENT, gameController.discMatrix[0][0].getFill());
        assertTrue(gameController.getSwitchPlayer());
    }

}