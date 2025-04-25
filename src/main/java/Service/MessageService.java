package Service;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();  // a new instance of the messagedao class is created. 
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO; 
    }

    public Message addMessage(Message message) { // we get a message object as an argument. 
        if ((!message.getMessage_text().isBlank()) && (message.getMessage_text().length() <= 255)) { // conduct business logic: proceed if message_text is not blank, and message_text length is <= 255.
            return messageDAO.insertMessage(message); // then we call the insert message method in the DAO
        }
        else {
            return null; // else we return empty. 
        }
        
    }
    public List<Message> getAllMessages() { // function 2
        return messageDAO.getAllMessages(); // call getallmsgs in DAO. 
    }

    public Message getMessageById(int message_id) { // function 3
        return messageDAO.getMessageById(message_id); // call getmsg by id function in DAO
    }    
    
    public Message deleteMessagebyId(int message_id) {
        return messageDAO.deleteMessageById(message_id); // call delete msg by id function in DAO.
    }

    public Message updateMessagebyId(int msg_id, String msg_text) { // we get a message id (int) and a msg_text (string) as two arguments.
        if ((!msg_text.isBlank()) && (msg_text.length() <= 255)) {   // conduct our business logic here. 
            return messageDAO.updateMessageById(msg_id, msg_text); // if true, we call our update msg in DAO with these two arguments provided from above. 
    }
        else {
            return null; // else return empty. 
        }
    
}
    public List<Message> getMessageByAccountId(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id); // call getallmsgs in DAO.
    }
}
