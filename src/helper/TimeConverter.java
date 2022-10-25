package helper;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**This class uses lambda expressions to replace a lot of the messy code that would have been used throughout the project.*/
public class TimeConverter{
    /**This method uses a lambda expression to change a string from a local date time format to a more readable format so it can
     * be displayed to the user. It removes the milliseconds from the end of the string and changes the 'T' to a whitespace.
     * @param s takes in a string and changes it to the yyyy-MM-dd HH:mm:ss format.
     * @return returns the new string in yyyy-MM-dd HH:mm:ss format.*/
    public static String toReadableString(String s){
        LDTtoStringInterface ldttoString = (string) -> {
            int length = string.length();
            string = string.substring(0, 19);
            string = string.replace('T', ' ');
            return string;
        };
        return ldttoString.LDTtoString(s);
    }

    /**This method uses a lambda expression to change a string from user time to UTC time so that it can be stored in the database.
     * @param s takes a string and converts it to UTC time.
     * @return returns the string in UTC time.*/
    public static String toUTCTime(String s){
        ToUTCTimeInterface toUserTime = (string) -> {
            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime userStart = LocalDateTime.parse(string, dt_formatter);

            ZoneId utcZone = ZoneId.of("UTC");
            ZoneId userZone = ZoneId.systemDefault();

            ZonedDateTime userStartZDT = userStart.atZone(userZone);

            ZonedDateTime DBstartZDT = userStartZDT.withZoneSameInstant(utcZone);

            string = DBstartZDT.toLocalDateTime().format(dt_formatter);
            return string;
        };
        return toUserTime.ToUTCTime(s);
    }
}
