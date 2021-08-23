package fi.tasis.routeplanner.routeplannermodel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RouteDataItem {

    private String stop;
    private Integer targetArrival;
    private Integer actualArrival;
    private String date;

    public static RouteDataItem from(StopMessage stopMessage) {
        RouteDataItem routeDataItem = new RouteDataItem();
        routeDataItem.setStop(stopMessage.getStop());
        routeDataItem.setTargetArrival(Instant.parse(stopMessage.getTtarr()).atOffset(ZoneOffset.UTC).get(ChronoField.SECOND_OF_DAY));
        routeDataItem.setActualArrival(Instant.parse(stopMessage.getTst()).atOffset(ZoneOffset.UTC).get(ChronoField.SECOND_OF_DAY));
        routeDataItem.setDate(stopMessage.getOday());
        return routeDataItem;
    }
}
