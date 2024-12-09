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
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor thread interrupted while adding tickets.");
                return;
            }
        }
        ticketQueue.add(ticket);
        notifyAll();
        System.out.println("Ticket added by Vendor - Current Pool Size: " + ticketQueue.size());
    }

    // Remove tickets from the pool
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Customer thread interrupted while buying tickets.");
                return null;
            }
        }
        Ticket ticket = ticketQueue.poll();
        notifyAll();
        System.out.println("Ticket bought - Remaining Pool Size: " + ticketQueue.size());
        return ticket;
    }
}
