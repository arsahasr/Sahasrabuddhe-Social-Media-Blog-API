package Service;
import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    
    private AccountDAO accountDAO;
    /**
     * No-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Persist a new Account (without an account_id).
     *
     * @param account the Account object to insert
     * @return the inserted Account with generated ID
     */
    public Account addAccount(Account account) { 
        if ((!account.getUsername().isBlank()) && (account.getPassword().length() >= 4)) {
            return accountDAO.insertAccount(account);
        }
        else {
            return null;
        }
}
    
        
    public Account verifyAccount(Account account) { // function 2
        return accountDAO.verifyAccount(account);
    }
        
    public Account getByAccountId(int accountId) { // function 3
        return accountDAO.getByAccountId(accountId);
    }
    
    public static void main(String[] args) {
        // now test the media controller object. in the final version, we will communicate with the port 8080
        // and ask the media controller object to create a new account for us, and do other stuff. but for this
        // test we will directly call the addAccount method and other methods to make sure that these methods
        // work correctly. once we know that these methods work correctly, then the SocialMediaController.java
        // code will basically call the methods with the proper Account object, and everything should work correctly.

        // prepare the database so that we can insert a new username/password. basically create the
        // right tables etc.

        // create an account service object that will do the database stuff
        AccountService as = new AccountService();

        // now try to create a username/password by directly 
        Account a = new Account("user", "password");
        Account na = as.addAccount(a);
        System.out.println(na);
        
        // later we need to check if na has the info we wanted and also then check if we can lookup the account by 
        // querying the database.
    }

}
