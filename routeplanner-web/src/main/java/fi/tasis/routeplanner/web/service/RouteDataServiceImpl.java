package fi.tasis.routeplanner.web.service;

import fi.tasis.routeplanner.routeplannermodel.model.Route;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataAggregation;
import fi.tasis.routeplanner.routeplannermodel.repository.RouteDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteDataServiceImpl implements RouteDataService {

    private final RouteDataRepository routeDataRepository;

    public RouteDataServiceImpl(RouteDataRepository routeDataRepository) {
        this.routeDataRepository = routeDataRepository;
    }

    @Override
    public List<RouteDataAggregation> getAggregatedRouteData(String line, String direction, String startTime, String queryFromDate, String queryToDate) {
        return routeDataRepository.getRouteData(line, direction, startTime, queryFromDate, queryToDate);
    }

    @Override
    public List<Route> getDistinctRoutes() {
        return routeDataRepository.getAllDistinctRoutes();
    }
}
