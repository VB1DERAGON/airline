public class Flight {

    private String FlightId;
    private String Origin;
    private String Destination;
    private String Date;
    private String Time;
    private int Price;
    private int Seats;

    public Flight() {}

    public Flight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        FlightId = flightId;
        Origin = origin;
        Destination = destination;
        Date = date;
        Time = time;
        Price = price;
        Seats = seats;
    }

    public String getFlightId() {
        return FlightId;
    }

    public void setFlightId(String flightId) {
        FlightId = flightId;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getSeats() {
        return Seats;
    }

    public void setSeats(int  seats) {
        Seats = seats;
    }

    public void increaseSeats(){
        this.Seats++;
    }

    public void decreaseSeats(){
        this.Seats--;
    }
    @Override
    public String toString() {
        //تو اینترنت دیدم
        return String.format("|%-10s|%-10s|%-15s|%-10s|%-5s|%-10d|%-10d|", FlightId, Origin, Destination, Date, Time, Price, Seats);
    }
}