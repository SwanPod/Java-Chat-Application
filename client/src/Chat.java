import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Chat extends Application{

    @FXML private TextField ip;
    @FXML private TextField username;
    @FXML private PasswordField pass;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_layout.fxml"));

        primaryStage.setTitle("SwanPod Chat");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void connect(ActionEvent event) throws IOException {
        ip.getScene().getWindow().hide();

        Stage chatStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("main_chat.fxml"));

        String ipa;

        if(ip.getText().equals("localhost")){
            ipa = "127.0.0.1 (localhost)";
        }else{
            ipa= ip.getText();
        }

        String user = username.getText();

        chatStage.setTitle("Connected - " + ipa + " | Username - " + user);

        Scene scene = new Scene(root, 1280, 800);
        chatStage.setScene(scene);
        chatStage.show();

        chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
