/**
 * Represents a customer in the ticketing system.
 * Implements the Runnable interface to allow multi-threaded ticket purchasing.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool; // Reference to the ticket pool
    private final int customerRetrievalRate; // Delay in seconds between ticket purchases
    private final int config; // Number of tickets to purchase

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int config) {
        if (customerRetrievalRate <= 0) {
            throw new IllegalArgumentException("Retrieval rate and quantity must be positive.");
        }
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.config = config;
    }

    /**
     * The run method executed when the thread starts.
     * Simulates a customer purchasing tickets from the ticket pool.
     */
    @Override
    public void run() {
        int quantity = config; // Number of tickets to purchase
        try {
            for (int i = 0; i < quantity; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Customer thread interrupted. Exiting...");
                    break;
                }
                Ticket ticket = ticketPool.buyTicket(); // Attempt to purchase a ticket
                if (ticket == null) { // No tickets available
                    System.err.println("No tickets available. Customer is waiting.");
                    Thread.sleep(customerRetrievalRate * 1000L); // Simulate wait time
                    continue;
                }
                // Ticket purchased successfully
                System.out.println("Ticket purchased: " + ticket + " by Customer ");
                Thread.sleep(customerRetrievalRate * 1000L); // Simulate purchase delay
            }
        } catch (InterruptedException e) {
            System.out.println("Customer thread interrupted during sleep. Exiting...");
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (Exception e) {
            System.err.println("Unexpected error in Customer thread: " + e.getMessage());
        }
    }

}
