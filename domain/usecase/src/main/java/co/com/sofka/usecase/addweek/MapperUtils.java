package co.com.sofka.usecase.addweek;

import co.com.sofka.model.week.values.Availability;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

            s = s.replace("[","");
            s = s.replace("]","");

            String[] dateTimeString = s.split(",");
            Set<LocalDateTime> availabilityArray = new HashSet<>();

            for (Integer i = 0; i< dateTimeString.length; i++){

                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                String date = dateTimeString[i];
                date = date.replace(" ","");
                LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
                availabilityArray.add(dateTime);
            }

            Availability availability = new Availability(availabilityArray);

            return availability;
        };
    }


    public Function<String, LocalDate[]> mapperToDate() {
        return s -> {
            String[] dateString = s.split(";");
            LocalDate dates[] = new LocalDate[2];

            for (int i = 0; i<2; i++){

                String dateAux = dateString[i];

                    LocalDate date = LocalDate.parse(dateAux);
                    dates[i]=date;


            }

            return dates;

        };
    }
}
