package fi.tasis.routeplanner.web.service;

import fi.tasis.routeplanner.routeplannermodel.model.Route;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataAggregation;

import java.util.List;

public interface RouteDataService {
    List<RouteDataAggregation> getAggregatedRouteData(String line, String direction, String startTime, String queryFromDate, String queryToDate);

    List<Route> getDistinctRoutes();
}
