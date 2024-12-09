import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;

        System.out.print("Do you want to load configuration from a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();

            try {
                config = Configuration.loadConfiguration(filePath);
                System.out.println("Configuration loaded successfully.");
                System.out.println("Total Tickets: " + config.totalTickets);
                System.out.println("Ticket Release Rate: " + config.ticketReleaseRate);
                System.out.println("Customer Retrieval Rate: " + config.customerRetrievalRate);
                System.out.println("Max Ticket Capacity: " + config.maxTicketCapacity);
            } catch (IOException e) {
                System.out.println("Error loading configuration: " + e.getMessage());
                System.out.println("Switching to manual configuration input.");
                config = inputConfiguration(scanner);
            }
        } else {
            config = inputConfiguration(scanner);
        }

        // Create the TicketPool
        TicketPool ticketPool = new TicketPool(config.maxTicketCapacity);

        // Create and start the vendor thread
        Vendor vendor = new Vendor(config.totalTickets, config.ticketReleaseRate, ticketPool);
        Thread vendorThread = new Thread(vendor, "Vendor");
        vendorThread.start();

        // Create and start the customer thread
        Customer customer = new Customer(ticketPool, config.customerRetrievalRate, 5);
        Thread customerThread = new Thread(customer, "Customer");
        customerThread.start();
    }

    private static Configuration inputConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        // Input total tickets with validation
        config.totalTickets = getValidatedInput(scanner, "Enter total tickets: ",
                value -> value > 0, "Total tickets must be a positive number.");

        // Input ticket release rate with validation
        config.ticketReleaseRate = getValidatedInput(scanner, "Enter ticket release rate (in seconds): ",
                value -> value > 0, "Ticket release rate must be a positive number.");

        // Input customer retrieval rate with validation
        config.customerRetrievalRate = getValidatedInput(scanner, "Enter customer retrieval rate (in seconds): ",
                value -> value > 0, "Customer retrieval rate must be a positive number.");

        // Input maximum ticket capacity with validation
        // Input maximum ticket capacity with validation
        config.maxTicketCapacity = getValidatedInput(scanner, "Enter max ticket capacity: ",
                value -> value > 0 && value >= config.totalTickets,
                "Max ticket capacity must be greater than 0 and greater than or equal to total tickets.");


        // Optionally save configuration to a file
        System.out.print("Do you want to save this configuration to a file? (yes/no): ");
        String saveResponse = scanner.nextLine().trim().toLowerCase();
        if (saveResponse.equals("yes")) {
            System.out.print("Enter the file path to save: ");
            String filePath = scanner.nextLine();
            try {
                config.saveConfiguration(filePath);
                System.out.println("Configuration saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving configuration: " + e.getMessage());
            }
        }

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


