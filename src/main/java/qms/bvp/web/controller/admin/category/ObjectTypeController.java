package qms.bvp.web.controller.admin.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionObjectType;
import qms.bvp.web.service.category.ReceptionObjectTypeService;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/13/2018.
 */
@Controller
@RequestMapping("/admin/category/object-type")
public class ObjectTypeController {
    private Logger logger= LogManager.getLogger(DoorController.class);
    @Autowired
    ReceptionObjectTypeService objectTypeService;

    @GetMapping("")
    public String list(Model model){
        return "admin/category/objectType/list";
    }

    @GetMapping("/list")
    public ResponseEntity<PagingResult> list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=objectTypeService.page(page).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method listAll:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/list-active")
    public ResponseEntity<List<ReceptionObjectType>> listActive(){
        List<ReceptionObjectType> list=new ArrayList<>();
        try{
            list=objectTypeService.listAllNotDeleted().orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("Have an error on method list:"+e.getMessage());
        }
        return new ResponseEntity<List<ReceptionObjectType>>(list,HttpStatus.OK);
    }
}
