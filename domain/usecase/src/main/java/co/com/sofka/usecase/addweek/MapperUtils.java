package co.com.sofka.usecase.addweek;

import co.com.sofka.model.week.values.Availability;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;


public class MapperUtils {
    public MapperUtils() {
    }

    public Function<String, Availability> mapperToAvailability(){
        return s -> {
            String[] dateTimeString = s.split(";");
            Set<LocalDateTime> availabilityArray = new HashSet<>();

            for (Integer i = 0; i< dateTimeString.length; i++){
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String date = dateTimeString[i];
                LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
                availabilityArray.add(dateTime);
            }

            Availability availability = new Availability(availabilityArray);

            return availability;
        };
    }

    public Function<String, Availability> mapperToStringToAvailability(){
        return s -> {

            s.replace("[","");

            String[] dateTimeString = s.split(",");
            Set<LocalDateTime> availabilityArray = new HashSet<>();

            for (Integer i = 0; i< dateTimeString.length; i++){

                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String date = dateTimeString[i];
                LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
                availabilityArray.add(dateTime);
            }

            Availability availability = new Availability(availabilityArray);

            return availability;
        };
    }



}
