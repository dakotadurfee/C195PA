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
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class addAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField contactIDField;
    public TextField customerIDField;
    public TextField userIDField;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public Button addAppointmentSave;
    public Spinner startTimeHours;
    public Spinner startTimeMinutes;
    public Spinner endTimeHours;
    public Spinner endTimeMinutes;
    public ComboBox contactField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int dynamicID = 0;
        int i = Appointment.getAllAppointments().size() - 1;
        if(i < 0) {
            dynamicID = 1;
            appointmentIDField.setText(Integer.toString(dynamicID));
        }
        else{
            dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
            appointmentIDField.setText(Integer.toString(dynamicID));
        }

        String sql = "SELECT Contact_Name FROM contacts";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            while(rs.next()){
                String contact = rs.getString("Contact_Name");
                contactNames.add(contact);
            }
            contactField.setItems(contactNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        int dynamicID = 0;
        boolean error = false;
        boolean dateError = false;
        String title = null;
        String description = null;
        String location = null;
        String contact = null;
        int contactID = 0;
        String type = null;
        if (titleField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Title field cannot be blank");
            alert.showAndWait();
            error = true;
        } else {
            title = titleField.getText();
        }

        if (descriptionField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Description field cannot be blank");
            alert.showAndWait();
            error = true;
        } else {
            description = descriptionField.getText();
        }

        if (locationField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Location field cannot be blank");
            alert.showAndWait();
            error = true;
        } else {
            location = locationField.getText();
        }

        if (typeField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Type field cannot be blank");
            alert.showAndWait();
            error = true;
        } else {
            type = typeField.getText();
        }


        contact = (String)contactField.getSelectionModel().getSelectedItem();
        if(contact == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select contact");
            alert.showAndWait();
            error = true;
        }
        else {
            contactID = getContactID(contact);
        }


        int customerID = 0;
        try {
            customerID = Integer.parseInt(customerIDField.getText());
            boolean found = false;
            for(Customers customer : Customers.getAllCustomers()){
                if(customer.getCustomerID() == customerID){
                    found = true;
                }
            }
            if(found == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Customer ID does not match an existing customer ID");
                alert.showAndWait();
                error = true;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in customer ID field");
            alert.showAndWait();
            error = true;
        }

        int userID = 0;
        try {
            userID = Integer.parseInt(userIDField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in user ID field");
            alert.showAndWait();
            error = true;
        }

        String startDate = null;
        String end = null;
        try {
            startDate = startDateField.getValue().toString();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select start date");
            alert.showAndWait();
            dateError = true;
        }

        String startTimeH = startTimeHours.getValue().toString();
        String startTimeM = startTimeMinutes.getValue().toString();
        if(Integer.parseInt(startTimeH) < 10) {
            startTimeH = "0" + startTimeH;
        }

        if (Integer.parseInt(startTimeM) < 10) {
            startTimeM = "0" + startTimeM;
        }

        String start = startDate + " " + startTimeH + ":" + startTimeM + ":" + "00";
        if(dateError == false) {
            LocalDateTime LDTstart = LocalDateTime.parse(startDate + "T" + startTimeH + ":" + startTimeM + ":" + "00");

            String endTimeH = endTimeHours.getValue().toString();
            String endTimeM = endTimeMinutes.getValue().toString();

            if (Integer.parseInt(endTimeH) < 10) {
                endTimeH = "0" + endTimeH;
            }


            if (Integer.parseInt(endTimeM) < 10) {
                endTimeM = "0" + endTimeM;
            }
            end = startDate + " " + endTimeH + ":" + endTimeM + ":" + "00";
            LocalDateTime LDTend = LocalDateTime.parse(startDate + "T" + endTimeH + ":" + endTimeM + ":" + "00");

            LocalDateTime openTime = LocalDateTime.parse(startDate + "T08:00:00");
            LocalDateTime closeTime = LocalDateTime.parse(startDate + "T22:00:00");

            ZoneId ESTzone = ZoneId.of("US/Eastern");
            ZoneId userZone = ZoneId.systemDefault();

            ZonedDateTime startZDT = LDTstart.atZone(userZone);
            ZonedDateTime endZDT = LDTend.atZone(userZone);

            ZonedDateTime userStartEST = startZDT.withZoneSameInstant(ESTzone);
            ZonedDateTime userEndEST = endZDT.withZoneSameInstant(ESTzone);

            if(userStartEST.toLocalDateTime().isBefore(openTime) || userEndEST.toLocalDateTime().isAfter(closeTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Selected time is outside of company hours: 8:00a.m. - 10:00p.m. EST");
                alert.showAndWait();
                error = true;
            }

            if (LDTend.isBefore(LDTstart) || LDTend.isEqual(LDTstart)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("End time must be after start time");
                alert.showAndWait();
                error = true;
            } else {
                for (Appointment appointment : Appointment.getAllAppointments()) {
                    if (customerID == appointment.getCustomerID()) {
                        String appointmentStart = appointment.getStart();
                        String appointmentEnd = appointment.getEnd();
                        appointmentStart = appointmentStart.replace(' ', 'T');
                        appointmentEnd = appointmentEnd.replace(' ', 'T');
                        LocalDateTime appointmentLDTstart = LocalDateTime.parse(appointmentStart);
                        LocalDateTime appointmentLDTend = LocalDateTime.parse(appointmentEnd);
                        if (LDTstart.isAfter(appointmentLDTstart) && LDTstart.isBefore(appointmentLDTend)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This appointment overlaps with another appointment for the same customer");
                            alert.showAndWait();
                            error = true;
                        } else if (LDTend.isAfter(appointmentLDTstart) && LDTend.isBefore(appointmentLDTend)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This appointment overlaps with another appointment for the same customer");
                            alert.showAndWait();
                            error = true;
                        } else if (LDTstart.isBefore(appointmentLDTstart) && LDTend.isAfter(appointmentLDTend)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This appointment overlaps with another appointment for the same customer");
                            alert.showAndWait();
                            error = true;
                        } else if (LDTstart.isAfter(appointmentLDTstart) && LDTend.isBefore(appointmentLDTend)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This appointment overlaps with another appointment for the same customer");
                            alert.showAndWait();
                            error = true;
                        } else if(LDTstart.isEqual(appointmentLDTstart) || LDTend.isEqual(appointmentLDTend)){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This appointment overlaps with another appointment for the same customer");
                            alert.showAndWait();
                            error = true;
                        }
                    }
                }
            }
        }

        String createDate = LocalDateTime.now().toString();
        createDate = createDate.substring(0,createDate.length() - 10);
        createDate = createDate.replace('T', ' ');
        String lastUpdate = createDate;

        int i = Appointment.getAllAppointments().size() - 1;
        if(i < 0) {
            dynamicID = 1;
        }
        else {
            dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
        }
        if (error == false && dateError == false) {
            Appointment appointment = new Appointment(dynamicID, customerID, userID, title, description, location, contactID, type, start, end, createDate, "script", lastUpdate, "script");
            Appointment.addAppointment(appointment);
            addAppointmentDB(dynamicID, customerID, userID, title, description, location, contactID, type, start, end, createDate, "script", lastUpdate, "script");
            toMain(actionEvent);
        }
    }

    public void addAppointmentDB(int appointmentID, int customerID, int userID, String title, String description, String location, int contactID, String type, String start, String end, String createDate,
                                 String createdBy, String lastUpdate, String lastUpdateBy) throws SQLException {

        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime userStart = LocalDateTime.parse(start, dt_formatter);
        LocalDateTime userEnd = LocalDateTime.parse(end, dt_formatter);
        LocalDateTime userCreateDate = LocalDateTime.parse(createDate, dt_formatter);

        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId userZone = ZoneId.systemDefault();

        ZonedDateTime userStartZDT = userStart.atZone(userZone);
        ZonedDateTime userEndZDT = userEnd.atZone(userZone);
        ZonedDateTime userCreateDateZDT = userCreateDate.atZone(userZone);

        ZonedDateTime DBstartZDT = userStartZDT.withZoneSameInstant(utcZone);
        ZonedDateTime DBendZDT = userEndZDT.withZoneSameInstant(utcZone);
        ZonedDateTime DBcreateDateZDT = userCreateDateZDT.withZoneSameInstant(utcZone);

        start = DBstartZDT.toLocalDateTime().format(dt_formatter);
        end = DBendZDT.toLocalDateTime().format(dt_formatter);
        createDate = DBcreateDateZDT.toLocalDateTime().format(dt_formatter);
        lastUpdate = createDate;

        ps.setInt(1, appointmentID);
        ps.setString(2,title);
        ps.setString(3,description);
        ps.setString(4,location);
        ps.setString(5,type);
        ps.setString(6,start);
        ps.setString(7,end);
        ps.setString(8,createDate);
        ps.setString(9,createdBy);
        ps.setString(10,lastUpdate);
        ps.setString(11,lastUpdateBy);
        ps.setInt(12,customerID);
        ps.setInt(13,userID);
        ps.setInt(14,contactID);
        ps.executeUpdate();
    }

    public int getContactID(String contact) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contact + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int contactID = 0;
        while(rs.next()){
            contactID = rs.getInt("Contact_ID");
        }
        return contactID;
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
