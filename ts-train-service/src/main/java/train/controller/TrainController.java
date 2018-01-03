package train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import train.domain.Information;
import train.domain.Information2;
import train.domain.TrainType;
import train.service.TrainService;

import java.util.List;


/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@RestController
public class TrainController {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TrainService trainService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/train/create",method= RequestMethod.POST)
    public boolean create(@RequestBody Information info){
        return trainService.create(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/train/retrieve",method= RequestMethod.POST)
    public TrainType retrieve(@RequestBody Information2 info){
        return trainService.retrieve(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/train/update",method= RequestMethod.POST)
    public boolean update(@RequestBody Information info){
        return trainService.update(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/train/delete",method= RequestMethod.POST)
    public boolean delete(@RequestBody Information2 info){
        return trainService.delete(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/train/query",method= RequestMethod.GET)
    public List<TrainType> query(){
        return trainService.query();
    }
}
