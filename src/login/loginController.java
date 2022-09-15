package login;

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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class loginController implements Initializable {

    public Label username;
    public Label password;
    public TextField usernameField;
    public TextField passwordField;
    public Button loginbutton;
    public Label language;
    public Label timeZone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("login/languages", locale);
        username.setText(resourceBundle.getString("username"));
        password.setText(resourceBundle.getString("password"));
        language.setText(locale.getLanguage());
        TimeZone zone = TimeZone.getTimeZone(TimeZone.getDefault().toZoneId());
        timeZone.setText(zone.getDisplayName());
    }

    public void setLoginbutton(ActionEvent actionEvent) throws IOException {
        String usern = usernameField.getText();
        String passw = passwordField.getText();
        if(usern.equals("sqlUser") && passw.equals("Passw0rd!")){
            Parent root = FXMLLoader.load(getClass().getResource("/sample/mainMenu.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 924, 400);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("incorrect login");
            alert.setContentText("Username or password are incorrect");
            alert.showAndWait();
        }
    }
}

