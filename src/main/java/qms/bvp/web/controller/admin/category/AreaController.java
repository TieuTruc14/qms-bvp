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
import qms.bvp.model.swap.AreaSwap;
import qms.bvp.validator.category.AreaViewValidator;
import qms.bvp.web.service.category.ReceptionAreaService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 8/13/2018.
 */
@Controller
@RequestMapping("/admin/category/area")
public class AreaController {
    private Logger logger= LogManager.getLogger(AreaController.class);
    @Autowired
    ReceptionAreaService areaService;
    @Autowired
    AreaViewValidator areaViewValidator;

    @GetMapping("")
    public String list(Model model){
        return "admin/category/area/list";
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

    @PostMapping("/add")
    public ResponseEntity<Byte> add(@RequestBody AreaSwap item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        areaViewValidator.validate(item,result);
        if(result.hasErrors()){
            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
        }
        try{
            boolean checkExits=areaService.checkAreaByNameOrPrefix(item.getName(),item.getPrefix()).orElse(true);
            if(checkExits) return new ResponseEntity<Byte>(Byte.valueOf("2"), HttpStatus.CONFLICT);//409
            Byte value=areaService.add(item).orElse(Byte.valueOf("0"));
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
    public ResponseEntity<Byte> edit(@RequestBody AreaSwap item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
                areaViewValidator.validate(item,result);
        if(result.hasErrors() || item.getId()==null || item.getId().intValue()==0){
            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
        }
        try{
            Byte value=areaService.edit(item).orElse(Byte.valueOf("0"));
            if(value==1){
                return new ResponseEntity<Byte>(Byte.valueOf("1"), HttpStatus.OK);
            }else if(value==3){
                return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.NOT_FOUND);//404
            }else if(value==2){
                return new ResponseEntity<Byte>(Byte.valueOf("2"), HttpStatus.FORBIDDEN);//403
            }
        }catch (Exception e){
            logger.error("Have an error on method edit:"+e.getMessage());
        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Byte> delete(@PathVariable("id") Integer id){
        try{
            Byte result=areaService.delete(id).orElse(Byte.valueOf("0"));
            if(result==1){
                return new ResponseEntity<Byte>(result,HttpStatus.OK);
            }else if(result==5){
                return new ResponseEntity<Byte>(result,HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            logger.error("Have an error method delete(): "+e.getMessage());
        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.NOT_FOUND);
    }



}
