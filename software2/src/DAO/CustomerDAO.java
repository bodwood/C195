package DAO;


import Model.Appointment;
import Model.Customer;
import Utility.ConnectDB;
import Utility.DBQuery;
import View_Controller.ReportsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
/**
 * FXML DAO class
 *
 * @author Darya Krutyeva
 */

public class CustomerDAO implements Initializable {

private static ObservableList<Customer> allCustomer;
    private int Division_ID;
    /**adds new customer to the sql database, generates customer id*/
    public void addCustomer (Customer customer) {
        //sql insert function to insert new customer into database
    String addCustomer = "INSERT INTO customers(customer_id,customer_name,address,postal_code,phone, division_id)" + "VALUES (NULL,?,?,?,?,?)";
    Connection conn = ConnectDB.getConnection(); //connect to database
    try {
        DBQuery.createStatement(conn); //create statement object
        Statement statement = DBQuery.getStatement(); //get Statement reference
        PreparedStatement ptmt = conn.prepareStatement(addCustomer, statement.RETURN_GENERATED_KEYS); //prepares statement and generates customer id
       //gets customer information from class
        ptmt.setString(1, customer.getCustomer_name());
        ptmt.setString(2, customer.getAddress());
        ptmt.setString(3, customer.getPostal_code());
        ptmt.setString(4, customer.getPhone());
        ptmt.setInt(5, customer.getDivision_ID());
        ptmt.executeUpdate(); //executes statement
        ResultSet rs = ptmt.getGeneratedKeys(); //generates and returns customer_id
        rs.next();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

}
/**retrieves all customers and customer info from database and adds to a list*/
public ObservableList<Customer> getAllCustomer() {
    ObservableList<Customer> allCustomer = FXCollections.observableArrayList(); //defines list
    //sql select function to retrive info from multiple tables in the database
    String queryString = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, first_level_divisions.Division_ID, countries.Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID";
    Connection conn = ConnectDB.getConnection(); //connect to database
    PreparedStatement ptst = null;
    try {
        ptst = conn.prepareStatement(queryString); //prepare statement

        ResultSet rs = ptst.executeQuery(); //execute statement
        while (rs.next()) {
            Customer getCustomer = new Customer();
            //gets info from columns and sets in Customer class
            getCustomer.setCustomer_id(rs.getInt("customer_id"));
            getCustomer.setCustomer_name(rs.getString("customer_name"));
            getCustomer.setAddress(rs.getString("Address"));
            getCustomer.setPostal_code(rs.getString("postal_code"));
            getCustomer.setPhone(rs.getString("phone"));
            getCustomer.setDivision_ID(rs.getInt("Division_ID"));
            getCustomer.setCountry(rs.getString("Country"));

            allCustomer.add(getCustomer); //adds retrieved info to Customer list
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    //returns customer list
    return allCustomer;
}


/**updates existing customer in the database*/
public  void updateCustomer(Customer customer){
        //sql update function to update information for an existing customer
        String updateString ="UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(updateString); //prepare statement
            //defines parameters to be updated
            psts.setString(1, customer.getCustomer_name());
            psts.setString(2,customer.getAddress());
            psts.setString(3,customer.getPostal_code());
            psts.setString(4,customer.getAddress());
            psts.setInt(5,customer.getDivision_ID());
            psts.setInt(6,customer.getCustomer_id());
            psts.executeUpdate(); //executes statement

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**this counts the customers per Country, gets row count*/
    public int getCustomerCount() {

        //sql select function to retrieve row count from customers based on country
        String queryString = "SELECT COUNT(*) AS rowcount FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID AND countries.Country=?";
        Connection conn = ConnectDB.getConnection(); //connect to database

        int custCount = 0; //set initial row count and define the integer
        Customer cust = new Customer();

        try {
            PreparedStatement ptst = conn.prepareStatement(queryString); //prepare statement
            ptst.setString(1, cust.getCountryForCount()); //define country variable to base row count on

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                custCount = rs.getInt("rowcount"); //retrieves the row count


            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns row count, or customers per selected country
        return custCount;



    }
    /** customer from database*/
    public void deleteCustomer (Customer customer){
        //sql detele function to remove customer from database based on defined customer id
        String deleteString2 = "DELETE from customers WHERE Customer_ID = ?";
        Connection conn = ConnectDB.getConnection(); //connect to database
        try{


            PreparedStatement ptt = conn.prepareStatement(deleteString2); //preparestatement
            ptt.setInt(1,customer.getCustomer_id()); //defines customer id parameter aka the selected customer
            ptt.executeUpdate(); //executes statement


        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}


