package DAO;

import Model.Account;
import Service.AccountService;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account insertAccount(Account account) { // function 1
        Connection connection = ConnectionUtil.getConnection(); // connect to database.

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getUsername()); // get username val for acct
            statement.setString(2, account.getPassword()); // get password val for acct.
            
            statement.executeUpdate(); // update database.
            ResultSet pkeyResultSet = statement.getGeneratedKeys(); // create generated keys for the database by rows. (1, 2, etc.)
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword()); // return the new acct object while the result set has a column.
            }
    } catch (SQLException e) {
        System.out.println(e.getMessage()); 
    }
    return null;

    }

    public Account verifyAccount(Account account) { // function 2
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = ? AND PASSWORD = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getUsername()); // get username val for acct
            statement.setString(2, account.getPassword()); // get password val for acct.
            
            ResultSet rs = statement.executeQuery();

            while(rs.next()){ // while there is a column:
                Account account_new = new Account(rs.getInt("account_id"), // return an acct object referencing these columns.
                        rs.getString("username"),
                        rs.getString("password"));

                return account_new; // return this acct new object.
                }
            }
         catch (SQLException e){
            System.out.println(e.getMessage());
            }

            return null;
        }      

    public Account getByAccountId(int accountId) { // function 3
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE account_id = ?"; // only want those for a specific id.
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, accountId);
            
            ResultSet rs = statement.executeQuery(); // as we are using select.

            while(rs.next()){
                Account accnt = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return accnt;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Account> getAllAccounts(){ // function 4
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account"; // want all accounts from the database.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(); // as we are using select.
            while(rs.next()){
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}


