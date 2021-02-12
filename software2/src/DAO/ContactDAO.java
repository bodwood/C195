package DAO;

import Model.Contact;
import Model.Country;
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

public class ContactDAO implements Initializable {

    /**creates list for available contacts*/
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList(); //defines list
        //sql query function to select all contacts
        String queryString = "SELECT * FROM contacts";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                Contact getContact = new Contact();
                //set variables
                getContact.setContact_id(rs.getInt("contact_id"));
                getContact.setContact_name(rs.getString("contact_name"));
                getContact.setEmail(rs.getString("email"));
                allContacts.add(getContact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list of all contacts
        return allContacts;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
