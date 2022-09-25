package views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

    }

    public void onSave(ActionEvent actionEvent) {
        boolean error = false;
        if(titleField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Title field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String title = titleField.getText();
        }

        if(descriptionField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Description field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String description = descriptionField.getText();
        }

        if(locationField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Location field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String location = locationField.getText();
        }

        if(typeField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Type field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String type = typeField.getText();
        }

        try{
            int contactID = Integer.parseInt(contactIDField.getText());
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in contact ID field");
            alert.showAndWait();
            error = true;
        }

        try{
            int customerID = Integer.parseInt(customerIDField.getText());
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in customer ID field");
            alert.showAndWait();
            error = true;
        }

        try{
            int userID = Integer.parseInt(userIDField.getText());
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must enter an integer in user ID field");
            alert.showAndWait();
            error = true;
        }

        if(startDateField.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Start date field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String startDate = startDateField.getAccessibleText();
        }

        if(startTimeHours.equals("") || startTimeMinutes.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Start time field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String startTime = startTimeHours.getAccessibleText() + ":" + startTimeMinutes.getAccessibleText();
        }

        if(endDateField.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("End date field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String endDate = endDateField.getAccessibleText();
        }


        if(endTimeHours.equals("") || endTimeMinutes.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("End time field cannot be blank");
            alert.showAndWait();
            error = true;
        }
        else{
            String endTime = endTimeHours.getAccessibleText() + ":" + endTimeMinutes.getAccessibleText();
        }

    }
}
