import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maximumTicketCapacity;
    private final Queue<Ticket> ticketQueue;

    public TicketPool(int maximumTicketCapacity) {
        if (maximumTicketCapacity <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be positive.");
        }
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    // Add tickets to the pool
    public synchronized void addTicket(Ticket ticket) {
        try {
            while (ticketQueue.size() >= maximumTicketCapacity) {
                System.out.println("TicketPool full. Vendor is waiting...");
                wait(); // Wait until there is space
            }
            ticketQueue.add(ticket);
            notifyAll(); // Notify waiting consumers
            System.out.println("Ticket added by Vendor - Current Pool Size: " + ticketQueue.size());
        } catch (InterruptedException e) {
            System.err.println("Vendor interrupted while adding tickets. Terminating operation.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (Exception e) {
            System.err.println("Unexpected error while adding ticket: " + e.getMessage());
        }
    }

    // Remove tickets from the pool
    public synchronized Ticket buyTicket() {
        try {
            while (ticketQueue.isEmpty()) {
                System.out.println("TicketPool empty. Customer is waiting...");
                wait(); // Wait until tickets are available
            }
            Ticket ticket = ticketQueue.poll();
            notifyAll(); // Notify waiting producers
            System.out.println("Ticket bought - Remaining Pool Size: " + ticketQueue.size());
            return ticket;
        } catch (InterruptedException e) {
            System.err.println("Customer interrupted while buying tickets. Terminating operation.");
            Thread.currentThread().interrupt(); // Restore interrupted status
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while buying ticket: " + e.getMessage());
            return null;
        }
    }
}
