package View_Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * FXML Controller class
 *
 * @author Darya Krutyeva
 */

public class AppointmentMainController implements Initializable {

    public static Appointment selectAppt;
    //defines all the objects used
    @FXML
    public TableView<Appointment> appointmentTblView;
    @FXML
    public TableColumn<Integer, Appointment> apptIDColumn;
    @FXML
    public TableColumn<String, Appointment> apptTitleColumn;
    @FXML
    public TableColumn<String, Appointment> apptDescriptColumn;
    @FXML
    public TableColumn<String, Appointment> apptLocationColumn;
    @FXML
    public TableColumn<String, Appointment> apptContactColumn;
    @FXML
    public TableColumn<String, Appointment> apptTypeColumn;
    @FXML
    public TableColumn<LocalDateTime, Appointment> apptStartColumn;
    @FXML
    public TableColumn<LocalDateTime, Appointment> apptEndColumn;
    @FXML
    public TableColumn<Integer, Appointment> apptCustomerIDColumn;
    Stage stage;
    Parent scene;
    @FXML
    private RadioButton sortByWeekBttn;
    @FXML
    private RadioButton sortByMonthBttn;
    @FXML
    private RadioButton allRadioSelect;
    /**upon click add appointment, loads addAppointment screen*/
    @FXML
    void addApptBttnHndlr(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    /**deletes selected appointment and updates tableview*/
    void apptDeleteBttnHndlr(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("Appointment ID: " + String.valueOf(appointmentTblView.getSelectionModel().getSelectedItem().getAppointment_id()) + "\nAppointment type " +appointmentTblView.getSelectionModel().getSelectedItem().getType());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {


            AppointmentDAO deleteAppt = new AppointmentDAO(); //new instance
            Appointment selectAppt = new Appointment(); //new instance
            //selected appointment that is going to be delete is set
            selectAppt.setLocation(appointmentTblView.getSelectionModel().getSelectedItem().getLocation());
            selectAppt.setTitle(appointmentTblView.getSelectionModel().getSelectedItem().getTitle());
            selectAppt.setCustomer_ID(appointmentTblView.getSelectionModel().getSelectedItem().getCustomer_ID());
            selectAppt.setDescription(appointmentTblView.getSelectionModel().getSelectedItem().getDescription());
            selectAppt.setType(appointmentTblView.getSelectionModel().getSelectedItem().getType());
            selectAppt.setAppointment_id(appointmentTblView.getSelectionModel().getSelectedItem().getAppointment_id());
            selectAppt.setUser_ID(appointmentTblView.getSelectionModel().getSelectedItem().getUser_ID());
            selectAppt.setContact_ID(appointmentTblView.getSelectionModel().getSelectedItem().getContact_ID());
            selectAppt.setStartTmStmp(appointmentTblView.getSelectionModel().getSelectedItem().getStartTmStmp());
            selectAppt.setEndTmpStmp(appointmentTblView.getSelectionModel().getSelectedItem().getEndTmpStmp());
            deleteAppt.deleteAppointment(selectAppt); //runs delete appointment
            //this refreshes the table view filtered by week
            if (sortByWeekBttn.isSelected()) {
                AppointmentDAO weekAppt = new AppointmentDAO();
                appointmentTblView.setItems(weekAppt.getWeekAppointment());
            }
            //refreshes tableview filtered by month
            else if (sortByMonthBttn.isSelected()) {
                AppointmentDAO monthAppt = new AppointmentDAO();
                appointmentTblView.setItems(monthAppt.getMonthAppointment());

            }
            //refreshes tableview that isn't filtered
            else if (allRadioSelect.isSelected()) {
                setTableView();
            }


            if (result.get() == ButtonType.CANCEL) {
                //if cancel is clicked then nothing happens
                alert.close();

            }

        }
    }
/**handles appointment update button, confirms selection and loads screen*/
    @FXML
    void apptUpdateBttnHndlr(ActionEvent event) throws IOException {
        //alert to confirm whether this is the appointment you want to update
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Do you want to update this appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if ok button is pressed then the update appointment window is opened
    selectAppt = appointmentTblView.getSelectionModel().getSelectedItem();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();}
        //if cancel is pressed then the user stays
        if(result.get() == ButtonType.CANCEL) {
            alert.close();

        }


    }
    /**handles main menu button click confirms selection and loads screen*/
    @FXML
    void mainMenuBttnHndlr(ActionEvent event) throws IOException {
        //alert to see if user wants to go back to main menu
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to go back?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if clicked ok, user taken back to MainMenu
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(result.get() == ButtonType.CANCEL) {
            //if clicked cancel then the alert is closed and the user stays on the same window
            alert.close();

        }

    }
    /**sets all appointments in tableview*/
    @FXML
    void allButtonHandlr(ActionEvent event) {
        setTableView();
    }
//sets appointment for current month
    @FXML
    void sortByMonthBttnHndlr(ActionEvent event) {
        AppointmentDAO monthAppt = new AppointmentDAO();
        appointmentTblView.setItems(monthAppt.getMonthAppointment());


    }
/**sets appointments for current week*/
    @FXML
    void sortByWeekBttnHndlr(ActionEvent event) {
        AppointmentDAO weekAppt = new AppointmentDAO();
        appointmentTblView.setItems(weekAppt.getWeekAppointment());

    }
    /**set table view for all appointments*/
    public void setTableView ()
    {
        AppointmentDAO apptDao = new AppointmentDAO();
        appointmentTblView.setItems(apptDao.getAllAppointment());

    }



/** initialize class, set table view and columns*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set table
        setTableView();
        //set columns

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("startZ"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("endZ"));


    }



}
