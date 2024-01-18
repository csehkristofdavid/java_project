package datahandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonReader {

    /**
     * The Logger instance for the JsonReader Class.
     */
    private static final Logger LOG = LogManager.getLogger(JsonReader.class);

    /**
     * The path to the JSON file to be read.
     */
    private final String path;
    /**
     * The Gson instance used for reading the JSON file.
     */
    private final Gson gson;

    /**
     * Constructs a JsonReader instance with the
     * specified file path.
     * @param filePath the path to the JSON file to be read
     */
    public JsonReader(final String filePath) {
        this.path = filePath;
        this.gson = new Gson();
    }

    /**
     * Reads data from the JSON file.
     * @return a list of Data objects read from
     *         the file, or null if an error occurs
     */
    public List<Data> readData() {
        try {
            String fileContent = Files.readString(Path.of(path),
                    StandardCharsets.UTF_8);
            LOG.info("Data loaded from file: " + path);
            return gson.fromJson(fileContent,
                    new TypeToken<List<Data>>() { }.getType());
        } catch (IOException e) {
            LOG.error("Error reading data from file: " + path, e);
            return null;
        }
    }
}
