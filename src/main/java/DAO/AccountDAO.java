package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class AccountDAO {
    
    /*
     *  Adds the passed account to the DB and returns an object containing said 
     *  account on success, returns null account object otherwise.
     */
    public Account addAccount(Account account){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement prepStmnt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            prepStmnt.setString(1, account.username);
            prepStmnt.setString(2, account.password);

            prepStmnt.executeUpdate();
            ResultSet pKey = prepStmnt.getGeneratedKeys();
            if(pKey.next()){
                int account_id = (int) pKey.getLong(1);
                return new Account(account_id, account.username, account.password);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
     *  Returns an account object containing the account info that matches 
     *  the username passed to this method. Returns a null account object
     *  if the username does not match the username of an account in the DB.
     */
    public Account getAccountByUsername(String username){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account where username = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setString(1, username);

            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                return new Account(
                        resSet.getInt("account_id"),
                        resSet.getString("username"),
                        resSet.getString("password"));
                
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     *  Returns an account object containing the account retrieved
     *  from the DB using the account id passed to it. Returns a null
     *  account object if the id does not match an account in the DB.
     */
    public Account getAccountByUID(int account_id){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account where account_id = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setInt(1, account_id);

            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                return new Account(
                        resSet.getInt("account_id"),
                        resSet.getString("username"),
                        resSet.getString("password")
                    );
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
