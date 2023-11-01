package com.lw1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;

public class UsertableController {
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> usernameColumn = new TableColumn<>("username");
    @FXML
    private TextField newusername;
    @FXML
    private Button btnOpen;
    @FXML
    private Button backbtn;

    @FXML
    void register(ActionEvent event) {
        JavaPostgreSQL.writeNewUserToDB(newusername.getText());
    }

    @FXML
    void changeScenetoSelectedUser() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("oneuserview.fxml"));
        Parent tableViewParent = fxmlLoader.load();
        Scene tableViewScene = new Scene(tableViewParent);

        OneUserViewController controller = fxmlLoader.getController();
        controller.initDate(tableUsers.getSelectionModel().getSelectedItem());

        Stage st = new Stage();
        st.setScene(tableViewScene);
        st.setTitle("user page");
        st.showAndWait();
    }


    @FXML
    private void initialize() throws IOException {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tableUsers.getItems().addAll(JavaPostgreSQL.getUsersFromDB());
    }

    @FXML
    void back() throws IOException {
        backbtn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("adminpage.fxml"));
        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.setTitle("home page");
        st.showAndWait();
    }
}
