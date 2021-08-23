package fi.tasis.routeplanner.routeplannermodel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document
public class RouteData {

    private String line;
    private String direction;
    private String startTime;
    private List<RouteDataItem> items;

    public static RouteData from(StopMessage stopMessage) {
        RouteData routeData = new RouteData();
        routeData.setLine(stopMessage.getDesi());
        routeData.setDirection(stopMessage.getDir());
        routeData.setStartTime(stopMessage.getStart());
        return routeData;
    }
}
