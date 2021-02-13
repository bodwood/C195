package View_Controller;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * FXML Controller class
 *
 * @author Darya Krutyeva
 */

public class CustomersController implements Initializable {

    public static Customer selectedCust;
    Stage stage;
    Parent scene;
    @FXML
    private TableView<Customer> customerTblView;
    @FXML
    private TableColumn<Customer, Integer> customerIdclmn;
    @FXML
    private TableColumn<Customer, String> customerNameClmn;
    @FXML
    private TableColumn<Country, String> regionClmn;
    @FXML
    private TableColumn<Country, String> countryClmn;
    @FXML
    private TableColumn<Customer, String> phoneClmn;

    @FXML
    private Button saveCstBttn;

    @FXML
    private TextField customerIdTxtFld;
    @FXML
    private TextField nameTxtFld;
    @FXML
    private TextField addressTxtFld;
    @FXML
    private TextField postalTxtBox;
    @FXML
    private TextField phoneBox;

    @FXML
    private ComboBox<Country> stateBox;
    @FXML
    private ComboBox<Country> countryCmboBox;
    @FXML
    private TableColumn<String, Customer> postalHndlr;

    /** initializes class, sets columns, comboboxes, tableview*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set columns
        customerIdTxtFld.setText("auto-generated");
        customerIdclmn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerNameClmn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        regionClmn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        countryClmn.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        phoneClmn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        postalHndlr.setCellValueFactory(new PropertyValueFactory<>("postal_code"));

        //set country combobox
        setCountryCombo();
        //set customer table view
        updateCstTbv();
    }
    //set customer tableview from list of all customers
    public void updateCstTbv (){
        CustomerDAO cstDAO = new CustomerDAO();
        customerTblView.setItems(cstDAO.getAllCustomer());

    }
/**set country and division comboboxes, add listener to country combobox to change statebox based on selection, includes lambda to translate combobox from hash to string*/
public void setCountryCombo (){
        CountryDAO cntryDao = new CountryDAO();
    countryCmboBox.setItems(cntryDao.getAllCountry()); //sets country combobox with list of country items
    //listener for country combobox to change state box based on selection
    countryCmboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, country, t1) -> {
        switch (t1.toString()){
            case "":
                stateBox.getSelectionModel().clearSelection(); //if nothing is selected statebox is empty
                break;

            case "U.S":
                stateBox.setItems(cntryDao.getUsDivision()); //if us is selected statebox filled with UsDivision list
                break;
            case "UK":
                stateBox.setItems(cntryDao.getUkDivision()); //if uk selected statebox filled with uk division list
                break;
            case "Canada":
                stateBox.setItems(cntryDao.getCaDivision()); //if canada is selected statebox is filled with canada division list
                break;

        }
    });


    //lambda in order to convert hash format to string for proper display in the comboboxes
    //Factory for the List Cells
    Callback<ListView<Country>, ListCell <Country>> fctory = lv -> new ListCell<Country>() {
        @Override
        protected void updateItem(Country item, boolean empty) {
            super.updateItem(item,empty);
            setText(empty ? "" : item.getDivision() + " " + (empty ? " ":item.getDivision_ID()));
        }
    };
// Different Factory for button cell
    Callback <ListView<Country>, ListCell<Country>> fctoryused = lv -> new ListCell<Country>() {
        @Override
        protected void updateItem(Country item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? "" : item.getDivision());
        }
    };
