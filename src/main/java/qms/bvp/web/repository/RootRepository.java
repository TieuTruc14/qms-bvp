package qms.bvp.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionObjectType;
import qms.bvp.web.service.category.ReceptionAreaService;
import qms.bvp.web.service.category.ReceptionDoorService;
import qms.bvp.web.service.category.ReceptionObjectTypeService;
import qms.bvp.web.service.reception.ReceptionService;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Admin on 8/9/2018.
 */
@Component
public class RootRepository {

    public static List<ReceptionArea> listReceptionAreas;
    public static HashMap<Integer,ReceptionArea> mapReceptionAreas=new HashMap<>();
    public static Hashtable<Integer,Integer> mapNumberOfReceptionArea=new Hashtable<>();//hien thi so hien tai cua receptionArea

    public static Hashtable<Integer,ReceptionDoor> mapReceptionDoor=new Hashtable<>();//idDoor
    public static List<ReceptionObjectType> receptionObjectTypeList;
    //for reception wait
    public static HashMap<Integer,Hashtable<Byte,TreeSet<Integer>>> mapAreaAndPriorityMapOrderNumber=new HashMap<>();//HashMap<IdArea,HashTable<priority,TreeSet<OrderNumber>>>
    public static HashMap<Integer,Hashtable<Integer,Reception>> mapAreaAndNumberMapReception=new HashMap<>();//HashMap<IdArea,HashTable<OrderNumber,Reception>>

    @Autowired
    ReceptionObjectTypeService objectTypeService;
    @Autowired
    ReceptionAreaService areaService;
    @Autowired
    ReceptionDoorService doorService;
    @Autowired
    ReceptionService receptionService;

    @PostConstruct
    public void init(){
        listReceptionAreas=areaService.listAll().orElse(new ArrayList<>());
        for(ReceptionArea item:listReceptionAreas){
            mapReceptionAreas.put(item.getId(),item);
        }
        mapNumberOfReceptionArea=areaService.initAreaWithOrderNumber().orElse(new Hashtable<>());
        receptionObjectTypeList=objectTypeService.listAll().orElse(new ArrayList<>());
        mapReceptionDoor=doorService.initReceptionDoor();
        genInfoMapReceptionDoor();
        initReceptionWaitAndmapNumberOfReceptionArea(listReceptionAreas,receptionObjectTypeList);
        //load receptionCurrentDoor and ReceptionMiss of Door
        loadCurrentReceptionAndMissReceptionOfDoor();
    }


    private void genInfoMapReceptionDoor(){
        mapReceptionDoor.forEach((k,v)->{
            ReceptionDoor item=v;
            item.setPrefix(item.getReception_area().getPrefix());
            item.setPrioritys(getPriorityDoor(item.getReception_type_value()));
        });
    }
    private TreeSet<Byte> getPriorityDoor(Long reception_type_value){
        TreeSet<Byte> tree=new TreeSet<>();
        for(ReceptionObjectType item:receptionObjectTypeList){
            if((reception_type_value.longValue() & item.getValue().longValue())>0){
                tree.add(item.getPriority());
            }
        }
        return tree;
    }

    private void initReceptionWaitAndmapNumberOfReceptionArea(List<ReceptionArea> listReceptionAreas,List<ReceptionObjectType> receptionObjectTypeList){
        List<Reception> listReception =receptionService.getAllReceptionWaitInDB().orElse(new ArrayList<>());
        Set<Byte> set=new HashSet();
        for(ReceptionObjectType item: receptionObjectTypeList){
            set.add(item.getPriority());
        }
        HashMap<Integer,List<Reception>> mapAreaWithReception=new HashMap<>();
        for(ReceptionArea item:listReceptionAreas){
            mapAreaWithReception.put(item.getId(),new ArrayList<>());
        }

        for(Reception item:listReception){
            List<Reception> lst=mapAreaWithReception.get(item.getReception_area());
            lst.add(item);
        }

        for(ReceptionArea ra:listReceptionAreas){
            Hashtable<Byte,TreeSet<Integer>> table1 =new Hashtable<>();
            Hashtable<Integer,Reception> table2=new Hashtable<>();
            //init table1
            for(Byte i:set){
                table1.put(i,new TreeSet<Integer>());
            }

            List<Reception> lst=mapAreaWithReception.get(ra.getId());
            for(Reception item: lst){
                TreeSet<Integer> tree=table1.get(item.getPriority());
                tree.add(item.getOrder_number());

                table2.put(item.getOrder_number(),item);
            }
            mapAreaAndPriorityMapOrderNumber.put(ra.getId(),table1);
            mapAreaAndNumberMapReception.put(ra.getId(),table2);
        }

        //for mapNumberOfReceptionArea
        mapAreaWithReception.forEach((k,v)->{
            Integer max=0;
            for(Reception item:v){
                if(max.compareTo(item.getOrder_number())<0) max=new Integer(item.getOrder_number().intValue());
            }
            mapNumberOfReceptionArea.put(k,max);
        });
//        for(Map.Entry<Integer, List<Reception>> entry : mapAreaWithReception.entrySet()) {
//            Integer key = entry.getKey();
//            List<Reception> value = entry.getValue();
//        }

    }

    private void loadCurrentReceptionAndMissReceptionOfDoor(){
        List<Reception> listCurrentOfDoor=receptionService.getReceptionCurrentInDoor().orElse(new ArrayList<>());
        List<Reception> listMissOfDoor=receptionService.getAllReceptionMissOfDoor().orElse(new ArrayList<>());
        listCurrentOfDoor.forEach(item->{
            ReceptionDoor door=mapReceptionDoor.get(item.getReception_door());
            if(door!=null){
                door.setOrder_number_current(item.getOrder_number());
                door.setReception_current(item);
            }
        });
        listMissOfDoor.forEach(item->{
            ReceptionDoor door=mapReceptionDoor.get(item.getReception_door());
            if(door!=null){
                door.getReceptions_miss().add(item);
            }
        });
    }






}
