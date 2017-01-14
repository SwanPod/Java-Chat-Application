import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by rcsad on 1/14/2017.
 */
public class Controller {

    @FXML private Button sendButton;
    @FXML private TextField messageField;
    @FXML private TextArea chatArea;

    public void sendMessage(ActionEvent event){
        String message = messageField.getText();
        chatArea.appendText("\n" + message);
    }
    public void showSettings(ActionEvent event){

    }
    public void exit(){

    }
    public void showServerInfo(){

    }
    public void disconnect(){

    }

}
