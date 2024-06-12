package com.todo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.IOException;

public class LoginPageController {
    public static String userName;
    private String userPassword;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userPasswordField;
    @FXML
    private Label status;
    UserDBManager dbManager = new UserDBManager();

    @FXML
    private void initialize(){
        //create mouse event listener for the list view
        userNameField.setOnMouseClicked(event ->{
            status.setText("");
        });
    }

    //check if usn and pwd exist
    //if yes, load the main page and their data (tasks, name, date)
    //if not, display warnings and allow users to re enter their credentials
    @FXML
    private void signIn() throws IOException{
        userName = userNameField.getText();
        userPassword = userPasswordField.getText();
        if(dbManager.validateUser(userName, userPassword) == true){
            App.setRoot("primary");
        }else{
            status.setText("Invalid username or password. Please try again.");
        }
    }

    @FXML
    private void signUp(){
        userName = userNameField.getText();
        userPassword = userPasswordField.getText();
        if(dbManager.addUser(userName, userPassword) == true){
            status.setText("Account created successfully! Click sign in.");
        }else if(dbManager.userExisted == true){
            status.setText("Username already existed, please choose another one.");
        }else{
            status.setText("Internal error encountered. Please try again.");
        }
    }
}
