package com.example.demo7;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button LogInBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordLoginTF;

    @FXML
    private TextField usernameLoginTF;

    private Service service;

    public void setService(Service service) throws IOException{
        this.service = service;
    }

    @FXML
    private void onClickLogin(ActionEvent actionEvent) throws IOException {
        if(!service.checkUserExists(usernameLoginTF.getText(), passwordLoginTF.getText())){
            errorLabel.setText("Invalid credentials!");
            return;
        }

        User loggedInUser = service.findLoggedInUser(usernameLoginTF.getText(), passwordLoginTF.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/MatchesUI.fxml"));
        Parent root = loader.load();
        UserController userViewController = loader.getController();
        userViewController.setLoggedInUser(loggedInUser);
        userViewController.setService(service);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Hello, " + loggedInUser.getUsername() + "");
        stage.show();
        //userViewController.initModel();
        userViewController.initialize();
        Stage thisStage = (Stage) LogInBtn.getScene().getWindow();
        //thisStage.close();
    }
}
