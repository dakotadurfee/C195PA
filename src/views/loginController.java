package views;

import helper.TimeConverter;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**This class controls the login page of the application.*/
public class loginController implements Initializable {

    public Label username;
    public Label password;
    public TextField usernameField;
    public TextField passwordField;
    public Button loginbutton;
    public Label language;
    public Label timeZone;
    private static String DBusername;
    private String tAlert;
    private static int userID;

    /**This method checks to see what language and time zone the user's computer is using. It translates all the words in the application to the user's language and
     * displays what time zone they are in.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("login/languages", locale);
        username.setText(resourceBundle.getString("username"));
        password.setText(resourceBundle.getString("password"));
        language.setText(locale.getLanguage());
        tAlert = resourceBundle.getString("alertMessage");
        TimeZone zone = TimeZone.getTimeZone(TimeZone.getDefault().toZoneId());
        timeZone.setText(zone.getDisplayName());
    }

    /**This method checks to see if the user's login information is valid. If the credentials are valid then it takes the user to the main menu of the application.
     * If the login information is incorrect an error message will be displayed. The method logs the date and time for every login attempt and if the login attempt
     * was successful. It saves that information to a login_activity.txt file.*/
    public void setLoginbutton(ActionEvent actionEvent) throws IOException, SQLException {
        String usern = usernameField.getText();
        String passw = passwordField.getText();
        boolean correct = false;

        String sql = "SELECT User_Name, Password, User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");

            if(usern.equals(username) && passw.equals(password)){
                Main.switchScene("/views/mainMenu.fxml", 1449, 400, "", actionEvent);
                correct = true;
                setDBusername(username);
                setUserID(rs.getInt("User_ID"));
            }
        }

        if(!correct){
            Main.showError("Invalid username or password");
        }

        FileWriter fwWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fwWriter);

        String date = LocalDateTime.now().toString();
        date = TimeConverter.toReadableString(date);

        outputFile.println("\nUser log-in Attempt: \nDate: " + date);
        if(!correct){
            outputFile.println("Unsuccessful");
        }
        else{
            outputFile.println("Successful");
        }

        outputFile.close();

    }

    public void setDBusername(String username){
        DBusername = username;
    }

    public static String getDBusername(){
        return DBusername;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public static int getUserID(){
        return userID;
    }
}

