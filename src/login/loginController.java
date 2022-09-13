package login;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public Label username;
    public Label password;
    public TextField usernameField;
    public TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("login/languages", locale);
        username.setText(resourceBundle.getString("username"));
        password.setText(resourceBundle.getString("password"));

    }
}
