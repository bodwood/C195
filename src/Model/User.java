package Model;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * FXML Model class
 * declared fields getters setters
 *
 * @author Darya Krutyeva
 */

public class User implements Initializable {
    //declare fields
    private Integer user_id;
    private String username;
    private String password;

//getters and setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //returns string value of user id instead of hash
    public String toString() {
        return String.valueOf(user_id);
    }
}
