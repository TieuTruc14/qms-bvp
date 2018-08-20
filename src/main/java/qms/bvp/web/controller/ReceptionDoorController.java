package qms.bvp.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qms.bvp.common.ReceptionStatus;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.view.DoorView;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.reception.ReceptionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/15/2018.
 */
@Controller
@RequestMapping("/door")
public class ReceptionDoorController {
    private Logger logger= LogManager.getLogger(ReceptionDoorController.class);
    @Autowired
    RootService rootService;
    @Autowired
    ReceptionService receptionService;

    @PostMapping("/confirm-reception")
    public ResponseEntity<Reception> nextOrMissReceptionOfDoor(Integer doorId, byte status,String code){
        //status:  1-da tiep don xong, 2- tiep don xong va lay so tiep theo,3-bo qua,4-bo qua va lay so tiep theo
        Reception reception=null;
        try{
            ReceptionDoor checkDoor=rootService.getReceptionDoorById(doorId);
            if(checkDoor==null){
                return new ResponseEntity<Reception>(reception, HttpStatus.NOT_ACCEPTABLE);
            }
            if(checkDoor.getOrder_number_current()!=null){
                if(!code.equals(checkDoor.getPrefix()+checkDoor.getOrder_number_current())){
                    return new  ResponseEntity<Reception>(reception, HttpStatus.NOT_FOUND);
                }
                boolean check=false;
                switch (status){
                    case 1:
                        check=receptionService.confirmReceptionOfDoor(doorId, ReceptionStatus.DaTiepDon).orElse(false);
                        break;
                    case 2:
                        check=receptionService.confirmReceptionOfDoor(doorId, ReceptionStatus.DaTiepDon).orElse(false);
                        break;
                    case 3:
                        check=receptionService.confirmReceptionOfDoor(doorId, ReceptionStatus.BoQua).orElse(false);
                        break;
                    case 4:
                        check=receptionService.confirmReceptionOfDoor(doorId, ReceptionStatus.BoQua).orElse(false);
                        break;
                    default:
                }

                if(check){//lay reception moi
                    if(status==1 || status==3){
                        return new ResponseEntity<Reception>(reception, HttpStatus.ACCEPTED);//202- da chap nhan
                    }else{
                        reception=receptionService.getReceptionForDoor(checkDoor);
                        if(reception==null){
                            return new ResponseEntity<Reception>(reception, HttpStatus.NO_CONTENT);//204
                        }else{
                            return new ResponseEntity<Reception>(reception, HttpStatus.OK);
                        }
                    }
                }

            }
        }catch (Exception e){
            logger.error("have a error in method nextOrMissReceptionOfDoor:"+e.getMessage());
            return new ResponseEntity<Reception>(reception, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Reception>(reception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/current-reception")
    public  ResponseEntity<Reception> getCurrentReceptionDoor(Integer doorId){
        Reception reception=null;
        try{
            ReceptionDoor checkDoor=rootService.getReceptionDoorById(doorId);
            if(checkDoor==null){
                throw new Exception();
            }
           if(checkDoor.getReception_current()!=null){
                reception=checkDoor.getReception_current();
               return new ResponseEntity<Reception>(reception, HttpStatus.OK);
           }else{
                reception=receptionService.getReceptionForDoor(checkDoor);
                if(reception==null){
                    return new ResponseEntity<Reception>(reception, HttpStatus.NO_CONTENT);//204
                }else{
                    return new ResponseEntity<Reception>(reception, HttpStatus.OK);
                }
           }

        }catch (Exception e){
            logger.error("have a error in method getCurrentReceptionDoor:"+e.getMessage());
            return new ResponseEntity<Reception>(reception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public String templateDoor(Model model, @PathVariable("id") Integer doorId){
        //check door roi send toi giao dien theo config nghiep vu ve sau
        ReceptionDoor door=rootService.getReceptionDoorById(doorId);
        if(door==null){
            return "404";
        }
        DoorView item=new DoorView();
        item.setArea_name(door.getReception_area().getName());
        item.setName(door.getName());
        item.setId(door.getId());
        if(door.getOrder_number_current()!=null){
            item.setOrder_number(door.getPrefix()+door.getOrder_number_current());
        }else{
            item.setOrder_number("");
        }
        List<String> listStr=new ArrayList<>();
        if(door.getReceptions_miss()!=null){
            door.getReceptions_miss().forEach(re->{
                listStr.add(re.getCode());
            });
        }
        model.addAttribute("door",item);
        return "public/door";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<DoorView> getDoorInfo(@PathVariable("id") Integer doorId){
        ReceptionDoor door=rootService.getReceptionDoorById(doorId);
        DoorView item=new DoorView();
        if(door==null){
            return new ResponseEntity<DoorView>(item, HttpStatus.NOT_FOUND);
        }
        item.setArea_name(door.getReception_area().getName());
        item.setName(door.getName());
        item.setId(door.getId());
        if(door.getOrder_number_current()!=null){
            item.setOrder_number(door.getPrefix()+door.getOrder_number_current());
        }else{
            item.setOrder_number("");
        }
        List<String> listStr=new ArrayList<>();
        if(door.getReceptions_miss()!=null){
            door.getReceptions_miss().forEach(re->{
                listStr.add(re.getCode());
            });
        }
        return new ResponseEntity<DoorView>(item, HttpStatus.OK);
    }
}
