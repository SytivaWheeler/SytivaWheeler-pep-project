package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.getUsername() != "" && account.getPassword().length() > 4)
        {
            return accountDAO.addAccount(account);
        }
        return null;
    }

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
