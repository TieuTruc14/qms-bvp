package qms.bvp.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.ReceptionObjectType;
import qms.bvp.web.repository.RootRepository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Admin on 8/15/2018.
 */
@Service
@Transactional
public class RootService {
    @Autowired
    RootRepository rootRepository;

    public Reception genPriorityAndSuffixOfReception(Reception reception){
        List<ReceptionObjectType> list=rootRepository.receptionObjectTypeList;
        Byte prioritys=0;
        for(ReceptionObjectType item:list){
            if((reception.getReception_type_value().longValue() & item.getValue().longValue())>0){
                if(prioritys.intValue()<item.getPriority().intValue()){
                    reception.setPriority(item.getPriority());
                    if(StringUtils.isNotBlank(item.getSuffix())){
                        reception.setSuffix(item.getSuffix());
                    }else{
                        reception.setSuffix("");
                    }
                }
            }
        }
        return reception;
    }


    public ReceptionDoor getReceptionDoorById(Integer doorId){
        ReceptionDoor item=rootRepository.mapReceptionDoor.get(doorId);
        return item;
    }
    public ReceptionArea getReceptionAreaById(Integer areaId){
        ReceptionArea item=rootRepository.mapReceptionAreas.get(areaId);
        return item;
    }
    public List<ReceptionDoor> listAllReceptionDoor(){
        Hashtable<Integer,ReceptionDoor> map=rootRepository.mapReceptionDoor;
        List<ReceptionDoor> list=new ArrayList<>();
        map.forEach((k,v)->{
            list.add(v);
        });
        return list;
    }
    public boolean setCurrentReceptionForDoor(Integer doorId,Reception reception){
        if(reception!=null && reception.getOrder_number()!=null){
            ReceptionDoor item=rootRepository.mapReceptionDoor.get(doorId);
            item.setOrder_number_current(reception.getOrder_number());
            item.setReception_current(reception);
            return true;
        }
        return false;
    }

    public synchronized void addReceptionToMap(Reception item){
        Integer current=rootRepository.mapNumberOfReceptionArea.get(item.getReception_area());
        if(item.getOrder_number().compareTo(current)>0){
            rootRepository.mapNumberOfReceptionArea.put(item.getReception_area(),item.getOrder_number());
            Hashtable<Byte,TreeSet<Integer>> table1=rootRepository.mapAreaAndPriorityMapOrderNumber.get(item.getReception_area());
//            for(Byte priority:item.getPrioritys()){
                TreeSet<Integer> tree1=table1.get(item.getPriority());
                if(tree1!=null){
                    tree1.add(item.getOrder_number());
                }
//            }
            Hashtable<Integer,Reception> table2=rootRepository.mapAreaAndNumberMapReception.get(item.getReception_area());
            table2.put(item.getOrder_number(),item);
        }
    }

    public synchronized void removeReceptionInMap(Reception item){
        Hashtable<Byte,TreeSet<Integer>> table1=rootRepository.mapAreaAndPriorityMapOrderNumber.get(item.getReception_area());
        Hashtable<Integer,Reception> table2=rootRepository.mapAreaAndNumberMapReception.get(item.getReception_area());
//        for(Byte priority:item.getPrioritys()){
            TreeSet<Integer> tree=table1.get(item.getPriority());
            if(tree!=null){
                tree.remove(item.getOrder_number());
            }
//        }
        table2.remove(item.getOrder_number());
        setCurrentReceptionForDoor(item.getReception_door(),item);
    }
}
