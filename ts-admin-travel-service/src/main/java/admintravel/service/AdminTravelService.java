package admintravel.service;

import admintravel.domain.request.AddAndModifyTravelRequest;
import admintravel.domain.request.DeleteTravelRequest;
import admintravel.domain.response.AdminFindAllResult;
import admintravel.domain.response.ResponseBean;

public interface AdminTravelService {
    AdminFindAllResult getAllTravels(String id);
    ResponseBean addTravel(AddAndModifyTravelRequest request);
    ResponseBean updateTravel(AddAndModifyTravelRequest request);
    ResponseBean deleteTravel(DeleteTravelRequest request);
}
