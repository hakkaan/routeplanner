package fi.tasis.routeplanner.routeplannermodel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Route {
    private String line;
    private String direction;
    private String startTime;
}
