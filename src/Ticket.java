
public class Ticket {
    private int ticketId;
    private String event;

    public Ticket(int ticketId, String event) {
        this.ticketId = ticketId;
        this.event = event;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", event='" + event + '\'' +
                '}';
    }
}
