public class Accounts {
    private Account[] accounts = new Account[100];
    private int lastIndex = 0;

    //اضافه کردن اکانت
    public void addAccount(Account account){
        accounts[lastIndex] = account;
        lastIndex++;
    }
    //تشخیص وجود حساب
    public Account getByUsername(String username){

        for (int i = 0; i < lastIndex; i++) {

            if (username.equals(accounts[i].getName())){

                return accounts[i];
            }

        }
        return null;
    }

    //پاس دادن پرواز حذف شده
    public void removeTicketAccount(Flight flight){
        for (int i = 0; i < lastIndex; i++) {
            accounts[i].removeTicket(flight);
        }
    }
}
