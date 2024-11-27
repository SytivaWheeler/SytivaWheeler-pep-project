package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;

import Util.ConnectionUtil;

public class MessageDAO {

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

    //Gets messages by their message ID 
    public Message getMessageByMID(String message_id){
        Connection con = ConnectionUtil.getConnection();
        Message message = new Message();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ? ";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setInt(1, Integer.valueOf(message_id));

            ResultSet resSet = prepStmnt.executeQuery();

            while(resSet.next()){
                message = new Message(
                    resSet.getInt("message_id"),
                    resSet.getInt("posted_by"),
                    resSet.getString("message_text"),
                    resSet.getLong("time_posted_epoch")
                );
            }

            if(resSet.getRow() == 0){
                return message;
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

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

    public int updateMessage(String message_id, Message newMessage){
        Connection con = ConnectionUtil.getConnection();
        int affectedRows = 0;

        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement prepStmnt = con.prepareStatement(sql);

            prepStmnt.setString(1, newMessage.getMessage_text());
            prepStmnt.setInt(2, Integer.valueOf(message_id));

            affectedRows = prepStmnt.executeUpdate();
            
            if(affectedRows == 0){
                System.out.println("nothing changed....");
            }else{
                System.out.println(affectedRows + " row(s) affected!!!");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    //Gets all messages by a specific user using their user id
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
