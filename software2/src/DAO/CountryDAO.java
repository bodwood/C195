package DAO;

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

public class CountryDAO implements Initializable {



    /**retrieves all countries from database and adds to list*/
    public ObservableList<Country> getAllCountry() {
        ObservableList<Country> allCountry = FXCollections.observableArrayList(); //defines observable list
        //sql query function to select all information from countries table
        String queryString = "SELECT * FROM countries";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                Country getCountry = new Country();
                getCountry.setCountry(rs.getString("Country"));
                getCountry.setCountry_id(rs.getInt("country_id"));
                allCountry.add(getCountry); //adds retrieved info to list
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return allCountry;
    }


    /**this retrieves all states in the US and adds them to list*/
    public ObservableList<Country> getUsDivision(){
        ObservableList<Country> usDivision = FXCollections.observableArrayList(); //defines list
        //sql query function to retrieve all information from first level divisions table where the country is US
        String queryString = "SELECT * FROM first_level_divisions WHERE first_level_divisions.COUNTRY_ID = 1";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(queryString); //prepare statement
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Country getUsDivision = new Country();
                getUsDivision.setDivision_ID(rs.getInt("Division_ID"));
                getUsDivision.setDivision(rs.getString("Division"));
                usDivision.add(getUsDivision); //adds retrieved info to list
            }
        }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        //returns list
            return usDivision;

        }
        /**this retrives all divisions in the UK and adds them to a list*/
    public ObservableList <Country> getUkDivision(){
        ObservableList<Country> ukDivision = FXCollections.observableArrayList(); //defines list
        //sql query function to retrive all divisions from first_level_divisions where the country is U.K
        String queryString = "SELECT * FROM first_level_divisions WHERE first_level_divisions.COUNTRY_ID = 2";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(queryString); //prepare statement
            ResultSet rs = ptmt.executeQuery(); //execute statement
            while (rs.next()) {
                Country getUkDivision = new Country();
                getUkDivision.setDivision_ID(rs.getInt("Division_ID"));
                getUkDivision.setDivision(rs.getString("Division"));
                ukDivision.add(getUkDivision); //adds retrieve info to list
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return ukDivision;
    }

    /**this retrieves all divisions in Canada and adds them to a list*/
    public ObservableList <Country> getCaDivision(){
        ObservableList<Country> caDivision = FXCollections.observableArrayList(); //define list
        //sql query function to retrives all divisions from Canada
        String queryString = "SELECT * FROM first_level_divisions WHERE first_level_divisions.COUNTRY_ID = 3";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(queryString); //prepare statement
            ResultSet rs = ptmt.executeQuery(); //execute statement
            while (rs.next()) {
                Country getCaDivision = new Country();
                getCaDivision.setDivision_ID(rs.getInt("Division_ID"));
                getCaDivision.setDivision(rs.getString("Division"));
                caDivision.add(getCaDivision); //adds info to the list
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return caDivision;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
