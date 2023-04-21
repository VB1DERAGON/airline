public class Ticket {
    private String ticketId;
    private Flight flight;

    //نام گذاری رندوم
    public Ticket(Flight flight) {
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 6; i++) {

            this.ticketId += alpha.charAt((int) (Math.random()* alpha.length()));
        }
        this.flight = flight;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return String.format("|%-10s%s", ticketId, flight);
    }
}
