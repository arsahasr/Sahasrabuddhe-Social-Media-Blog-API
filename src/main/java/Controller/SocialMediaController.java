package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import DAO.MessageDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService(); // new instance of Msg service is created
        this.accountService = new AccountService(); // new instance of Msg service is created. 
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerNewUserHandler); // process registration of a new user.
        app.post("/login", this::verifyLoginCredentialsHandler); // verify login information (username, password) of a user.
        app.post("/messages", this::newMessageCreationHandler); // create a new message. 
        app.get("/messages", this::getAllMessagesHandler); // get all messages.
        app.get("/messages/{message_id}", this::getMessageByIDHandler); // get messages for a particular ID.
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler); // delete messages for a particular ID.
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler); // update messages for a particular ID.
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIDHandler); // all messages for a particular user.

        return app;
    }


    /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerNewUserHandler(Context ctx) throws JsonProcessingException { // function 1
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class); // retrieve the data from the ctx.body, and converts it to account type object.  
        Account newaccount = accountService.addAccount(account); // now we want to add our acct to the database. But first, we call the acct service method.
        
        if (newaccount != null) {    // if we are able to register, we return the account object for that user.
            ctx.json(mapper.writeValueAsString(newaccount));
        } 
        else { // else we give a client error.
            ctx.status(400);
        }
    }

     private void verifyLoginCredentialsHandler(Context ctx) throws JsonProcessingException { // function 2
           ObjectMapper mapper = new ObjectMapper();
           Account account = mapper.readValue(ctx.body(), Account.class); // read json body, set equal to account.
           Account verify_account = accountService.verifyAccount(account); // we need to verify acct from the database.

           if (verify_account != null) { // if we do verify, then write our verifyaccount return statement to our user.
                ctx.json(mapper.writeValueAsString(verify_account));
           }
           else {
                ctx.status(401);
           }
        
    }

    private void newMessageCreationHandler(Context ctx) throws JsonProcessingException { // function 3
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class); // 1. create msg obj from ctx.body

        // 2. check if posted_by exists in account database etc.; 3. if so, then insert message
        Message newmessage = messageService.addMessage(message);

        if (newmessage != null) {
            ctx.json(mapper.writeValueAsString(newmessage));
        }
        else {    
            ctx.status(400); // give a client error.
        }
    }

    private void getAllMessagesHandler(Context ctx) { // function 4
        List<Message> messages = messageService.getAllMessages(); 
        ctx.json(messages); // we want to receive the list, even if it is an empty list.

    }

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int msg_id = Integer.parseInt(ctx.pathParam("message_id"));  // here, we only extract the msg_id as an integer, not as a message object.  
        Message msg = messageService.getMessageById(msg_id); // we want the specific id. so we create a message object that calls the getmsg id.

        if (msg != null) {
            ctx.json(mapper.writeValueAsString(msg));
        }
        else {
            ctx.result("");
        }
    }

    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int msg_id = Integer.parseInt(ctx.pathParam("message_id"));  // here, we only extract the msg_id as an integer, not as a message object.
        Message msg = messageService.deleteMessagebyId(msg_id);

        if (msg != null) {
            ctx.json(mapper.writeValueAsString(msg));
        }
        else { // else the result is an empty string.
            ctx.result("");
        }
    }
    
    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int msg_id = Integer.parseInt(ctx.pathParam("message_id")); // we want to just get the last portion of the path.
        Message msg = mapper.readValue(ctx.body(), Message.class);
        
        String msg_text = msg.getMessage_text(); // we want the string object of message. Our message has getMessage_text() defined in the model. So, we can use that here. 
        Message update_message = messageService.updateMessagebyId(msg_id, msg_text);

        if (update_message != null) { // if not empty, then we convert the java object to json for the user
            ctx.json(mapper.writeValueAsString(update_message));
        }

        else {
            ctx.status(400);
        }

    }


    private void getAllMessagesByAccountIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int account_id = Integer.parseInt(ctx.pathParam("account_id")); // we want to retrieve the account_id as an integer from the path.
        List<Message> messages_list = messageService.getMessageByAccountId(account_id); // our messages are stored in a list object. 

        ctx.json(mapper.writeValueAsString(messages_list)); // we return this messages_list back to the user as a json object.
    }
   
}

