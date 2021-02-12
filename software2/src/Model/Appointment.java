package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * FXML Model class
 * declared fields, getters, setters
 * @author Darya Krutyeva
 */

public class Appointment {
    //declare fields
    public static ObservableList<Appointment> selectAppointment = FXCollections.observableArrayList();
    static String typeForCount; //static to remain constant across all instances
    static Timestamp startStatic; //static to remain constant across all instances
    static Timestamp endStatic; //static to remain constant across all instances
    String startZ; //formatted time stamp in a readable local date time format
    String endZ; //formatted time stamp in a readable local date time format
    Timestamp startTmStmp;  //start time stamp in utc
    Timestamp endTmpStmp; //end time stamp in utc
    private SimpleIntegerProperty appointment_id;
    private SimpleStringProperty contact_name;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty type;
    private SimpleStringProperty location;
    private SimpleIntegerProperty Customer_ID;
    private SimpleIntegerProperty Contact_ID;
    private SimpleIntegerProperty User_ID;

    //constructor
    public Appointment () {
        appointment_id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        description = new SimpleStringProperty();
        location = new SimpleStringProperty();
        type = new SimpleStringProperty();
        Customer_ID = new SimpleIntegerProperty();
        Contact_ID = new SimpleIntegerProperty();
        User_ID = new SimpleIntegerProperty();
        contact_name = new SimpleStringProperty();



    }
//getters and setters
    public static String getTypeForCount() {
        return typeForCount;
    }

    public static void setTypeForCount(String typeForCount) {
        Appointment.typeForCount = typeForCount;
    }

    public static Timestamp getStartStatic() {
        return startStatic;
    }

    public static void setStartStatic(Timestamp startStatic) {
        Appointment.startStatic = startStatic;
    }

    public static Timestamp getEndStatic() {
        return endStatic;
    }

    public static void setEndStatic(Timestamp endStatic) {
        Appointment.endStatic = endStatic;
    }

    public static void addSelectedAppointment (Appointment selectedAppointment)
    {
        selectAppointment.add(selectedAppointment);
    }

    public ObservableList<Appointment> getSelectAppointment()
    {
    return selectAppointment;
    }

    public int getAppointment_id() {
        return appointment_id.get();
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id.set(appointment_id);
    }

    public SimpleIntegerProperty appointment_idProperty() {
        return appointment_id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public int getCustomer_ID() {
        return Customer_ID.get();
    }

    public void setCustomer_ID(int customer_ID) {
        this.Customer_ID.set(customer_ID);
    }

    public SimpleIntegerProperty customer_IDProperty() {
        return Customer_ID;
    }

    public int getContact_ID() {
        return Contact_ID.get();
    }

    public void setContact_ID(int contact_ID) {
        this.Contact_ID.set(contact_ID);
    }

    public SimpleIntegerProperty contact_IDProperty() {
        return Contact_ID;
    }

    public int getUser_ID() {
        return User_ID.get();
    }

    public void setUser_ID(int user_ID) {
        this.User_ID.set(user_ID);
    }

    public SimpleIntegerProperty user_IDProperty() {
        return User_ID;
    }

    public Timestamp getStartTmStmp() {
        return startTmStmp;
    }

    public void setStartTmStmp(Timestamp startTmStmp) {
        this.startTmStmp = startTmStmp;
    }

    public Timestamp getEndTmpStmp() {
        return endTmpStmp;
    }

    public void setEndTmpStmp(Timestamp endTmpStmp) {
        this.endTmpStmp = endTmpStmp;
    }

    public String getContact_name() {
        return contact_name.get();
    }

    public void setContact_name(String contact_name) {
        this.contact_name.set(contact_name);
    }

    public SimpleStringProperty contact_nameProperty() {
        return contact_name;
    }

    public String getStartZ() {
        return startZ;
    }

    public void setStartZ(String startZ) {
        this.startZ = startZ;
    }

    public String getEndZ() {
        return endZ;
    }

    public void setEndZ(String endZ) {
        this.endZ = endZ;
    }


}
