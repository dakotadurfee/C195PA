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
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

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

    /**This method is called every time the user is sent to the main menu and populates the table with all the scheduled appointments. The first time it is called
     * it displays a message saying if there are any upcoming appointments or not. It does not indicate if there are upcoming appointments after the first time the
     * method is called.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        appointmentsTable.setItems(Appointment.getAllAppointments());
        addCustomer.setVisible(false);
        modifyCustomerButton.setVisible(false);
        deleteCustomerButton.setVisible(false);

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

    /**This method is called when the user clicks the add appointment button, and it takes the user to the add appointment page.
     * @param actionEvent method is called when the user presses the add appointment button.*/
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 491);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**This method is called when the suer clicks the add customer button, and it takes the user to the add customer page.
     * @param actionEvent method is called when the user presses the add customer button.*/
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 608);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**This method is called when the user clicks the customers radio button, and it changes the table to display all the customers.
     * @param actionEvent method is called when the user presses the customers radio button.*/
    public void viewCustomers(ActionEvent actionEvent) {
        firstCol.setText("Customer_ID");
        secondCol.setText("Customer_Name");
        thirdCol.setText("Address");
        fourthCol.setText("Postal_Code");
        fifthCol.setText("Phone");
        sixthCol.setText("Create_Date");
        seventhCol.setText("Created_By");
        eighthCol.setText("Last_Update");
        ninthCol.setText("Last_Updated_By");
        tenthCol.setText("Division_ID");
        eleventhCol.setText("");
        twelfthCol.setText("");

        firstCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        sixthCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        eighthCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        ninthCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        tenthCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        eleventhCol.setCellValueFactory(new PropertyValueFactory<>(""));
        twelfthCol.setCellValueFactory(new PropertyValueFactory<>(""));
        appointmentsTable.setItems(Customers.getAllCustomers());
        addCustomer.setVisible(true);
        modifyCustomerButton.setVisible(true);
        deleteCustomerButton.setVisible(true);
        addAppointmentButton.setVisible(false);
        modifyAppointment.setVisible(false);
        deleteAppointmentButton.setVisible(false);
    }

    /**This method is called when the user selects the monthly radio button, and it displays all the appointments that are in the current month.
     * @param actionEvent method is called when the user presses the monthly radio button.*/
    public void onMonthly(ActionEvent actionEvent){
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
        appointmentsTable.setItems(monthlyAppointments);
        addCustomer.setVisible(false);
        modifyCustomerButton.setVisible(false);
        deleteCustomerButton.setVisible(false);
        addAppointmentButton.setVisible(true);
        modifyAppointment.setVisible(true);
        deleteAppointmentButton.setVisible(true);
    }

    /**This method is called when the user selects the weekly radio button, and it displays all the appointments that are in the current week.
     * @param actionEvent method is called when the user presses the weekly radio button.*/
    public void onWeekly(ActionEvent actionEvent){
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

        appointmentsTable.setItems(weeklyAppointments);
        addCustomer.setVisible(false);
        modifyCustomerButton.setVisible(false);
        deleteCustomerButton.setVisible(false);
        addAppointmentButton.setVisible(true);
        modifyAppointment.setVisible(true);
        deleteAppointmentButton.setVisible(true);
    }

    /**This method is called when the user selects the all appointments radio button, and it displays all the scheduled appointments.
     * @param actionEvent method is called when the user presses the all appointments radio button.*/
    public void onAll(ActionEvent actionEvent){
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
        appointmentsTable.setItems(Appointment.getAllAppointments());
        addCustomer.setVisible(false);
        modifyCustomerButton.setVisible(false);
        deleteCustomerButton.setVisible(false);
        addAppointmentButton.setVisible(true);
        modifyAppointment.setVisible(true);
        deleteAppointmentButton.setVisible(true);

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
            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 820, 491);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method deletes an appointment from the all appointments list and from the database if the user selected an appointment. The method displays an error
     * message if the user did not select an appointment. It also displays a confirmation message.
     * @param actionEvent method is called when the user presses the delete appointment button.*/
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(appointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Must select appointment to be deleted");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Must select customer to be modified");
            alert.showAndWait();
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 692, 400);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method deletes a customer and any appointments the customer has if the user selects a customer. An error message is displayed if the user did not select
     * a customer. It also displays a confirmation message.
     * @param actionEvent method is called when the user presses the delete customer button.*/
    public void deleteCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customers customer = (Customers) appointmentsTable.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Must select customer to be deleted");
            alert.showAndWait();
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
        Parent root = FXMLLoader.load(getClass().getResource("/views/Reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1449, 400);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
