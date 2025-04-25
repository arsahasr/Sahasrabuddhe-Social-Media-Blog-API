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
        accountDAO = new AccountDAO(); // a new instance of the accountdao class is created.
    }

    /**
     * Persist a new Account (without an account_id).
     *
     * @param account the Account object to insert
     * @return the inserted Account with generated ID
     */
    public Account addAccount(Account account) {  // here we are getting an account object from the controller, and conducting business logic.
        if ((!account.getUsername().isBlank()) && (account.getPassword().length() >= 4)) { // logic makes sure username is not blank, and the password length is at least 4.
            return accountDAO.insertAccount(account); // if it is true we call the insertaccount method, which exists in the DAO layer.
        }
        else {
            return null; // else we return empty. 
        }
}
    
        
    public Account verifyAccount(Account account) { // no verify business logic needed.
        return accountDAO.verifyAccount(account); // we call the verify acct method in the DAO layer
    }
        
    public Account getByAccountId(int accountId) { // no get by account id business logic needed.
        return accountDAO.getByAccountId(accountId); // we call the get by acct id method in the DAO layer
    }

}
