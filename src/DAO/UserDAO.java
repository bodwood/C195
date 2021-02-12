package DAO;

import Model.User;
import Utility.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * FXML DAO class
 *
 * @author Darya Krutyeva
 */
public class UserDAO implements Initializable {




    /**sets user id for the logged in user using the application, gets user id from database*/
    public int getCurrentUser(){
        ObservableList<User> currentUser = FXCollections.observableArrayList();
        //sql query to select the user_id based on entered user name
        String queryString = "SELECT * FROM users WHERE user_name = user_name";
        Connection conn = ConnectDB.getConnection(); //establish connection
        PreparedStatement ps = null;
        int user_ID = -1; //defines initial user id integer (no user)

        try{

            ps = conn.prepareStatement(queryString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //retrieves and sets current user id
               user_ID =  rs.getInt("User_ID");
            }
        }
     catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        //returns user id for current user
        return user_ID;
    }
    /**check for correct log in info before allowing log in, checks against database*/
    public boolean validateUser(String username, String password) {
        //sql query string to select test user
        String userValidate = "SELECT * FROM users WHERE user_name = 'test' AND password = 'test'";

        Connection conn = ConnectDB.getConnection(); //establish connect
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(userValidate); //prepare statement
            ResultSet rs = psts.executeQuery(userValidate); //execute statement
             {
                rs.next();
                //validates if entered username and password matches the database
                if(rs.getString("password").equals(password)) {if(rs.getString("user_name").equals(username)) {
                    return true;
                }
             }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns false if doesn't match
        return false;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
