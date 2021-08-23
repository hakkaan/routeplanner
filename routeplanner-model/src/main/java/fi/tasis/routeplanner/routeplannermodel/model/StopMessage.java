package fi.tasis.routeplanner.routeplannermodel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StopMessage {
    private String desi;
    private String dir;
    private String tst;
    private String stop;
    private String start;
    private String ttarr;
    private String oday;
}
