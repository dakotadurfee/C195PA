package views;

import classes.Appointment;
import classes.Customers;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**This method controls the reports page in the user interface.*/
public class ReportsController implements Initializable {

    public TableColumn firstCol;
    public TableColumn secondCol;
    public TableColumn thirdCol;
    public TableColumn fourthCol;
    public TableColumn fifthCol;
    public TableColumn sixthCol;
    public TableColumn seventhCol;
    public TableColumn eighthCol;
    public TableColumn ninthCol;
    public TableColumn tenthCol;
    public TableColumn eleventhCol;
    public TableColumn twelfthCol;
    public ToggleGroup schedules;
    public TableView reportsTable;
    public ComboBox contactsPicker;
    public ComboBox typesPicker;
    public ComboBox monthsPicker;
    public ComboBox customerPicker;
    private ObservableList<Appointment> emptyList = FXCollections.observableArrayList();

    /**This method is called when the reports page is loaded and sets the items for the contacts field with all the contacts from the database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typesPicker.setVisible(false);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(false);


        String sql = "SELECT Contact_Name FROM contacts";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            while(rs.next()){
                String contact = rs.getString("Contact_Name");
                contactNames.add(contact);
            }
            contactsPicker.setItems(contactNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method changes the table to display the information for the appointments by contact. It also sets the items for the contacts field with all the contacts
     * from the database.
     * @param actionEvent method is called when the user selects the contact schedule radio button.*/
    public void onContactAppointments(ActionEvent actionEvent){
        firstCol.setText("Appt ID");
        secondCol.setText("Title");
        thirdCol.setText("Type");
        fourthCol.setText("Description");
        fifthCol.setText("Start");
        sixthCol.setText("End");
        seventhCol.setText("CustomerID");
        eighthCol.setText("");
        ninthCol.setText("");
        tenthCol.setText("");
        eleventhCol.setText("");
        twelfthCol.setText("");
        firstCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactsPicker.setVisible(true);
        typesPicker.setVisible(false);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(false);
        reportsTable.setItems(emptyList);


        String sql = "SELECT Contact_Name FROM contacts";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            while(rs.next()){
                String contact = rs.getString("Contact_Name");
                contactNames.add(contact);
            }
            contactsPicker.setItems(contactNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method fills the table with appointments under the selected contact.
     * @param actionEvent method is called once the user selects a contact from the combo box.*/
    public void onContactSelection(ActionEvent actionEvent){
        String contact = (String)contactsPicker.getSelectionModel().getSelectedItem();
        int contactID = 0;

        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = " + "'" + contact + "'";
        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                contactID = rs.getInt("Contact_ID");
            }
            ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
            for(Appointment appointment : Appointment.getAllAppointments()){
                if(appointment.getContact() == contactID){
                    contactAppointments.add(appointment);
                }
            }
            reportsTable.setItems(contactAppointments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**This method changes the table to display the information for the appointments by type. It also sets the items for the types field with all the types
     * from the database.
     * @param actionEvent method is called when the user selects the type appointments radio button.*/
    public void onTypeAppointments(ActionEvent actionEvent) throws SQLException {
        firstCol.setText("Appt ID");
        secondCol.setText("Title");
        thirdCol.setText("Description");
        fourthCol.setText("Location");
        fifthCol.setText("Contact");
        sixthCol.setText("Type");
        seventhCol.setText("Start Time");
        eighthCol.setText("End Time");
        ninthCol.setText("Start Date");
        tenthCol.setText("Created By");
        eleventhCol.setText("Customer ID");
        twelfthCol.setText("User ID");

        firstCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        eighthCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        ninthCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tenthCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        eleventhCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        twelfthCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactsPicker.setVisible(false);
        typesPicker.setVisible(true);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(false);
        reportsTable.setItems(emptyList);

        String sql = "SELECT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> types = FXCollections.observableArrayList();
        while(rs.next()){
            types.add(rs.getString("Type"));
        }
        typesPicker.setItems(types);
    }

    /**This method fills the table with appointments under the selected type.
     * @param actionEvent method is called once the user selects an appointment type.*/
    public void onTypeSelection(ActionEvent actionEvent){
        String type = (String)typesPicker.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> typeAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            if(appointment.getType().equals(type)){
                typeAppointments.add(appointment);
            }
        }
        reportsTable.setItems(typeAppointments);
    }

    /**This method changes the table to display appointments by month. It also sets the options for the months combo box.
     * @param actionEvent method is called once the user selects the monthly appointments radio button.*/
    public void onMonthlyAppointments(ActionEvent actionEvent){
        firstCol.setText("Appt ID");
        secondCol.setText("Title");
        thirdCol.setText("Description");
        fourthCol.setText("Location");
        fifthCol.setText("Contact");
        sixthCol.setText("Type");
        seventhCol.setText("Start Time");
        eighthCol.setText("End Time");
        ninthCol.setText("Start Date");
        tenthCol.setText("Created By");
        eleventhCol.setText("Customer ID");
        twelfthCol.setText("User ID");

        firstCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        eighthCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        ninthCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tenthCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        eleventhCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        twelfthCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactsPicker.setVisible(false);
        typesPicker.setVisible(false);
        monthsPicker.setVisible(true);
        customerPicker.setVisible(false);
        reportsTable.setItems(emptyList);

        monthsPicker.setItems(getMonths());

    }

    /**This method fills the table with appointments under the selected month.
     * @param actionEvent method is called once the user selects a month.*/
    public void onMonthlySelection(ActionEvent actionEvent){
        String month = (String)monthsPicker.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            if(appointment.getStart().substring(5,7).equals(month)){
                monthlyAppointments.add(appointment);
            }
        }
        reportsTable.setItems(monthlyAppointments);
    }

    /**This method is called at the end of the onMonthlyAppointments method, and it fills an observable list with all the months in string format.
     * @return returns an observable list holding strings representing the 12 months of the year.*/
    public ObservableList<String> getMonths(){
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("01");
        months.add("02");
        months.add("03");
        months.add("04");
        months.add("05");
        months.add("06");
        months.add("07");
        months.add("08");
        months.add("09");
        months.add("10");
        months.add("11");
        months.add("12");
        return months;
    }

    /**This method changes the table to display appointments by customer. It also sets the options for the customer combo box.
     * @param actionEvent method is called once the user selects the customer appointments combo box.*/
    public void onCustomerAppointments(ActionEvent actionEvent) throws SQLException {
        firstCol.setText("Appt ID");
        secondCol.setText("Title");
        thirdCol.setText("Description");
        fourthCol.setText("Location");
        fifthCol.setText("Contact");
        sixthCol.setText("Type");
        seventhCol.setText("Start Time");
        eighthCol.setText("End Time");
        ninthCol.setText("Start Date");
        tenthCol.setText("Created By");
        eleventhCol.setText("Customer ID");
        twelfthCol.setText("User ID");

        firstCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        eighthCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        ninthCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tenthCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        eleventhCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        twelfthCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactsPicker.setVisible(false);
        typesPicker.setVisible(false);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(true);
        reportsTable.setItems(emptyList);

        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> customers = FXCollections.observableArrayList();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            customers.add(customerID);
        }
        customerPicker.setItems(customers);
    }

    /**This method fills the table with appointments under the selected customer.
     * @param actionEvent method is called once the user selects a customer.*/
    public void onCustomerSelection(ActionEvent actionEvent){
        int customer = (Integer)customerPicker.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            if(appointment.getCustomerID() == customer){
                customerAppointments.add(appointment);
            }
        }
        reportsTable.setItems(customerAppointments);
    }

    /**This method takes the user back to the main menu.
     * @param actionEvent method is called once the user presses the main menu button.*/
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1449, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}
