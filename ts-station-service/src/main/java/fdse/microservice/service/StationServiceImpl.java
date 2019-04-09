package fdse.microservice.service;

import fdse.microservice.entity.*;
import fdse.microservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository repository;

    @Override
    public boolean create(Station station, HttpHeaders headers){
        boolean result = false;
        if(repository.findById(station.getId()) == null){
            station.setStayTime(station.getStayTime());
            repository.save(station);
            result = true;
        }
        return result;
    }


    @Override
    public boolean exist(String stationName,HttpHeaders headers){
        boolean result = false;
        if(repository.findByName(stationName) != null){
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(Station info,HttpHeaders headers){
        boolean result = false;

        Station station = new Station(info.getId(), info.getName());
        station.setStayTime(info.getStayTime());
        repository.save(station);
        result = true;

        return result;
    }

    @Override
    public boolean delete(Station info,HttpHeaders headers){
        boolean result = false;
        if(repository.findById(info.getId()) != null){
            Station station = new Station(info.getId(),info.getName());
            repository.delete(station);
            result = true;
        }
        return result;
    }

    @Override
    public List<Station> query(HttpHeaders headers){
        return repository.findAll();
    }

    @Override
    public String queryForId(String stationName,HttpHeaders headers){
        Station station = repository.findByName(stationName);
        return station.getId();
    }


    @Override
    public List<String> queryForIdBatch(List<String> nameList, HttpHeaders headers) {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < nameList.size(); i++) {
            Station station = repository.findByName(nameList.get(i));
            if(station == null) {
                result.add("Not Exist");
            }else{
                result.add(station.getId());
            }
        }
        return result;
    }

    @Override
    public String queryById(String stationId,HttpHeaders headers){
        Station station = repository.findById(stationId);
        if(station != null){
            return station.getName();
        }else{
            return null;
        }
    }

    @Override
    public List<String> queryByIdBatch(List<String> idList, HttpHeaders headers) {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < idList.size(); i++) {
            Station station = repository.findById(idList.get(i));
            if(station == null) {
                result.add("Not Exist");
            }else{
                result.add(station.getName());
            }
        }
        return result;
    }
}
