package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public Customers(int customerID, String customerName, String address, String postalCode, String phone, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    public static void addCustomer(Customers customer){
        allCustomers.add(customer);
    }

    public int getCustomerID(){
        return customerID;
    }

    public void setCustomerID(int id){
        customerID = id;
    }

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String name){
        customerName = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getCreateDate(){
        return createDate;
    }

    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public String getLastUpdate(){
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID(){
        return divisionID;
    }

    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }
}
