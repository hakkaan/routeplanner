package fi.tasis.routeplanner.routeplannerintegration.service;

import fi.tasis.routeplanner.routeplannermodel.model.RouteData;
import fi.tasis.routeplanner.routeplannermodel.model.RouteDataItem;
import fi.tasis.routeplanner.routeplannermodel.model.StopMessage;
import fi.tasis.routeplanner.routeplannermodel.model.StopMessageContainer;
import fi.tasis.routeplanner.routeplannermodel.repository.RouteDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StopMessageServiceImpl implements StopMessageService {

    private final RouteDataRepository routeDataRepository;

    public StopMessageServiceImpl(RouteDataRepository routeDataRepository) {
        this.routeDataRepository = routeDataRepository;
    }

    @Override
    public void saveStopMessage(StopMessageContainer stopMessageContainer) {
        log.debug(stopMessageContainer.toString());
        StopMessage stopMessage = stopMessageContainer.getArs();
        routeDataRepository.insertRouteDataItem(RouteData.from(stopMessage), RouteDataItem.from(stopMessage));
    }
}
