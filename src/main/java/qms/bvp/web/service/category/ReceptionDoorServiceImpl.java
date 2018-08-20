package qms.bvp.web.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.User;
import qms.bvp.model.view.Door;
import qms.bvp.web.repository.RootRepository;
import qms.bvp.web.repository.category.ReceptionDoorRepository;
import qms.bvp.web.service.reception.ReceptionService;

import java.util.*;

/**
 * Created by Admin on 8/13/2018.
 */
@Service
public class ReceptionDoorServiceImpl implements ReceptionDoorService {
    @Autowired
    ReceptionDoorRepository doorRepository;
    @Autowired
    RootRepository rootRepository;
    @Autowired
    ReceptionService receptionService;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        return doorRepository.page(page);
    }

    @Override
    public Optional<List<ReceptionDoor>> listAll() {
        return Optional.of(doorRepository.findAll());
    }
    @Override
    public Optional<List<ReceptionDoor>> listAllActive() {
        return doorRepository.listAllActive();
    }

    @Override
    public Hashtable<Integer, ReceptionDoor> initReceptionDoor() {
        Hashtable<Integer, ReceptionDoor> mapReceptionDoor=new Hashtable<>();
        List<ReceptionDoor> list=listAllActive().orElse(new ArrayList<>());
        for(ReceptionDoor item:list){
            mapReceptionDoor.put(item.getId(),item);
        }
        return mapReceptionDoor;
    }

    @Override
    public Optional<Byte> add(Door item) {
        if(item==null) return Optional.of(Byte.valueOf("0"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionDoor door=new ReceptionDoor();
        ReceptionArea area=new ReceptionArea();
        area.setId(item.getArea());
        door.setId(item.getId());
        door.setName(item.getName());
        door.setReception_area(area);
        door.setDescription(item.getDescription());
        door.setReception_type_value(item.getReception_type_value());
        door.setDisable(false);
        door.setDeleted(false);
        door.setDate_created(new Date());
        door.setUser_created(user.getId());
        doorRepository.save(door);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    public Optional<Byte> edit(Door item) {
        if(item==null) return Optional.of(Byte.valueOf("0"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionDoor itemDB=get(item.getId()).orElse(null);
        if(itemDB==null) return Optional.of(Byte.valueOf("0"));
        ReceptionArea area=new ReceptionArea();
        area.setId(item.getArea());
        itemDB.setName(item.getName());
        itemDB.setReception_area(area);
        itemDB.setDescription(item.getDescription());
        itemDB.setReception_type_value(item.getReception_type_value());
        itemDB.setDate_updated(new Date());
        itemDB.setUser_updated(user.getId());
        doorRepository.save(itemDB);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    public Optional<Byte> delete(Integer id) {
        if(id==null) return Optional.of(Byte.valueOf("0"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionDoor itemDB=getNotDelete(id).orElse(null);
        if(itemDB==null) return Optional.of(Byte.valueOf("0"));
        Long count=receptionService.countReceptionByDoor(id).orElse(Long.valueOf("0"));
        if(count>0) return  Optional.of(Byte.valueOf("3"));
        itemDB.setDate_updated(new Date());
        itemDB.setUser_updated(user.getId());
        itemDB.setDeleted(true);
        doorRepository.save(itemDB);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    public Optional<ReceptionDoor> get(Integer id) {
        return doorRepository.findById(id);
    }

    @Override
    public Optional<ReceptionDoor> getNotDelete(Integer id) {
        return doorRepository.getNotDeleted(id);
    }

    @Override
    public void deleteAllDoorOfArea(Integer areaId,Long userId,Date date) {
         doorRepository.deleteAllOfArea(areaId,userId,date);
    }
}
