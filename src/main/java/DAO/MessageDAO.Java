import Model.Message;

public class MessageDAO{

    public Message addMessage(Message message){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement prepStmnt = con.prepareStatement(sql);
            
            prepStmnt.setString(1, message.posted_by);
            prepStmnt.setString(2, message.message_text);

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

    public Message getMessageByID(){

    }

    public List<Message> getAllMessages(){

    }

    public List<Message> getAllMessagesByID(){

    }

    public Message deleteMessage(){

    }

    public Message updateMessage(){

    }
}