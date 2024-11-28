package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    private AccountDAO accountDAO;

    /*
     *  Empty param constructor for account service object.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /*
     *  Constructor for account service object with accountDAO object param.
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /*
     *  Adds account to DB if username isnt blank and password length is greater than 4.
     *  Returns account object containing the newly added account, returns null otherwise.
     */
    public Account addAccount(Account account){
        if(account.getUsername() != "" && account.getPassword().length() > 4)
        {
            return accountDAO.addAccount(account);
        }
        return null;
    }

    /*
     *  Checks if login attempt matches with any accounts username and password in the DB.
     *  Returns the account object of the account if it matches, returns null otherwise.
     */
    public Account loginCheck(Account account){
        Account checkedAccount = accountDAO.getAccountByUsername(account.username);

        if(checkedAccount != null){
            if(checkedAccount.getUsername().equals(account.username) && checkedAccount.getPassword().equals(account.password)){
                return checkedAccount;
            }
            
        }
        
        return null;
    }
}
