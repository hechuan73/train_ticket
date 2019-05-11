package admintravel.service;

import admintravel.entity.AdminTrip;
import admintravel.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AdminTravelServiceImpl implements AdminTravelService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getAllTravels(HttpHeaders headers) {
        Response<ArrayList<AdminTrip>> result = new Response<ArrayList<AdminTrip>>();
        ArrayList<AdminTrip> trips = new ArrayList<AdminTrip>();

        System.out.println("[Admin Travel Service][Get All Travels]");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<ArrayList<AdminTrip>>> re = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/admin_trip",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re.getBody();

        if (result.getStatus() == 1) {
            ArrayList<AdminTrip> adminTrips = result.getData();
            System.out.println("[Admin Travel Service][Get Travel From ts-travel-service successfully!]");
            trips.addAll(adminTrips);
        } else
            System.out.println("[Admin Travel Service][Get Travel From ts-travel-service fail!]");

        HttpEntity requestEntity2 = new HttpEntity(headers);
        ResponseEntity<Response<ArrayList<AdminTrip>>> re2 = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/admin_trip",
                HttpMethod.GET,
                requestEntity2,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re2.getBody();

        if (result.getStatus() == 1) {
            System.out.println("[Admin Travel Service][Get Travel From ts-travel2-service successfully!]");
            ArrayList<AdminTrip> adminTrips = result.getData();
            trips.addAll(adminTrips);
        } else
            System.out.println("[Admin Travel Service][Get Travel From ts-travel2-service fail!]");
        result.setData(trips);

        return result;
    }

    @Override
    public Response addTravel(TravelInfo request, HttpHeaders headers) {
        Response resultResponse;
        String requestUrl;
        if (request.getTrainTypeId().charAt(0) == 'G' || request.getTrainTypeId().charAt(0) == 'D') {
            requestUrl = "http://ts-travel-service:12346/api/v1/travelservice/trips";
        } else {
            requestUrl = "http://ts-travel2-service:16346/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                Response.class);
        resultResponse = re.getBody();

        if (resultResponse.getStatus() == 1) {
            System.out.println("[Admin Travel Service][Admin add new travel]");
            return new Response<>(1, "[Admin Travel Service][Admin add new travel]", null);
        } else {
            return new Response<>(0, "Admin add new travel failed", null);
        }
    }

    @Override
    public Response updateTravel(TravelInfo request, HttpHeaders headers) {
        Response result;

        String requestUrl = "";
        if (request.getTrainTypeId().charAt(0) == 'G' || request.getTrainTypeId().charAt(0) == 'D') {
            requestUrl = "http://ts-travel-service:12346/api/v1/travelservice/trips";
        } else {
            requestUrl = "http://ts-travel2-service:16346/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        result = re.getBody();
        System.out.println("[Admin Travel Service][Admin update travel]");
        return result;
    }

    @Override
    public Response deleteTravel(String tripId, HttpHeaders headers) {

        Response result;
        String requestUtl = "";
        if (tripId.charAt(0) == 'G' || tripId.charAt(0) == 'D') {
            requestUtl = "http://ts-travel-service:12346/api/v1/travelservice/trips/" + tripId;
        } else {
            requestUtl = "http://ts-travel2-service:16346/api/v1/travel2service/trips/" + tripId;
        }
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUtl,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        result = re.getBody();

        return result;
    }
}
