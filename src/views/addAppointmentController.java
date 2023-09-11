package views;

import classes.Appointment;
import classes.Customers;
import helper.JDBC;
import helper.LDTtoStringInterface;
import helper.contactIDInterface;
import helper.getContact;
import helper.TimeConverter;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**This class controls the add appointment page in the user interface.*/
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

    /**This method fills the appointment id field with an auto generated value that is one higher than the highest appointment ID value and fills the
     * contact field with all the names from the contacts table in the database.*/
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

    /**This method is called when the save button is clicked in the user interface. It creates a new appointment object and adds it to the all appointments list and the database if there are no errors.
     * It uses error checking to see if there are any blank fields or if there are characters in fields that require integers. It also checks to see if there are any overlapping appointments
     * and if the user entered time is within company hours. If there are any errors it will display a message saying what the error is and will not save the
     * information.
     * @param actionEvent method is called when the save button is clicked.*/
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        int dynamicID = 0;
        boolean error = false;
        boolean dateError = false;
        String title = null;
        String description = null;
        String location = null;
        String contact = null;
        String createDate = LocalDateTime.now().toString();
        int contactID = 0;
        String type = null;
        if (titleField.getText().equals("")) {
            Main.showError("Title field cannot be blank");
            error = true;
        } else {
            title = titleField.getText();
        }

        if (descriptionField.getText().equals("")) {
            Main.showError("Description field cannot be blank");
            error = true;
        } else {
            description = descriptionField.getText();
        }

        if (locationField.getText().equals("")) {
            Main.showError("Location field cannot be blank");
            error = true;
        } else {
            location = locationField.getText();
        }

        if (typeField.getText().equals("")) {
            Main.showError("Type field cannot be blank");
            error = true;
        } else {
            type = typeField.getText();
        }


        contact = (String)contactField.getSelectionModel().getSelectedItem();
        if(contact == null){
            Main.showError("Must select contact");
            error = true;
        }
        else {
            contactID = getContact.contactID(contact);
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
                Main.showError("Customer ID does not match an existing customer ID");
                error = true;
            }
        } catch (NumberFormatException e) {
            Main.showError("Must enter an integer in customer ID field");
            error = true;
        }

        String startDate = null;
        String end = null;
        try {
            startDate = startDateField.getValue().toString();
        } catch (Exception e) {
            Main.showError("Must select start date");
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
                Main.showError("Selected time is outside of company hours: 8:00a.m. - 10:00p.m. EST");
                error = true;
            }

            if (LDTend.isBefore(LDTstart) || LDTend.isEqual(LDTstart)) {
                Main.showError("End time must be after start time");
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
                            Main.showError("This appointment overlaps with another appointment for the same customer");
                            error = true;
                        } else if (LDTend.isAfter(appointmentLDTstart) && LDTend.isBefore(appointmentLDTend)) {
                            Main.showError("This appointment overlaps with another appointment for the same customer");
                            error = true;
                        } else if (LDTstart.isBefore(appointmentLDTstart) && LDTend.isAfter(appointmentLDTend)) {
                            Main.showError("This appointment overlaps with another appointment for the same customer");
                            error = true;
                        } else if (LDTstart.isAfter(appointmentLDTstart) && LDTend.isBefore(appointmentLDTend)) {
                            Main.showError("This appointment overlaps with another appointment for the same customer");
                            error = true;
                        } else if(LDTstart.isEqual(appointmentLDTstart) || LDTend.isEqual(appointmentLDTend)){
                            Main.showError("This appointment overlaps with another appointment for the same customer");
                            error = true;
                        }
                    }
                }
            }
        }

        createDate = TimeConverter.toReadableString(createDate);
        String lastUpdate = createDate;

        int i = Appointment.getAllAppointments().size() - 1;
        if(i < 0) {
            dynamicID = 1;
        }
        else {
            dynamicID = Appointment.getAllAppointments().get(i).getId() + 1;
        }
        if (error == false && dateError == false) {
            Appointment appointment = new Appointment(dynamicID, customerID, loginController.getUserID(), title, description, location, contactID, type, start, end, createDate, loginController.getDBusername(), lastUpdate, loginController.getDBusername());
            Appointment.addAppointment(appointment);
            addAppointmentDB(dynamicID, customerID, loginController.getUserID(), title, description, location, contactID, type, start, end, createDate, loginController.getDBusername(), lastUpdate, loginController.getDBusername());
            Main.switchScene("/views/mainMenu.fxml", 1449, 400, "Main Menu", actionEvent);
        }
    }

    /**This method is called at the end of the onSave method and takes all the variables needed for the database and adds an appointment to the appointments table.*/
    public void addAppointmentDB(int appointmentID, int customerID, int userID, String title, String description, String location, int contactID, String type, String start, String end, String createDate,
                                 String createdBy, String lastUpdate, String lastUpdateBy) throws SQLException {

        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);



        start = TimeConverter.toUTCTime(start);
        end = TimeConverter.toUTCTime(end);
        createDate = TimeConverter.toUTCTime(createDate);
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
        ps.setInt(13,loginController.getUserID());
        ps.setInt(14,contactID);
        ps.executeUpdate();
    }
}
