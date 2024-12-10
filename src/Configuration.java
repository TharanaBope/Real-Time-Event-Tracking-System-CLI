import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;

public class Configuration implements Serializable {
    int totalTickets; //Total number of tickets available
    int ticketReleaseRate; //Number of tickets released per minute
    int customerRetrievalRate; //Number of customers retrieved per minute
    int maxTicketCapacity; //Maximum number of tickets that can be held by a customer
//
    public int getTotalTickets() { //Gettr method for totalTickets
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) { //sttr for totalTickets
        if (totalTickets <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        this.totalTickets = totalTickets;
    }

    // Save configuration to a file with enhanced error handling
    public void saveConfiguration(String filePath) throws IOException { // Save configuration to a file with error handling
        Gson gson = new Gson();
        File file = new File(filePath);

        if (file.isDirectory()) { // Check if the file path is a directory
            throw new IOException("The provided file path is a directory: " + filePath);
        }

        try (FileWriter writer = new FileWriter(file)) { // Write the configuration to the file
            gson.toJson(this, writer);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON format while saving configuration.", e);
        } catch (IOException e) {
            throw new IOException("Error writing configuration to file: " + filePath, e);
        }
    }


    // Load configuration from a file with enhanced error handling
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Configuration file does not exist: " + filePath);
        }
        if (!file.isFile()) {
            throw new IOException("The specified path is not a file: " + filePath);
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON format in configuration file.", e);
        } catch (IOException e) {
            throw new IOException("Error reading configuration from file: " + filePath, e);
        }
    }

}
