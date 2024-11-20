package Service;

public class AccountService{
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDao accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.username != "" && account.password.length() > 4 &&
        accountDAO.getAccountByUsername(account.username).username != account.username)
        {
            return accountDAO.addAccount(account);
        }
        return null;
    }
}