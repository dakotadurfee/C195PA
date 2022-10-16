package helper;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter{
    public static String toReadableString(String s){
        LDTtoStringInterface ldttoString = (string) -> {
            string = string.substring(0, string.length() - 10).replace('T', ' ');
            return string;
        };
        return ldttoString.LDTtoString(s);
    }

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
