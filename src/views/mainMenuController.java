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
    public Button modifyAppointment;
    public Button deleteAppointmentButton;


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
        addAppointmentButton.setVisible(false);
        modifyAppointment.setVisible(false);
        deleteAppointmentButton.setVisible(false);
    }

    public void viewAppointments(ActionEvent actionEvent){
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

    public void deleteAppointment(ActionEvent actionEvent){
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
            }
        }
    }
}
