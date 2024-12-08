import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Real-Time Ticketing System!");

        System.out.println("System setup in progress...");

        System.out.print("Do you want to load configuration from a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        Configuration config;
        if (response.equals("yes")) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();
            try {
                config = Configuration.loadConfiguration(filePath);
                System.out.println("Configuration loaded successfully.");
            } catch (IOException e) {
                System.out.println("Error loading configuration: " + e.getMessage());
                System.out.println("Switching to manual configuration input.");
                config = inputConfiguration(scanner);
            }
        } else {
            config = inputConfiguration(scanner);
        }
        System.out.println("Configuration setup complete.");

    }
    private static Configuration inputConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        config.totalTickets = getValidatedInput(scanner, "Enter total tickets: ",
                value -> value > 0, "Total tickets must be a positive number.");

        config.ticketReleaseRate = getValidatedInput(scanner, "Enter ticket release rate (in seconds): ",
                value -> value > 0, "Ticket release rate must be a positive number.");

        config.customerRetrievalRate = getValidatedInput(scanner, "Enter customer retrieval rate (in seconds): ",
                value -> value > 0, "Customer retrieval rate must be a positive number.");

        config.maxTicketCapacity = getValidatedInput(scanner, "Enter max ticket capacity: ",
                value -> value > 0, "Max ticket capacity must be a positive number.");
        return config;
    }

    private static int getValidatedInput(Scanner scanner, String prompt, ValidationRule validation, String errorMessage) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (validation.isValid(value)) {
                    break;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        return value;
    }

}
