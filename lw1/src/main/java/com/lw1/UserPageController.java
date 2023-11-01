package com.lw1;

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

import javax.swing.*;
//import java.awt.*;
import java.io.IOException;

public class UserPageController {
    private String encryptionKey = "qw qw1";
    @FXML
    private Button close;
    @FXML
    private Button logbtn;
    @FXML
    private PasswordField oldpswd;
    @FXML
    private PasswordField newpswd;
    @FXML
    private PasswordField newpswd2;
    @FXML
    private Label lim;

    User currentUser = new User();
    public void initUser(User user){
        currentUser = user;
        if (currentUser.getIsPswd()) {
            lim.setVisible(false);
        } else{
            lim.setVisible(true);
        }
    }

    @FXML
    void changePSWD(ActionEvent event) {
        String oldDecrPass = decryptAndShowPass(currentUser.getPassword());
        System.out.println(oldDecrPass);
        if (oldDecrPass.equals(oldpswd.getText())) {
            if (newpswd2.getText().equals(newpswd.getText())) {
                if (!currentUser.getIsPswd()) { //без ограничений
                    changepsd();
                    currentUser.setPassword(newpswd.getText());
                } else {
                    limitpass();
                    currentUser.setPassword(newpswd.getText());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords don't match");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wrong old password");
        }
    }
    @FXML
    void log(ActionEvent event) throws IOException {
        logbtn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.showAndWait();
    }
    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    void changepsd() {
        String newEncPass = encryptAndShowPass(newpswd.getText());
        JavaPostgreSQL.UpdatePswToDB(currentUser.getUsername(), newEncPass);
        JOptionPane.showMessageDialog(null, "Password changed");
        newpswd.setText("");
        newpswd2.setText("");
        oldpswd.setText("");
    }

    //Наличие букв и знаков арифметических операций
    public void limitpass(){
        if((containsLetters(newpswd.getText())) && (containsSigns(newpswd.getText())) ){
            changepsd();
        } else {
            JOptionPane.showMessageDialog(null, "Password don't respond to limits");
        }
    }
    public static boolean containsLetters(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (Character.isLetter(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsSigns(String string) {
        String signs = "+-/*%^";
        for (int i = 0; i < string.length(); ++i) {
            String c = Character.toString(string.charAt(i));
            if (signs.contains(c)) {
                return true;
            }
        }
        return false;
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