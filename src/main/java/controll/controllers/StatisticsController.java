package controll.controllers;

import datahandler.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StatisticsController {

    /**
     * The Logger instance for the JsonReader Class.
     */
    private static final Logger LOG = LogManager
            .getLogger(StatisticsController.class);

    /**
     * A reference to the TableView object used for
     * displaying data.
     */
    @FXML
    private TableView<Data> table;

    /**
     * A reference to the TableColumn object used for
     * displaying the name column.
     */
    @FXML
    private TableColumn<Data, String> nameCol;

    /**
     * A reference to the TableColumn object used for
     * displaying the moves column.
     */
    @FXML
    private TableColumn<Data, Integer> movesCol;

    /**
     * Initializes the TableView object with data from
     * the "data.json" file. If the initialization is
     * successful, a message is logged with the logger
     * instance. Otherwise, an error message is logged.
     */
    @FXML
    public void initialize() {
        JsonReader reader = new JsonReader("data.json");
        List<Data> dataList = reader.readData();

        if (dataList != null) {
            ObservableList<Data> data = FXCollections
                    .observableArrayList(dataList);
            nameCol.setCellValueFactory(
                    new PropertyValueFactory<>("stringValue"));
            movesCol.setCellValueFactory(
                    new PropertyValueFactory<>("intValue"));
            table.setItems(data);
            LOG.info("Data base initialization is successful.");
        } else {
            LOG.info("Data base initialization is failed.");
        }
        LOG.info("Statistics initialization is successful.");
    }
}
