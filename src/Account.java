public class Account {

    //--------------------------------
    private int lastIndex = 0;
    Ticket[] tickets = new Ticket[20];
    //--------------------------------
    private String name;
    private String password;
    private int purse;

    public Account() {
        name = null;
        password = null;
        purse = 0;
    }

    public Account(String name, String id, int purse) {
        this.name = name;
        this.password = id;
        this.purse = purse;
    }

//    public Ticket[] getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(Ticket[] tickets) {
//        this.tickets = tickets;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }



//-------------------------------------------------

    //نمایش بلیط های رزرو شده
    public String showTicket(){

        String res = "";

        if(lastIndex > 0) {

            for (int i = 0; i < lastIndex; i++) {
                res += "----------------------------------------------------------------------------------------------\n";
                res += String.format("%-3d%s\n", i + 1, tickets[i]);
            }
            res += "----------------------------------------------------------------------------------------------\n";
            return res;
        }
        return "Unreserved ticket";
    }

    //تابع کنسل بلیط
    public boolean cancellation(int index){
        if(index<0 && index>=lastIndex)
            return false;

        purse += tickets[index].getFlight().getPrice();
        tickets[index].getFlight().increaseSeats();
        //جایگذاری مقدار بعدی در خانه
        for (int i = index; i < lastIndex; i++) {
            tickets[i] = tickets[i+1];
        }
        lastIndex--;
        tickets[lastIndex] = null;
        return true;
    }

    //حذف بلیط در صورت حذف پرواز توسط ادمین
    public void removeTicket(Flight flight){
        for (int i = 0; i < lastIndex; i++) {
            if (tickets[i].getFlight().getFlightId().equals(flight.getFlightId()))
                cancellation(i);
        }
    }

    //------------------------------------------------------
    //رزرو کردن بلیط
    public boolean reservation(Flight flight){

        if(flight.getSeats() > 0 && this.getPurse() >= flight.getPrice()){
            this.setPurse(this.getPurse() - flight.getPrice());
            flight.decreaseSeats();
            tickets[lastIndex] = new Ticket(flight);
            lastIndex++;
            return true;
        }
        return false;
    }
}