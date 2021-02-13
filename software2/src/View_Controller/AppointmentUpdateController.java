package View_Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static View_Controller.AppointmentMainController.selectAppt;
/**
 * FXML Controller class
 *
 * @author Darya Krutyeva
 */


public class AppointmentUpdateController implements Initializable {


    //create lists for the hour and minutes comboboxes
    public static ObservableList<String> hoursList = FXCollections.observableArrayList();
    public static ObservableList<String> minutesList = FXCollections.observableArrayList();
Stage stage;
Parent scene;

//define objects
    @FXML
    private TableView<Customer> customerTblView;
    @FXML
    private TableColumn<Integer, Customer> apptCustomerIDColumn;
    @FXML
    private TableColumn<String, Customer> apptCustomerNameClmn;

    @FXML
    private TextField customerIdTxtFld;
    @FXML
    private TextField titleTxtFld;
    @FXML
    private TextField descriptionTxtFld;
    @FXML
    private TextField locationTxtFld;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private TextField typeTxtBox;
    @FXML
    private TextField userIdTxtBx;
    @FXML
    private TextField apptIdTxtBx;
    @FXML
    private ComboBox<String> startHrComboBox;
    @FXML
    private ComboBox<String> startMin;
    @FXML
    private ComboBox<String> endHr;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> endComboBox;
    /**handles click on back button, confirms selection and takes user back to main menu*/

    @FXML
    void backBttnHndlr(ActionEvent event) throws IOException {
        //shows an alert confirming going back to main appointment screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to go back?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if user click ok then main appointment screen is shown
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("AppointmentMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(result.get() == ButtonType.CANCEL) {
            //if user clicks cancel the alert is closed and user stays in same window
            alert.close();

        }

    }
    @FXML
    void customerTblrViewClickHndlr(MouseEvent event) {
        customerIdTxtFld.setText(String.valueOf(customerTblView.getSelectionModel().getSelectedItem().getCustomer_id()));

    }
    /**handles click on clear button, clears all fields and selection*/

    @FXML
    void cancelAddBttnHndlr(ActionEvent event) {
        customerTblView.getSelectionModel().clearSelection();
        titleTxtFld.clear();
        descriptionTxtFld.clear();
        locationTxtFld.clear();
        contactComboBox.getSelectionModel().clearSelection();
        typeTxtBox.clear();
        startHrComboBox.getSelectionModel().clearSelection();
        startMin.getSelectionModel().clearSelection();
        endHr.getSelectionModel().clearSelection();
        endComboBox.getSelectionModel().clearSelection();

    }

    /**check to see if all appointment fields are filled and creates alert for any empty*/

    private boolean checkValidAppointment() {

        //retrieve text from fields
        String Customer_ID = customerIdTxtFld.getText();
        String title = titleTxtFld.getText();
        String description = descriptionTxtFld.getText();
        String location = locationTxtFld.getText();
        Integer Contact_ID = contactComboBox.getValue().getContact_id();
        String type = typeTxtBox.getText();
        Integer User_ID = Integer.valueOf(userIdTxtBx.getText());
        String startHour = startHrComboBox.getValue();
        String startMinute = startMin.getValue();
        String endHour = endHr.getValue();
        String endMinute = endComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String errorText = ""; //set string so I don't have to type a new alert for each if statement
        //check if customer id field empty
        if (Customer_ID == null) {
            errorText += "There must be a selected customer\n"; //set alert text

        }
        //check if title field empty
        if (title == null) {
            errorText += "There must be a title\n"; //set alert text
        }
        //check if description field empty
        if (description == null) {
            errorText += "There must be a description\n"; //set alert text
        }
        //check if location field empty
        if (location == null) {
            errorText += "There must be a location\n"; //set alert text
        }
        //check if contact id field empty
        if (Contact_ID == null) {
            errorText += "There must be a contact\n"; //set alert text
        }
        //check if type field empty
        if (type == null) {
            errorText += "There must be a type\n"; //set alert text

        }
        //check if user id field empty
        if (User_ID == null) {
            errorText += "There must be a user\n"; //set alert text
        }
        //check if start hour combo box empty
        if (startHour == null) {
            errorText += "There must be a start hour\n"; //set alert text
        }
        //check if start minute combobox empty
        if (startMinute == null) {
            errorText += "There must be an end minute\n";
        }
        //check if end hour combobox empty
        if (endHour == null) {
            errorText += "There must be a start hour\n";
        }
        //check if end minute combobox empty
        if (endMinute == null) {
            errorText += "There must be an end minute\n";
        }
        //check if datepicker is empty
        if (date == null) {
            errorText += "There must be a date\n";
        }

        if (errorText.length() == 0) {
            return true;
        } else {
            //alert popup plus the error text based on the error found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText(errorText);
            alert.showAndWait();
            //false if not a valid appt
            return false;
        }
    }


