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
                return;
            }
        }
        ticketQueue.add(ticket);
        notifyAll();
    }

    // Remove tickets from the pool
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return ticketQueue.poll();
    }
}
