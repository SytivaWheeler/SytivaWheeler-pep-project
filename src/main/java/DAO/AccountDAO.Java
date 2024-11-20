public class AccountDAO {

    public Account addAccount(Account account){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement prepStmnt = con.prepareStatement(sql);
            
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

    public Account getAccountByUsername(String username){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account where username = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setString(1, username);

            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                Account account = new Account(
                        resSet.getString("username"),
                        resSet.getString("password"));
                return account;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

}