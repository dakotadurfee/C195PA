package views;

import classes.CountryData;
import helper.JDBC;
import helper.TimeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static views.mainMenuController.mCustomer;

/**This class controls the modify customer page in the user interface.*/
public class ModifyCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox countryField;
    public ComboBox divisionField;

    /**This method sets the values of the fields to the information from the user selected customer.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDField.setText(Integer.toString(mCustomer.getCustomerID()));
        customerNameField.setText(mCustomer.getCustomerName());
        addressField.setText(mCustomer.getAddress());
        postalCodeField.setText(mCustomer.getPostalCode());
        phoneField.setText(mCustomer.getPhone());
        if(mCustomer.getDivisionID() <= 54){
            countryField.setPromptText("U.S");
        }
        else if(mCustomer.getDivisionID() <= 72){
            countryField.setPromptText("UK");
        }
        else{
            countryField.setPromptText("Canada");
        }

        countryField.setItems(CountryData.getCountryList());

        try {
            getDivision();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        countryField.setItems(CountryData.getCountryList());
    }

    /**This method fills the division field with all available divisions from the customers country.
     * @param actionEvent method is called once the user*/
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

    /**This method gets the division name from the database based off the selected customer's division ID and sets the division field's value to that name.*/
    public void getDivision() throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + Integer.toString(mCustomer.getDivisionID());
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            divisionField.setValue(division);
        }
    }

    /**This method saves all the user entered information for the selected customer. It uses error checking to see if there are any blank fields. If there
     * are any errors an error message will display saying what the error is and the information will not be saved.
     * @param actionEvent method is called when the user presses the save button.*/
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException{
        boolean error = false;
        int customerID = Integer.parseInt(customerIDField.getText());
        String customerName = null;
        String address = null;
        String postalCode = null;
        String phone = null;
        int divisionID = 0;
        if(customerNameField.getText().isEmpty()){
            Main.showError("Customer name field cannot be blank");
            error = true;
        }else{
            customerName = customerNameField.getText();
        }

        if(addressField.getText().isEmpty()){
            Main.showError("Address field cannot be blank");
            error = true;
        } else{
            address = addressField.getText();
        }

        if(postalCodeField.getText().isEmpty()){
            Main.showError("Postal code field cannot be blank");
            error = true;
        } else{
            postalCode = postalCodeField.getText();
        }

        if(phoneField.getText().isEmpty()){
            Main.showError("Phone field cannot be blank");
            error = true;
        } else{
            phone = phoneField.getText();
        }

        try {
            String countryName = (String) countryField.getSelectionModel().getSelectedItem();
            String divisionName = (String) divisionField.getSelectionModel().getSelectedItem();
            if(divisionName.equals(null)){
                Main.showError("Must select country and division");
                error = true;
            }
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = " + "'" + divisionName + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        } catch (Exception e) {
            Main.showError("Must select country and division");
            error = true;
        }

        if(error == false){
            mCustomer.setCustomerName(customerName);
            mCustomer.setAddress(address);
            mCustomer.setPostalCode(postalCode);
            mCustomer.setPhone(phone);
            String lastUpdate = LocalDateTime.now().toString();
            lastUpdate = lastUpdate.substring(0, lastUpdate.length() - 10);
            lastUpdate = lastUpdate.replace('T', ' ');
            mCustomer.setLastUpdate(lastUpdate);
            mCustomer.setDivisionID(divisionID);
            modifyCustomerDB(customerName, address, postalCode, phone, lastUpdate, divisionID);
            Main.switchScene("/views/mainMenu.fxml", 1449, 400, "Main Menu", actionEvent);
        }
    }

    /**This method is called at the end of the onSave method and takes all the variables needed for the database and updates the information for the selected customer.*/
    public void modifyCustomerDB(String customerName, String address, String postalCode, String phone, String lastUpdate, int divisionID) throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = " + Integer.parseInt(customerIDField.getText());
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        lastUpdate = TimeConverter.toUTCTime(lastUpdate);

        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, loginController.getDBusername());
        ps.setString(6, lastUpdate);
        ps.setString(7, loginController.getDBusername());
        ps.setInt(8, divisionID);
        ps.executeUpdate();
    }
}
