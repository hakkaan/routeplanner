package fi.tasis.routeplanner.routeplannermodel.repository;

import fi.tasis.routeplanner.routeplannermodel.model.Route;
import fi.tasis.routeplanner.routeplannermodel.model.RouteData;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataAggregation;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.filter;
import static org.springframework.data.mongodb.core.aggregation.BooleanOperators.And.and;
import static org.springframework.data.mongodb.core.aggregation.ComparisonOperators.valueOf;

@Slf4j
@Repository
public class RouteDataRepositoryImpl implements RouteDataRepository {

    private final MongoOperations mongoOperations;

    public RouteDataRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void insertRouteDataItem(RouteData routeData, RouteDataItem routeDataItem) {
        log.debug("RouteData: {} - {}", routeData.toString(), routeDataItem.toString());

        Query query = new Query(where("line").is(routeData.getLine())
            .and("direction").is(routeData.getDirection())
            .and("startTime").is(routeData.getStartTime()));

        Update update = new Update().push("items", routeDataItem);

        mongoOperations.upsert(query, update, RouteData.class);
    }

    @Override
    public List<RouteDataAggregation> getRouteData(String line, String direction, String startTime, String queryFromDate, String queryToDate) {

        MatchOperation match = match(new Criteria().andOperator(
                Criteria.where("line").is(line),
                Criteria.where("direction").is(direction),
                Criteria.where("startTime").is(startTime)
        ));

        ProjectionOperation filterProjection = project()
                .and(filter("items")
                        .as("item")
                        .by(and(valueOf("item.date")
                                        .greaterThanEqualToValue(queryFromDate),
                                valueOf("item.date")
                                        .lessThanEqualToValue(queryToDate)
                        ))).as("items");

        UnwindOperation unwind = unwind("items");

        GroupOperation group = group("items.stop")
                .stdDevPop("items.actualArrival").as("standardDeviation")
                .avg("items.actualArrival").as("average")
                .min("items.actualArrival").as("min")
                .max("items.actualArrival").as("max")
                .first("items.targetArrival").as("target")
                .first("items.stop").as("stop");

        SortOperation sort = sort(Sort.Direction.ASC, "target");

        Aggregation agg = newAggregation(
                match,
                filterProjection,
                unwind,
                group,
                sort
        );

        AggregationResults<RouteDataAggregation> results = mongoOperations.aggregate(agg, "routeData", RouteDataAggregation.class);

        return results.getMappedResults();
    }

    @Override
    public List<Route> getAllDistinctRoutes() {
        GroupOperation group = group("line", "direction", "startTime");
        ProjectionOperation projection = project("_id.line", "_id.direction", "_id.startTime").andExclude("_id");
        SortOperation sort = sort(Sort.Direction.ASC, "line", "direction", "startTime");
        Aggregation agg = newAggregation(
                group,
                projection,
                sort
        );

        AggregationResults<Route> results = mongoOperations.aggregate(agg, "routeData", Route.class);

        return results.getMappedResults();
    }
}
