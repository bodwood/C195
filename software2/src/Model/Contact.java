package Model;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * FXML Model class
 * delcared fields, getters, setters
 * @author Darya Krutyeva
 */

public class Contact {
    //define variables
    private String contact_name;
    private Integer contact_id;
    private String email;

    //getters and setters
    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    //this changes the hash form of contact name to string form for list view
    public String toString(){
        return(contact_name);
    }
}