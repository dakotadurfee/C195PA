package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class is responsible for holding the lists with country and division names where the customers of the company are from.*/
public class CountryData {
    private static ObservableList<String> countryList = FXCollections.observableArrayList();
    private static ObservableList<String> USDivisionList = FXCollections.observableArrayList();
    private static ObservableList<String> UKDivisionList = FXCollections.observableArrayList();
    private static ObservableList<String> CanadaDivisionList = FXCollections.observableArrayList();

    /**This method adds a country the country list.
     * @param country takes in a string and adds that string to the list of countries.*/
    public static void addCountry(String country){
        countryList.add(country);
    }

    /**This method returns the list of countries.
     * @return returns the list of countries that the customers are from.*/
    public static ObservableList<String> getCountryList(){
        return countryList;
    }

    /**This method adds a U.S. division to the U.S. division list.
     * @param division takes in a string and adds that string to the list of divisions in the U.S.*/
    public static void addUSDivision(String division){
        USDivisionList.add(division);
    }

    /**This method returns the list of the U.S. divisions.
     * @return returns the list holding all the divisions in the U.S.*/
    public static ObservableList<String> getUSdivisionList(){ return USDivisionList; }

    /**This method adds a UK division to the UK division list.
     * @param division takes in a string and adds that string to the list of divisions in the UK.*/
    public static void addUKDivision(String division){
        UKDivisionList.add(division);
    }

    /**This method returns the list of the UK divsions.
     * @return returns the list holding all the divisions in the UK.*/
    public static ObservableList<String> getUKdivisionList(){
        return UKDivisionList;
    }

    /**This method adds a canada division to the canada division list.
     * @param division takes in a string and adds that string to the list of divisions in canada.*/
    public static void addCanadaDivision(String division){
        CanadaDivisionList.add(division);
    }

    /**This method returns the list of the canada divisions.
     * @return returns the list holding all the divisions in canada.*/
    public static ObservableList<String> getCanadadivisionList(){
        return CanadaDivisionList;
    }
}
