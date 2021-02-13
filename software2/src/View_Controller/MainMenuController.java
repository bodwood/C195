package View_Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    /**
     * FXML Controller class
     *
     * @author Darya Krutyeva
     */
    Stage stage;
    Parent scene;
        @FXML
        private Button exitBttn;

        @FXML
        /**handles click on appointment button and opens appointment screen*/
        void appointmentsBttnHndlr(ActionEvent event) throws IOException {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("AppointmentMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); //opens appointment screen

        }
        /**handles click on customer button and opens customer screen */
        @FXML
        void customerButtonHndlr(ActionEvent event) throws IOException {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); //open customer screen
        }
    /**handles click on exit button and exits program */
        @FXML
        void exitBttnHndlr(ActionEvent event) { Stage stage = (Stage)exitBttn.getScene().getWindow();
            stage.close(); //exit program

        }
    /**handles click on reports button and opens report screen*/
        @FXML
        void reportsBttnHndlr(ActionEvent event) throws IOException {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); //shows reports screen

        }
    /** initializes controller class and checks if there is an appointment within 15 minutes upon the loading of this screen, displays appropriate alert*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    AppointmentDAO appointmentDAO = new AppointmentDAO(); //new instance
        Appointment fifteenAppt = AppointmentDAO.getFifteenAppointment();

       //check if there is an appointment within 15 minutes
        //if there isn't an apppointment within 15 mins an alert says so
        if (fifteenAppt.getAppointment_id() == 0) {
            Alert noAppt = new Alert(Alert.AlertType.INFORMATION);
            noAppt.setHeaderText("Appointments");
            noAppt.setContentText("There are no upcoming appointments!");
            noAppt.show();
        }
        //if there is an appointment within 15 mins alert displays appt id and timestamp
         if(!(fifteenAppt.getAppointment_id() == 0)) {
            Alert yesAppt = new Alert(Alert.AlertType.INFORMATION);
            yesAppt.setHeaderText("User TEST has an Upcoming Appointment");
            yesAppt.setContentText("User TEST has an upcoming appointment\n " + "\n Appointment ID: " + fifteenAppt.getAppointment_id() + "\n" + "at " + fifteenAppt.getStartZ());
            yesAppt.show();
        }

    }
}

