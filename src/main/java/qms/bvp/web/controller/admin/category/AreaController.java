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
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.view.AreaView;
import qms.bvp.web.service.category.ReceptionAreaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/13/2018.
 */
@Controller
@RequestMapping("/admin/category/area")
public class AreaController {
    private Logger logger= LogManager.getLogger(AreaController.class);
    @Autowired
    ReceptionAreaService areaService;

    @GetMapping("")
    public String list(Model model){
        return "/admin/category/area/list";
    }

    @GetMapping("/page")
    public ResponseEntity<PagingResult> listAll(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=areaService.page(page).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method listAll:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }



}
