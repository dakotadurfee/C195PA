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
    }
    
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 608);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 608);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
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
            Scene scene = new Scene(root, 820, 608);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

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

    public void deleteAppointments(ObservableList<Appointment> appointmentList) throws SQLException {
        for(int i = 0; i < appointmentList.size(); i++){
            Appointment.deleteAppointment(appointmentList.get(i));
        }
    }
}