//note separate factories used
    stateBox.setCellFactory(fctory);
    stateBox.setButtonCell(fctoryused.call(null));
}
    /**handles click on back button and confirms, loads main menu*/

    @FXML
    void backBttnHndlr(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation alert to see if user would like to exit to main menu
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to go back?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if user clicks ok main menu is opened
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(result.get() == ButtonType.CANCEL) {
            // if user clicks cancel user remains at current window
            alert.close();
        }
    }

    /**delete button click handler, confirms deletion, deletes associated appointments and customer*/

    @FXML
    void deleteBttnHndlr(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //shows alert to confirm whether user would like to delete selected customer and all associated appointments
                alert.setHeaderText("ARE YOU SURE");
        alert.setContentText("Confirm to delete customer\n"  +"and associated appointments");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if user clicks ok then appointments and customer is deleted
            Customer deleteCust = new Customer(); //new instance
            CustomerDAO dltCst = new CustomerDAO(); //new instance
            //sets selected customer
            deleteCust.setCustomer_id(customerTblView.getSelectionModel().getSelectedItem().getCustomer_id());
            deleteCust.setPhone(customerTblView.getSelectionModel().getSelectedItem().getPhone());
            Appointment delAppt = new Appointment();
            delAppt.setCustomer_ID(customerTblView.getSelectionModel().getSelectedItem().getCustomer_id());

            AppointmentDAO deleteappt = new AppointmentDAO(); //deletes appointments associated with customer
            deleteappt.deleteAllAppointment(delAppt);
            dltCst.deleteCustomer(deleteCust); //deletes selected customer
            updateCstTbv(); //updates table view sans deleted customer
        }
        if(result.get() == ButtonType.CANCEL) {
            //if cancel is clicked then nothing happens
            alert.close();
        }

    }

    /**click on table sets fields, changes button from save to update to reflect that this will be an updated customer*/

    @FXML
    void tablecClickCntrl(MouseEvent event) {
        saveCstBttn.setText("UPDATE"); //changes button from save to update to reflect that this will be an update on existing customer

        selectedCust = customerTblView.getSelectionModel().getSelectedItem(); //get selected customer
        customerIdTxtFld.setText(String.valueOf(selectedCust.getCustomer_id()));
        nameTxtFld.setText(selectedCust.getCustomer_name());
        phoneBox.setText(selectedCust.getPhone());
        addressTxtFld.setText(selectedCust.getAddress());
        postalTxtBox.setText(selectedCust.getPostal_code());
        countryCmboBox.setPromptText(selectedCust.getCountry() +" " + "click to set");
        stateBox.setPromptText(String.valueOf(selectedCust.getDivision_ID() + " " + "click to set"));




    }
    /**click to clear fields*/

    @FXML
    void clrBtnHndlr(ActionEvent event) {
    customerIdTxtFld.clear();
    nameTxtFld.clear();
    phoneBox.clear();
    addressTxtFld.clear();
    postalTxtBox.clear();
    customerTblView.getSelectionModel().clearSelection();
    stateBox.getSelectionModel().clearSelection();
    stateBox.setPromptText("STATE");
    countryCmboBox.getSelectionModel().clearSelection();
    countryCmboBox.setPromptText("COUNTRY");
    saveCstBttn.setText("SAVE");
    }
    /**check to see if all fields are filled in order to save a valid customer, returns alerts if any info is missing*/
    private boolean checkFields() {
        //retrieves text from fields
        String Customer_Name = nameTxtFld.getText();
        String Address = addressTxtFld.getText();
        String Postal_Code = postalTxtBox.getText();
        String Phone = phoneBox.getText();
        Country Division_ID = stateBox.getValue();
        Country Country = countryCmboBox.getValue();
        String errorText = "";
//checks if there is a customer name
        if (Customer_Name == null) {
            errorText += "There must be a name\n"; //if so then this error pops up
        }
        //checks if title is not filled
        if (Address == null) {
            errorText += "There must be a title\n";
        }
        //checks if Postal_Code is not filled
        if (Postal_Code == null) {
            errorText += "There must be a postal code\n";
        }
        //checks if location is not filled
        if (Phone == null) {
            errorText += "There must be a Phone\n";
        }
        //check if state  is not filled
        if (Division_ID == null) {
            errorText += "There must be a state\n";
        }
        //checks if Country is not filled
        if (Country == null) {
            errorText += "There must be a country\n";

        }
        if (errorText.length() == 0) {
            return true;
        } else {
            //alert popup plus the error text based on the error found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText(errorText);
            alert.showAndWait();
            //false if not a valid customer
            return false;
        }
    }

    /** saves customer or updates customer based on button, checks to see if all the fields are filled*/

    @FXML
    void saveCstBttnHndlr(ActionEvent event) {
        checkFields(); //checks if customer is valid and all fields are filled
        //retrives text from fields
        String Customer_Name = nameTxtFld.getText();
        String Address = addressTxtFld.getText();
        String Postal_Code = postalTxtBox.getText();
        String Phone = phoneBox.getText();

        Integer Division_ID = stateBox.getValue().getDivision_ID();
        String Country = countryCmboBox.getValue().getCountry();
        checkFields();
        //if appointment fields are not filled an alert is shown
        /*if (checkFields() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Check fields");
            alert.show();
        }

         */
        //if the customer is valid proceed to save
        if (!checkFields() == false) {
            //if the save button says save that reflects this is a new customer
            if (!saveCstBttn.getText().equals("UPDATE")) {
                Customer customer = new Customer(); //new instance
                //set fields
                customer.setDivision_ID(Division_ID);
                customer.setCustomer_name(Customer_Name);
                customer.setAddress(Address);
                customer.setPostal_code(Postal_Code);
                customer.setPhone(Phone);
                customer.setCountry(Country);

                CustomerDAO addCst = new CustomerDAO(); //new instance
                addCst.addCustomer(customer); //adds new customer
                updateCstTbv(); //updates tableview with new customer
            } else {
                Customer updCustomer = new Customer(); //new instace
                CustomerDAO customerDAO = new CustomerDAO(); //new instance
                //set fields
                Integer Customer_ID = Integer.valueOf(customerIdTxtFld.getText());
                updCustomer.setCustomer_id(Customer_ID);
                updCustomer.setDivision_ID(Division_ID);
                updCustomer.setCustomer_name(Customer_Name);
                updCustomer.setAddress(Address);
                updCustomer.setPostal_code(Postal_Code);
                updCustomer.setPhone(Phone);
                updCustomer.setCountry(Country);
                customerDAO.updateCustomer(updCustomer); //update customer in database
                updateCstTbv(); //update tableview to reflect changes

            }
        }
    }



    }






