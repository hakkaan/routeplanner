package fi.tasis.routeplanner.routeplannermodel.repository;


import fi.tasis.routeplanner.routeplannermodel.model.Route;
import fi.tasis.routeplanner.routeplannermodel.model.RouteData;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataAggregation;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataItem;

import java.util.List;

public interface RouteDataRepository {
    void insertRouteDataItem(RouteData routeData, RouteDataItem routeDataItem);
    List<RouteDataAggregation> getRouteData(String line, String direction, String startTime, String queryFromDate, String queryToDate);
    List<Route> getAllDistinctRoutes();
}
