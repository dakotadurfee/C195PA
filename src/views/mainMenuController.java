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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**This class controls the main menu of the application.*/
public class mainMenuController implements Initializable {
    public ToggleGroup appointments;
    public TableView appointmentsTable;
    public Button addAppointmentButton;
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
    public Button addCustomer;
    public static Appointment mAppointment;
    public static Customers mCustomer;
    public Button modifyAppointment;
    public Button deleteAppointmentButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    private static boolean called = false;
    public static Map<TableColumn, String> appointmentsTextMap;
    public static Map<TableColumn, String> appointmentsValueMap;
    public static Map<TableColumn, String> customersTextMap;
    public static Map<TableColumn, String> customersValueMap;

    /**This method is called every time the user is sent to the main menu and populates the table with all the scheduled appointments. The first time it is called
     * it displays a message saying if there are any upcoming appointments or not. It does not indicate if there are upcoming appointments after the first time the
     * method is called.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTextMap = new LinkedHashMap<>() {{
            put(firstCol, "Appt ID");
            put(secondCol, "Title");
            put(thirdCol, "Description");
            put(fourthCol, "Location");
            put(fifthCol, "Contact");
            put(sixthCol, "Type");
            put(seventhCol, "Start Time");
            put(eighthCol, "End Time");
            put(ninthCol, "Start Date");
            put(tenthCol, "Created By");
            put(eleventhCol, "Customer ID");
            put(twelfthCol, "User ID");
        }};

        appointmentsValueMap = new LinkedHashMap<>() {{
            put(firstCol, "id");
            put(secondCol, "title");
            put(thirdCol, "description");
            put(fourthCol, "location");
            put(fifthCol, "contact");
            put(sixthCol, "type");
            put(seventhCol, "start");
            put(eighthCol, "end");
            put(ninthCol, "createDate");
            put(tenthCol, "createdBy");
            put(eleventhCol, "customerID");
            put(twelfthCol, "userID");
        }};

        customersTextMap = new LinkedHashMap<>() {{
            put(firstCol, "Customer_ID");
            put(secondCol, "Customer_Name");
            put(thirdCol, "Address");
            put(fourthCol, "Postal_Code");
            put(fifthCol, "Phone");
            put(sixthCol, "Create_Date");
            put(seventhCol, "Created_By");
            put(eighthCol, "Last_Update");
            put(ninthCol, "Last_Updated_By");
            put(tenthCol, "Division_ID");
            put(eleventhCol, "");
            put(twelfthCol, "");
        }};

        customersValueMap = new LinkedHashMap<>(){{
            put(firstCol, "customerID");
            put(secondCol, "customerName");
            put(thirdCol, "address");
            put(fourthCol, "postalCode");
            put(fifthCol, "phone");
            put(sixthCol, "createDate");
            put(seventhCol, "createdBy");
            put(eighthCol, "lastUpdate");
            put(ninthCol, "lastUpdatedBy");
            put(tenthCol, "divisionID");
            put(eleventhCol, "");
            put(twelfthCol, "");
        }};

        setAppointmentsTable(Appointment.getAllAppointments());

        if(!called) {
            ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ZoneId utcZone = ZoneId.of("UTC");
            ZoneId userZone = ZoneId.systemDefault();

            LocalDateTime currentTime = LocalDateTime.now(utcZone);

            for (Appointment appointment : Appointment.getAllAppointments()) {
                LocalDateTime userAppointmentTime = LocalDateTime.parse(appointment.getStart(), dt_formatter);

                ZonedDateTime userAppointmentTimeZDT = userAppointmentTime.atZone(userZone);

                ZonedDateTime userAppointmentTimeUTC = userAppointmentTimeZDT.withZoneSameInstant(utcZone);

                userAppointmentTime = userAppointmentTimeUTC.toLocalDateTime();

                if ((userAppointmentTime.minusMinutes(15).isEqual(currentTime) || userAppointmentTime.minusMinutes(15).isBefore(currentTime)
                        && userAppointmentTime.isAfter(currentTime))) {
                    upcomingAppointments.add(appointment);
                }
            }

            if (upcomingAppointments.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setContentText("There are no upcoming appointments");
                alert.showAndWait();
            } else {
                for (Appointment appointment : upcomingAppointments) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointments");
                    alert.setContentText("Upcoming appointment:\n Appointment ID: " + appointment.getId() + "\n Start: " + appointment.getStart() + "\n " +
                            "End: " + appointment.getEnd());
                    alert.showAndWait();
                }
            }
            called = true;
        }
    }

    public void setAppointmentsTable(ObservableList<Appointment> items){
        for(TableColumn column: appointmentsTextMap.keySet()){
            column.setText(appointmentsTextMap.get(column));
        }

        for(TableColumn column: appointmentsValueMap.keySet()){
            column.setCellValueFactory(new PropertyValueFactory<>(appointmentsValueMap.get(column)));
        }
        appointmentsTable.setItems(items);
        addCustomer.setVisible(false);
        modifyCustomerButton.setVisible(false);
        deleteCustomerButton.setVisible(false);
        addAppointmentButton.setVisible(true);
        modifyAppointment.setVisible(true);
        deleteAppointmentButton.setVisible(true);
    }

    public void setCustomersTable(ObservableList<Customers> items){
        for(TableColumn column: customersTextMap.keySet()){
            column.setText(customersTextMap.get(column));
        }

        for(TableColumn column: customersValueMap.keySet()){
            column.setCellValueFactory(new PropertyValueFactory<>(customersValueMap.get(column)));
        }
        appointmentsTable.setItems(items);
        addCustomer.setVisible(true);
        modifyCustomerButton.setVisible(true);
        deleteCustomerButton.setVisible(true);
        addAppointmentButton.setVisible(false);
        modifyAppointment.setVisible(false);
        deleteAppointmentButton.setVisible(false);
    }

    /**This method is called when the user clicks the add appointment button, and it takes the user to the add appointment page.
     * @param actionEvent method is called when the user presses the add appointment button.*/
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Main.switchScene("/views/addAppointment.fxml", 820, 491, "Add Appointment", actionEvent);
    }

    /**This method is called when the suer clicks the add customer button, and it takes the user to the add customer page.
     * @param actionEvent method is called when the user presses the add customer button.*/
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Main.switchScene("/views/AddCustomer.fxml", 820, 608, "Add Customer", actionEvent);
    }

    /**This method is called when the user clicks the customers radio button, and it changes the table to display all the customers.
     * @param actionEvent method is called when the user presses the customers radio button.*/
    public void viewCustomers(ActionEvent actionEvent) {
        setCustomersTable(Customers.getAllCustomers());
    }

    /**This method is called when the user selects the monthly radio button, and it displays all the appointments that are in the current month.
     * @param actionEvent method is called when the user presses the monthly radio button.*/
    public void onMonthly(ActionEvent actionEvent){
        LocalDateTime currentLDT = LocalDateTime.now();
        String currentMonth = currentLDT.toString();
        currentMonth = currentMonth.substring(5,7);
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            String month = appointment.getStart().substring(5,7);
            if(month.equals(currentMonth)){
                monthlyAppointments.add(appointment);
            }
        }
        setAppointmentsTable(monthlyAppointments);
    }

    /**This method is called when the user selects the weekly radio button, and it displays all the appointments that are in the current week.
     * @param actionEvent method is called when the user presses the weekly radio button.*/
    public void onWeekly(ActionEvent actionEvent){
        LocalDateTime currentLDT = LocalDateTime.now();
        LocalDateTime sundayBefore = currentLDT.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime saturdayAfter = currentLDT.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.getAllAppointments()){
            String start = appointment.getStart();
            start = start.replace(' ', 'T');
            LocalDateTime LDTstart = LocalDateTime.parse(start);

            if((LDTstart.isEqual(sundayBefore) || LDTstart.isAfter(sundayBefore)) && (LDTstart.isEqual(saturdayAfter) || LDTstart.isBefore(saturdayAfter))){
                weeklyAppointments.add(appointment);
            }
        }

        setAppointmentsTable(weeklyAppointments);
    }

    /**This method is called when the user selects the all appointments radio button, and it displays all the scheduled appointments.
     * @param actionEvent method is called when the user presses the all appointments radio button.*/
    public void onAll(ActionEvent actionEvent){
        setAppointmentsTable(Appointment.getAllAppointments());
    }

    /**This method takes the user to the modify appointment page if the user selected an appointment and displays an error message if the user did not select an
     * appointment.
     * @param actionEvent method is called when the user presses the modify appointment button. */
    public void modifyAppointment(ActionEvent actionEvent) throws IOException {

        mAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(mAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Must select appointment to be modified");
            alert.showAndWait();
        }
        else {
            Main.switchScene("/views/ModifyAppointment.fxml", 820, 491, "Modify Appointment", actionEvent);
        }
    }

    /**This method deletes an appointment from the all appointments list and from the database if the user selected an appointment. The method displays an error
     * message if the user did not select an appointment. It also displays a confirmation message.
     * @param actionEvent method is called when the user presses the delete appointment button.*/
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(appointment == null){
            Main.showError("Must select appointment to be deleted");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Appointment.deleteAppointment(appointment);

                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, appointment.getId());
                ps.executeUpdate();

                String appointmentID = String.valueOf(appointment.getId());
                String appointmentType = appointment.getType();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Appointment cancelled: \nAppointment ID: " + appointmentID + "\nType: "+ appointmentType);
                a.showAndWait();
            }
        }
    }

    /**This method takes the user to the modify customer page if the user selected a customer and displays an error message if the user did not select a customer.
     * @param actionEvent method is called when the user presses the modify customer button.*/
    public void modifyCustomer(ActionEvent actionEvent) throws IOException {
        mCustomer = (Customers) appointmentsTable.getSelectionModel().getSelectedItem();
        if(mCustomer == null){
            Main.showError("Must select customer to be modified");
        }
        else{
            Main.switchScene("/views/ModifyCustomer.fxml", 692, 400, "Modify Customer", actionEvent);
        }
    }

    /**This method deletes a customer and any appointments the customer has if the user selects a customer. An error message is displayed if the user did not select
     * a customer. It also displays a confirmation message.
     * @param actionEvent method is called when the user presses the delete customer button.*/
    public void deleteCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customers customer = (Customers) appointmentsTable.getSelectionModel().getSelectedItem();
        if(customer == null){
            Main.showError("Must select customer to be deleted");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this customer? All appointments this customer has will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
                for(Appointment appointment : Appointment.getAllAppointments()){
                    if(appointment.getCustomerID() == customer.getCustomerID()){
                        appointmentList.add(appointment);
                    }
                }
                deleteAppointments(appointmentList);
                Customers.deleteCustomer(customer);

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customer.getCustomerID());
                ps.executeUpdate();

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Customer and customer's appointments successfully deleted");
                a.showAndWait();
            }
        }
    }

    /**This method deletes all the appointments a customer has before the customer is deleted.
     * @param appointmentList method takes in a list holding all the appointments a customer has and deletes each of those appointments.*/
    public void deleteAppointments(ObservableList<Appointment> appointmentList) throws SQLException {
        for(int i = 0; i < appointmentList.size(); i++){
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,appointmentList.get(i).getId());
            ps.executeUpdate();
            Appointment.deleteAppointment(appointmentList.get(i));
        }

    }

    /**This method takes the user to the reports page of the application and is called when the user presses the reports button from the main menu.*/
    public void toReports(ActionEvent actionEvent) throws IOException {
        Main.switchScene("/views/Reports.fxml", 1449, 400, "Reports", actionEvent);
    }
}
