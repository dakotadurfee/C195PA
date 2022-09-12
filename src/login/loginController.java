package login;

import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public Label username;
    public Label password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         ResourceBundle rb = ResourceBundle.getBundle("login/Nat", Locale.getDefault());
         if(Locale.getDefault().getLanguage().equals("fr")){
             username.setText(rb.getString("username"));
         }
    }
}
