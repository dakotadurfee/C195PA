package classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**This class stores all the appointments and has the getters and setters for all the variables associated with appointments.*/
public class Appointment {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private int id;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private int contact;
    private String type;
    private String start;
    private String end;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    /**This is the class constructor and sets all the appointment class variables.*/
    public Appointment(int id, int customerID, int userID, String title, String description, String location, int contact, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy){
        this.id = id;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**This method takes in an appointment object as a parameter and adds that object to the all appointments list.
     * @param newappointment takes in an appointment object and adds it to the all appointments list.*/
    public static void addAppointment(Appointment newappointment){
        allAppointments.add(newappointment);
    }

    /**This method returns a list holding all the appointments.
     * @return returns an observable list that holds all the appointments.*/
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    /**This method returns the appointments ID.
     * @return returns an appointment objects ID.*/
    public int getId(){
        return id;
    }

    /**This method sets the appointment ID.
     * @param id takes in an integer and sets the appointment's id to that integer.*/
    public void setId(int id){
        this.id = id;
    }

    /**This method returns the customer's ID associated with an appointment.
     * @return returns the customer's ID who is associated with the appointment.*/
    public int getCustomerID(){
        return customerID;
    }

    /**This method sets the customer's ID associated with an appointment.
     * @param customerID takes in an integer and sets the customer's ID to that integer.*/
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }

    /**This method gets the user's ID.
     * @return returns the user's ID associated with an appointment.*/
    public int getUserID(){
        return userID;
    }

    /**This method sets the user's ID.
     * @param userID takes in an integer and sets the user's ID to that integer.*/
    public void setUserID(int userID){
        this.userID = userID;
    }

    /**This method returns the title associated with an appointment.
     * @return returns the title associated with the appointment.*/
    public String getTitle(){
        return title;
    }

    /**This method sets the title of the associated appointment.
     * @param title takes in a string object and sets the title of the associated appointment to that string.*/
    public void setTitle(String title){
        this.title = title;
    }

    /**This method returns the description of the associated appointment.
     * @return returns the description of the associated appointment.*/
    public String getDescription(){
        return description;
    }

    /**This method sets the description of the associated appointment.
     * @param description takes a string object and sets the description of the associated appointment to that string.*/
    public void setDescription(String description){
        this.description = description;
    }

    /**This method returns the location of the associated appointment.
     * @return returns the locations of the associated appointment.*/
    public String getLocation(){
        return location;
    }

    /**This method sets the location of the associated appointment.
     * @param location takes in a string object and sets the location of the associated appointment to that string.*/
    public void setLocation(String location){
        this.location = location;
    }

    /**This method returns the contact of the associated appointment.
     * @return returns the contact of the associated appointment.*/
    public int getContact(){
        return contact;
    }

    /**This method sets the contact of the associated appointment.
     * @param contact takes in an integer and sets the contact ID of the associated appointment to that integer.*/
    public void setContact(int contact){
        this.contact = contact;
    }

    /**This method returns the type of the associated appointment.
     * @return returns the type of the associated appointment.*/
    public String getType(){
        return type;
    }

    /**This method sets the type of the associated appointment.
     * @param type takes in a string and sets the type of the associated appointment to that string.*/
    public void setType(String type){
        this.type = type;
    }

    /**This method returns the start date of the associated appointment.
     * @return returns the start date of the associated appointment.*/
    public String getStart(){
        return start;
    }

    /**This method sets the start date of the associated appointment.
     * @param start takes in a string and sets the start date of the associated appointment to that string.*/
    public void setStart(String start){
        this.start = start;
    }

    /**This method returns the end date of the associated appointment.
     * @return returns of the end date of the associated appointment.*/
    public String getEnd(){
        return end;
    }

    /**This method sets the end date of the associated appointment.
     * @param end takes in a string and sets the end date of the associated appointment to that string.*/
    public void setEnd(String end){
        this.end = end;
    }

    /**This method returns the create date of the associated appointment.
     * @return returns the create date of the associated appointment.*/
    public String getCreateDate(){
        return createDate;
    }

    /**This method sets the date the associated appointment was created.
     * @param createDate takes a string and sets the date the associated appointment was created to that string.*/
    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }

    /**This method returns who the appointment was created by.
     * @return returns who created the associated appointment.*/
    public String getCreatedBy(){
        return createdBy;
    }

    /**This method sets who created the associated appointment.
     * @param createdBy takes in a string and sets who created the associated appointment to that string.*/
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**This method returns the date the associated appointment was last updated.
     * @return returns the date the associated appointment was last updated.*/
    public String getLastUpdate(){
        return lastUpdate;
    }

    /**This method sets the date the associated appointment was last updated.
     * @param lastUpdate takes in a string and sets the date the associated appointment was last updated to that string.*/
    public void setLastUpdate(String lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**This method returns who last updated the associated appointment.
     * @return returns who last updated the associated appointment.*/
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }

    /**This method sets who last updated the associated appointment.
     * @param lastUpdateBy takes in a string and sets who updated the associated appointment to that string.*/
    public void setLastUpdateBy(String lastUpdateBy){
        this.lastUpdateBy = lastUpdateBy;
    }

    /**This method looks for an appointment in a list holding all the appointments and deletes it from that list once it is found.
     * @param appointment takes in an appointment object and searches for it in the list holding all the appointments then deletes it from the list.*/
    public static void deleteAppointment(Appointment appointment){
        for(int i = 0; i < allAppointments.size(); i++){
            if(appointment == allAppointments.get(i)){
                allAppointments.remove(i);
            }
        }
    }


}
