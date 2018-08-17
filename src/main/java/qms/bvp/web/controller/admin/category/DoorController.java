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
import qms.bvp.web.service.category.ReceptionDoorService;

/**
 * Created by Admin on 8/13/2018.
 */
@Controller
@RequestMapping("/admin/category/door")
public class DoorController {
    private Logger logger= LogManager.getLogger(DoorController.class);
    @Autowired
    ReceptionDoorService doorService;

    @GetMapping("")
    public String list(Model model){
        return "/admin/category/door/list";
    }

    @GetMapping("/listAll")
    public ResponseEntity<PagingResult> listAll(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=doorService.page(page).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method listAll:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }
}
