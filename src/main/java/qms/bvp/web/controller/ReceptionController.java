package qms.bvp.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.reception.ReceptionService;

/**
 * Created by Admin on 8/15/2018.
 */
@Controller
@RequestMapping("/reception")
public class ReceptionController {
    private Logger logger= LogManager.getLogger(ReceptionController.class);
    @Autowired
    RootService rootService;
    @Autowired
    ReceptionService receptionService;

    @GetMapping("/born-number")
    public ResponseEntity<Reception> getNumber(boolean assurance,Long reception_type_value,Integer areaId) {
        Reception item=new Reception();
        try{
            if(reception_type_value==null || areaId==null || reception_type_value.longValue()<=0 || areaId.intValue()<=0){
                throw new Exception();
            }
            item.setAssurance(assurance);
            item.setReception_type_value(reception_type_value);
            item.setPriority(rootService.genPriorityOfReception(reception_type_value));
            item.setReception_area(areaId);
            item=receptionService.createReception(item);
            return new ResponseEntity<Reception>(item, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error when born number reception method getNumber:"+e.getMessage());
            item=null;
        }
        return new ResponseEntity<Reception>(item, HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public String reception(){
        return "public/reception";
    }
}
