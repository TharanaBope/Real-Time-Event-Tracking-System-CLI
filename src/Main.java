import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;
        TicketPool ticketPool = null;
        Thread vendorThread = null;
        Thread customerThread = null;

        System.out.println("===============================================");
        System.out.println("   Welcome to the Ticket Management System!    ");
        System.out.println("===============================================");
        System.out.println("Available Commands:");
        System.out.println("  start  - Start ticket release.");
        System.out.println("  status - Display the current ticket pool status.");
        System.out.println("  config - Configure ticket system settings.");
        System.out.println("  exit   - Exit the application.");
        System.out.println("===============================================");

        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    if (config == null || ticketPool == null) {
                        System.out.println("Configuration or TicketPool is not initialized. Please configure first.");
                        break;
                    }

                    if (vendorThread == null || !vendorThread.isAlive()) {
                        Vendor vendor = new Vendor(config.getTotalTickets(), config.ticketReleaseRate, ticketPool);
                        vendorThread = new Thread(vendor, "Vendor");
                        vendorThread.start();
                        System.out.println("Ticket release started.");
                    } else {
                        System.out.println("Ticket release is already running.");
                    }

                    if (customerThread == null || !customerThread.isAlive()) {
                        Customer customer = new Customer(ticketPool, config.customerRetrievalRate, config.totalTickets);
                        customerThread = new Thread(customer, "Customer");
                        customerThread.start();
                        System.out.println("Customer process started.");
                    } else {
                        System.out.println("Customer process is already running.");
                    }

                    // Wait for threads to finish naturally
                    try {
                        if (vendorThread != null) vendorThread.join();
                        if (customerThread != null) customerThread.join();
                        System.out.println("Vendor has added all tickets.");
                        System.out.println("Customer has purchased all tickets.");
                        System.out.println("Exiting the application.");
                        return; // Exit the program
                    } catch (InterruptedException e) {
                        System.out.println("Process interrupted. Exiting...");
                        Thread.currentThread().interrupt();
                        return;
                    }

                case "status":
                    if (ticketPool != null) {
                        System.out.println("Current Ticket Pool Status: " + ticketPool);
                    } else {
                        System.out.println("TicketPool is not initialized.");
                    }
                    break;

                case "config":
                    System.out.print("Do you want to load configuration from a file? (yes/no): ");
                    String response = scanner.nextLine().trim().toLowerCase();

                    boolean isConfigLoadedFromFile = false;

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
                            isConfigLoadedFromFile = true;
                        } catch (IOException e) {
                            System.out.println("Error loading configuration: " + e.getMessage());
                            System.out.println("Switching to manual configuration input.");
                            config = inputConfiguration(scanner);
                        }
                    } else {
                        config = inputConfiguration(scanner);
                    }

                    // Save configuration if loaded manually
                    if (!isConfigLoadedFromFile) {
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
                    }

                    ticketPool = new TicketPool(config.maxTicketCapacity);
                    System.out.println("Configuration completed.");
                    break;

                case "exit":
                    System.out.println("Exiting the application.");
                    return;

                default:
                    System.out.println("Invalid command. Please enter a valid command.");
            }
        }
    }

    private static Configuration inputConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        System.out.println("Please configure the system manually:");
        config.totalTickets = getValidatedInput(scanner, "Enter total tickets: ",
                value -> value > 0, "Total tickets must be greater than 0.");

        config.ticketReleaseRate = getValidatedInput(scanner, "Enter ticket release rate (in seconds): ",
                value -> value > 0, "Release rate must be greater than 0.");

        config.customerRetrievalRate = getValidatedInput(scanner, "Enter customer retrieval rate (in seconds): ",
                value -> value > 0, "Retrieval rate must be greater than 0.");

        config.maxTicketCapacity = getValidatedInput(scanner, "Enter max ticket capacity: ",
                value -> value >= config.totalTickets, "Max capacity must be at least equal to total tickets.");

        return config;
    }

    private static int getValidatedInput(Scanner scanner, String prompt, ValidationRule rule, String errorMessage) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (rule.isValid(value)) {
                    return value;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format.");
            }
        }
    }
}
