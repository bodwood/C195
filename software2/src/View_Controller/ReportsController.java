package View_Controller;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Country;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    public static ObservableList<String> month = FXCollections.observableArrayList(); //define month list
Stage stage;
    Parent scene;
    //define objects
    @FXML
    private ComboBox<Appointment> typeCombo;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private TextField typeTotalText;
    @FXML
    private TextField monthTotalText;
    @FXML
    private TableView<Appointment> contactReportTbv;
    @FXML
    private TableColumn<Integer, Appointment> apptIdClmn;
    @FXML
    private TableColumn<Integer, Appointment> custIDClmn;
    @FXML
    private TableColumn<String, Appointment> descClmn;
    @FXML
    private TableColumn<String, Appointment> typeClmn;
    @FXML
    private TableColumn<LocalDateTime, Appointment> strtClmn;
    @FXML
    private TableColumn<LocalDateTime, Appointment> endClmn;

    @FXML
    private ComboBox<Country> stateBox;
    @FXML
    private TextField stateTotalTxtb;
    /**
     * FXML Controller class
     *
     * @author Darya Krutyeva
     */

    @FXML
    //sets schedule for Costa
    void contact1Hndlr(ActionEvent event) {
        AppointmentDAO apptDao = new AppointmentDAO(); //new instance
        contactReportTbv.setItems(apptDao.getAcostaAppt()); //retrieves data from database into a list and displays in table

    }
    /**sets schedule for Garcia */

    @FXML
    void contact2Hndlr(ActionEvent event) {
        AppointmentDAO apptDao = new AppointmentDAO(); //new instance
        contactReportTbv.setItems(apptDao.getGarciaAppt()); //retrieves data from database into a list and displays in table

    }
    /**sets schedule for Lee */

    @FXML
    void contact3Hndlr(ActionEvent event) {
        AppointmentDAO apptDao = new AppointmentDAO(); //new instance
        contactReportTbv.setItems(apptDao.getLiAppt()); //retrieves data from database into a list and displays in table
    }
    /** handles exit button click, exits to main menu */

    @FXML
    void exitBttnHndlr(ActionEvent event) throws IOException {
        //shows alert for exit confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to go back?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //if confirmed ok then main menu loaded
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(result.get() == ButtonType.CANCEL) {
            //if canceled nothing happens
            alert.close();

        }
    }
    /** handles month combo box selection, includes a listener for changed made to combobox selection, listener replaced with lambda to cut down on code needed to be typed */

    @FXML
    void monthComboHndlr(ActionEvent event) {
        monthTotalText.clear();


        AppointmentDAO dao = new AppointmentDAO();
        monthTotalText.setText(String.valueOf(dao.getMonthCount()));
        /**replaced listener with lambda to reduce number of code needed*/
        monthCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            switch (t1) {
                case "MONTH": //clears month text box if no selection
                    monthCombo.getSelectionModel().clearSelection();
                    monthTotalText.clear();
                    break;
                case "January": //selection changes to January
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate startJan1 = LocalDate.of(LocalDate.now().getYear(),01,01);
                    //Local time start of month
                    LocalTime startJanT1 = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime ldtStartJan1 = LocalDateTime.of(startJan1, startJanT1);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime startJanLdt1 = ZonedDateTime.of(ldtStartJan1, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime startJanUTCZDT1 = startJanLdt1.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp startJanTime1 = Timestamp.from(Instant.from(startJanUTCZDT1));
                    //local date end of month
                    LocalDate endJan1 = LocalDate.of(LocalDate.now().getYear(),01,31);
                    //local time end month
                    LocalTime endJanT1 = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime ldtEndJan1 = LocalDateTime.of(endJan1, endJanT1);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime endJanLdt1 = ZonedDateTime.of(ldtEndJan1, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime endJanUtcZdt1 = endJanLdt1.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endJanTime1 = Timestamp.from(Instant.from(endJanUtcZdt1));

                    Appointment newAppt1 = new Appointment(); //new instance
                    newAppt1.setStartStatic(startJanTime1); //set the time stamps to a static variable so it's the same across all instances
                    newAppt1.setEndStatic(endJanTime1);

                    AppointmentDAO dao1 = new AppointmentDAO(); //new instance
                    //retrieve the row count for customer appointments this month
                    monthTotalText.setText(String.valueOf(dao1.getMonthCount()));
                    break;
                case "February": //selection changes to February
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate startFeb= LocalDate.of(LocalDate.now().getYear(),02,01);
                    //Local time start of month
                    LocalTime startFebT = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime ldtStartFed = LocalDateTime.of(startFeb,startFebT);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime startFebZdt = ZonedDateTime.of(ldtStartFed, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime startFebZDTUTC = startFebZdt.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp startFebTime = Timestamp.from(Instant.from(startFebZDTUTC));
                    //local date end of month
                    LocalDate endFeb= LocalDate.of(LocalDate.now().getYear(),02,28);
                    //local time end month
                    LocalTime endFebT = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime ldtEndFeb = LocalDateTime.of(endFeb,endFebT);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime endFebZDT = ZonedDateTime.of(ldtEndFeb, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime endFebZDTUTC = endFebZDT.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endFebTime = Timestamp.from(Instant.from(endFebZDTUTC));

                    Appointment Feb = new Appointment(); //new instance
                    Feb.setStartStatic(startFebTime); //set time stamps to static variable so it's the same accross all instances
                    Feb.setEndStatic(endFebTime);
                    AppointmentDAO dao2 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao2.getMonthCount()));
                    break;
                case "March": //selection changes to March
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate startMar= LocalDate.of(LocalDate.now().getYear(),03,01);
                    //Local time start of month
                    LocalTime startMarT = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime ldtM = LocalDateTime.of(startMar,startMarT);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime startM = ZonedDateTime.of(ldtM, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime strtMUT = startM.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp startMarTi = Timestamp.from(Instant.from(strtMUT));
                    //local date end of month
                    LocalDate eM= LocalDate.of(LocalDate.now().getYear(),03,31);
                    //local time end month
                    LocalTime endMMT = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime enM = LocalDateTime.of(eM,endMMT);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime eennM = ZonedDateTime.of(enM, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime enmU = eennM.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endMTime = Timestamp.from(Instant.from(enmU));
                    Appointment Mar = new Appointment(); //new instance
                    Mar.setStartStatic(startMarTi); //set time stamps to static variable so it's the same accross all instances
                    Mar.setEndStatic(endMTime);
                    AppointmentDAO Dao3 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(Dao3.getMonthCount()));
                    break;
                case "April": //selection changes to April
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate a= LocalDate.of(LocalDate.now().getYear(),04,01);
                    //Local time start of month
                    LocalTime aa = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime aaa = LocalDateTime.of(a,aa);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime aaaa = ZonedDateTime.of(aaa, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime aaaaa = aaaa.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp startAp = Timestamp.from(Instant.from(aaaaa));
                    //local date end of month
                    LocalDate ea= LocalDate.of(LocalDate.now().getYear(),04,30);
                    //local time end month
                    LocalTime eaa = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime eaaa = LocalDateTime.of(ea,eaa);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime eaaaa = ZonedDateTime.of(eaaa, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime eaaaaa = eaaaa.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endApTim = Timestamp.from(Instant.from(eaaaaa));
                    Appointment Ap = new Appointment(); //new instance
                    Ap.setStartStatic(startAp); //set time stamps to static variable so it's the same accross all instances
                    Ap.setEndStatic(endApTim);
                    AppointmentDAO dao4 = new AppointmentDAO();//new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao4.getMonthCount()));
                    break;
                case "May": //if selection changed to may
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate ma= LocalDate.of(LocalDate.now().getYear(),05,01);
                    //Local time start of month
                    LocalTime maa = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime mam = LocalDateTime.of(ma,maa);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime maam = ZonedDateTime.of(mam, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime maaam = maam.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp startMa = Timestamp.from(Instant.from(maaam));
                    //local date end of month
                    LocalDate em= LocalDate.of(LocalDate.now().getYear(),05,31);
                    //local time end month
                    LocalTime emm = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime emmm = LocalDateTime.of(em,emm);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime eemmm = ZonedDateTime.of(emmm, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime eeemmmm = eemmm.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endMayTi = Timestamp.from(Instant.from(eeemmmm));
                    Appointment May = new Appointment(); //new instance
                    May.setStartStatic(startMa); //set time stamps to static variable so it's the same accross all instances
                    May.setEndStatic(endMayTi);
                    AppointmentDAO dao5 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao5.getMonthCount()));
                    break;
                case "June": //if selection changes to June
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate jun= LocalDate.of(LocalDate.now().getYear(),06,01);
                    //Local time start of month
                    LocalTime june = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime j = LocalDateTime.of(jun,june);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime ju = ZonedDateTime.of(j, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime junez = ju.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp juneTiS = Timestamp.from(Instant.from(junez));
                    //local date end of month
                    LocalDate enju= LocalDate.of(LocalDate.now().getYear(),06,30);
                    //local time end month
                    LocalTime endju = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime enddj = LocalDateTime.of(enju,endju);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime enddjuu = ZonedDateTime.of(enddj, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime endjune = enddjuu.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endJunTime = Timestamp.from(Instant.from(endjune));
                    Appointment June = new Appointment(); //new instance
                    June.setStartStatic(juneTiS); //set time stamps to static variable so it's the same across all instances
                    June.setEndStatic(endJunTime);
                    AppointmentDAO dao6 = new AppointmentDAO();//new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao6.getMonthCount()));
                    break;
                case "July": //if month changes to July
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate ly= LocalDate.of(LocalDate.now().getYear(),07,01);
                    //Local time start of month
                    LocalTime lyy = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime l = LocalDateTime.of(ly,lyy);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime lyl = ZonedDateTime.of(l, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime july = lyl.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp julyt = Timestamp.from(Instant.from(july));
                    //local date end of month
                    LocalDate enly= LocalDate.of(LocalDate.now().getYear(),07,31);
                    //local time end month
                    LocalTime endly = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime enjul = LocalDateTime.of(enly,endly);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime endjuly = ZonedDateTime.of(enjul, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime endjulyz = endjuly.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endjulytimest = Timestamp.from(Instant.from(endjulyz));
                    Appointment July = new Appointment(); //new instance
                    July.setStartStatic(julyt); //set time stamps to static variable so it's the same across all instances
                    July.setEndStatic(endjulytimest);
                    AppointmentDAO dao7 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao7.getMonthCount()));
                    break;

                case "August": //if month changes to august
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate au= LocalDate.of(LocalDate.now().getYear(),8,01);
                    //Local time start of month
                    LocalTime auu = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime aug = LocalDateTime.of(au,auu);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime augu = ZonedDateTime.of(aug, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime augus = augu.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp august = Timestamp.from(Instant.from(augus));
                    //local date end of month
                    LocalDate enda= LocalDate.of(LocalDate.now().getYear(),8,31);
                    //local time end month
                    LocalTime endau = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime endaug = LocalDateTime.of(enda,endau);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime endaugu = ZonedDateTime.of(endaug, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime endaugus = endaugu.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp endaugust = Timestamp.from(Instant.from(endaugus));
                    Appointment August = new Appointment();//new instance
                    August.setStartStatic(august);//set time stamps to static variable so it's the same across all instances
                    August.setEndStatic(endaugust);
                    AppointmentDAO dao8 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao8.getMonthCount()));
                    break;
                case "September": //if month changes to september
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate se= LocalDate.of(LocalDate.now().getYear(),9,01);
                    //Local time start of month
                    LocalTime sep = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime sept = LocalDateTime.of(se,sep);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime septe = ZonedDateTime.of(sept, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime septem = septe.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp septemb = Timestamp.from(Instant.from(septem));
                    //local date end of month
                    LocalDate septembe= LocalDate.of(LocalDate.now().getYear(),9,30);
                    //local time end month
                    LocalTime september = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime esept = LocalDateTime.of(septembe,september);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime esepte = ZonedDateTime.of(esept, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime eseptem = esepte.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp eseptember = Timestamp.from(Instant.from(eseptem));
                    Appointment September = new Appointment(); //new instance
                    September.setStartStatic(septemb); //set time stamps to static variable so it's the same across all instances
                    September.setEndStatic(eseptember);
                    AppointmentDAO dao9 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao9.getMonthCount()));
                    break;
                case "October":
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate o= LocalDate.of(LocalDate.now().getYear(),10,01);
                    //Local time start of month
                    LocalTime oc = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime oct = LocalDateTime.of(o,oc);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime octo = ZonedDateTime.of(oct, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime octob = octo.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp octobe = Timestamp.from(Instant.from(octob));
                    //local date end of month
                    LocalDate eo= LocalDate.of(LocalDate.now().getYear(),10,31);
                    //local time end of month
                    LocalTime eoc = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime eoct = LocalDateTime.of(eo,eoc);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime eocto = ZonedDateTime.of(eoct, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime eoctob = eocto.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp eoctobe = Timestamp.from(Instant.from(eoctob));
                    Appointment October = new Appointment(); //new instance
                    October.setStartStatic(octobe); //set time stamps to static variable so it's the same across all instances
                    October.setEndStatic(eoctobe);
                    AppointmentDAO dao10 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao10.getMonthCount()));
                    break;
                case "November":
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate n= LocalDate.of(LocalDate.now().getYear(),11,01);
                    //Local time start of month
                    LocalTime no = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime nov = LocalDateTime.of(n,no);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime nove = ZonedDateTime.of(nov, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime novem = nove.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp novemb = Timestamp.from(Instant.from(novem));
                    //local date end of month
                    LocalDate novembe= LocalDate.of(LocalDate.now().getYear(),11,30);
                    //local time end of month
                    LocalTime november = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime ovember = LocalDateTime.of(novembe,november);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime vember = ZonedDateTime.of(ovember, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime ember = vember.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp mber = Timestamp.from(Instant.from(ember));
                    Appointment November = new Appointment(); //new instance
                    November.setStartStatic(novemb); //set time stamps to static variable so it's the same across all instances
                    November.setEndStatic(mber);
                    AppointmentDAO dao11 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao11.getMonthCount()));
                    break;
                case "December":
                    monthTotalText.clear();
                    //Local date of start of month
                    LocalDate d= LocalDate.of(LocalDate.now().getYear(), 12,01);
                    //Local time start of month
                    LocalTime de = LocalTime.of(00,00);
                    //LocalDateTime from date and time
                    LocalDateTime dec = LocalDateTime.of(d,de);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime dece = ZonedDateTime.of(dec, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime decem = dece.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp decemb = Timestamp.from(Instant.from(decem));
                    //local date end of month
                    LocalDate decembe= LocalDate.of(LocalDate.now().getYear(),12,31);
                    //local time end of month
                    LocalTime december = LocalTime.of(23,59);
                    //LocalDateTime from date and time
                    LocalDateTime ecember = LocalDateTime.of(decembe,december);
                    //LocalDateTime to zonedatetime
                    ZonedDateTime cember = ZonedDateTime.of(ecember, ZoneId.systemDefault());
                    //zonedatetime to zonedatetime utc
                    ZonedDateTime dember = cember.withZoneSameInstant(ZoneOffset.UTC);
                    //create timestamp
                    Timestamp deer = Timestamp.from(Instant.from(dember));
                    Appointment December = new Appointment(); //new instance
                    December.setStartStatic(decemb); //set time stamps to static variable so it's the same across all instances
                    December.setEndStatic(deer);
                    AppointmentDAO dao12 = new AppointmentDAO(); //new instance
                    //retrieve row count for customers in this month and set to text box
                    monthTotalText.setText(String.valueOf(dao12.getMonthCount()));
                    break;

            }
        });
    }
    /** handles country combobox selection and fills the the textbox by it with total customer per country*/

    @FXML
    void stateBoxHndlr(ActionEvent event) {
        Customer customer = new Customer(); //new instance
        customer.setCountryForCount(stateBox.getValue().getCountry()); //set static country variable so it's the same across all instances
        setStateTotalTxtb(); //set total customers per country


    }
    /** handles type combobox selection and fills the type textbox with total number of appointments per chosen type*/

    @FXML
    void typeComboHndlr(ActionEvent event) {
        Appointment appt = new Appointment(); //new instance
        appt.setTypeForCount(typeCombo.getValue().getType()); //set static variable type so it's the same across all instances
     setTypeTotalText(); //set total appointments per type

    }
    /** retrieve row count for customers in selected country and set to txt box*/

    public void setStateTotalTxtb(){

        CustomerDAO customerDAO = new CustomerDAO(); //new instance
        stateTotalTxtb.setText(String.valueOf(customerDAO.getCustomerCount()));
    }
    /**retrieve row count of appointments of selected type and set to text box*/

    public void setTypeTotalText(){

        AppointmentDAO apptDao = new AppointmentDAO(); //new instance
        typeTotalText.setText(String.valueOf(apptDao.getTypeCount()));
    }
    /**lambda function to translate list to a readable format from hash to string, retrieves list of appointment types */

    public void setTypeCombo() {
        AppointmentDAO type = new AppointmentDAO(); //new instance
        typeCombo.setItems(type.getType());

        //Factory for the List Cells
        Callback<ListView<Appointment>, ListCell <Appointment>> fctory = lv -> new ListCell<Appointment>() {
            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item,empty);
                setText(empty ? "" : item.getType());
            }
        };
// Different Factory for button cell
        Callback <ListView<Appointment>, ListCell<Appointment>> fctoryused = lv -> new ListCell<Appointment>() {
            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getType());
            }
        };
//note separate factories used
        typeCombo.setCellFactory(fctory);
        typeCombo.setButtonCell(fctoryused.call(null));


    }
    /**retrieves list of all countries and sets to combo box*/
    public void setStateBox(){
        CountryDAO ctry = new CountryDAO(); //new instance
        stateBox.setItems(ctry.getAllCountry());



    }
    /** initializes Controller class*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppointmentDAO appt = new AppointmentDAO(); //new instance
        //sets columns in schedule table view
        apptIdClmn.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        custIDClmn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        typeClmn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descClmn.setCellValueFactory(new PropertyValueFactory<>("description"));
        strtClmn.setCellValueFactory(new PropertyValueFactory<>("startZ"));
        endClmn.setCellValueFactory(new PropertyValueFactory<>("endZ"));
        contactReportTbv.setItems(appt.getAcostaAppt());

        //sets list to combobox
        setTypeCombo();
        //sets list to combobox
        setStateBox();
        // adds months to month list
        month.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November","December");
        monthCombo.setItems(month); //sets month list to month combobox
        monthTotalText.clear();



    }
}
