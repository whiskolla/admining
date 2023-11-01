package com.lw1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.lw1.EncryptionUtils.hashPassWithSalt;

public class LoginController{
        User currentUser = new User();
        private String encryptionKey = "qw qw1";
        Boolean isUserOk = false;
        List<User> ul = new ArrayList<>();
        int countOfErrors = 0;
        @FXML
        private PasswordField tfps;
        @FXML
        private TextField tfusr;
        @FXML
        private Button loginbtn;
        @FXML
        void clearall(ActionEvent event) {
            tfusr.setText("");
            tfps.setText("");
        }
    void logic(String usr, String pswd){
        try{
            ul = JavaPostgreSQL.getUsersFromDB();
            for(User user : ul) {
                System.out.println(user.getUsername());
                String currentPass = decryptAndShowPass(user.getPassword());

                if (usr.equals(user.getUsername()) & pswd.equals(currentPass)) {
                    isUserOk = true;
                    currentUser = user;
                    JOptionPane.showMessageDialog(null, "Password matched");
                    System.out.println("Hashed pass: " + hashPassWithSalt(currentPass));
                    countOfErrors = 0;
                    break;
                }
            }
            if(!isUserOk) {
                countOfErrors++;
                JOptionPane.showMessageDialog(null, "Incorrect username or password. Attempts left: " + (3 - countOfErrors));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(countOfErrors == 3){System.exit(0);}
    }

    @FXML
    void login(ActionEvent event) throws Exception {
            System.out.println("Logic:\n");
            //encryptAndShowPass(tfps.getText());
            logic(tfusr.getText(), tfps.getText());

        if(!currentUser.getIsBlock()){
            if(isUserOk) {
                loginbtn.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader();
                if (currentUser.getUsername().equals("admin")) {
                    fxmlLoader.setLocation(getClass().getResource("adminpage.fxml"));
                    fxmlLoader.load();
                    AdminPageController adminController = fxmlLoader.getController();
                    adminController.initUser(currentUser);
                } else {
                    fxmlLoader.setLocation(getClass().getResource("userpage.fxml"));
                    fxmlLoader.load();
                    UserPageController userController = fxmlLoader.getController();
                    userController.initUser(currentUser);
                }

                Parent root = fxmlLoader.getRoot();
                Stage st = new Stage();
                st.setScene(new Scene(root));
                st.setTitle("home page");
                st.showAndWait();
                isUserOk = false;
            }
        } else {
                JOptionPane.showMessageDialog(null, "User is block");
        }
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