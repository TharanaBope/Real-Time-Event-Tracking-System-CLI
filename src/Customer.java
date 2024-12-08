
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate; // Delay in seconds between ticket purchases
    private final int quantity; // Number of tickets to purchase

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        if (customerRetrievalRate <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Retrieval rate and quantity must be positive.");
        }
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            try {
                Ticket ticket = ticketPool.buyTicket();
                if (ticket != null) {
                    System.out.println("Ticket purchased: " + ticket + " by " + Thread.currentThread().getName());
                }
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                System.err.println("Customer thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
