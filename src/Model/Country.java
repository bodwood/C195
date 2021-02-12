package Model;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * FXML Model class
 * declared fields, getters setters
 * @author Darya Krutyeva
 */

public class Country {


    public Country getCountryObj() {
        return countryObj;
    }

    public void setCountryObj(Country countryObj) {
        this.countryObj = countryObj;
    }

    //define variables
    private Country countryObj;
    private String country;
    private Integer country_id;
    private Integer division_ID;
    private String division;

//getters and setters
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public int getDivision_ID() {
        return division_ID;
    }

    public void setDivision_ID(Integer division_ID) {
        this.division_ID = division_ID;
    }









    public void initialize(URL url, ResourceBundle resourceBundle) {
        }
        //changes hash form of country to string
        public String toString(){
        return(country);

        }
    }



