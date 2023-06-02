import java.util.Scanner;
import java.time.LocalDate;

public class Application {

    static Scanner input = new Scanner(System.in);

    Accounts accounts = new Accounts();

    Flights flights = new Flights();

    public Account loginUser;
//
//    //شمارنده حساب ها
//    private int counterAccount = 1;
//    // شمارنده پرواز ها
//    private int counterList = 5;

    //-----------------------------------------------------------------------
    //تابع نمایش منو اولیه
    public void run() {

        LocalDate time = LocalDate.now();

        System.out.println(time.toString());

        int loop = 0;

        while (loop < 1) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println(" WELCOME TO AIRLINE RESERVATION SYSTEM");
            System.out.println("::::::::::::::::::::::::::::::::::::::::");
            System.out.println(" .............MENUE OPTION............. ");
            System.out.println("1-sing in");
            System.out.println("2-sing up");

            //گرفتن گزینه از کاربر
            int option = input.nextInt();
            switch (option) {
                case 1:
                    signin();
                    break;
                case 2:
                    signup();
                    break;
                case 0:
                    loop++;
                    break;
                default:
                    System.out.println("please enter other digit :)");
            }

        }


    }

    //-----------------------------------------------------------------------
    //مقدار دهی اولیه کلاس ها
    public Application() {

        //مقدار دهی Admin
        Account admin = new Account("Admin", "Admin", 0);
        this.accounts.addAccount(admin);

        //new کردن کلاس لیست
        flights.addFlight(new Flight("FZ1930", "BAFGH", "TEHRAN", "01/26", "00:15", 150, 250));
        flights.addFlight(new Flight("IR7202", "YAZD", "TEHRAN", "01/26", "13:45", 120, 220));
        flights.addFlight(new Flight("QB2232", "YAZD", "ESFAHAN", "01/27", "00:25", 100, 450));
        flights.addFlight(new Flight("PH8520", "QATAR", "TEHRAN", "01/27", "05:45", 70, 400));
        flights.addFlight(new Flight("QS2352", "ESFAHAN", "BAHABAD", "01/27", "17:25", 50, 150));
    }


    //-----------------------------------------------------------------------
    // ساخت حساب جدید
    public void signup() {

        System.out.println("Username and password must be between six and fifteen characters");

        while (true) {

            System.out.println("enter username:");
            String name = input.next();

            // صفر برای بیرون رفتن
            if (name.equals("0")) {
                break;
            }

            System.out.println("enter password:");
            String password = input.next();
            System.out.println("Enter the amount of money:");
            int pure = input.nextInt();

            //بررسی تعداد حروف
            if (name.length() < 6 || password.length() < 6) {
                System.out.println("username and password must be at least six characters :)");
                continue;
            }

            if (name.length() > 15 || password.length() >15) {
                System.out.println("Username and password must be a maximum of fifteen characters :)");
                continue;
            }

            //بررسی وجود نداشتن نام کاربری تکراری
            if (accounts.getByUsername(name) != null) {
                System.out.println("username exist please enter other username :)");
                continue;
            }

            accounts.addAccount(new Account(name, password, pure));

            break;

        }

    }

    //-----------------------------------------------------------------------
    //ورود حساب
    public void signin() {
//متغیر f برای نمایش وجود نداشتن حساب وارد شده
        while (true) {

            //گرفتن username و password
            System.out.println("enter username:");
            String username = input.next();

            // صفر برای بیرون رفتن
            if (username.equals("0")) {
                break;
            }

            System.out.println("enter password:");
            String password = input.next();

            //بررسی username و password

            Account account = accounts.getByUsername(username);

            //وجود نداشتن حساب
            if (account == null) {
                System.out.println("this account not exist :)");

                //بررسی password
            } else if (account.getPassword().equals(password)) {
                loginUser = account;
                if (account.getName().equals("Admin"))
                    showAdmin();
                else
                    showUser();
                break;

            } else {
                System.out.println("password wrong:");

            }

        }

    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //تابع های مورد نیاز user
    //-----------------------------------------------------------------------
    //تابع نمایش منو user
    public void showUser() {

        int choose;
        int loop = 0;

        while (loop < 1) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println("         PASSENGER MENUE OPTIONS         ");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println(" ....................................... ");
            System.out.println("<1> Change password");
            System.out.println("<2> Search flight tickets");
            System.out.println("<3> Booking ticket");
            System.out.println("<4> Ticket cancellation");
            System.out.println("<5> Booked tickets");
            System.out.println("<6> Add charge");
            System.out.println("<0> Sign out");

            //اضافه کردن choose

            choose = input.nextInt();
            switch (choose) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    bookingTicket();
                    break;
                case 4:
                    cancel();
                    break;
                case 5:
                    booked_tickets();
                    break;
                case 6:
                    charge();
                    break;
                case 0:
                    loop++;
                    break;
                default:
                    System.out.println("plese be human,enter correct number :]");
            }

        }

    }

    //-----------------------------------------------------------------------
    //تغییر رمز
    public void changePassword() {

        boolean loop = true;
        System.out.println("Username and password must be between six and fifteen characters");

        while (loop) {
            System.out.println("enter new password");
            String x = input.next();

            if (x.equals("0")) {
                break;
            }

            if (x.length() < 6 || x.length() > 15) {

                System.out.println("Username and password must be between six and fifteen characters :)");

            } else {

                accounts.changePassword(x, loginUser.index);
                loop = false;
            }

        }

    }

    //-----------------------------------------------------------------------
    //سرچ بلیط
    public void search() {
        input.nextLine();
        String origin = input.nextLine();
        String destination = input.nextLine();
        System.out.print(flights.search(origin, destination));
    }

    //-----------------------------------------------------------------------
    //رزرو کردن بلیط
    public void bookingTicket() {

        while(true) {
            schedule();
            System.out.println("enter number of flight");
            int choose = input.nextInt();

            //خروج برای 0
            if (choose == 0) {
                break;
            }

            choose--;
            Flight flight = flights.getFlight(choose);
            //بررسی عدد وارد شده
            if (flight == null) {
                System.out.println("please enter correct number :)");
                continue;
            }

            boolean checkMoney = accounts.reservation(flight,loginUser);
            if (checkMoney){
                flight.setSeats(flight.getSeats()-1);
                flights.updateFlight(flight,choose);
            }
            if (!checkMoney){

                System.err.println("Your money is not enough");
                System.out.println("do you charge purse?");
                System.out.println("1-Yes\t2-No");
                int add = input.nextInt();

                if (add == 1){
                    charge();
                    continue;
                }

            }

            break;

        }

    }

    //-----------------------------------------------------------------------
    //کنسل کردن بلیط
    public void cancel() {
        while(true) {
            booked_tickets();
            System.out.println("enter number of flight");
            int choose = input.nextInt();

            //خروج برای 0
            if (choose == 0) {
                return;
            }

            choose--;
            if (!accounts.cancellation(loginUser,choose)){
                System.out.println("please enter correct number :)");
            }else {
                break;
            }

        }

    }

    //-----------------------------------------------------------------------
    //نمایش بلیط های رزرو شده
    public void booked_tickets() {

        accounts.showTicket(loginUser);

    }

    //-----------------------------------------------------------------------
    //اضافه کردن مقدار شارژ
    public void charge() {

        System.out.println("enter amount of money");
        int charge = input.nextInt();

        accounts.charge(loginUser.index,charge);
        System.out.println("amount your money"+loginUser.getPurse());
    }


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //تابع های مورد نیاز admin
    //-----------------------------------------------------------------------
    //تابع نمایش منو admin
    public void showAdmin() {

        int choose;
        boolean loop = true;

        while (loop) {
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println("           ADMIN MENUE OPTIONS           ");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
            System.out.println(" ....................................... ");
            System.out.println("<1> Add");
            System.out.println("<2> Update");
            System.out.println("<3> Remove");
            System.out.println("<4> Flight schedule");
            System.out.println("<0> Sign out");

            //اضافه کردن choose

            choose = input.nextInt();
            switch (choose) {
                case 1:
                    add();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    remove();
                    break;
                case 4:
                    schedule();
                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("please be human,enter correct number :]");
            }

        }

    }

    //-----------------------------------------------------------------------
    //گزینه add
    public void add() {

        System.out.println("enter FightId");
        String choose = input.next();

        //بررسی خروج برای 0
        if (choose.equals("0"))
            return;


        Flight flight = new Flight();

        flight.setFlightId(choose);

        System.out.println("enter origin");
        flight.setOrigin(input.next());

        System.out.println("enter Destination");
        flight.setDestination(input.next());

        System.out.println("enter Date");
        flight.setDate(input.next());

        System.out.println("enter Time");
        flight.setTime(input.next());

        System.out.println("enter Price");
        flight.setPrice(input.nextInt());

        System.out.println("enter Seats");
        flight.setSeats(input.nextInt());

        flights.addFlight(flight);

    }

    //-----------------------------------------------------------------------
    //گزینه update
    public void update() {
        int choose;
        schedule();
        boolean loop = true;

        while (loop) {

            System.out.println("enter number of flight");
            choose = input.nextInt();

            //خروج برای 0
            if (choose == 0) {
                break;
            }

            choose--;
            Flight flight = flights.getFlight(choose);
            //بررسی عدد وارد شده
            if (flight == null) {
                System.out.println("please enter correct number :)");
                continue;
            }

            System.out.println("enter FightId");
            flight.setFlightId(input.next());

            System.out.println("enter origin");
            flight.setOrigin(input.next());

            System.out.println("enter Destination");
            flight.setDestination(input.next());

            System.out.println("enter Date");
            flight.setDate(input.next());

            System.out.println("enter Time");
            flight.setTime(input.next());

            System.out.println("enter Price");
            flight.setPrice(input.nextInt());

            System.out.println("enter Seats");
            flight.setSeats(input.nextInt());

            flights.updateFlight(flight,choose);
            loop = false;
        }
    }

    //-----------------------------------------------------------------------
    //گزینه remove
    public void remove() {
        schedule();

        while (true) {

            System.out.println("enter number of flight");
            int choose = input.nextInt();

            //خروج برای 0
            if (choose == 0) {
                break;
            }
            choose--;

            Flight flight = flights.getFlight(choose);
            choose++;
            //بررسی عدد وارد شده
            if (!flights.removeFlight(choose)) {
                System.out.println("please enter correct number :)");
                continue;
            }

            accounts.removeTicketAccount(flight);
            break;
        }
    }

    //-----------------------------------------------------------------------
    //گزینه schedule
    public void schedule() {

        System.out.printf("   |%-10s|%-10s|%-15s|%-5s|%-5s|%-5s|%-5s|\n", "FlightId", "Origin","Destination", "Date", "Time", "Price", "Seats");
        //System.out.println(flights);

        input.nextLine();
    }


}
