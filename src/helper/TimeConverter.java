package helper;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**This class uses lambda expressions to replace a lot of the messy code that would have been used throughout the project.*/
public class TimeConverter{
    /**This method changes a string from local date time format to a yyyy-MM-dd HH:mm:ss format so that it can be displayed to the user.
     * @param s takes in a string and changes it to the yyyy-MM-dd HH:mm:ss format.
     * @return returns the new string in yyyy-MM-dd HH:mm:ss format.*/
    public static String toReadableString(String s){
        LDTtoStringInterface ldttoString = (string) -> {
            string = string.substring(0, string.length() - 10).replace('T', ' ');
            return string;
        };
        return ldttoString.LDTtoString(s);
    }

    /**This method changes a string that is in user time and changes it to UTC time, so it can be stored in the database.
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
