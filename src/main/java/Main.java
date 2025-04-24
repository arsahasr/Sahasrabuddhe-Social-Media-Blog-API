import Controller.SocialMediaController;
import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */

 public class Main {
    public static void main(String[] args) {
        // create social media controller object. this object listens on a port and responds to
        // login/password actions
        // databaseSetup();
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}