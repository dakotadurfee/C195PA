package views;

import classes.Appointment;
import classes.CountryData;
import classes.Customers;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox countryField;
    public ComboBox divisionField;
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = Customers.getAllCustomers().size() - 1;
        int dynamicID = 0;
        if(i < 0){
            dynamicID = 1;
        }
        else {
            dynamicID = Customers.getAllCustomers().get(i).getCustomerID() + 1;
        }
        customerIDField.setText(Integer.toString(dynamicID));
        countryField.setItems(CountryData.getCountryList());
    }

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        boolean error = false;
        int customerID = Integer.parseInt(customerIDField.getText());
        String customerName = null;
        String address = null;
        String postalCode = null;
        String phone = null;
        String createDate = LocalDateTime.now().toString();
        createDate = createDate.substring(0, createDate.length() - 10);
        createDate = createDate.replace('T', ' ');
        String lastUpdate = createDate;
        int divisionID = 0;
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

        try {
            String countryName = (String) countryField.getSelectionModel().getSelectedItem();
            String divisionName = (String) divisionField.getSelectionModel().getSelectedItem();
            if(divisionName.equals(null)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Must select country and division");
                alert.showAndWait();
                error = true;
            }
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = " + "'" + divisionName + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select country and division");
            alert.showAndWait();
            error = true;
        }

        if(error == false) {
            Customers customer = new Customers(customerID, customerName, address, postalCode, phone, createDate, "Script", lastUpdate, "Script", divisionID);
            Customers.addCustomer(customer);
            toMain(actionEvent);
        }


    }

    public void onCountrySelection(ActionEvent actionEvent) {
        String country = (String)countryField.getSelectionModel().getSelectedItem();
        if(country.equals("U.S")){
            divisionField.setItems(CountryData.getUSdivisionList());
        }
        else if(country.equals("UK")){
            divisionField.setItems(CountryData.getUKdivisionList());
        }
        else if(country.equals("Canada")){
            divisionField.setItems(CountryData.getCanadadivisionList());
        }
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1449, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
