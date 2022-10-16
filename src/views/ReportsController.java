package views;

import classes.Appointment;
import classes.Customers;
import classes.ReportsData;
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
    private ObservableList<Appointment> emptyList = FXCollections.observableArrayList();


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
        reportsTable.setItems(emptyList);

        monthsPicker.setItems(getMonths());

    }

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

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1449, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}