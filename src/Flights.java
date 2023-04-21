public class Flights {

    private Flight[] flights = new Flight[50];
    private int lastIndex = 0;

    public void addFlight(Flight flight){
        flights[lastIndex] = flight;
        lastIndex++;
    }

    public boolean removeFlight(int index){
        if(index<0 && index>=lastIndex)
            return false;

        //جایگذاری مقدار بعدی در خانه
        for (int i = index; i < lastIndex; i++) {
            flights[i] = flights[i+1];
        }
        lastIndex--;
        flights[lastIndex] = null;
        return true;
    }

    public Flight getFlight(int index){
        if(index>=0 && index<lastIndex)
            return flights[index];
        return null;
    }

    public Flights search(String origin, String destination){
        Flights flightsSearch = new Flights();
        for (int i = 0; i < lastIndex; i++) {
            if ((origin.isEmpty() || flights[i].getOrigin().equals(origin)) && (destination.isEmpty() || flights[i].getDestination().equals(destination))){
                flightsSearch.addFlight(flights[i]);
            }
        }
        return flightsSearch;
    }



    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < lastIndex; i++) {
            res += "---------------------------------------------------------------------------------\n";
            res += String.format("%-3d%s\n",(i+1), flights[i].toString());
        }
        res += "---------------------------------------------------------------------------------\n";
        return res;
    }
}
