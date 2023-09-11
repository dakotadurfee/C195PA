package views;

import classes.CountryData;
import classes.Customers;
import helper.JDBC;
import helper.TimeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**This class controls the add customer page in the user interface.*/
public class AddCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox<String> countryField;
    public ComboBox<String> divisionField;
    public Button saveButton;

    /**This method fills the customer ID field with an auto generated value that is one higher than the highest customer ID value and fills the country field
     * with all available country options.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = Customers.getAllCustomers().size() - 1;
        int dynamicID;
        if(i < 0){
            dynamicID = 1;
        }
        else {
            dynamicID = Customers.getAllCustomers().get(i).getCustomerID() + 1;
        }
        customerIDField.setText(Integer.toString(dynamicID));
        countryField.setItems(CountryData.getCountryList());
    }

    /**This method is called when the save button is clicked. It creates a new customer object and adds it to the all customers list and the database if
     * there are no errors. It uses error checking to see if there are any blank fields. If there are any errors an error message will be displayed saying what the error is,
     * and it will not save any of the information.*/
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
            String divisionName = divisionField.getSelectionModel().getSelectedItem();
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

        if(!error) {
            Customers customer = new Customers(customerID, customerName, address, postalCode, phone, createDate, loginController.getDBusername(), lastUpdate, loginController.getDBusername(), divisionID);
            Customers.addCustomer(customer);
            addCustomerDB(customerID, customerName, address, postalCode, phone, createDate, lastUpdate, divisionID);
            Main.switchScene("/views/mainMenu.fxml", 1449, 400, "Main Menu", actionEvent);
        }
    }

    /**This method is called at the end of the onSave method and takes all the variables needed for the database and adds a customer to the customers table.*/
    public void addCustomerDB(int customerID, String customerName, String address, String postalCode, String phone, String createDate, String lastUpdate,
                              int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                "Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        createDate = TimeConverter.toUTCTime(createDate);
        lastUpdate = TimeConverter.toUTCTime(lastUpdate);

        ps.setInt(1, customerID);
        ps.setString(2,customerName);
        ps.setString(3, address);
        ps.setString(4,postalCode);
        ps.setString(5, phone);
        ps.setString(6, createDate);
        ps.setString(7, loginController.getDBusername());
        ps.setString(8, lastUpdate);
        ps.setString(9, loginController.getDBusername());
        ps.setInt(10, divisionID);
        ps.executeUpdate();
    }

    /**This method is called when a user selects a country, and it fills the division field with divisions from the selected country. It gets the division data
     * from the CountryData class. */
    public void onCountrySelection() {
        String country = countryField.getSelectionModel().getSelectedItem();
        switch (country) {
            case "U.S":
                divisionField.setItems(CountryData.getUSdivisionList());
                break;
            case "UK":
                divisionField.setItems(CountryData.getUKdivisionList());
                break;
            case "Canada":
                divisionField.setItems(CountryData.getCanadadivisionList());
                break;
        }
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Main.switchScene("/views/mainMenu.fxml", 1449, 400, "Main Menu", actionEvent);
    }
}
