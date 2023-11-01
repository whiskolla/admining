package com.lw1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OneUserViewController {
    String Username, Password, isBlock, isPswd;
    int ln;
    User selectedUser = new User();
    @FXML
    private Label usernameLabel;
    @FXML
    private CheckBox block;
    @FXML
    private CheckBox restpswd;
    @FXML
    private Button cancelbtn;
    @FXML
    private Button savebtn;
    public void initDate(User user) throws IOException {
        selectedUser = user;
        usernameLabel.setText(selectedUser.getUsername());

        if (!selectedUser.getIsBlock()) {
            block.setSelected(false);
        } else {
            block.setSelected(true);
        }

        if (!selectedUser.getIsPswd()) {
            restpswd.setSelected(false);
        } else {
            restpswd.setSelected(true);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        cancelbtn.getScene().getWindow().hide();
    }

    @FXML
    void save(ActionEvent event) {
        savebtn.getScene().getWindow().hide();
    }

    @FXML
    void changeIsBlock(ActionEvent event) {
        selectedUser.setIsBlock(!selectedUser.getIsBlock());
        JavaPostgreSQL.ChangeBlockInDB(selectedUser.getUsername(), selectedUser.getIsBlock());
    }

    @FXML
    void changeIsPswd(ActionEvent event) {
        selectedUser.setIsPswd(!selectedUser.getIsPswd());
        JavaPostgreSQL.ChangeIsPswdInDB(selectedUser.getUsername(), selectedUser.getIsPswd());
    }

}
