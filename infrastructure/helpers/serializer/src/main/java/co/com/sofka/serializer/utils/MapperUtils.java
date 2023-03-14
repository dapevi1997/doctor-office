package co.com.sofka.serializer.utils;

import co.com.sofka.model.week.values.Availability;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<String, Availability> mapperToAvailability(String availabilityString){
        return s -> {
            String[] dateTimeString = availabilityString.split(";");
            Set<LocalDateTime> availabilityArray = new HashSet<>();

            for (Integer i = 0; i< dateTimeString.length; i++){
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString[0], dateTimeFormatter);
                availabilityArray.add(dateTime);
            }

            Availability availability = new Availability(availabilityArray);

          return null;
        };
    }


}
