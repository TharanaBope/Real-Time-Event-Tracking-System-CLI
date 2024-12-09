import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;

public class Configuration implements Serializable {
    int totalTickets;
    int ticketReleaseRate;
    int customerRetrievalRate;
    int maxTicketCapacity;

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        if (totalTickets <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        this.totalTickets = totalTickets;
    }

    // Save configuration to a file
    public void saveConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        } catch (JsonSyntaxException e) {
            // Handle invalid JSON format errors
            throw new IOException("Invalid JSON format in configuration file.", e);
        }
    }

    // Load configuration from a file
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON format in configuration file.", e);
        }
    }
}
