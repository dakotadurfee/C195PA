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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**This class starts the application.*/
public class Main extends Application {

    /**This method loads the first screen in the application which is the login form.*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root, 460, 242));
        primaryStage.show();
    }

    /**This method calls a method to connect to the database, fills the all customers list, fills the all appointments list, fills all the lists used for 1st
     * level division data, and launches the application.*/
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

    /**This method is called from main and gets each customer from the customer table in the database and adds them to the all customers list in the Customers
     *  class.*/
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

    /**This method is called from main and gets each appointment from the appointments table in the database and adds them to the all appointments list in the
     * Appointment class. It also changes the appointment start and end times to user time before adding them to the list.*/
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

    /**This method is called from main and adds each country from the database to the country list in the CountryData class.*/
    public static void addCountries() throws SQLException{
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String country = rs.getString("Country");
            CountryData.addCountry(country);
        }
    }

    /**This method is called from main and adds each U.S. division to the U.S. divisions list in the CountryData class.*/
    public static void addUSDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addUSDivision(division);
        }
    }

    /**This method is called from main and adds each UK division to the UK divisions list in the CountryData class.*/
    public static void addUKDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addUKDivision(division);
        }
    }

    /**This method is called from main and adds each Canada division to the Canada divisions list in the CountryData class.*/
    public static void addCanadaDivisions() throws SQLException{
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String division = rs.getString("Division");
            CountryData.addCanadaDivision(division);
        }
    }

}
