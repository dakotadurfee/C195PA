package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportsData {
    private static ObservableList<Appointment> janAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> febAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> marAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> aprAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> mayAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> juneAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> julyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> augAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> septAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> octAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> novAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> decAppointments = FXCollections.observableArrayList();


    private static ObservableList<Appointment> debriefingAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> planningAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    public void addJanAppointments(Appointment appointment){
        janAppointments.add(appointment);
    }

    public void deleteJanAppointments(Appointment appointment){
        janAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getJanAppointments(){
        return janAppointments;
    }

    public void addFebAppointments(Appointment appointment){
        febAppointments.add(appointment);
    }

    public void deleteFebAppointments(Appointment appointment){
        febAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getFebAppointments(){
        return febAppointments;
    }

    public void addMarAppointments(Appointment appointment){
        marAppointments.add(appointment);
    }

    public void deleteMarAppointments(Appointment appointment){
        marAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getMarAppointments(){
        return marAppointments;
    }

    public void addAprAppointments(Appointment appointment){
        aprAppointments.add(appointment);
    }

    public void deleteAprAppointments(Appointment appointment){
        aprAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getAprAppointments(){
        return aprAppointments;
    }

    public void addMayAppointments(Appointment appointment){
        mayAppointments.add(appointment);
    }

    public void deleteMayAppointments(Appointment appointment){
        mayAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getMayAppointments(){
        return mayAppointments;
    }

    public void addJuneAppointments(Appointment appointment){
        juneAppointments.add(appointment);
    }

    public void deleteJuneAppointments(Appointment appointment){
        juneAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getJuneAppointments(){
        return juneAppointments;
    }

    public void addJulyAppointments(Appointment appointment){
        julyAppointments.add(appointment);
    }

    public void deleteJulyAppointments(Appointment appointment){
        julyAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getJulyAppointments(){
        return julyAppointments;
    }

    public void addAugAppointments(Appointment appointment){
        augAppointments.add(appointment);
    }

    public void deleteAugAppointments(Appointment appointment){
        augAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getAugAppointments(){
        return augAppointments;
    }

    public void addSeptAppointments(Appointment appointment){
        septAppointments.add(appointment);
    }

    public void deleteSeptAppointments(Appointment appointment){
        septAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getSeptAppointments(){
        return septAppointments;
    }

    public void addOctAppointments(Appointment appointment){
        octAppointments.add(appointment);
    }

    public void deleteOctAppointments(Appointment appointment){
        octAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getOctAppointments(){
        return octAppointments;
    }

    public void addNovAppointments(Appointment appointment){
        novAppointments.add(appointment);
    }

    public void deleteNovAppointments(Appointment appointment){
        novAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getNovAppointments(){
        return novAppointments;
    }

    public void addDecAppointments(Appointment appointment){
        decAppointments.add(appointment);
    }

    public void deleteDecAppointments(Appointment appointment){
        decAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getDecAppointments(){
        return decAppointments;
    }

    public void addDebriefingAppointments(Appointment appointment){
        debriefingAppointments.add(appointment);
    }

    public void deleteDebriefingAppointments(Appointment appointment){
        debriefingAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getDebriefingAppointments(){
        return debriefingAppointments;
    }

    public void addPlanningAppointments(Appointment appointment){
        planningAppointments.add(appointment);
    }

    public void deletePlanningAppointments(Appointment appointment){
        planningAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getPlanningAppointments(){
        return planningAppointments;
    }

    public void addContactAppointments(Appointment appointment){
        contactAppointments.add(appointment);
    }

    public void deleteContactAppointments(Appointment appointment){
        contactAppointments.remove(appointment);
    }

    public static ObservableList<Appointment> getContactAppointments(){
        return contactAppointments;
    }
}
