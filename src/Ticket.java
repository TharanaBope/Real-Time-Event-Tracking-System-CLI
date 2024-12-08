
public class Ticket {
    private int ticketId;
    private String event;
    private double price;

    public Ticket(int ticketId, String event, double price) {
        this.ticketId = ticketId;
        this.event = event;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", event='" + event + '\'' +
                ", price=" + price +
                '}';
    }
}
