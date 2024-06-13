package com.todo;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class FrontPageController {
    UserDBManager dbManager = new UserDBManager();
    private LocalDate currentDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private String input = "";
    private String task = "";
    private String nameString = LoginPageController.userName;
    @FXML
    private ListView<String> listView;        
    @FXML
    private Label currentDateLabel;
    @FXML
    private Label userName;
    @FXML
    private Label numberOfTasks;
    @FXML
    private ObservableList<String> items;
    @FXML
    private TextField userInput;
    @FXML
    private TextField nameInput;
    @FXML
    private void initialize(){
        items = FXCollections.observableArrayList();
        List<String> userData = dbManager.loadUserData();
        if (userData != null) {
            items.addAll(userData);
        }
        //shows username
        userName.setText("User: " + nameString);
        //sets and shows current date
        currentDate = LocalDate.now();
        currentDateLabel.setText("Today's date: " + currentDate.format(formatter));
        //shows the number of tasks
        numberOfTasks.setText("Number of tasks to complete today: " + items.size());
        listView.setItems(items);
        //create mouse event listener for the list view
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ;
            }
        });

        // Add mouse click listener
        listView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            //remove a task when double click on that task
            if (event.getClickCount() == 2) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    items.remove(selectedItem);
                    dbManager.updateUserTasks(items);
                    numberOfTasks.setText("Number of tasks to complete today: " + items.size());
                }
            }

            // Check for right click, to be implemented showing task details
            if (event.getButton() == MouseButton.SECONDARY) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    System.out.println("Right-clicked item: " + selectedItem);
                }
            }
        });

        userInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                inputTask();
            }
        });

        
    }

    @FXML
    private void inputTask(){
        task = userInput.getText();
        userInput.clear();
        input = task;
        items.add(input);
        dbManager.updateUserTasks(items);
        numberOfTasks.setText("Number of tasks to complete today: " + items.size());
    }

    @FXML
    private void goToLogin() throws IOException{
        App.setRoot("login");
    }
}
