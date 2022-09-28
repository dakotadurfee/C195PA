package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CountryData {
    private static ObservableList<String> countryList = FXCollections.observableArrayList();
    private static ObservableList<String> USDivisionList = FXCollections.observableArrayList();
    private static ObservableList<String> UKDivisionList = FXCollections.observableArrayList();
    private static ObservableList<String> CanadaDivisionList = FXCollections.observableArrayList();

    public static void addCountry(String country){
        countryList.add(country);
    }

    public ObservableList<String> getCountryList(){
        return countryList;
    }

    public static void addUSDivision(String division){
        USDivisionList.add(division);
    }

    public ObservableList<String> getUSdivisionList(){
        return USDivisionList;
    }

    public static void addUKDivision(String division){
        UKDivisionList.add(division);
    }

    public ObservableList<String> getUKdivisionList(){
        return UKDivisionList;
    }

    public static void addCanadaDivision(String division){
        CanadaDivisionList.add(division);
    }

    public ObservableList<String> getCanadaDivisionList(){
        return CanadaDivisionList;
    }
}
