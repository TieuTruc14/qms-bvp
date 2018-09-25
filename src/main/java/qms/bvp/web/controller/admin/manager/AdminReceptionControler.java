package qms.bvp.web.controller.admin.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qms.bvp.common.PagingResult;
import qms.bvp.common.Utils;
import qms.bvp.config.ConstantAuthor;
import qms.bvp.model.Reception;
import qms.bvp.web.controller.ReceptionController;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.reception.ReceptionService;

/**
 * Created by Admin on 8/22/2018.
 */
@Controller
@RequestMapping("/admin/management/reception")
public class AdminReceptionControler {
    private Logger logger= LogManager.getLogger(ReceptionController.class);
    @Autowired
    RootService rootService;
    @Autowired
    ReceptionService receptionService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.MANAGEMENT.reception)
    public ResponseEntity<PagingResult> list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber
    ,String code,String from,String to) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(50);
        try{
            page=receptionService.page(page, Utils.trim(code),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());
            return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error method list:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    @Secured(ConstantAuthor.MANAGEMENT.reception)
    public String reception(){
        return "admin/manager/admin.reception";
    }
}
