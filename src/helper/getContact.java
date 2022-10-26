package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class uses a lambda expression to remove duplicate code from the program.*/
public class getContact {
    /**This method searches for a string in contacts table of the database and returns the ID of the matching contact name.
     * @param s takes in a string to search for it in the database.
     * @return returns the contact ID of the matching contact name from the contacts table in the database.*/
    public static int contactID(String s) throws SQLException {
        contactIDInterface contact = (string) -> {
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + string + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int contactID = 0;
            while(rs.next()){
                contactID = rs.getInt("Contact_ID");
            }
            return contactID;
        };
        return contact.getContactID(s);
    }
}
