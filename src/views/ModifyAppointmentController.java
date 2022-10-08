package views;

import classes.Appointment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        appointmentIDField.setText(Integer.toString(mAppointment.getCustomerID()));
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

    public void onSave(ActionEvent actionEvent) throws IOException {
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

        int i = Appointment.getAllAppointments().size() - 1;
        int dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
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
            toMain(actionEvent);
        }
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
