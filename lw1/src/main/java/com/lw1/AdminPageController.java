package com.lw1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminPageController {
    @FXML
    private Button close;
    private String encryptionKey = "qw qw1";
    List<User> ul = new ArrayList<>();
    @FXML
    private Button logbtn;
    @FXML
    private Button changePswdbtn;
    @FXML
    private Button utbtn;
    @FXML
    private PasswordField oldpswd;
    @FXML
    private PasswordField newpswd;
    @FXML
    private PasswordField newpswd2;
    User currentUser = new User();
    public void initUser(User user){
        currentUser = user;
    }
    void confirmpsd() {
        String oldDecrPass = decryptAndShowPass(currentUser.getPassword());
        System.out.println(oldDecrPass);
        if (oldDecrPass.equals(oldpswd.getText())) {
            if (newpswd2.getText().equals(newpswd.getText())) {
                String newEncPass = encryptAndShowPass(newpswd.getText());
                JavaPostgreSQL.UpdatePswToDB(currentUser.getUsername(), newEncPass);
                JOptionPane.showMessageDialog(null, "Password changed");
                newpswd.setText("");
                newpswd2.setText("");
                oldpswd.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Passwords don't match");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wrong old password");
        }
    }

    @FXML
    void changePSWD(ActionEvent event) {
        confirmpsd();
    }

    @FXML
    void userTable(ActionEvent event) throws IOException {
        utbtn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("usertable.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 350);
        Stage stage = new Stage();
        stage.setTitle("all users");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void log(ActionEvent event) throws IOException {
        logbtn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 330, 250);
        Stage stage = new Stage();
        stage.setTitle("enter");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    String encryptAndShowPass(String originalPass) {
        String encryptedText = null;
        try {
            encryptedText = EncryptionUtils.encrypt(originalPass, encryptionKey);

            System.out.println("Original Password: " + originalPass);
            System.out.println("Encrypted Password: " + encryptedText);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return encryptedText;
    }

    String decryptAndShowPass(String ecryptedPass) {
        String decryptedText = null;
        try {
            decryptedText = EncryptionUtils.decrypt(ecryptedPass, encryptionKey);

            System.out.println("Encrypted Password: " + ecryptedPass);
            System.out.println("Original Password: " + decryptedText);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return decryptedText;
    }
}
