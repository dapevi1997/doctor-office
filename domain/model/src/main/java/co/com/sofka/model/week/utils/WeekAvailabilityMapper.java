package co.com.sofka.model.week.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeekAvailabilityMapper {
    private Set<Map<String, String>> schedule;


    public Set<Map<String, String>> getSchedule() {
        return schedule;
    }

    public WeekAvailabilityMapper(String scheduleString) {
        String[] days = scheduleString.split(",");
        this.schedule = new HashSet<>();
        for (Integer i = 0; i<5; i++){
            schedule.add(Map.of(i.toString(), days[i.intValue()]));
        }






     /*   schedule.add(Map.of("ma", ""));
        schedule.add(Map.of("mi", ""));
        schedule.add(Map.of("ju", ""));
        schedule.add(Map.of("vi", ""));*/


    }
}
