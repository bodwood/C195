package View_Controller;

import DAO.UserDAO;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * FXML Controller class
 *
 * @author Darya Krutyeva
 */

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;
//define objects
    @FXML
    private Label directionsLbl;

    @FXML
    private Label signInLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField usernameTxtFld;

    @FXML
    private TextField passwordTxtFld;

    @FXML
    private Label connectionLbl;

    @FXML
    private Button logInBtn;

    @FXML
    private TextField connectiontxtFld;

    @FXML
    private Button exitBtn;
    private String errorTitle;
    private String errorMessage;
    private String errorHead;




    /**handle exit button click and closes program*/
    @FXML
    void exitBtnHandlr(ActionEvent event) { Stage stage = (Stage)exitBtn.getScene().getWindow(); //closes application
        stage.close();
    }

/**handle login button click, appends log in attempt to file, checks if the log in info is correct, if it is the main menu screen is loaded, if it isn't then an alert is displayed*/
    @FXML
    void logInHndlr(ActionEvent event) throws IOException {
        //appends log in attempt to file
        writeLogin();
        //retrieve text from fields
        String username = usernameTxtFld.getText();
        String password = passwordTxtFld.getText();


        UserDAO userDAO = new UserDAO(); //new instance
        //check correct login info
        if (userDAO.validateUser(username, password)) {
            //set User_ID
            User currentUser = new User();
            currentUser.setUsername(username);
            //open main menu
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else { //error message for incorrect log in
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setContentText(errorMessage);
            alert.setHeaderText(errorHead);
            alert.showAndWait();

            }


        }





    /** initializes controller class, also translates log in screen text based on default locale*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb;
        //get resource bundle for french or english based on locale
        rb = ResourceBundle.getBundle("Utility.Login", Locale.getDefault());
        System.out.println(Locale.getDefault());
        //translates the labels and error to language based on locale
            signInLbl.setText(rb.getString("signin"));
            directionsLbl.setText(rb.getString("instruction"));
            usernameLbl.setText(rb.getString("username"));
            passwordLbl.setText(rb.getString("password"));
            connectionLbl.setText(rb.getString("connection"));
            logInBtn.setText(rb.getString("login"));
            exitBtn.setText(rb.getString("exit"));
            errorMessage = rb.getString("wrong");
            errorTitle = rb.getString("wrongTitle");
            errorHead = rb.getString("wrongHead");
        //retrieves zone id of user
            ZoneId z = ZoneId.systemDefault();
            String s = z.getId();
            System.out.println(connectiontxtFld); //displays zone id in text field
        connectiontxtFld.setText(s);


    }
    /**log in attempt logger*/
    public void writeLogin()
    {
        Timestamp currTime = Timestamp.valueOf(LocalDateTime.now());
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("log_activity.txt", true))){
            bw.write(usernameTxtFld.getText());
            bw.write(" " +String.valueOf(currTime));
            UserDAO userDAO = new UserDAO();
            if(userDAO.validateUser(usernameTxtFld.getText(), passwordTxtFld.getText())){
                bw.write(" successfully logged in "); }
            else {bw.write(" unsuccessful log in"); }
            bw.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}





