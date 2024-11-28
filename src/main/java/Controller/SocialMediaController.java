package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

/**
 * You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accService;
    MessageService msgService;

    public SocialMediaController(){
        this.accService = new AccountService();
        this.msgService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/sample", this::exampleHandler);

        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUID);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /*
     *  Handler for registering accounts. Returns 400 status if it fails.
     */
    private void postRegisterAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accService.addAccount(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }

    }

    /*
     *  Handler for logging in. Returns 401 status code if it fails
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account checkedAccount = accService.loginCheck(account);
        if(checkedAccount != null){
            ctx.json(mapper.writeValueAsString(checkedAccount));
        }else{
            ctx.status(401);
        }
    }

    /*
     *  Handler for posting new messages to the DB. Returns 400 status if it fails.
     */
    private void postNewMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = msgService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    /*
     *  Handler for retrieving all messages in the DB.
     */
    private void getAllMessagesHandler(Context ctx){
        List<Message> allMessagesList = msgService.getAllMessages();
        ctx.json(allMessagesList);
    }

    /*
     *  Handler for retrieving messages by their message id. Returns 
     *  empty json if it fails. 
     */
    private void getMessageByMIDHandler(Context ctx){
        Message message = msgService.getMessageByMID(ctx.pathParam("message_id"));
        if(message != null){
            ctx.json(message);
        }else{
            ctx.json("");
        }
        
    }

    /*
     *  Handler for deleting messages from the DB. Returns 
     *  empty json if it fails.
     */
    private void deleteMessageHandler(Context ctx){
        Message message = msgService.deleteMessage(ctx.pathParam("message_id"));
        if(message != null){
            ctx.json(message);
        }else{
            ctx.json("");
        }
    }

    /*
     *  Handler for updating messages in the DB. Returns 400 status
     *  if it fails.
     */
    private void updateMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message resultMessage = msgService.updateMessage(ctx.pathParam("message_id"), newMessage);
        if(resultMessage != null){
            ctx.json(mapper.writeValueAsString(resultMessage));
        }else{
            ctx.status(400);
        }


    }

    /*
     *  Handler for retrieving all messages made by a specific user using their user ID.
     */
    private void getAllMessagesByUID(Context ctx){
        List<Message> allMessagesByUIDList = msgService.getAllMessagesByUID(ctx.pathParam("account_id"));
        ctx.json(allMessagesByUIDList);
    }
}