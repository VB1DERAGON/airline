import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Flights {
    //    private Flight[] flights = new Flight[50];
    private RandomAccessFile flights;
    private RandomAccessFile backup;

    {
        try {
            flights = new RandomAccessFile("flight", "rw");
            backup = new RandomAccessFile("backup", "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int lastIndex = 0;

    public void addFlight(Flight flight) {
        try {
            flights.seek(lastIndex * 88);
            flights.writeChars(fixString1(flight.getFlightId()));
            flights.writeChars(fixString1(flight.getOrigin()));
            flights.writeChars(fixString1(flight.getDestination()));
            flights.writeChars(fixString2(flight.getDate()));
            flights.writeChars(fixString2(flight.getTime()));
            flights.writeInt(flight.getPrice());
            flights.writeInt(flight.getSeats());//88 bytes
            lastIndex++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeFlight(int index) {
        if (index < 0 && index >= lastIndex)
            return false;
        try {
            flights.seek(index * 88);
            backup.seek(0);
            for (int i = index; i < lastIndex; i++) {
                backup.writeChars(readChar1(flights));
                backup.writeChars(readChar1(flights));
                backup.writeChars(readChar1(flights));
                backup.writeChars(readChar2(flights));
                backup.writeChars(readChar2(flights));
                backup.writeInt(flights.readInt());
                backup.writeInt(flights.readInt());
            }
            flights.seek((index-1) * 88);
            backup.seek(0);
            for (int i = index; i < lastIndex; i++) {
                flights.writeChars(readChar1(backup));
                flights.writeChars(readChar1(backup));
                flights.writeChars(readChar1(backup));
                flights.writeChars(readChar2(backup));
                flights.writeChars(readChar2(backup));
                flights.writeInt(backup.readInt());
                flights.writeInt(backup.readInt());
            }
            lastIndex--;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Flight getFlight(int index) {
        if (index >= 0 && index < lastIndex) {
            Flight flight = null;
            try {

                flights.seek(index * 88);
                flight.setFlightId(readChar1(flights));
                flight.setOrigin(readChar1(flights));
                flight.setDestination(readChar1(flights));
                flight.setDate(readChar2(flights));
                flight.setTime(readChar2(flights));
                flight.setPrice(flights.readInt());
                flight.setSeats(flights.readInt());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return flight;
        }
        return null;
    }

    public String search(String origin, String destination) {
        try {
            String str = "";
            for (int i = 0; i < lastIndex; i++) {
                flights.seek((i * 88) + 20);
                if ((origin.isEmpty() || readChar1(flights).equals(origin)) && (destination.isEmpty() || readChar1(flights).equals(destination))) {
                    flights.seek(flights.getFilePointer() - 60);
                    str += "---------------------------------------------------------------------------------\n";
                    str += String.format("%-3d|%-10s|%-10s|%-15s|%-5s|%-5s|%-5s|%-5s|\n", (i + 1), readChar1(flights), readChar1(flights), readChar1(flights), readChar2(flights), readChar2(flights), flights.readInt(), flights.readInt());
                }
            }
            str += "---------------------------------------------------------------------------------\n";
            return str;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFlight(Flight flight, int index) {
        try {
            flights.seek(index * 88);
            flights.writeChars(fixString1(flight.getFlightId()));
            flights.writeChars(fixString1(flight.getOrigin()));
            flights.writeChars(fixString1(flight.getDestination()));
            flights.writeChars(fixString2(flight.getDate()));
            flights.writeChars(fixString2(flight.getTime()));
            flights.writeInt(flight.getPrice());
            flights.writeInt(flight.getSeats());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //fixSize for flightId , origin , destination
    public String fixString1(String string) {
        for (int i = string.length(); i <= 10; i++) {
            string += " ";
        }
        return string.substring(0, 10);
    }

    //fixSize for date , time
    public String fixString2(String string) {
        for (int i = string.length(); i <= 5; i++) {
            string += " ";
        }
        return string.substring(0, 5);
    }

    //read 10
    public String readChar1(RandomAccessFile read) {
        String str = "";
        for (int i = 0; i < 10; i++) {
            try {
                str += read.readChar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return str;
    }

    //read 5
    public String readChar2(RandomAccessFile read) {
        String str = "";
        for (int i = 0; i < 5; i++) {
            try {
                str += read.readChar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return str;
    }


    @Override
    public String toString() {
        try {
            String res = "";
            flights.seek(0);
            for (int i = 0; i < lastIndex; i++) {
                res += "---------------------------------------------------------------------------------\n";
                res += String.format("%-3d|%-10s|%-10s|%-15s|%-5s|%-5s|%-5s|%-5s|\n", (i + 1), readChar1(flights), readChar1(flights), readChar1(flights), readChar2(flights), readChar2(flights), flights.readInt(), flights.readInt());
            }
            res += "---------------------------------------------------------------------------------\n";
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}