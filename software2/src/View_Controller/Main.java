package View_Controller;

import Utility.ConnectDB;
import Utility.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {
    /** Main class*/

    @Override
    public void start(Stage stage) throws Exception{
        //loads login screen upon opening of program

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();



    }


    public static void main(String[] args) throws SQLException {


        Connection conn = ConnectDB.getConnection(); //connect to database
        DBQuery.createStatement(conn); //create statement object
        launch(args);
        ConnectDB.closeConnection(); //close connection

    }

}
