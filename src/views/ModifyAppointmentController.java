package views;

import classes.Appointment;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static views.mainMenuController.mAppointment;

public class ModifyAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField contactIDField;
    public TextField customerIDField;
    public TextField userIDField;
    public DatePicker startDateField;
    public Spinner startTimeHours;
    public Spinner startTimeMinutes;
    public Spinner endTimeHours;
    public Spinner endTimeMinutes;
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDField.setText(Integer.toString(mAppointment.getId()));
        titleField.setText(mAppointment.getTitle());
        descriptionField.setText(mAppointment.getDescription());
        locationField.setText(mAppointment.getLocation());
        typeField.setText(mAppointment.getType());
        contactIDField.setText(Integer.toString(mAppointment.getContact()));
        customerIDField.setText(Integer.toString(mAppointment.getCustomerID()));
        userIDField.setText(Integer.toString(mAppointment.getUserID()));
        startDateField.setValue(LocalDate.of(Integer.parseInt(mAppointment.getStart().substring(0,4)), Integer.parseInt(mAppointment.getStart().substring(5,7)), Integer.parseInt(mAppointment.getStart().substring(8,10))));
        startTimeHours.getValueFactory().setValue(Integer.parseInt(mAppointment.getStart().substring(12,13)));
        startTimeMinutes.getValueFactory().setValue(Integer.parseInt(mAppointment.getStart().substring(14,16)));
        endTimeHours.getValueFactory().setValue(Integer.parseInt(mAppointment.getEnd().substring(12,13)));
        endTimeMinutes.getValueFactory().setValue(Integer.parseInt(mAppointment.getEnd().substring(14,16)));
    }

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        boolean error = false;
        boolean dateError = false;
        String title = null;
        String description = null;
        String location = null;
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

        int contactID = 0;
        try {
            contactID = Integer.parseInt(contactIDField.getText());
            if(contactID < 1 || contactID > 3) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Contact ID does not match an existing contact ID");
                alert.showAndWait();
                error = true;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in contact ID field");
            alert.showAndWait();
            error = true;
        }


        int customerID = 0;
        try {
            customerID = Integer.parseInt(customerIDField.getText());
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
                    if(appointment == mAppointment){

                    }
                    else if (customerID == appointment.getCustomerID()) {
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
                        }
                    }
                }
            }
        }

        String lastUpdate = LocalDateTime.now().toString();
        lastUpdate = lastUpdate.substring(0, lastUpdate.length() - 10);
        lastUpdate = lastUpdate.replace('T', ' ');

        int appointmentID = Integer.parseInt(appointmentIDField.getText());
        if(error == false && dateError == false) {
            mAppointment.setCustomerID(customerID);
            mAppointment.setUserID(userID);
            mAppointment.setTitle(title);
            mAppointment.setDescription(description);
            mAppointment.setLocation(location);
            mAppointment.setContact(contactID);
            mAppointment.setType(type);
            mAppointment.setStart(start);
            mAppointment.setEnd(end);
            mAppointment.setCreatedBy("Script");
            mAppointment.setLastUpdate(lastUpdate);
            mAppointment.setLastUpdateBy("Script");
            modifyAppointmentDB(appointmentID, customerID, userID, title, description, location, contactID, type, start, end, "Script", lastUpdate, "Script");
            toMain(actionEvent);
        }
    }

    public void modifyAppointmentDB(int appointmentID, int customerID, int userID, String title, String description, String location, int contactID, String type, String start, String end, String createdBy,
                                    String lastUpdate, String lastUpdatedBy) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, " +
                "Contact_ID = ? WHERE Appointment_ID = " + appointmentID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime userStart = LocalDateTime.parse(start, dt_formatter);
        LocalDateTime userEnd = LocalDateTime.parse(end, dt_formatter);
        LocalDateTime userLastUpdate = LocalDateTime.parse(lastUpdate, dt_formatter);

        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId userZone = ZoneId.systemDefault();

        ZonedDateTime userStartZDT = userStart.atZone(userZone);
        ZonedDateTime userEndZDT = userEnd.atZone(userZone);
        ZonedDateTime userLastUpdateZDT = userLastUpdate.atZone(userZone);

        ZonedDateTime DBstartZDT = userStartZDT.withZoneSameInstant(utcZone);
        ZonedDateTime DBendZDT = userEndZDT.withZoneSameInstant(utcZone);
        ZonedDateTime DBlastUpdate = userLastUpdateZDT.withZoneSameInstant(utcZone);

        start = DBstartZDT.toLocalDateTime().format(dt_formatter);
        end = DBendZDT.toLocalDateTime().format(dt_formatter);
        lastUpdate = DBlastUpdate.toLocalDateTime().format(dt_formatter);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, createdBy);
        ps.setString(8, lastUpdate);
        ps.setString(9, lastUpdatedBy);
        ps.setInt(10, customerID);
        ps.setInt(11, userID);
        ps.setInt(12, contactID);
        ps.executeUpdate();
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
