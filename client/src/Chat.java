import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class Chat extends Application{

    private static AESEncryption encryption = new AESEncryption();

    @FXML private TextField ip;
    @FXML private TextField username;
    @FXML private Label errorLabel;
    @FXML private TextField messageField;
    @FXML private TextArea chatArea;
    private static Stage settings;
    public static String ipa;
    public static String user;
    public static DataOutputStream out;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage loginStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login_layout.fxml"));
        loginStage.setTitle("SwanPod Chat - Connect");
        loginStage.getIcons().add(new Image("logo.png"));
        Scene scene = new Scene(root, 600, 400);
        loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loginStage.setScene(scene);
        loginStage.show();
    }
    public void connect(ActionEvent e) throws Exception {
        ipa = ip.getText();
        user = username.getText();
        if (Objects.equals(ipa, "")){
            if(Objects.equals(user, "")){
                errorLabel.setText("Please enter an IP address and username.");
            }else{
                errorLabel.setText("Please enter an IP address.");
            }
        }else if(ip.getText().length() > 1) {
            if (Objects.equals(user, "")) {
                errorLabel.setText("Please enter a username.");
            }else{
                if (Objects.equals(ip, "localhost")) {
                    ipa = "127.0.0.1 (localhost)";
                    netConnect(ipa);
                } else {
                    ipa = ip.getText();
                    netConnect(ipa);
                }
            }
        }
    }
    public void netConnect(String serverName) throws Exception {
        try{
            Socket client = new Socket(serverName, 4444);
            OutputStream toServer = client.getOutputStream();
            out = new DataOutputStream(toServer);
            InputStream fromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(fromServer);
            chatArea.appendText("\n" + AESEncryption.decrypt(in.readUTF()));
            openChat();
        }catch (IOException ec){
            ec.printStackTrace();
        }

    }
    public void openChat() throws IOException {
        Stage chatStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("main_chat.fxml"));
        chatStage.setTitle("Connected - " + ipa + " | Username - " + user);
        chatStage.getIcons().add(new Image("logo.png"));
        Scene scene = new Scene(root, 1280, 800);
        chatStage.setScene(scene);
        chatStage.show();
        ip.getScene().getWindow().hide();
        chatStage.setOnCloseRequest(event1 -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void sendMessage() throws Exception {
        encryption = new AESEncryption();
        String message = messageField.getText();
        String encMessage = AESEncryption.encrypt(message);
        chatArea.appendText("\n" + message);
        //out.writeUTF(encMessage);
        messageField.setText("");
    }
    public void showSettings(ActionEvent e) throws IOException {
        settings = new Stage();
        settings.setTitle("Settings");
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene scene = new Scene(root, 800, 600);
        settings.setScene(scene);
        settings.show();
    }
    public void changeSettings(ActionEvent e){

    }
    public void closeWindow(ActionEvent e){
        settings.hide();
    }
    public void exit(ActionEvent e){

    }
    public void showServerInfo(ActionEvent e) throws IOException {
        //String ip = Chat.ipa;
        Stage serverInfo = new Stage();
        serverInfo.setTitle("Server Information");
        Parent root2 = FXMLLoader.load(getClass().getResource("server_info.fxml"));
        Scene scene2 = new Scene(root2, 640, 400);
        //ipLabel.setText("Server IP Address: " + ip);
        serverInfo.setScene(scene2);
        serverInfo.show();
    }
    public void disconnect(ActionEvent e){

    }
    /*public void readKey(ActionEvent e){

    }*/
    public static void main(String[] args){
        launch(args);
    }
}