package train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainTypeRepository repository;

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    public boolean create(TrainType trainType, HttpHeaders headers) {
        boolean result = false;
        if (repository.findById(trainType.getId()) == null) {
            TrainType type = new TrainType(trainType.getId(), trainType.getEconomyClass(), trainType.getConfortClass());
            type.setAverageSpeed(trainType.getAverageSpeed());
            repository.save(type);
            result = true;
        }
        return result;
    }

    public TrainType retrieve(String id, HttpHeaders headers) {
        if (repository.findById(id) == null) {
            //log.info("ts-train-service:retireve "+id+ " and there is no TrainType with the id:" +id);
            return null;
        } else {
            return repository.findById(id);
        }
    }

    public boolean update(TrainType trainType, HttpHeaders headers) {
        boolean result = false;
        //
        if (repository.findById(trainType.getId()) != null) {
            TrainType type = new TrainType(trainType.getId(), trainType.getEconomyClass(), trainType.getConfortClass());
            type.setAverageSpeed(trainType.getAverageSpeed());
            repository.save(type);
            result = true;
        }
        return result;
    }

    public boolean delete(String id, HttpHeaders headers) {
        boolean result = false;
        if (repository.findById(id) == null) {
            //log.info("ts-train-service:delete " + id +" and there doesn't exist TrainType with the id:" +id);
        } else {
            repository.deleteById(id);
            result = true;
        }
        return result;
    }

    @Override
    public List<TrainType> query(HttpHeaders headers) {
        return repository.findAll();
    }

}
