package DAO;

import Model.Account;
import Service.AccountService;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account insertAccount(Account account) { // function 1
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            
            statement.executeUpdate();
            ResultSet pkeyResultSet = statement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
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
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Account account_new = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));

                return account_new;
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
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, accountId);
            
            ResultSet rs = statement.executeQuery();

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
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }






    public static void main(String[] args) {
        AccountDAO accnt_dao = new AccountDAO();
        Account a1 = new Account("user1", "password");
        Account a2 = accnt_dao.insertAccount(a1);

        System.out.println(a1.toString());
        System.out.println(a2.toString());

        Account a3 = accnt_dao.getByAccountId(0);
        Account a4 = accnt_dao.getByAccountId(2);

        if (a3 != null) {
            System.out.println(a3.toString());
        }
        else {
            System.out.println("Null Account");
        }
        System.out.println(a4.toString());
    }

}


