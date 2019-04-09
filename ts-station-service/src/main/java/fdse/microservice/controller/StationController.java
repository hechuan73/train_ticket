package fdse.microservice.controller;

import fdse.microservice.entity.*;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/station")
public class StationController {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private StationService stationService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Station Service ] !";
    }

    @GetMapping(value = "/stations")
    public HttpEntity query(@RequestHeader HttpHeaders headers) {
        List<Station> stations = stationService.query(headers);
        return ok(stations);
    }

    @PostMapping(value = "/stations")
    public HttpEntity<?> create(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean createResult = stationService.create(station, headers);
        return new ResponseEntity<>(createResult, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/stations")
    public HttpEntity<?> update(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean updateResult = stationService.update(station, headers);
        return new ResponseEntity<>(updateResult, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/stations")
    public HttpEntity<?> delete(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean deleteResult = stationService.delete(station, headers);
        return new ResponseEntity<>(deleteResult, HttpStatus.ACCEPTED);
    }


    // 根据车站名 ---> 查询 车站是否存在
//    @GetMapping(value="/stations/{stationName}")
//    public HttpEntity exist(@PathVariable String stationName,@RequestHeader HttpHeaders headers){
//        return ok(stationService.exist(stationName,headers));
//    }

    // 根据车站名 ---> 查询 车站 id
    @GetMapping(value = "/stations/id/{stationNameForId}")
    public HttpEntity queryForStationId(@PathVariable(value = "stationNameForId")
                                                    String stationName, @RequestHeader HttpHeaders headers) {
        // string
        return ok(stationService.queryForId(stationName, headers));
    }

    // 根据车站名 list --->  查询 所有车站 id
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/idlist")
    public HttpEntity queryForIdBatch(@RequestBody List<String> stationNameList, @RequestHeader HttpHeaders headers) {
        List<String> stationIdList = stationService.queryForIdBatch(stationNameList, headers);
        return ok(stationIdList);
    }

    // 根据station id 查询  车站名
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/stations/name/{stationIdForName}")
    public HttpEntity queryById(@PathVariable(value = "stationIdForName")
                                            String stationId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Station Service] Query By Id:" + stationId);
        // string
        return ok(stationService.queryById(stationId, headers));
    }

    // 根据 station  id list  ---> 查询 所有 车站名
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/namelist")
    public HttpEntity queryForNameBatch(@RequestBody List<String> stationIdList, @RequestHeader HttpHeaders headers) {
        List<String> nameList = stationService.queryByIdBatch(stationIdList, headers);
        return ok(nameList);
    }

    //  根据stationId  ----> 查询 车站名
//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/station/queryByIdForName",method = RequestMethod.POST)
//    public String queryByIdForName(@RequestBody QueryById queryById,@RequestHeader HttpHeaders headers){
//        System.out.println("[Station Service] Query By Id For Name:" + queryById.getStationId());
//        return stationService.queryById(queryById.getStationId(),headers).getName();
//    }
}
