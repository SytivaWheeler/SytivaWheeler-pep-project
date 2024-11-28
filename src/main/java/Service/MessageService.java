package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    /*
     *  Empty param constructor for a message service object.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    /*
     *  Constructor for a message service object with both account DAO and 
     *  message DAO as parameters.
     */
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    /*
     *  Returns message objcect with the message that was just added to the DB.
     */
    public Message addMessage(Message message){
        if(accountDAO.getAccountByUID(message.getPosted_by()) != null){
            if(!(message.getMessage_text().equals("")) && 
            (message.getMessage_text().length() <= 255 ))
            {
                return messageDAO.addMessage(message);
            }
        }
        return null;
    }

    /*
     *  Returns a list of all messages in the DB. All list elements are
     *  message objects.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /*
     *  Returns a message object containing the message if it existed in
     *  the DB, returns a null message object otherwise.
     */
    public Message getMessageByMID(String message_id){
        return messageDAO.getMessageByMID(message_id);
    }

    /*
     *  Returns message object containing the now deleted message if it existed 
     *  in the DB. Returns an empty new message object otherwise.
     */
    public Message deleteMessage(String message_id){
        Message retrievedMessage = messageDAO.getMessageByMID(message_id);
        
        if(retrievedMessage != null){
            messageDAO.deleteMessage(message_id);
        }
        
        return retrievedMessage;
    }

    /*
     *  Returns message object containing updated message if said message exists, 
     *  returns a null message object if the message to be updated doesnt exist and 
     *  the new message text given is either empty or too long.
     */
    public Message updateMessage(String message_id, Message newMessage){
        Message retrievedMessage = messageDAO.getMessageByMID(message_id);

        if((retrievedMessage != null) && 
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

    /*
     *  Returns a list of messages from a specific user using their account id.
     *  All list elements are message objects.
     */
    public List<Message> getAllMessagesByUID(String account_id){
        return messageDAO.getAllMessagesByUID(account_id);
    }
}
