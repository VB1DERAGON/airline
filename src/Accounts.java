public class Accounts {
    private Account[] accounts = new Account[100];
    private int lastIndex = 0;

    public void addAccount(Account account){
        accounts[lastIndex] = account;
        lastIndex++;
    }

    public Account getByUsername(String username){

        for (int i = 0; i < lastIndex; i++) {

            if (username.equals(accounts[i].getName())){

                return accounts[i];
            }

        }
        return null;
    }

    public void removeTicketAccount(Flight flight){
        for (int i = 0; i < lastIndex; i++) {
            accounts[i].removeTicket(flight);
        }
    }
}
