import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
public class Accounts {
    //private Account[] accounts = new Account[100];
    public Account account = new Account();
    public RandomAccessFile accounts;
    public RandomAccessFile backUp;
    {
        try {
            backUp = new RandomAccessFile("backUp","rw");
            accounts = new RandomAccessFile("accounts","rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int lastIndex = 0;

    //اضافه کردن اکانت
    public void addAccount(Account account){
        try {
            accounts.seek(lastIndex * 452);
            account.index = lastIndex;
            accounts.writeInt(account.index);
            accounts.writeInt(account.lastIndex);
            accounts.writeChars(fixString1(account.getName()));
            accounts.writeChars(fixString1(account.getPassword()));
            accounts.writeInt(account.getPurse());
            for (int i = 0;i <= 10;i++){
                accounts.writeChars(fixString1(""));
                accounts.writeChars(fixString1(""));
            }
            lastIndex++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //تشخیص وجود حساب
    public Account getByUsername(String username){
        try {
            for (int i = 0; i < lastIndex; i++) {
                accounts.seek(i*452);
                account.index = accounts.readInt();
                account.setName(withoutSpace(readChar1(accounts)));
                if (account.getName().equals(username)){
                    account.setPassword(withoutSpace(readChar1(accounts)));
                    account.setPurse(accounts.readInt());
                    /*for (int j = 0; j <= 10; j++) {
                        account.tickets[j].setTicketId(readChar1(accounts));
                        account.tickets[j].setFlightId(readChar1(accounts));
                    }*/
                    return account;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void changePassword(String string, int index){
        try {
            accounts.seek((index*452)+28);
            accounts.writeChars(fixString1(string));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean reservation(Flight flight,Account account){
        try {
            if (account.getPurse() >= flight.getPrice() && flight.getSeats() > 0) {
                accounts.seek((account.index * 452) + (account.lastIndex * 20) + 52);
                Ticket ticket = new Ticket(flight);
                accounts.writeChars(fixString1(ticket.getTicketId()));
                accounts.writeChars(fixString1(ticket.getTicketId()));
                accounts.seek((account.index * 452) + 4);
                accounts.writeInt(lastIndex + 1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void charge(int index, int charge){
        try {
            accounts.seek((index * 452) + 28);
            int a = accounts.readInt();
            accounts.seek((index * 452) + 28);
            charge = charge + a;
            accounts.writeInt(charge);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTicket(Account account){
        try {
            accounts.seek((account.index * 452) + 32);
            for (int i = 0; i <= 10; i++) {
                System.out.println("----------------------------------------------------------------------------------------------\n");
                System.out.print(String.format("%-3d", (i + 1)));
                System.out.println(readChar1(accounts) + readChar1(accounts));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //*****
    public boolean cancellation(Account account,int index){
        try {
            if(index<0 && index>=lastIndex) {
                return false;
            }
            accounts.seek((account.index * 452) + 32 + (index * 40) + 40);
            backUp.seek(0);
            for (int i = 0; i < 9 - index; i++) {
                backUp.writeChars(readChar1(accounts));
                backUp.writeChars(readChar1(accounts));
            }
            accounts.seek((account.index * 452) + 32 + (index * 40));
            backUp.seek(0);
            for (int i = 0; i < -index; i++) {
                accounts.writeChars(readChar1(backUp));
                accounts.writeChars(readChar1(backUp));
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //پاس دادن پرواز حذف شده
    public void removeTicketAccount(Flight flight){
        try {
            Account account;
            for (int i = 0; i < lastIndex; i++) {
                accounts.seek((i * 452) + 8);
                account = getByUsername(readChar1(accounts));
                for (int j = 0; j < account.lastIndex; j++) {
                    if (account.tickets[i].equals(flight.getFlightId())) {
                        cancellation(account, i);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // fixSize for name and password
    public String fixString1(String string) {
        for (int i = string.length(); i <= 10; i++) {
            string += " ";
        }
        return string.substring(0, 10);
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
    public String withoutSpace(String str){
        char[] name = new char[10];
        String space = "";
        name = str.toCharArray();
        for (int i = 0;i < 10;i++){
            if (name[i] != ' '){
                space += name[i];
            }
        }
        return space;
    }
}