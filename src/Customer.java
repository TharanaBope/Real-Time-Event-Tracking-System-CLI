
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate; // Delay in seconds between ticket purchases
    private final int config; // Number of tickets to purchase

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int config) {
        if (customerRetrievalRate <= 0) {
            throw new IllegalArgumentException("Retrieval rate and quantity must be positive.");
        }
        this.ticketPool = ticketPool;//ds
        this.customerRetrievalRate = customerRetrievalRate;
        this.config = config;
    }

    @Override
    public void run() {
        int quantity = config;
        for (int i = 0; i < quantity; i++) {
            try {
                Ticket ticket = ticketPool.buyTicket();
                if (ticket == null) {
                    System.err.println("No tickets available. Customer is waiting.");
                    continue;
                }
                System.out.println("Ticket purchased: " + ticket + " by Customer - " + Thread.currentThread().getName());
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                System.err.println("Customer thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                break;
            } catch (RuntimeException e) {
                System.err.println("Error during ticket purchase: " + e.getMessage());
            }
        }
        System.out.println("Customer has purchased all tickets.");
    }
}
