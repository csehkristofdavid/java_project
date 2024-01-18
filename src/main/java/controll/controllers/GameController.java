package controll.controllers;

import datahandler.JsonWriter;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameController {

    /**
     * The Logger instance for the GameController Class.
     */
    private static final Logger LOG = LogManager
            .getLogger(GameController.class);

    /**
     * GridPane reference to the grid in the game window.
     */
    @FXML
    private GridPane board;

    /**
     * Label reference to make visual whose turn is going.
     */
    @FXML
    private Label currentPlayer;

    /**
     * Label reference to make visual who won the game.
     */
    @FXML
    private Label gameOver;

    /**
     * Boolean reference if it is true, the is currently,
     * at the disk selecting phase.
     */
    private boolean selectDisc;

    /**
     * Boolean reference if it is true, the is currently,
     * at the move selecting phase.
     */
    private boolean selectGrid;

    /**
     * Integer reference to the grid's width.
     */
    private final int width = 6;

    /**
     * Integer reference to the grid's height.
     */
    private final int height = 7;

    /**
     * A 2D array, or matrix. It contains the disks
     * with their position.
     */
    public Circle[][] discMatrix;

    /**
     * It contains the row index of the selected
     * disk to move.
     */
    private int selectedRow = 0;

    /**
     * It contains the column index of the selected
     * disk to move.
     */
    private int  selectedCol = 0;

    /**
     * Decide which player is currently playing. if true,
     * it is the blue's turn if false, it's red's turn.
     */
    private boolean switchPlayer = true;

    /**
     * It contains how many disk does the blue player have.
     */
    private int counterBlue = 0;

    /**
     * It contains how many disk does the red player have.
     */
    private int counterRed = 0;

    /**
     * It contains how many moves have been since the beginning.
     */
    private int turnCounter = 0;

    /**
     * A String that contains the color of the winner player.
     */
    private String winner;

    /**
     * The row index of the left black box.
     */
    private final int leftBlackBoxRow = 3;
    /**
     * The column index of the left black box.
     */
    private final int leftBlackBoxCol = 2;
    /**
     * The row index of the right black box.
     */
    private final int rightBlackBoxRow = 2;
    /**
     * The column index of the right black box.
     */
    private final int rightBlackBoxCol = 4;
    /**
     * The width of the black boxes.
     */
    private final int blackBoxWidth = 70;
    /**
     * The height of the black boxes.
     */
    private final int blackBoxHeight = 80;
    /**
     * The radius of the disks.
     */
    private final int circleRadius = 25;

    /**
     * This method initializes the game board and sets up the disc matrix.
     * It is called automatically when the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        selectDisc = true;
        selectGrid = false;
        discMatrix = new Circle[width][height];
        boardInitialize();
        turnCounter = 0;
        LOG.info("Game initialization is successful.");

    }

    /**
     * This method initializes the game board UI and
     * sets up the disc matrix.
     */
    private void boardInitialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
                discMatrix[i][j] = (Circle) square.getChildren().get(0);
                if (i == 0) {
                    var coin = (Circle) square.getChildren().get(0);
                    coin.setFill(Color.RED);
                }
                if (i == board.getRowCount() - 1) {
                    var coin = (Circle) square.getChildren().get(0);
                    coin.setFill(Color.BLUE);
                }
                if ((i == leftBlackBoxRow && j == leftBlackBoxCol)
                        || (i == rightBlackBoxRow && j == rightBlackBoxCol)) {
                    var coin = (Circle) square.getChildren().get(0);
                    coin.setFill(Color.BLACK);
                    var blackSquare = new Rectangle(blackBoxWidth,
                            blackBoxHeight);
                    blackSquare.setFill(Color.BLACK);
                    square.getChildren().removeAll();
                    square.getChildren().add(blackSquare);
                }
            }
        }
    }

    /**
     * This method creates a square and sets it up for the game board UI.
     * @return Returns the StackPane object which is a UI element.
     */
    public StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(circleRadius);
        piece.setFill(Color.TRANSPARENT);
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    /**
     * Handle mouse click events on a StackPane object. Determines
     * the location of the clicked disk and if the clicked disk
     * belongs to the current player, with the next click check if
     * they can move that disk there. If can it moves the disk on
     * the board.
     * @param event representing the click event on a StackPane
     *             object
     */
    @FXML
    private void handleMouseClick(final MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var clickedDisc = discMatrix[row][col];
        if (selectBlue(clickedDisc)) {
            selectDisk(square);
        }
        if (selectRed(clickedDisc)) {
            selectDisk(square);
        }
        if (canBlueMoveHere(clickedDisc, row, col)) {
            turnCounter++;
            moveBlue(row, col);
            canMoveRed();
        }
        if (canRedMoveHere(clickedDisc, row, col)) {
            moveRed(row, col);
            canMoveBlue();
        }
    }

    /**
     * Determines if a blue disc is selected.
     * @param clickedDisc The disk that is clicked
     * @return if a blue disc is selected and the
     *         conditions are met, false otherwise
     */
    public boolean selectBlue(final Circle clickedDisc) {
        if (clickedDisc.getFill() == Color.BLUE
                && selectDisc
                && !selectGrid
                && switchPlayer) {
            LOG.info("Blue is selected.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if a red disc is selected.
     * @param clickedDisc The disk that is clicked
     * @return if a red disc is selected and the
     *         conditions are met, false otherwise
     */
    public boolean selectRed(final Circle clickedDisc) {
        if (clickedDisc.getFill() == Color.RED
                && selectDisc
                && !selectGrid
                && !switchPlayer) {
            LOG.info("Red is selected.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines whether the blue player can move a disc
     * to the specified location.
     * @param clickedDisc The circle representing the
     *                    clicked disc.
     * @param row The row of the clicked disc
     * @param col The column of the clicked disk
     * @return true if the blue player can move a disc to
     *         the specified location, false otherwise.
     */
    public boolean canBlueMoveHere(final Circle clickedDisc,
                                    final int row, final int col) {
        if ((clickedDisc.getFill() == Color.TRANSPARENT
                || discMatrix[row][col].getFill() == Color.RED)
                && !selectDisc
                && selectGrid
                && switchPlayer) {
            LOG.info("Blue can move there.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines whether the red player can move a disc
     * to the specified location.
     * @param clickedDisc The circle representing the
     *                    clicked disc.
     * @param row The row of the clicked disc
     * @param col The column of the clicked disk
     * @return true if the red player can move a disc to
     *         the specified location, false otherwise.
     */
    public boolean canRedMoveHere(final Circle clickedDisc,
                                   final int row, final int col) {
        if ((clickedDisc.getFill() == Color.TRANSPARENT
                || discMatrix[row][col].getFill() == Color.BLUE)
                && !selectDisc
                && selectGrid
                && !switchPlayer) {
            LOG.info("Red can move there.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Selects the disk in the clicked square and sets the
     * variables that represents the current game state.
     * @param square the StackPane of the clicked square on
     *               the game board.
     */
    public void selectDisk(final StackPane square) {
        selectedRow = GridPane.getRowIndex(square);
        selectedCol = GridPane.getColumnIndex(square);
        selectDisc = false;
        selectGrid = true;
        LOG.info("Selection is completed.");
    }

    /**
     * Handles the move of a blue disc to a given position.
     *
     * @param row The row to move the disc to.
     * @param col The column to move the disc to.
     */
    public void moveBlue(final int row, final int col) {
        if (checkValidMoveBlue(row, col)) {
            discMatrix[row][col].setFill(Color.BLUE);
            discMatrix[selectedRow][selectedCol].setFill((Color.TRANSPARENT));
            selectDisc = true;
            selectGrid = false;
            LOG.info("Blue's disk is moved.");
        }
        switchPlayer = false;
        currentPlayer.setText("Red's turn");
        LOG.info("Switch turn taker .");
    }

    /**
     * Checks if the blue's move valid.
     * @param row the row index to move to
     * @param col the column index to move to
     * @return true if the blue player can move a disc to
     *         the specified location, false otherwise.
     */
    public boolean checkValidMoveBlue(final int row, final int col) {
        if (selectedRow - row == 1 && (Math.abs(selectedCol - col) == 1
                || selectedCol - col == 0)) {
            LOG.info("Blue's move is valid.");
            return true;
        } else {
            LOG.info("Blue's move is invalid.");
            return false;
        }
    }

    /**
     * Handles the move of a red disc to a given position.
     *
     * @param row The row to move the disc to.
     * @param col The column to move the disc to.
     */
    private void moveRed(final int row, final int col) {
        if (checkValidMoveRed(row, col)) {
            discMatrix[row][col].setFill(Color.RED);
            discMatrix[selectedRow][selectedCol].setFill((Color.TRANSPARENT));
            selectDisc = true;
            selectGrid = false;
            LOG.info("Red's disk is moved.");
        }
        switchPlayer = true;
        currentPlayer.setText("Blue's turn");
        LOG.info("Switch turn taker player.");
    }

    /**
     * Checks if the red's move valid.
     * @param row the row index to move to
     * @param col the column index to move to
     * @return true if the red player can move a disc to
     *         the specified location, false otherwise.
     */
    private boolean checkValidMoveRed(final int row, final int col) {
        if (selectedRow - row == -1 && (Math.abs(selectedCol - col) == 1
                || selectedCol - col == 0)) {
            LOG.info("Red's move is valid.");
            return true;
        } else {
            LOG.info("Red's move is invalid.");
            return false;
        }
    }

    /**
     * Count the disks owned by the blue player, and if he doesn't
     * have anymore it calls the gameOver function.
     */
    private void canMoveBlue() {
        counterBlue = 0;
        for (var i = 1; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (discMatrix[i][j].getFill() == Color.BLUE) {
                    counterBlue++;
                }
            }
        }
        LOG.info("Validate if Blue can move anywhere.");
        if (counterBlue == 0) {
            gameOver();
            LOG.info("Blue don't have any other possible move.");
        }
    }

    /**
     * Count the disks owned by the red player, and if he doesn't
     * have anymore it calls the gameOver function.
     */
    private void canMoveRed() {
        counterRed = 0;
        for (var i = 0; i < board.getRowCount() - 1; i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (discMatrix[i][j].getFill() == Color.RED) {
                    counterRed++;
                }
            }
        }
        LOG.info("Validate if Blue can move anywhere.");
        if (counterRed == 0) {
            gameOver();
            LOG.info("Red don't have any other possible move.");
        }
    }

    /**
     * Stops the game, and write the winners data to the data.json file.
     */
    private void gameOver() {
        if (counterBlue == 0) {
            board.setOnMouseClicked(null);
            gameOver.setText("Red Wins!");
            currentPlayer.setText("");
            winner = "Red";
            String filePath = "data.json";
            JsonWriter jsonWriter = new JsonWriter(filePath);
            jsonWriter.appendData(winner, turnCounter);
            LOG.info("Red won the game.");

        }
        if (counterRed == 0) {
            board.setOnMouseClicked(null);
            gameOver.setText("Blue Wins!");
            currentPlayer.setText("");
            winner = "Blue";
            String filePath = "data.json";
            JsonWriter jsonWriter = new JsonWriter(filePath);
            jsonWriter.appendData(winner, turnCounter);
            LOG.info("Blue won the game.");
        }
    }

    /**
     * Return the board.
     * @return the game board
     */
    public GridPane getBoard() {
        return board;
    }

    /**
     * Access function to the selectDisc variable.
     * @param isSelected boolean type to represent the
     *                   current game stage.
     */
    public void setSelectDisc(final boolean isSelected) {
        this.selectDisc = isSelected;
    }

    /**
     * Return the value of the selectDisc.
     * @return true if no disk is selected, and false
     *         if a disk is already selected.
     */
    public boolean getSelectDisc() {
        return selectDisc;
    }

    /**
     * Access function to the selectDisc variable.
     * @param isSelected boolean type to represent the
     *                   current game stage.
     */
    public void setSelectGrid(final boolean isSelected) {
        this.selectGrid = isSelected;
    }

    /**
     * Return the value of the selectGrid.
     * @return true if no grid is selected, and false
     *         if a grid is already selected.
     */
    public boolean getSelectGrid() {
        return selectGrid;
    }

    /**
     * Access function to the switchPlayer variable.
     * @param switchP boolean type, represents the current player
     */
    public void setSwitchPlayer(final boolean switchP) {
        this.switchPlayer = switchP;
    }

    /**
     * Return the value of the switchPlayer.
     * @return true if current player is blue, and false
     *         if current player is red.
     */
    public boolean getSwitchPlayer() {
        return switchPlayer;
    }

    /**
     * Return the value of the selectedRow.
     * @return integer, which is the index of the row.
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Return the value of the selectedRow.
     * @return integer, which is the index of the column.
     */
    public int getSelectedCol() {
        return selectedCol;
    }
}
