package fdse.microservice.service;

import fdse.microservice.domain.Information;
import fdse.microservice.domain.QueryForId;
import fdse.microservice.domain.QueryStation;
import fdse.microservice.domain.Station;
import fdse.microservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository repository;

    @Override
    public boolean create(Information info){
        boolean result = false;
        if(repository.findById(info.getId()) == null){
            Station station = new Station(info.getId(), info.getName());
            station.setStayTime(info.getStayTime());
            repository.save(station);
            result = true;
        }

        return result;
    }

    @Override
    public boolean exist(QueryStation info){
        boolean result = false;
        if(repository.findByName(info.getName()) != null){
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(Information info){
        boolean result = false;

        Station station = new Station(info.getId(), info.getName());
        station.setStayTime(info.getStayTime());
        repository.save(station);
        result = true;

        return result;
    }

    @Override
    public boolean delete(Information info){
        boolean result = false;
        if(repository.findById(info.getId()) != null){
            Station station = new Station(info.getId(),info.getName());
            repository.delete(station);
            result = true;
        }
        return result;
    }

    @Override
    public List<Station> query(){
        return repository.findAll();
    }

    @Override
    public String queryForId(QueryForId info){
        Station station = repository.findByName(info.getName());
        return station.getId();
    }

    @Override
    public QueryStation queryById(String stationId){
        Station station = repository.findById(stationId);
        if(station != null){
            return new QueryStation(station.getName());
        }else{
            return new QueryStation("Station Not Found");
        }
    }
}
