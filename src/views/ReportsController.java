package views;

import classes.Appointment;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**This method controls the reports page in the user interface.*/
public class ReportsController implements Initializable {

    public TableColumn<Appointment, String> firstCol;
    public TableColumn<Appointment, String> secondCol;
    public TableColumn<Appointment, String> thirdCol;
    public TableColumn<Appointment, String> fourthCol;
    public TableColumn<Appointment, String> fifthCol;
    public TableColumn<Appointment, String> sixthCol;
    public TableColumn<Appointment, String> seventhCol;
    public TableColumn<Appointment, String> eighthCol;
    public TableColumn<Appointment, String> ninthCol;
    public TableColumn<Appointment, String> tenthCol;
    public TableColumn<Appointment, String> eleventhCol;
    public TableColumn<Appointment, String> twelfthCol;
    public ToggleGroup schedules;
    public TableView<Appointment> reportsTable;
    public ComboBox<String> contactsPicker;
    public ComboBox<String> typesPicker;
    public ComboBox<String> monthsPicker;
    public ComboBox<Integer> customerPicker;
    private Map<TableColumn<Appointment, String>, String> appointmentsTextMap;
    private Map<TableColumn<Appointment, String>, String> appointmentsValueMap;
    private final ObservableList<Appointment> emptyList = FXCollections.observableArrayList();

    /**This method is called when the reports page is loaded and sets the items for the contacts field with all the contacts from the database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<TableColumn<Appointment, String>, String> tempMap = new LinkedHashMap<>();
        tempMap.put(firstCol, "Appt ID");
        tempMap.put(secondCol, "Title");
        tempMap.put(thirdCol, "Description");
        tempMap.put(fourthCol, "Location");
        tempMap.put(fifthCol, "Contact");
        tempMap.put(sixthCol, "Type");
        tempMap.put(seventhCol, "Start Time");
        tempMap.put(eighthCol, "End Time");
        tempMap.put(ninthCol, "Start Date");
        tempMap.put(tenthCol, "Created By");
        tempMap.put(eleventhCol, "Customer ID");
        tempMap.put(twelfthCol, "User ID");
        appointmentsTextMap = Collections.unmodifiableMap(tempMap);

        Map<TableColumn<Appointment, String>, String> tMap = new LinkedHashMap<>();
        tMap.put(firstCol, "id");
        tMap.put(secondCol, "title");
        tMap.put(thirdCol, "description");
        tMap.put(fourthCol, "location");
        tMap.put(fifthCol, "contact");
        tMap.put(sixthCol, "type");
        tMap.put(seventhCol, "start");
        tMap.put(eighthCol, "end");
        tMap.put(ninthCol, "createDate");
        tMap.put(tenthCol, "createdBy");
        tMap.put(eleventhCol, "customerID");
        tMap.put(twelfthCol, "userID");

        appointmentsValueMap = Collections.unmodifiableMap(tMap);

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

    public void setAppointmentsTable(){
        for(TableColumn<Appointment, String> column: appointmentsTextMap.keySet()){
            column.setText(appointmentsTextMap.get(column));
        }

        for(TableColumn<Appointment, String> column: appointmentsValueMap.keySet()){
            column.setCellValueFactory(new PropertyValueFactory<>(appointmentsValueMap.get(column)));
        }
        reportsTable.setItems(emptyList);
    }

    /**This method changes the table to display the information for the appointments by contact. It also sets the items for the contacts field with all the contacts
     * from the database.*/
    public void onContactAppointments(){
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

    /**This method fills the table with appointments under the selected contact.*/
    public void onContactSelection(){
        String contact = contactsPicker.getSelectionModel().getSelectedItem();
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
     * from the database.*/
    public void onTypeAppointments() throws SQLException {
        setAppointmentsTable();
        contactsPicker.setVisible(false);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(false);
        typesPicker.setVisible(true);
        String sql = "SELECT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> types = FXCollections.observableArrayList();
        while(rs.next()){
            types.add(rs.getString("Type"));
        }
        typesPicker.setItems(types);
    }

    /**This method fills the table with appointments under the selected type.*/
    public void onTypeSelection(){
        String type = typesPicker.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> typeAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            if(appointment.getType().equals(type)){
                typeAppointments.add(appointment);
            }
        }
        reportsTable.setItems(typeAppointments);
    }

    /**This method changes the table to display appointments by month. It also sets the options for the months combo box.*/
    public void onMonthlyAppointments(){
        setAppointmentsTable();
        contactsPicker.setVisible(false);
        typesPicker.setVisible(false);
        customerPicker.setVisible(false);
        monthsPicker.setVisible(true);
        monthsPicker.setItems((getMonths()));
    }



    /**This method fills the table with appointments under the selected month.*/
    public void onMonthlySelection(){
        String month = monthsPicker.getSelectionModel().getSelectedItem();
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

    /**This method changes the table to display appointments by customer. It also sets the options for the customer combo box.*/
    public void onCustomerAppointments() throws SQLException {
        setAppointmentsTable();
        contactsPicker.setVisible(false);
        typesPicker.setVisible(false);
        monthsPicker.setVisible(false);
        customerPicker.setVisible(true);
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

    /**This method fills the table with appointments under the selected customer.*/
    public void onCustomerSelection(){
        int customer = customerPicker.getSelectionModel().getSelectedItem();
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
        Main.switchScene("/views/mainMenu.fxml", 1449, 400, "Main Menu", actionEvent);
    }
}
