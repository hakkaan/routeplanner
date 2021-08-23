package fi.tasis.routeplanner.web.controller;

import fi.tasis.routeplanner.routeplannermodel.model.Route;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataAggregation;
import fi.tasis.routeplanner.web.service.RouteDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteDataService routeDataService;

    public RouteController(RouteDataService routeDataService) {
        this.routeDataService = routeDataService;
    }

    @GetMapping("/statistics")
    public List<RouteDataAggregation> getRouteStatistics(@RequestParam String line, @RequestParam String direction, @RequestParam String startTime,
                                                   @RequestParam String queryFromDate, @RequestParam String queryToDate) {

        return routeDataService.getAggregatedRouteData(line, direction, startTime, queryFromDate, queryToDate);
    }

    @GetMapping("/")
    public List<Route> getDistinctRoutes() {
        return routeDataService.getDistinctRoutes();
    }

}
