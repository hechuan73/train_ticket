package fdse.microservice.controller;

import edu.fudan.common.util.Response;
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
@RequestMapping("/api/v1/stationservice")
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
        if (stations != null && stations.size() > 0) {
            return ok(new Response(1, "Find all content", stations));
        } else {
            return ok(new Response(0, "No content", null));
        }
    }

    @PostMapping(value = "/stations")
    public ResponseEntity<Response> create(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean createResult = stationService.create(station, headers);
        if (createResult) {
            return new ResponseEntity<>(new Response(1, "Create success", createResult), HttpStatus.CREATED);
        } else {
            return ok(new Response(0, "Already exists", createResult));
        }
    }

    @PutMapping(value = "/stations")
    public HttpEntity update(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean updateResult = stationService.update(station, headers);
        if (updateResult) {
            return ok(new Response(1, "Update success", station));
        } else {
            return ok(new Response(0, "Station not exist", station));
        }
    }

    @DeleteMapping(value = "/stations")
    public ResponseEntity<Response> delete(@RequestBody Station station, @RequestHeader HttpHeaders headers) {
        boolean deleteResult = stationService.delete(station, headers);
        if (deleteResult) {
            return ok(new Response(1, "Delete success", station));
        } else {
            return ok(new Response(0, "Station not exist", station));
        }
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
        String stationId = stationService.queryForId(stationName, headers);
        if (stationId != null) {
            return ok(new Response(1, "Success", stationId));
        } else {
            return ok(new Response(0, "Not exists", stationId));
        }
    }

    // 根据车站名 list --->  查询 所有车站 id
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/idlist")
    public HttpEntity queryForIdBatch(@RequestBody List<String> stationNameList, @RequestHeader HttpHeaders headers) {
        List<String> stationIdList = stationService.queryForIdBatch(stationNameList, headers);
        if (stationIdList != null && stationIdList.size() > 0) {
            return ok(new Response(1, "Success", stationIdList));
        } else {
            return ok(new Response(0, "No content", stationIdList));
        }
    }

    // 根据station id 查询  车站名
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/stations/name/{stationIdForName}")
    public HttpEntity queryById(@PathVariable(value = "stationIdForName")
                                        String stationId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Station Service] Query By Id:" + stationId);
        // string
        String stationName = stationService.queryById(stationId, headers);
        if (stationName == null) {
            return ok(new Response(0, "No that stationId", stationId));
        } else {
            return ok(new Response(1, "Success", stationName));
        }
    }

    // 根据 station  id list  ---> 查询 所有 车站名
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/namelist")
    public HttpEntity queryForNameBatch(@RequestBody List<String> stationIdList, @RequestHeader HttpHeaders headers) {
        List<String> nameList = stationService.queryByIdBatch(stationIdList, headers);
        if(nameList != null && nameList.size() >0){
            return ok(new Response(1, "Success", nameList));
        }else{
            return ok(new Response(0, "No stationNamelist according to stationIdList", stationIdList));
        }
    }

    //  根据stationId  ----> 查询 车站名
//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/station/queryByIdForName",method = RequestMethod.POST)
//    public String queryByIdForName(@RequestBody QueryById queryById,@RequestHeader HttpHeaders headers){
//        System.out.println("[Station Service] Query By Id For Name:" + queryById.getStationId());
//        return stationService.queryById(queryById.getStationId(),headers).getName();
//    }
}
