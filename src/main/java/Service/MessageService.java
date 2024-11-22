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

    public Message deletMessage(){
        return null;
    }

    public List<Message> getAllMessagesByUID(Account account){
        return messageDAO.getAllMessagesByUID(account);
    }
}
