package com.axs.edu.controller;

import com.axs.edu.model.User;
import com.axs.edu.util.GlobalVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {
    public void initialize(){
        txtFirstName.requestFocus();
    }
    public AnchorPane registerFormContext;
    public TextField txtFirstName;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtLastName;
    public Button btnSignUp;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        User user = new User(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );
        try{
            // 1 step
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2 step
            Connection connection =  DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/edusmart",
                    "root",
                    "Chathuhansika@2017");

            // 3 step
            String query = "INSERT INTO `user` VALUES (?,?,?,?,?)";
            // 4 step
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,user.getRootEmail());
            preparedStatement.setString(2,user.getFirstName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setBoolean(5,true);

            if (preparedStatement.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION, "User was Saved!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch(ClassNotFoundException | SQLException e){
             new Alert(Alert.AlertType.ERROR, "Something went wrong");
             e.printStackTrace();
        }

        GlobalVar.userEmail=user.getRootEmail();
        setUi("DashboardForm");
    }

    public void alreadyHavAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    //=========================
    private void setUi(String location) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml")));
        Stage stage = (Stage) registerFormContext.getScene().getWindow();
        stage.setTitle("EduSmart");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void emailOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void passwordNextOnAction(ActionEvent actionEvent) {
        btnSignUp.requestFocus();
    }

    public void fNameNextOnAction(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void lNameNextOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }
}