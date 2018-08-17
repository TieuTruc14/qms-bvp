package qms.bvp.web.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.web.repository.RootRepository;
import qms.bvp.web.repository.category.ReceptionDoorRepository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Service
public class ReceptionDoorServiceImpl implements ReceptionDoorService {
    @Autowired
    ReceptionDoorRepository doorRepository;
    @Autowired
    RootRepository rootRepository;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        return doorRepository.page(page);
    }

    @Override
    public Optional<List<ReceptionDoor>> listAll() {
        return Optional.of(doorRepository.findAll());
    }

    @Override
    public Hashtable<Integer, ReceptionDoor> initReceptionDoor() {
        Hashtable<Integer, ReceptionDoor> mapReceptionDoor=new Hashtable<>();
        List<ReceptionDoor> list=listAll().orElse(new ArrayList<>());
        for(ReceptionDoor item:list){
            mapReceptionDoor.put(item.getId(),item);
        }
        return mapReceptionDoor;
    }


}
