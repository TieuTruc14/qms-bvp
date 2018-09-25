package qms.bvp.web.controller.admin.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qms.bvp.config.ConstantAuthor;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.swap.GeneralityDoor;
import qms.bvp.web.service.RootService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/16/2018.
 */
@Controller
@RequestMapping("/admin/management/generality")
public class GeneralityController {
    private Logger logger= LogManager.getLogger(GeneralityController.class);
    @Autowired
    RootService rootService;

    @GetMapping("")
    public String list(Model model){
        return "admin/manager/generality-door";
    }

    @GetMapping("/list-door")
    @Secured(ConstantAuthor.MANAGEMENT.door)
    public ResponseEntity<List<GeneralityDoor>> listAll(){
       List<ReceptionDoor> list=rootService.listAllReceptionDoor();
       List<GeneralityDoor> lst=new ArrayList<>();
       list.forEach(item->{
           GeneralityDoor ge=new GeneralityDoor();
           ge.setId(item.getId());
           ge.setArea_name(item.getReception_area().getName());
           ge.setName(item.getName());
           ge.setOrder_number_current(item.getOrder_number_current());
           if(item.getOrder_number_current()!=null){
               ge.setCode(item.getReception_area().getPrefix()+item.getOrder_number_current());
           }
           ge.setReception_type_value(item.getReception_type_value());
           List<String> listStr=new ArrayList<>();
           if(item.getReceptions_miss()!=null){
               item.getReceptions_miss().forEach(re->{
                   listStr.add(re.getCode());
               });
           }
           ge.setReceptions_miss(listStr);
           lst.add(ge);
       });
        return new ResponseEntity<List<GeneralityDoor>>(lst, HttpStatus.OK);
    }

}
