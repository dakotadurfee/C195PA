package views;

import classes.Appointment;
import classes.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox countryField;
    public ComboBox divisionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = Customers.getAllCustomers().size() - 1;
        int dynamicID = Customers.getAllCustomers().get(i).getCustomerID() + 1;
        customerIDField.setText(Integer.toString(dynamicID));
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        boolean error = false;
        String customerName = null;
        String address = null;
        String postalCode = null;
        String phone = null;
        if(customerNameField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer name field cannot be blank");
            alert.showAndWait();
            error = true;
        }else{
            customerName = customerNameField.getText();
        }

        if(addressField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address field cannot be blank");
            alert.showAndWait();
            error = true;
        } else{
            address = addressField.getText();
        }

        if(postalCodeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal code field cannot be blank");
            alert.showAndWait();
            error = true;
        } else{
            postalCode = postalCodeField.getText();
        }

        if(phoneField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone field cannot be blank");
            alert.showAndWait();
            error = true;
        } else{
            phone = phoneField.getText();
        }
    }
}
