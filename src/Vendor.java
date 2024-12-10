public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate; // Delay in seconds between ticket releases
    private final TicketPool ticketPool;


    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        if (totalTickets <= 0 || ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Total tickets and release rate must be positive.");
        }
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalTickets; i++) {
                Ticket ticket = new Ticket(i, "Event-" + i);
                ticketPool.addTicket(ticket);
                System.out.println("Ticket added: " + ticket);

                Thread.sleep(ticketReleaseRate * 1000L); // Simulate release delay
            }
            System.out.println("Vendor has added all tickets.");
        } catch (InterruptedException e) {
            System.err.println("Vendor thread interrupted. Terminating gracefully.");
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (Exception e) {
            System.err.println("Unexpected error in Vendor thread: " + e.getMessage());
        }
    }
}
