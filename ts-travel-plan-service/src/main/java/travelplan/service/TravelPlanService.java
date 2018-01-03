package travelplan.service;

import travelplan.domain.*;

public interface TravelPlanService {

    TransferTravelSearchResult getTransferSearch(TransferTravelSearchInfo info);

    TravelAdvanceResult getCheapest(QueryInfo info);

    TravelAdvanceResult getQuickest(QueryInfo info);

    TravelAdvanceResult getMinStation(QueryInfo info);

}
