package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class stores all the customers and has teh getters and setters for all the variables associated with customers.*/
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

    /**This is the class constructor and sets all the customers class variables.*/
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

    /**This method takes in a customer object as a parameter and adds that object to the all customers list.
     * @param customer takes in a customer object and adds it the all customers list.*/
    public static void addCustomer(Customers customer){
        allCustomers.add(customer);
    }

    /**This method returns the ID of the associated customer.
     * @return returns the ID of the associated customer.*/
    public int getCustomerID(){
        return customerID;
    }

    /**This method sets the ID of the associated customer.
     * @param id takes in an integer and sets the ID of the associated customer to that integer.*/
    public void setCustomerID(int id){
        customerID = id;
    }

    /**This method returns the ID of the associated customer.
     * @return returns the ID of the associated customer.*/
    public String getCustomerName(){
        return customerName;
    }

    /**This method sets the name of the associated customer.
     * @param name takes in a string and sets the name of the associated customer to that string.*/
    public void setCustomerName(String name){
        customerName = name;
    }

    /**This method returns the address of the associated customer.
     * @return returns the address of the associated customer.*/
    public String getAddress(){
        return address;
    }

    /**This method sets the address of the associated customer.
     * @param address takes in a string and sets the address of the associated customer to that string.*/
    public void setAddress(String address){
        this.address = address;
    }

    /**This method returns the postal code of the associated customer.
     * @return returns the postal code of the associated customer.*/
    public String getPostalCode(){
        return postalCode;
    }

    /**This method sets the postal code of the associated customer.
     * @param postalCode takes in a string and sets the postal code of the associated customer to that string.*/
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    /**This method returns the phone number of the associated customer.
     * @return returns the phone number of the associated customer.*/
    public String getPhone(){
        return phone;
    }

    /**This method sets the phone number of the associated customer.
     * @param phone takes in a string and sets the phone number of the associated customer to that string.*/
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**This method returns the date the associated customer object was created.
     * @return returns the date the associated customer object was created.*/
    public String getCreateDate(){
        return createDate;
    }

    /**This method sets the date the associated customer object was created.
     * @param createDate takes in a string object and sets the date the associated customer object was created to that string.*/
    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }

    /**This method returns who the customer object was created by.
     * @return returns who the customer object was created.*/
    public String getCreatedBy(){
        return createdBy;
    }

    /**This method sets who the associated customer object was created by.
     * @param createdBy takes in a string and sets who the associated customer object was created by to that string.*/
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**This method returns the date the associated customer records were last updated.
     * @return returns date the associated customer records were last updated.*/
    public String getLastUpdate(){
        return lastUpdate;
    }

    /**This method sets the date the associated customer records were last updated.
     * @param lastUpdate takes in a string and sets the date the associated customer records were last updated to that string.*/
    public void setLastUpdate(String lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**This method returns who last updated the associated customer records.
     * @return returns who last updated the associated customer records.*/
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**This method sets who last updated the associated customer records.
     * @param lastUpdatedBy takes in a string and sets who last updated the associated customer records to that string.*/
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**This method returns the division ID of the associated customer.
     * @return returns the division ID of the associated customer.*/
    public int getDivisionID(){
        return divisionID;
    }

    /**This method sets the division ID of the associated customer.
     * @param divisionID takes an integer and sets the division ID of the associated customer to that integer.*/
    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    /**This method returns all the customers.
     * @return returns a list with all the customers.*/
    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    /**This method deletes a customer from the all customers list.
     * @param customer takes a customer object and looks for it in the all customers list. Once it is found it is deleted from the list.*/
    public static void deleteCustomer(Customers customer){
        for(int i = 0; i < allCustomers.size(); i++){
            if(customer == allCustomers.get(i)){
                allCustomers.remove(i);
            }
        }
    }
}
