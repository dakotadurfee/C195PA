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
import javafx.scene.control.Button;
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

import static classes.Appointment.allAppointments;
import static classes.Customers.allCustomers;

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
        appointmentsTable.setItems(allAppointments);
    }
    
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 608);
        stage.setTitle("Add Appointment");
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
        appointmentsTable.setItems(allCustomers);
        //change column names to 1st column, 2nd column.... and when user selects customers radio button change text of table columns
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
        appointmentsTable.setItems(allAppointments);
    }
}