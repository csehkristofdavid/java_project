package datahandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonWriter {
    /**
     * The Logger instance for the JsonWriter Class.
     */
    private static final Logger LOG = LogManager
            .getLogger(JsonWriter.class);

    /**
     * The path to the Json file to write to.
     */
    private final String path;

    /**
     * The Gson object used for parsing Json data.
     */
    private final Gson gson;

    /**
     * Creates a new instance of the JsonWriter with
     * the specified file path.
     *
     * @param filePath the path to the Json file
     *                 to write to.
     */
    public JsonWriter(final String filePath) {
        this.path = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        LOG.info("jsonWriter initialization.");

    }

    /**
     * Appends new data to the Json file.
     *
     * @param stringValue The String value (player name)
     *                    to append.
     * @param intValue The integer value (turn took)
     *                 to append.
     */
    public void appendData(final String stringValue, final int intValue) {
        Data data = new Data(stringValue, intValue);
        List<Data> dataList = readDataList();
        dataList.add(data);
        writeToFile(dataList);
        LOG.info("Append new data.");
    }

    /**
     * Reads the data from a Json file.
     *
     * @return the list of data objects read
     *         from the Json file.
     */
    private List<Data> readDataList() {
        try {
            String fileContent = Files.readString(Path.of(path),
                    StandardCharsets.UTF_8);
            Data[] dataArray = gson.fromJson(fileContent, Data[].class);
            if (dataArray != null) {
                return new ArrayList<>(List.of(dataArray));
            }
        } catch (IOException e) {
            LOG.info("Error while reading data list from file.", e);
        }
        return new ArrayList<>();
    }

    /**
     * Writes the list of data objects to the Json file.
     *
     * @param dataList the list of data objects to write
     *                 to the Json file.
     */
    private void writeToFile(final List<Data> dataList) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(dataList.toArray(), writer);
            writer.flush();
            writer.close();
            LOG.info("Data written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
