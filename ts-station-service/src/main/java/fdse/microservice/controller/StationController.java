package fdse.microservice.controller;

import fdse.microservice.domain.*;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StationController {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private StationService stationService;

    @RequestMapping(value="/station/create",method= RequestMethod.POST)
    public boolean create(@RequestBody Information info){
        return stationService.create(info);
    }

    @RequestMapping(value="/station/exist",method= RequestMethod.POST)
    public boolean exist(@RequestBody QueryStation info){
        return stationService.exist(info);
    }

    @RequestMapping(value="/station/update",method= RequestMethod.POST)
    public boolean update(@RequestBody Information info){
        return stationService.update(info);
    }

    @RequestMapping(value="/station/delete",method= RequestMethod.POST)
    public boolean delete(@RequestBody Information info){
        return stationService.delete(info);
    }

    @RequestMapping(value="/station/query",method= RequestMethod.GET)
    public List<Station> query(){
        return stationService.query();
    }

    @RequestMapping(value="/station/queryForId",method= RequestMethod.POST)
    public String queryForId(@RequestBody QueryForId info){
        return stationService.queryForId(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/station/queryById",method = RequestMethod.POST)
    public QueryStation queryById(@RequestBody QueryById queryById){
        System.out.println("[Station Service] Query By Id:" + queryById.getStationId());
        return stationService.queryById(queryById.getStationId());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/station/queryByIdForName",method = RequestMethod.POST)
    public String queryByIdForName(@RequestBody QueryById queryById){
        System.out.println("[Station Service] Query By Id For Name:" + queryById.getStationId());
        return stationService.queryById(queryById.getStationId()).getName();
    }
}
