package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    // public MessageService(MessageDAO messageDAO){
    //     this.messageDAO = messageDAO;
    //     accountDAO = new AccountDAO();
    // }

    // public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
    //     this.messageDAO = messageDAO;
    //     this.accountDAO = accountDAO;
    // }

    public Message addMessage(Message message){
        if(accountDAO.getAccountByUID(message.getPosted_by()) != null){
            if(!(message.getMessage_text().equals("")) && (message.getMessage_text().length() <= 255 ))
            {
                return messageDAO.addMessage(message);
            }
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMID(String message_id){
        return messageDAO.getMessageByMID(message_id);
    }

    public Message deleteMessage(String message_id){
        Message retrievedMessage = messageDAO.getMessageByMID(message_id);
        
        if(retrievedMessage.getMessage_text() != null){
            messageDAO.deleteMessage(message_id);
        }
        
        return retrievedMessage;
    }

    public Message updateMessage(String message_id, Message newMessage){
        Message retrievedMessage = messageDAO.getMessageByMID(message_id);

        //Check if message doesnt exist and if the new message is too long or empty
        if((retrievedMessage.getMessage_text() != null) && 
            !(newMessage.getMessage_text().equals("")) && 
            (newMessage.getMessage_text().length() <= 255 ))   
        {
            messageDAO.updateMessage(message_id, newMessage);
            retrievedMessage = messageDAO.getMessageByMID(message_id);
        }else{
            retrievedMessage = null;
        }

        return retrievedMessage;
        
    }

    public List<Message> getAllMessagesByUID(String account_id){
        return messageDAO.getAllMessagesByUID(account_id);
    }
}
