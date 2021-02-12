package DAO;

import Model.Appointment;
import Utility.ConnectDB;
import Utility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentDAO implements Initializable{
    /**
     * FXML DAO class
     *
     * @author Darya Krutyeva
     */


    /**adds new appointment into database, generates appointment id*/
    public void addAppointment (Appointment appointment) {
        //sql query function to insert data to database
        String addAppointment = "INSERT INTO appointments(Appointment_ID,Title,Description,Location,Type, Start, End, Customer_ID, User_ID, Contact_ID)" + "VALUES (NULL,?,?,?,?,?,?,?,?,?)";
        Connection conn = ConnectDB.getConnection(); //connect to database
        try {
            DBQuery.createStatement(conn); //create statement object
            Statement statement = DBQuery.getStatement(); //get Statement reference
            PreparedStatement ptmt = conn.prepareStatement(addAppointment, statement.RETURN_GENERATED_KEYS); //prepare statement plus generate appointment id
            //set parameter strings
            ptmt.setString(1, appointment.getTitle());
            ptmt.setString(2, appointment.getDescription());
            ptmt.setString(3, appointment.getLocation());
            ptmt.setString(4, appointment.getType());
            ptmt.setTimestamp(5,appointment.getStartTmStmp());
            ptmt.setTimestamp(6, appointment.getEndTmpStmp());
            ptmt.setInt(7, appointment.getCustomer_ID());
            ptmt.setInt(8,appointment.getUser_ID());
            ptmt.setInt(9,appointment.getContact_ID());

            //execute prepared statement
            ptmt.executeUpdate();
            //sets a auto generate id for the appointment
            ResultSet rs = ptmt.getGeneratedKeys();
            rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    /**get filtered appointment by month*/
    public ObservableList<Appointment> getMonthAppointment() {
        ObservableList<Appointment> monthAppointment = FXCollections.observableArrayList(); //defines ObservableList
        //sql function to retrieve data from database
        String queryString = "SELECT appointment_id,title,description,location, type, start, end, customers.Customer_ID, users.User_ID, contacts.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts" +
                " WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID " +
                "AND start BETWEEN NOW() AND (SELECT LAST_DAY(NOW()))";

        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            //prepare statement
            ptst = conn.prepareStatement(queryString);

            ResultSet rs = ptst.executeQuery();
            while (rs.next()) {
                Appointment getAppointment = new Appointment();  //appointment object
                //sets appointment variables
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setLocation(rs.getString("location"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                getAppointment.setUser_ID(rs.getInt("User_ID"));
                getAppointment.setContact_ID(rs.getInt("Contact_ID"));
                getAppointment.setContact_name(rs.getString("Contact_Name"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //format database UTC start timestamp into LocalDateTime for better viewing on tables
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
                //format database UTC end timestamp into LocalDateTime for better viewing on tables
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);




                //add all retrieved info to list
                monthAppointment.add(getAppointment);



            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //returns list
        return monthAppointment;

    }

    /**get schedule info for selected contact for the report*/
    public ObservableList<Appointment> getAcostaAppt() {
        ObservableList<Appointment> acostaAppt = FXCollections.observableArrayList(); //defines ObservableList
        //sql query function to retrieve data from database
        String queryString = "SELECT appointment_id, title, description, type, start, end, customers.Customer_ID, users.User_ID FROM appointments, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND Contact_ID = 1";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            //prepare statement
            ptst = conn.prepareStatement(queryString); //prepare statement


            ResultSet rs = ptst.executeQuery(); //execute prepared statement
            while (rs.next()) {
                //get appointment info from database
                Appointment getAppointment = new Appointment();
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //format database UTC start time stamp to localdatetime for better viewing on table
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
                //format database UTC end timestamp to localdatetime for better viewing on table
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);



                //add appointment to list
                acostaAppt.add(getAppointment);



            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return acostaAppt;
    }
    /**get schedule info for selected contact*/
    public ObservableList<Appointment> getGarciaAppt() {
        ObservableList<Appointment> garciaAppt = FXCollections.observableArrayList(); //defines ObservableList
        //sql query function to retrieve data from database
        String queryString = "SELECT appointment_id, title, description, type, start, end, customers.Customer_ID, users.User_ID FROM appointments, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND Contact_ID = 2";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement


            ResultSet rs = ptst.executeQuery(); //executes statement
            while (rs.next()) {
                Appointment getAppointment = new Appointment();
                //retrieves info from sql database
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //format start timestamp from database to LocalDateTime at user's timezone for better viewing
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
            //format end timestamp from database to LocalDateTime at user's timezone for better viewing
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);



            //adds retrieved info to list
                garciaAppt.add(getAppointment);



            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //returns list
        return garciaAppt;
    }
    /**get schedule info for selected contact*/
    public ObservableList<Appointment> getLiAppt() {
        ObservableList<Appointment> LiAppt = FXCollections.observableArrayList(); //defines ObservableList
        //sql query function to retrieve info from database
        String queryString = "SELECT appointment_id, title, description, type, start, end, customers.Customer_ID, users.User_ID FROM appointments, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND Contact_ID = 3";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement


            ResultSet rs = ptst.executeQuery();
            while (rs.next()) {
                Appointment getAppointment = new Appointment(); //create instance
                //set variables
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //format database start time stamp from UTC to LocalDateTime at user's timezone for easier viewing on tables
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
        //format database end time stamp from UTC to LocalDateTime at user's timezone for easier viewing on tables
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);



        //add retrieved info to list
                LiAppt.add(getAppointment);



            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return LiAppt;
    }
    /**get appointment info for the following week from the user's current date*/
    public ObservableList<Appointment> getWeekAppointment() {
        ObservableList<Appointment> weekAppointment = FXCollections.observableArrayList(); //define list
        //sql query function to retrieve info from database
        String queryString = "SELECT appointment_id,title,description,location, type, start, end, customers.Customer_ID, users.User_ID, contacts.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts" +
                " WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID " +
                "AND start BETWEEN NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY))";

        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                Appointment getAppointment = new Appointment();
                //get info from database and set variables
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setLocation(rs.getString("location"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                getAppointment.setUser_ID(rs.getInt("User_ID"));
                getAppointment.setContact_ID(rs.getInt("Contact_ID"));
                getAppointment.setContact_name(rs.getString("Contact_Name"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //format start timestamp from database to user's current time zone with a more readable format
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
                //format end timestamp from database to user's current timezone with a more readable format
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);




            //add retrieved info to list
                weekAppointment.add(getAppointment);



            }

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        //returns list
        return weekAppointment;

    }
    /**retrieves types of appointments, with no duplicates*/
    public ObservableList<Appointment> getType() {
        ObservableList<Appointment> typeAppointment = FXCollections.observableArrayList(); //define ObservableList
        //sql query function to retrieve info from database with no duplicates
        String queryString = "SELECT DISTINCT type FROM appointments";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                Appointment getAppointment = new Appointment();


                //set variable
                    getAppointment.setType(rs.getString("type"));

                //add variable to list
                    typeAppointment.add(getAppointment);


                }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //return list
        return typeAppointment;
        }
        /**gets row count from database for total number of appointments per selected month*/
public int getMonthCount() {
        //sql query function to retrieve row count from database
        String queryString = "SELECT COUNT(*) AS rowcount FROM appointments WHERE Start BETWEEN ? AND ?";
        int monthCount = 0; //set initial count
    Connection conn = ConnectDB.getConnection(); //connect to database
    Appointment appt = new Appointment();
    try {
        PreparedStatement ptst = conn.prepareStatement(queryString); //prepare statement
        //define parameters
        ptst.setTimestamp(1, appt.getStartStatic());
        ptst.setTimestamp(2,appt.getEndStatic());

        ResultSet rs = ptst.executeQuery(); //execute statement
        while (rs.next()) {
            monthCount = rs.getInt("rowcount"); //retrives row count


        }


    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    //returns row count for total number of appointments per selected month
    return monthCount;


}
/**gets row count from database for total number of appointments per type of appointment*/
    public int getTypeCount() {
        //sql query function to retrieve row count from database
        String queryString = "SELECT COUNT(*) AS rowcount FROM appointments WHERE Type = ?";
        Connection conn = ConnectDB.getConnection(); //connect to database

         int typeCount = 0; //set initial row count
        Appointment appt = new Appointment();

        try {
           PreparedStatement ptst = conn.prepareStatement(queryString); //prepare statement
            //set parameter
           ptst.setString(1, appt.getTypeForCount());

            ResultSet rs = ptst.executeQuery(); //execute query
            while (rs.next()) {
                  typeCount = rs.getInt("rowcount"); //retrieves row count


            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns total number of customers by type of appointment
        return typeCount;



    }
    /**list of all appointment from sql database*/
    public ObservableList<Appointment> getAllAppointment() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(); //define observable list
        //query function to retrieve the needed appointment info
        String queryString = "SELECT appointment_id,title,description,location, type, start, end, customers.Customer_ID, users.User_ID, contacts.Contact_ID, contacts.Contact_Name FROM appointments, customers, users, contacts WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement ptst = null;
        try {
            ptst = conn.prepareStatement(queryString); //prepare statement

            ResultSet rs = ptst.executeQuery(); //execute statement
            while (rs.next()) {
                Appointment getAppointment = new Appointment();
                //set variables
                getAppointment.setAppointment_id(rs.getInt("appointment_id"));
                getAppointment.setTitle(rs.getString("title"));
                getAppointment.setDescription(rs.getString("description"));
                getAppointment.setLocation(rs.getString("location"));
                getAppointment.setType(rs.getString("type"));
                getAppointment.setStartTmStmp(rs.getTimestamp("start"));
                getAppointment.setEndTmpStmp(rs.getTimestamp("end"));
                getAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
                getAppointment.setUser_ID(rs.getInt("User_ID"));
                getAppointment.setContact_ID(rs.getInt("Contact_ID"));
                getAppointment.setContact_name(rs.getString("Contact_Name"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //format database start timestamp from UTC to a LocalDateTime at user's time zone for easier viewing
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setStartZ(StartOutFinal);
            //format database end time stamp from UTC to LocalDateTime at user's time zone for easier viewing
                LocalDateTime endLDT = tsEnd.toLocalDateTime();
                ZonedDateTime EndZdtOut = endLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String EndOutFinal = EndZdtOut.toLocalDateTime().format(newFormat);
                getAppointment.setEndZ(EndOutFinal);





            //add all retrieved info to list
                allAppointments.add(getAppointment);



            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //returns list
        return allAppointments;
    }

;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**checks whether the new appointment overlaps an existing appointment*/
    public boolean checkTime (Appointment appointment) {
        //sql query function to check whether the timestamps I'm entering overlap the existing timestamps
        String checkAppt = "SELECT * FROM appointments "
                + "WHERE (? BETWEEN Start AND end OR ? BETWEEN Start AND End OR ? < Start AND ? > End) "
                + "AND (Customer_ID = ?)";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement psts = null;
        try{
            psts = conn.prepareStatement(checkAppt); //prepare statement
            //define parameters aka the time stamps I'm trying to enter and customer
            psts.setTimestamp(1, appointment.getStartTmStmp());
            psts.setTimestamp(2, appointment.getEndTmpStmp());
            psts.setTimestamp(3, appointment.getStartTmStmp());
            psts.setTimestamp(4, appointment.getEndTmpStmp());
            psts.setInt(5, appointment.getCustomer_ID());

            ResultSet rs = psts.executeQuery(); //execute statement
            while (rs.next()) {
                //returns true if overlaps
                return true;

            }

        }
        catch(SQLException e) {
        e.printStackTrace();
    }

        //returns false if doesn't overlap
        return false;
    }
    /**checks if there is an appointment within 15 minutes of user's login and retrieves appointment info*/
    public static Appointment getFifteenAppointment () {
        //sql query function to retrieve appointment between current time stamp and a 15 minute interval
        String checkAppt = "SELECT * FROM appointments "
                + "WHERE (Start BETWEEN ? AND ?)";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement psts = null;
        ZoneId zId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zId);
        System.out.println(now);
        ZonedDateTime nowZDT = now.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(nowZDT);
        ZonedDateTime nowZDT15 = nowZDT.plusMinutes(15);

        Timestamp nowStamp = Timestamp.valueOf(nowZDT.toLocalDateTime());
        Timestamp fiftStamp = Timestamp.valueOf(nowZDT15.toLocalDateTime());
        System.out.println(nowStamp);


        Appointment appointment = new Appointment();

        try {
            psts = conn.prepareStatement(checkAppt); //prepare statement

            psts.setString(1, String.valueOf(nowStamp));
            psts.setString(2,String.valueOf(fiftStamp));

            ResultSet rs = psts.executeQuery(); //execute query
            while (rs.next()) {
                //retrieves appointment info if there is an appointment within 15 minutes
                appointment.setAppointment_id(rs.getInt("Appointment_ID"));
                appointment.setStartTmStmp(rs.getTimestamp("Start"));
                Timestamp tsStart = rs.getTimestamp("start");
                //formats database timestamp to local time in a more readable format
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startLDT = tsStart.toLocalDateTime();
                ZonedDateTime StartZdtOut = startLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                String StartOutFinal = StartZdtOut.toLocalDateTime().format(newFormat);
                //sets local time
                appointment.setStartZ(StartOutFinal);
                System.out.println(appointment.getAppointment_id());



            }
        }
        catch(SQLException e) {
            e.printStackTrace();
    }

        return appointment;
    }
    /**update info for existing appointment*/
    public  void updateAppointment(Appointment appointment){
        //sql query function to update needed columns
        String updateString ="UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        Connection conn = ConnectDB.getConnection(); //connect to database
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(updateString); //preparestatement
            //define parameters
            psts.setString(1, appointment.getTitle());
            psts.setString(2,appointment.getDescription());
            psts.setString(3,appointment.getLocation());
            psts.setString(4,appointment.getType());
            psts.setTimestamp(5,appointment.getStartTmStmp());
            psts.setTimestamp(6,appointment.getEndTmpStmp());
            psts.setInt(7, appointment.getCustomer_ID());
            psts.setInt(8, appointment.getUser_ID());
            psts.setInt(9,appointment.getContact_ID());
            psts.setInt(10, appointment.getCustomer_ID());

            psts.executeUpdate(); //execute statement

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**this deletes all appointments associated with a specific customer*/
    public void deleteAllAppointment (Appointment appointment) {
        //sql query function to delete appointments based on customer id
        String deleteString = "DELETE from appointments WHERE Customer_ID = ?";
        PreparedStatement psts = null;
        Connection conn = ConnectDB.getConnection(); //connect database
        try{
            psts = conn.prepareStatement(deleteString); //prepare statement
            psts.setInt(1, appointment.getCustomer_ID()); //define parameters, get customer id


            psts.executeUpdate(); //execute query

        }
        catch(SQLException e) {
            e.printStackTrace();
        }


    }
    /**delete singular appointment associated with appointment id*/
    public void deleteAppointment (Appointment appointment){
        //sql query function to delete selected appointment
        String deleteString = "DELETE from appointments WHERE Appointment_ID = ?";
        PreparedStatement psts = null;
        Connection conn = ConnectDB.getConnection();
        try{
            psts = conn.prepareStatement(deleteString);
            //define parameter, get the appointment id
            psts.setInt(1, appointment.getAppointment_id());


            psts.executeUpdate(); //execute statement

        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }


}