    /**handles save appointment button click, validates if all fields are filled, gets info from date and time boxes and combines, checks appt against business hours to validate, checks if appointments overlaps other appointments*/
    @FXML
    void saveApptBttnHndlr(ActionEvent event) {
        checkValidAppointment(); //checks if all fields are filled
        //retrieves info from all fields
        String Customer_ID = customerIdTxtFld.getText();
        String title = titleTxtFld.getText();
        String description = descriptionTxtFld.getText();
        String location = locationTxtFld.getText();
        Integer Contact_ID = contactComboBox.getValue().getContact_id();
        String type = typeTxtBox.getText();
        Integer User_ID = Integer.valueOf(userIdTxtBx.getText());

        //get string from Combo Boxes
        String startHour = startHrComboBox.getValue();
        String startMinute = startMin.getValue();
        String endHour = endHr.getValue();
        String endMinute = endComboBox.getValue();
        //obtain LocalDateTime
        LocalDate date = datePicker.getValue();
        LocalDateTime startLDT = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
        LocalDateTime endLDT = LocalDateTime.of(date.getYear(), date.getMonthValue(),date.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));


        //ZDT of EST
        ZonedDateTime startBSNS = ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 8, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime endBSNS = ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 22, 0, 0, 0,  ZoneId.of("America/New_York"));
        //EST TO UTC
        ZonedDateTime bsnsStartUTC = startBSNS.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime bsnsEndUTC = endBSNS.withZoneSameInstant(ZoneOffset.UTC);

        //UTC to Local
        LocalTime bsnsStartLocal = bsnsStartUTC.toLocalDateTime().toLocalTime();
        LocalTime bsnsEndLocal = bsnsEndUTC.toLocalDateTime().toLocalTime();


        //Obtain ZoneDateTime of LDT
        ZonedDateTime startZDT = ZonedDateTime.of(startLDT, ZoneId.systemDefault());
        ZonedDateTime endZDT = ZonedDateTime.of(endLDT, ZoneId.systemDefault());
        //obtain UTC ZonedateTime of LocalDateTime
        ZonedDateTime startUTCZDT = startZDT.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endUTCZDT = endZDT.withZoneSameInstant(ZoneOffset.UTC);

        //Timestamp
        Timestamp startTimestamp = Timestamp.from(Instant.from(startUTCZDT));
        Timestamp endTimeStamp = Timestamp.from(Instant.from(endUTCZDT));
       //checks if the appointment is valid
        if(!checkValidAppointment()==false) {
            //checks if the appointment time is within business hours
            if (startUTCZDT.isBefore(bsnsStartUTC) || endUTCZDT.isAfter(bsnsEndUTC)) {
                //shows error if not within business hours
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time must be between 10am and 8pm EST\n" + "\nor" + " " + bsnsStartLocal + " " + "and" + " " + bsnsEndLocal + " " + "local");

                alert.show();
            } else {
                //sets fields in appointment
                Appointment newAppt = new Appointment();
                newAppt.setCustomer_ID(Integer.valueOf(Customer_ID));
                newAppt.setTitle(title);
                newAppt.setDescription(description);
                newAppt.setLocation(location);
                newAppt.setContact_ID(Contact_ID);
                newAppt.setType(type);
                newAppt.setUser_ID(User_ID);
                newAppt.setStartTmStmp(startTimestamp);
                newAppt.setEndTmpStmp(endTimeStamp);

                AppointmentDAO newappt = new AppointmentDAO(); //new instance
                newappt.checkTime(newAppt);
                //check if the appointment overlaps existing appointment
                if (newappt.checkTime(newAppt) == true) {
                    //if it overlaps this error is shown
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("CONFLICT");
                    alert.setContentText("New Appointment cannot overlap existing Appointment");
                    alert.show();
                } else if (newappt.checkTime(newAppt) == false) {
                    //if it does not overlap the appointment is updated in database
                    newappt.updateAppointment(newAppt);
                }
            }
        }

    }
    /**fills the appointment fields with selected appointment*/

    public void fillAppt () {
        typeTxtBox.setText(selectAppt.getType());
        customerIdTxtFld.setText(String.valueOf(selectAppt.getCustomer_ID()));
        titleTxtFld.setText(selectAppt.getTitle());
        descriptionTxtFld.setText(selectAppt.getDescription());
        locationTxtFld.setText(selectAppt.getLocation());
        apptIdTxtBx.setText(String.valueOf(selectAppt.getAppointment_id()));
        userIdTxtBx.setText(String.valueOf(selectAppt.getUser_ID()));


        //retrieves hour from time stamp
        Integer startHour = selectAppt.getStartTmStmp().toLocalDateTime().getHour();
        startHrComboBox.setValue(String.valueOf(startHour));
        //retrieves minute from time stamp
        Integer startMinute = selectAppt.getStartTmStmp().toLocalDateTime().getMinute();
        startMin.setValue(String.valueOf(startMinute));
        //retrieves date from time stamp
        LocalDate startDate = selectAppt.getStartTmStmp().toLocalDateTime().toLocalDate();
        datePicker.setValue(startDate);
        //retrieves hour from time stamp
        Integer endHour = selectAppt.getEndTmpStmp().toLocalDateTime().getHour();
        endHr.setValue(String.valueOf(endHour));
        //retrieves minutes from time stamp
        Integer endMinute = selectAppt.getEndTmpStmp().toLocalDateTime().getMinute();
        endComboBox.setValue(String.valueOf(endMinute));




    }

/** initializes controllers class, adds hours and minutes to lists, fills comboboxes, fills tableview from database*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //defines columns in customer table view
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        apptCustomerNameClmn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
    //adds hours to hours list
        hoursList.addAll("00", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        //combobox shows hours list
        startHrComboBox.setItems(hoursList);
        //combobox shows minutes list
        startMin.setItems(minutesList);
        //combobox shows hours list
        endHr.setItems(hoursList);
        //combobox shows minutes list
        endComboBox.setItems(minutesList);
    //add minutes to minutes list
        minutesList.addAll("00", "15", "30", "45");

        //fills appointment fields with selected appointment
        fillAppt();
        //sets customer tableview
        updateCstTbv();
        //sets contact list to combobox
        setContactComboBox();
    }


//retrieves all customers from database and sets to tableview
    public void updateCstTbv() {
        CustomerDAO cstDAO = new CustomerDAO();
        customerTblView.setItems(cstDAO.getAllCustomer());
    }
//retrieves all contacts from database and sets to combobox
    public void setContactComboBox() {
        ContactDAO cntctDao = new ContactDAO();
        contactComboBox.setItems(cntctDao.getAllContacts());
        contactComboBox.getSelectionModel().selectFirst(); //autoselected first contact
    }

}
