package Model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * FXML Model class
 * declared fields, getters, setters
 * @author Darya Krutyeva
 */

public class Customer {
    //Declare fields
    private Integer division_id;
    private String country;
    private Integer customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone;
    static String countryForCount; //static so it stays the same accross all instances

    //getters and setters

    public Integer getDivision_ID() {
        return division_id;
    }

    public void setDivision_ID(Integer division_id) {
        this.division_id = division_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static String getCountryForCount() {
        return countryForCount;
    }

    public static void setCountryForCount(String countryForCount) {
        Customer.countryForCount = countryForCount;
    }
}