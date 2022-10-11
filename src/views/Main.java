package views;

import classes.Appointment;
import classes.CountryData;
import classes.Customers;
import helper.JDBC;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root, 460, 242));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        getCustomers();
        getAppointments();
        addCountries();
        addUSDivisions();
        addUKDivisions();
        addCanadaDivisions();
        launch(args);
        JDBC.closeConnection();
    }

    public static void getCustomers() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int customerID  = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");

            Customers customer = new Customers(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            Customers.addCustomer(customer);
        }
    }

    public static void getAppointments() throws SQLException{
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int appointment_ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            String db_start = rs.getString("Start");
            String db_end = rs.getString("End");

            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);
            LocalDateTime utc_end_dt = LocalDateTime.parse(db_end, dt_formatter);

            ZoneId utc_zone = ZoneId.of("UTC");
            ZoneId user_zone = ZoneId.systemDefault();

            ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);
            ZonedDateTime utc_end_zdt = utc_end_dt.atZone(utc_zone);

            ZonedDateTime user_start_zdt = utc_start_zdt.withZoneSameInstant(user_zone);
            ZonedDateTime user_end_zdt = utc_end_zdt.withZoneSameInstant(user_zone);

            String start = user_start_zdt.toLocalDateTime().format(dt_formatter);
            String end = user_end_zdt.toLocalDateTime().format(dt_formatter);

            Appointment appointment = new Appointment(appointment_ID, customerID, userID, title, description, location, contactID, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy);
            Appointment.addAppointment(appointment);
        }
    }

    public static void addCountries() throws SQLException{
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String country = rs.getString("Country");
            CountryData.addCountry(country);
        }
    }

    public static void addUSDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addUSDivision(division);
        }
    }

    public static void addUKDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addUKDivision(division);
        }
    }

    public static void addCanadaDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addCanadaDivision(division);
        }
    }

    public static void upcomingAppointments(){
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
    }
}
