package View_Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * FXML Controller class
 *
 * @author Darya Krutyeva
 */
public class AppointmentAddController implements Initializable {


    //created lists to select hours and minutes from combo boxes
    public static ObservableList<String> hoursList = FXCollections.observableArrayList();
    public static ObservableList<String> minutesList = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;
    //format datetime to a readable format
    DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm a");
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private TableView<Customer> customerTblView;
    @FXML
    private TableColumn<Customer, Integer> apptCustomerIDColumn;
    @FXML
    private TableColumn<Customer, String> apptCustomerNameClmn;
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
    private ComboBox<String> endComboBox;
    @FXML
    private ComboBox<String> startHrComboBox;
    @FXML
    private ComboBox<String> startMin;
    @FXML
    private ComboBox<String> endHr;
    @FXML
    private DatePicker datePicker;

/** initializes class, sets columns, fills time lists, fills comboboxes, sets tableview*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //cell value factories to define columns in table
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        apptCustomerNameClmn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        //add hours to hours list
        hoursList.addAll("00", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        //add minutes to minutes list
        minutesList.addAll("00", "15", "30", "45");


        //set start hour combobox
        startHrComboBox.setItems(hoursList);
        //set start minute combobox
        startMin.setItems(minutesList);
        //set end hour combobox
        endHr.setItems(hoursList);
        //set end minute combobox
        endComboBox.setItems(minutesList);




        //set customer table view
        updateCstTbv();
        //set contacts in contact box
        setContactComboBox();
        //set user id field
        setUserIdTxtBx();
    }
    /**handler for clicking back button, confirms selection and loads screen*/
    @FXML
    void backBttnHndlr(ActionEvent event) throws IOException {
        //throws alert to confirm exiting menu to appointment screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to go back?");
        Optional<ButtonType> result = alert.showAndWait();
        //if ok button is clicked, user is taken back to main appt window
        if(result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("AppointmentMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        //if cancel button clicked the user remains at current menu
            if(result.get() == ButtonType.CANCEL) {
                alert.close();

        }
    }
    /**sets customer table view from list of AllCustomer*/
    public void updateCstTbv () {
        CustomerDAO cstDAO = new CustomerDAO(); //instance
        customerTblView.setItems(cstDAO.getAllCustomer());
    }
    /**sets contact names from list of allContacts and includes lambda to override hash display to string*/
    public void setContactComboBox(){
        ContactDAO cntctDao = new ContactDAO(); //instance
        contactComboBox.setItems(cntctDao.getAllContacts()); //sets contacts from list
        contactComboBox.getSelectionModel().selectFirst(); //auto selects the first contact on the list

        //lambda in order to override the default display of hash and instead display the string values of contacts
        //Factory for the List Cells
        Callback<ListView<Contact>, ListCell <Contact>> fctory = lv -> new ListCell<Contact>() {
            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item,empty);
                setText(empty ? "" : item.getContact_name() + " " + (empty ? " ":item.getContact_id()));
            }
        };
// Different Factory for button cell
        Callback <ListView<Contact>, ListCell<Contact>> fctoryused = lv -> new ListCell<Contact>() {
            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getContact_name());
            }
        };
//note separate factories used
        contactComboBox.setCellFactory(fctory);
        contactComboBox.setButtonCell(fctoryused.call(null));

    }
    /**gets current user based on logged in user and sets it to the text box*/
    public void setUserIdTxtBx(){
UserDAO userDAO = new UserDAO();
userIdTxtBx.clear();
userIdTxtBx.setText(String.valueOf(userDAO.getCurrentUser()));


    }
    /**clears all the appointment fields*/
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
        customerIdTxtFld.clear();


    }


    /**sets customer id text field from the selected customer in the table view*/
    @FXML
    void customerTblrViewClickHndlr(MouseEvent event) {
        customerIdTxtFld.setText(String.valueOf(customerTblView.getSelectionModel().getSelectedItem().getCustomer_id()));

    }

/**checks whether all the appointment fields are filled out and creates alerts for empty fields*/
    private boolean checkValidAppointment(){
        //retrieves text from fields
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
        //defines error so I don't have to type out a new alert for each if statement
        String errorText = "";
        //checks if id is not filled
        if(Customer_ID ==null){
            errorText+="There must be a selected customer\n"; //if so then this error pops up

        }
        //checks if title is not filled
        if(title == null){
            errorText+="There must be a title\n";
        }
        //checks if description is not filled
        if(description == null){
            errorText+="There must be a description\n";
        }
        //checks if location is not filled
        if(location == null) {
            errorText+="There must be a location\n";
        }
        //check if contact id is not filled
        if(Contact_ID == null) {
            errorText+="There must be a contact\n";
        }
        //checks if type is not filled
        if(type == null) {
            errorText+="There must be a type\n";

        }
        //checks if user id is not filled
        if (User_ID == null){
            errorText+= "There must be a user\n";
        }
        //checks if start hour is not selected
        if(startHour == null) {
            errorText+= "There must be a start hour\n";
        }
        //checks if start minute is not selected
        if(startMinute==null){
            errorText+="There must be a start minute\n";
        }
        //checks if end hour is not selected
        if(endHour==null){
            errorText+="There must be an end hour\n";
        }
        //checks if end minute is not selected
        if(endMinute==null){
            errorText+="There must be an end minute\n";
        }
        //checks if date is not selected
        if(date == null){
            errorText+= "There must be a date\n";}
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

    /**handles clicking save appointment button, saves appointment, validates if all fields are filled, creates time stamps from combobox selections, validates against business hours and checks if there is an overlappingappointment */

    @FXML
    void saveApptBttnHndlr(ActionEvent event) {
        checkValidAppointment(); //runs to check if all fields filled
        //retrieves information from fields
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

        //Timestamp for database
        Timestamp startTimestamp = Timestamp.from(Instant.from(startUTCZDT));
        Timestamp endTimeStamp = Timestamp.from(Instant.from(endUTCZDT));




        //if an appointment is valid, proceeds to save appt
        if(!checkValidAppointment()==false){
            //checks to see if appointment is within business hours based on EST
        if (startUTCZDT.isBefore(bsnsStartUTC)||endUTCZDT.isAfter(bsnsEndUTC)) {
            //if appointment is not within business hours an error pops up
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment time must be between 10am and 8pm EST\n" + "\nor" + " " +bsnsStartLocal + " " + "and"+ " " +bsnsEndLocal+" " +"local");

            alert.show();
        }
        else {
            //if everything is filled and valid then the info is set
            Appointment newAppt = new Appointment(); //new instance
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
            newappt.checkTime(newAppt); //checks if the appointment overlaps existing appointments
            if (newappt.checkTime(newAppt) == true) {
                //if it overlaps and error pops up
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("CONFLICT");
                alert.setContentText("New Appointment cannot overlap existing Appointment");
                alert.show();
            } else if (newappt.checkTime(newAppt) == false) {
                //if it does not overlap, the appointment is added to the database thru AppointmentDAO
                newappt.addAppointment(newAppt);
            }
        }
        }


        }

}
