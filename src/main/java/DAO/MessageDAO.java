package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;

import Util.ConnectionUtil;

public class MessageDAO {

    /*
     *  Adds the message passed to it to the DB and returns a message object
     *  containing said message.
     */
    public Message addMessage(Message message){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement prepStmnt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            prepStmnt.setInt(1, message.getPosted_by());
            prepStmnt.setString(2, message.getMessage_text());
            prepStmnt.setLong(3, message.getTime_posted_epoch());

            prepStmnt.executeUpdate();
            ResultSet pKey = prepStmnt.getGeneratedKeys();
            if(pKey.next()){
                int message_id = (int) pKey.getLong(1);
                return new Message(
                    message_id, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                );
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     *  Returns a list of message objects containing all the mesaages in the DB
     */
    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();

        try{            
            String sql = "SELECT * FROM message";

            PreparedStatement prepStmnt = con.prepareStatement(sql);
            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                Message msg = new Message(
                    resSet.getInt("message_id"),
                    resSet.getInt("posted_by"),
                    resSet.getString("message_text"),
                    resSet.getLong("time_posted_epoch")
                );
                allMessages.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return allMessages;
    }

    /*
     * Returns a message object with a message obtained from the DB using its message id.
     * Returns a null message object if the message is not found within the DB.
    */
    public Message getMessageByMID(String message_id){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message WHERE message_id = ? ";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setInt(1, Integer.valueOf(message_id));

            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                return new Message(
                    resSet.getInt("message_id"),
                    resSet.getInt("posted_by"),
                    resSet.getString("message_text"),
                    resSet.getLong("time_posted_epoch")
                );
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     *  Deletes a message from the DB and returns the rows affected.
     */
    public int deleteMessage(String message_id){
        Connection con = ConnectionUtil.getConnection();
        int affectedRows = 0;

        try{
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setInt(1, Integer.valueOf(message_id));

            affectedRows = prepStmnt.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return affectedRows;
    }

    /*
     *  Updates a message in the DB and returns the rows affected.
     */
    public int updateMessage(String message_id, Message newMessage){
        Connection con = ConnectionUtil.getConnection();
        int affectedRows = 0;

        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setString(1, newMessage.getMessage_text());
            prepStmnt.setInt(2, Integer.valueOf(message_id));

            affectedRows = prepStmnt.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return affectedRows;
    }

    /*
     *  Returns a list of message objects containing all messages posted by 
     *  a specific user using their user id.
     */
    public List<Message> getAllMessagesByUID(String account_id){
        Connection con = ConnectionUtil.getConnection();
        List<Message> allMessagesID = new ArrayList<>();

        try{
            String sql = "SELECT * FROM message WHERE posted_by = ? ";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setInt(1, Integer.valueOf(account_id));
            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                Message retrievedMessage = new Message(
                    resSet.getInt("message_id"),
                    resSet.getInt("posted_by"),
                    resSet.getString("message_text"),
                    resSet.getLong("time_posted_epoch")
                );
                allMessagesID.add(retrievedMessage);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return allMessagesID;
    }
}
