package admintravel.service;

import admintravel.entity.AddAndModifyTravelRequest;
import admintravel.entity.DeleteTravelRequest;
import admintravel.entity.AdminFindAllResult;
import admintravel.entity.ResponseBean;
import org.springframework.http.HttpHeaders;

public interface AdminTravelService {
    AdminFindAllResult getAllTravels(String id, HttpHeaders headers);
    ResponseBean addTravel(AddAndModifyTravelRequest request, HttpHeaders headers);
    ResponseBean updateTravel(AddAndModifyTravelRequest request, HttpHeaders headers);
    ResponseBean deleteTravel(DeleteTravelRequest request, HttpHeaders headers);
}
