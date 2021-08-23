package fi.tasis.routeplanner.routeplannermodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import java.time.LocalTime;
import java.time.ZoneOffset;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RouteDataAggregation {

    private String stop;
    @JsonIgnore
    private Integer target;
    @JsonIgnore
    private Integer average;
    @JsonIgnore
    private Integer max;
    @JsonIgnore
    private Integer min;
    private Integer standardDeviation;

    @Transient
    public LocalTime getAverageTime() {
        return LocalTime.ofSecondOfDay(average).atOffset(ZoneOffset.of("Z")).withOffsetSameInstant(ZoneOffset.of("+03:00")).toLocalTime();
    }

    @Transient
    public LocalTime getMinTime() {
        return LocalTime.ofSecondOfDay(min).atOffset(ZoneOffset.of("Z")).withOffsetSameInstant(ZoneOffset.of("+03:00")).toLocalTime();
    }

    @Transient
    public LocalTime getMaxTime() {
        return LocalTime.ofSecondOfDay(max).atOffset(ZoneOffset.of("Z")).withOffsetSameInstant(ZoneOffset.of("+03:00")).toLocalTime();
    }

    @Transient
    public LocalTime getTargetTime() {
        return LocalTime.ofSecondOfDay(target).atOffset(ZoneOffset.of("Z")).withOffsetSameInstant(ZoneOffset.of("+03:00")).toLocalTime();
    }
}
