public class Ticket {
    private String ticketId;
    private String flightId;

    //نام گذاری رندوم
    public Ticket(Flight flight) {
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 6; i++) {

            this.ticketId += alpha.charAt((int) (Math.random()* alpha.length()));
        }
        this.flightId = flight.getFlightId();
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getFlightId() {
        return flightId;
    }

    @Override
    public String toString() {
        return String.format("|%-10s%s", ticketId, flightId);
    }
}
