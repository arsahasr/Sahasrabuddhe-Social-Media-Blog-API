package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)"; // we want to insert a message object into the database containing all columns. 
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, message.getPosted_by()); 
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());

            stmt.executeUpdate(); // updates our database
            ResultSet pkeyResultSet = stmt.getGeneratedKeys(); // generates keys (1, 2, 3, etc.)
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message"; // select all messages
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { // while our database has a row, we add a message entry to our messages list. We keep doing this until we don't have another row. 
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages; // finally return messages list (even if it's empty).
    }

    public Message getMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?"; // filters the database.

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, message_id); 

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), // with the resultset, it gets the integer at column "message_id"
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
            
                return message;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessageById(int message_id) {
        Message msg = getMessageById(message_id); // get message based on message_id

        if (msg != null) {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?";
            
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, message_id);

                stmt.executeUpdate();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return msg;
    }

    
    public Message updateMessageById(int msg_id, String msg_text) {
        
        Connection conn = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, msg_text);
            stmt.setInt(2, msg_id);

            stmt.executeUpdate(); // we update our database with the new message text we provided at that message_id.

            return getMessageById(msg_id); // here, we return this updated message object by calling the getMsgbyid function. 

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
      
    


    public List<Message> getAllMessagesByAccountId(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List <Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery(); // our query is executed on the preparedStatement.

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"), rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
            
                    messages.add(message);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            return messages;
    }
}
