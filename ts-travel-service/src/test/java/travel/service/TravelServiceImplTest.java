package travel.service;

import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import travel.entity.TravelInfo;
import travel.entity.Trip;
import travel.entity.TripId;
import travel.repository.TripRepository;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class TravelServiceImplTest {

    @InjectMocks
    private TravelServiceImpl travelServiceImpl;

    @Mock
    private TripRepository repository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);
    String success = "Success";
    String noCnontent = "No Content";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRouteByTripId() {

    }

    @Test
    public void testGetTrainTypeByTripId() {

    }

    @Test
    public void testGetTripByRoute1() {
        ArrayList<String> routeIds = new ArrayList<>();
        Response result = travelServiceImpl.getTripByRoute(routeIds, headers);
        Assert.assertEquals(new Response<>(0, noCnontent, null), result);
    }

    @Test
    public void testGetTripByRoute2() {
        ArrayList<String> routeIds = new ArrayList<>();
        routeIds.add("route_id_1");
        Mockito.when(repository.findByRouteId(Mockito.anyString())).thenReturn(null);
        Response result = travelServiceImpl.getTripByRoute(routeIds, headers);
        Assert.assertEquals(success, result.getMsg());
    }

    @Test
    public void testCreate1() {
        TravelInfo info = new TravelInfo();
        info.setTripId("G");
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(Trip.class))).thenReturn(null);
        Response result = travelServiceImpl.create(info, headers);
        Assert.assertEquals(new Response<>(1, "Create trip:G.", null), result);
    }

    @Test
    public void testCreate2() {
        TravelInfo info = new TravelInfo();
        info.setTripId("G");
        Trip trip = new Trip();
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(trip);
        Response result = travelServiceImpl.create(info, headers);
        Assert.assertEquals(new Response<>(1, "Trip G already exists", null), result);
    }

    @Test
    public void testRetrieve1() {
        Trip trip = new Trip();
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(trip);
        Response result = travelServiceImpl.retrieve("trip_id_1", headers);
        Assert.assertEquals(new Response<>(1, "Search Trip Success by Trip Id trip_id_1", trip), result);
    }

    @Test
    public void testRetrieve2() {
        Trip trip = new Trip();
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(trip);
        Response result = travelServiceImpl.retrieve("trip_id_1", headers);
        Assert.assertEquals(new Response<>(1, "Search Trip Success by Trip Id trip_id_1", trip), result);
    }

    @Test
    public void testUpdate1() {
        TravelInfo info = new TravelInfo();
        info.setTripId("G");
        Trip trip = new Trip();
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(trip);
        Mockito.when(repository.save(Mockito.any(Trip.class))).thenReturn(null);
        Response result = travelServiceImpl.update(info, headers);
        Assert.assertEquals("Update trip:G", result.getMsg());
    }

    @Test
    public void testUpdate2() {
        TravelInfo info = new TravelInfo();
        info.setTripId("G");
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(null);
        Response result = travelServiceImpl.update(info, headers);
        Assert.assertEquals(new Response<>(1, "TripGdoesn 't exists", null), result);
    }

    @Test
    public void testDelete1() {
        Trip trip = new Trip();
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(trip);
        Mockito.doNothing().doThrow(new RuntimeException()).when(repository).deleteByTripId(Mockito.any(TripId.class));
        Response result = travelServiceImpl.delete("trip_id_1", headers);
        Assert.assertEquals(new Response<>(1, "Delete trip:trip_id_1.", "trip_id_1"), result);
    }

    @Test
    public void testDelete2() {
        Mockito.when(repository.findByTripId(Mockito.any(TripId.class))).thenReturn(null);
        Response result = travelServiceImpl.delete("trip_id_1", headers);
        Assert.assertEquals(new Response<>(0, "Trip trip_id_1 doesn't exist.", null), result);
    }

    @Test
    public void testQuery() {

    }

    @Test
    public void testGetTripAllDetailInfo() {

    }

    @Test
    public void testQueryAll1() {
        ArrayList<Trip> tripList = new ArrayList<>();
        tripList.add(new Trip());
        Mockito.when(repository.findAll()).thenReturn(tripList);
        Response result = travelServiceImpl.queryAll(headers);
        Assert.assertEquals(new Response<>(1, success, tripList), result);
    }

    @Test
    public void testQueryAll2() {
        Mockito.when(repository.findAll()).thenReturn(null);
        Response result = travelServiceImpl.queryAll(headers);
        Assert.assertEquals(new Response<>(0, noCnontent, null), result);
    }

    @Test
    public void testAdminQueryAll1() {

    }
    @Test
    public void testAdminQueryAll2() {
        ArrayList<Trip> tripList = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(tripList);
        Response result = travelServiceImpl.adminQueryAll(headers);
        Assert.assertEquals(new Response<>(0, noCnontent, null), result);
    }


}
