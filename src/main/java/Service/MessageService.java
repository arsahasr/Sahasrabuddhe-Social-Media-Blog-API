package Service;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) { // function 1
        if ((!message.getMessage_text().isBlank()) && (message.getMessage_text().length() <= 255)) {
            return messageDAO.insertMessage(message);
        }
        else {
            return null;
        }
        
    }
    public List<Message> getAllMessages() { // function 2
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) { // function 3
        return messageDAO.getMessageById(message_id);
    }    
    
    public Message deleteMessagebyId(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessagebyId(int msg_id, String msg_text) {
        if ((!msg_text.isBlank()) && (msg_text.length() <= 255)) {   
            return messageDAO.updateMessageById(msg_id, msg_text);
    }
        else {
            return null;
        }
    
}
    public List<Message> getMessageByAccountId(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);

    }
}
