package qms.bvp.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.reception.ReceptionService;

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

    @PostMapping("/next-reception")
    public ResponseEntity<Reception> nextOrMissReceptionOfDoor(Integer doorId, int status){
        //status: 3-bo qua, 2-da tiep don xong, 1- quay lai
        Reception reception=null;
        try{
            ReceptionDoor checkDoor=rootService.getReceptionDoorById(doorId);
            if(checkDoor==null || status<0 || status>2){
                return new ResponseEntity<Reception>(reception, HttpStatus.NOT_ACCEPTABLE);
            }
            if(checkDoor.getOrder_number_current()!=null){
                boolean check=receptionService.confirmReceptionOfDoor(doorId,status).orElse(false);
                if(check){//lay reception moi
                    reception=receptionService.getReceptionForDoor(checkDoor);
                    return new ResponseEntity<Reception>(reception, HttpStatus.OK);
                }
            }
        }catch (Exception e){
        }
        return new ResponseEntity<Reception>(reception, HttpStatus.NO_CONTENT);
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
                }
           }

        }catch (Exception e){
            logger.error("have a error in method getCurrentReceptionDoor:"+e.getMessage());
        }
        return new ResponseEntity<Reception>(reception, HttpStatus.NOT_FOUND);
    }
}
