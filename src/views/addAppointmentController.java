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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = Appointment.getAllAppointments().size() - 1;
        int dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
        appointmentIDField.setText(Integer.toString(dynamicID));
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        boolean error = false;
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
        try {
            startDate = startDateField.getValue().toString();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select start date");
            alert.showAndWait();
            error = true;
        }

        String startTimeH = startTimeHours.getValue().toString();
        String startTimeM = startTimeMinutes.getValue().toString();
        if (startTimeH.equals("0")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select start time");
            alert.showAndWait();
            error = true;
        } else if (Integer.parseInt(startTimeH) < 10) {
            startTimeH = "0" + startTimeH;
        }

        if (Integer.parseInt(startTimeM) < 10) {
            startTimeM = "0" + startTimeM;
        }

        String start = startDate + " " + startTimeH + ":" + startTimeM + ":" + "00";

        String endTimeH = endTimeHours.getValue().toString();
        String endTimeM = endTimeMinutes.getValue().toString();
        if (endTimeH.equals("0")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select end time");
            alert.showAndWait();
            error = true;
        } else if (Integer.parseInt(endTimeH) < 10) {
            endTimeH = "0" + endTimeH;
        }

        if (Integer.parseInt(endTimeM) < 10) {
            endTimeM = "0" + endTimeM;
        }
        String end = startDate + " " + endTimeH + ":" + endTimeM + ":" + "00";




        String createDate = LocalDateTime.now().toString();
        String lastUpdate = createDate;

        int i = Appointment.getAllAppointments().size() - 1;
        int dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
        if(error == false) {
            Appointment appointment = new Appointment(dynamicID, customerID, userID, title, description, location, contactID, type, start, end, createDate, "script", lastUpdate, "script");
            Appointment.addAppointment(appointment);
            toMain(actionEvent);
        }
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1105, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}
