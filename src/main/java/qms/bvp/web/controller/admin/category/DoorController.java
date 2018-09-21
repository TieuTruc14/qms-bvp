package qms.bvp.web.controller.admin.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qms.bvp.common.PagingResult;
import qms.bvp.common.Utils;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.swap.Door;
import qms.bvp.validator.category.DoorValidator;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.category.ReceptionDoorService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 8/13/2018.
 */
@Controller
@RequestMapping("/admin/category/door")
public class DoorController {
    private Logger logger= LogManager.getLogger(DoorController.class);
    @Autowired
    ReceptionDoorService doorService;
    @Autowired
    DoorValidator doorValidator;
    @Autowired
    RootService rootService;

    @GetMapping("")
    public String list(Model model){
        return "admin/category/door/list";
    }

    @GetMapping("/list-all")
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

    @PostMapping("/add")
    public ResponseEntity<Byte> add(@RequestBody Door item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        doorValidator.validate(item,result);
        if(result.hasErrors()){
            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
        }
        try{
            ReceptionArea ra=rootService.getReceptionAreaById(item.getArea());
            if(ra==null) return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
            String ip= Utils.getIpClient(request);
            Byte value=doorService.add(item,ip).orElse(Byte.valueOf("0"));
            if(value==1){
                return new ResponseEntity<Byte>(Byte.valueOf("1"), HttpStatus.OK);
            }else if(value==2){
                return new ResponseEntity<Byte>(Byte.valueOf("2"), HttpStatus.FORBIDDEN);//403
            }
        }catch (Exception e){
            logger.error("Have an error on method add:"+e.getMessage());
        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/edit")
    public ResponseEntity<Byte> edit(@RequestBody Door item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        doorValidator.validate(item,result);
        if(result.hasErrors() || item.getId()==null || item.getId().intValue()==0){
            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
        }
        try{
            ReceptionArea ra=rootService.getReceptionAreaById(item.getArea());
            if(ra==null) return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
            String ip= Utils.getIpClient(request);
            Byte value=doorService.edit(item,ip).orElse(Byte.valueOf("0"));
            if(value==1){
                return new ResponseEntity<Byte>(Byte.valueOf("1"), HttpStatus.OK);
            }else if(value==2){
                return new ResponseEntity<Byte>(Byte.valueOf("2"), HttpStatus.FORBIDDEN);//403
            }
        }catch (Exception e){
            logger.error("Have an error on method edit:"+e.getMessage());
        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Byte> delete(@PathVariable("id") Integer id,HttpServletRequest request){
        try{
            String ip= Utils.getIpClient(request);
            Byte result=doorService.delete(id,ip).orElse(Byte.valueOf("0"));
            if(result==1){
                return new ResponseEntity<Byte>(result,HttpStatus.OK);
            }else if(result==2){
                return new ResponseEntity<Byte>(result,HttpStatus.FORBIDDEN);
            }else if(result==3){
                return new ResponseEntity<Byte>(result,HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            logger.error("Have an error method delete(): "+e.getMessage());
        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.NOT_FOUND);
    }
}
