package qms.bvp.web.service.reception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.DateUtils;
import qms.bvp.common.ReceptionStatus;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.web.repository.RootRepository;
import qms.bvp.web.repository.reception.ReceptionRepository;
import qms.bvp.web.service.RootService;

import java.util.*;

/**
 * Created by Admin on 8/15/2018.
 */
@Service
public class ReceptionServiceImpl implements ReceptionService {
    @Autowired
    ReceptionRepository receptionRepository;
    @Autowired
    RootService rootService;
    @Autowired
    RootRepository rootRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Reception createReception(Reception item) {
        ReceptionArea checkArea=rootRepository.mapReceptionAreas.get(item.getReception_area());
        if(checkArea==null) return null;
        Integer numberCurrent=rootRepository.mapNumberOfReceptionArea.get(item.getReception_area());
        if(numberCurrent==null)return null;
        item.setOrder_number(new Integer(numberCurrent.intValue()+1));
        item.setCode(checkArea.getPrefix()+item.getOrder_number());
        item.setDate_created(new Date());
        item.setStatus(ReceptionStatus.DangChoTiepDon);
        item=receptionRepository.save(item);
        rootService.addReceptionToMap(item);
        return item;
    }

    @Override
    public Optional<List<Reception>> getAllReceptionWaitInDB() {
        Date now=new Date();
        try{
            String dateStr= DateUtils.dateToStr(now,"dd/MM/yyyy");
            Date fromDate= DateUtils.genDate(dateStr,true);
            Date toDate=DateUtils.genDate(dateStr,false);
            return receptionRepository.listAllReceptionWait(fromDate,toDate);
        }catch (Exception e){

        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<List<Reception>> getReceptionCurrentInDoor() {
        Date now=new Date();
        try{
            String dateStr= DateUtils.dateToStr(now,"dd/MM/yyyy");
            Date fromDate= DateUtils.genDate(dateStr,true);
            Date toDate=DateUtils.genDate(dateStr,false);
            return receptionRepository.getReceptionCurrentInDoor(fromDate,toDate);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public Optional<List<Reception>> getAllReceptionMissOfDoor() {
        Date now=new Date();
        try{
            String dateStr= DateUtils.dateToStr(now,"dd/MM/yyyy");
            Date fromDate= DateUtils.genDate(dateStr,true);
            Date toDate=DateUtils.genDate(dateStr,false);
            return receptionRepository.getAllReceptionMissOfDoor(fromDate,toDate);
        }catch (Exception e){

        }
        return Optional.ofNullable(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Reception getReceptionForDoor(ReceptionDoor item) {
        if(item==null) return null;
        Reception reception=null;
        Integer orderNumber=null;
        Hashtable<Byte,TreeSet<Integer>> table1=rootRepository.mapAreaAndPriorityMapOrderNumber.get(item.getReception_area().getId());
        Hashtable<Integer,Reception> table2=rootRepository.mapAreaAndNumberMapReception.get(item.getReception_area().getId());
        Byte priority=item.getPrioritys().last();
        try{
            do{
                TreeSet<Integer> tree=table1.get(priority);
                int count=0;
                if(tree.size()>0){
                    while (count<10){
                        for(Integer number:tree){
                            reception=table2.get(number);
                            if(reception!=null){
                                reception.setStatus(ReceptionStatus.DangTiepDon);
                                reception.setReception_door(item.getId());
                                orderNumber=new Integer(number.intValue());
                                break;
                            }
                        }
                        if(orderNumber!=null) break;
                        count++;
                    }
                }
                if(orderNumber!=null){
                    break;
                }
                priority=item.getPrioritys().headSet(priority).last();
            }while (true);
        }catch (Exception e){

        }
        if(orderNumber!=null){
            reception.setDate_updated(new Date());
            receptionRepository.save(reception);
            rootService.removeReceptionInMap(reception);
        }
        return reception;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Optional<Boolean> confirmReceptionOfDoor(Integer doorId, byte status) {
        ReceptionDoor itemDoor=rootService.getReceptionDoorById(doorId);
        if(itemDoor!=null){
            Reception reception=itemDoor.getReception_current();
            if(status==ReceptionStatus.DaTiepDon){
                reception.setStatus(ReceptionStatus.DaTiepDon);
            }else{
                reception.setStatus(ReceptionStatus.BoQua);
            }
            reception.setDate_updated(new Date());
            receptionRepository.save(reception);
            itemDoor.setOrder_number_current(null);
            itemDoor.setReception_current(null);
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    @Override
    public Optional<Long> countReceptionByDoor(Integer doorId) {
        return receptionRepository.countReceptionByDoor(doorId);
    }

    @Override
    public Optional<Long> countReceptionByArea(Integer areaId) {
        return receptionRepository.countReceptionByArea(areaId);
    }
}
