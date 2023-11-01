package com.lw1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Passwordphrase {
    @FXML
    private TextField phase;
    @FXML
    private Button checkPhase;
    private String encryptionKey = "qw qw1";

    @FXML
    void checkphase(ActionEvent event) throws IOException {
        checkPhase.getScene().getWindow().hide();
        if (phase.getText().equals(encryptionKey)) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 330, 250);
            Stage stage = new Stage();
            stage.setTitle("enter");
            stage.setScene(scene);
            stage.show();
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password phase");
            System.exit(0);
        }
    }
    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }
}
