package qms.bvp.web.controller.admin.category;

import org.apache.commons.lang3.StringUtils;
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
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.view.AreaView;
import qms.bvp.validator.category.AreaViewValidator;
import qms.bvp.web.service.category.ReceptionAreaService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @Autowired
    AreaViewValidator areaViewValidator;

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

    @PostMapping("/add")
//    public ResponseEntity<Byte> add(@Valid AreaView item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
    public ResponseEntity<Byte> add(@RequestBody AreaView item){
//        areaViewValidator.validate(item,result);
//        if(result.hasErrors()){
//            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
//        }
        if(StringUtils.isBlank(item.getName()) || StringUtils.isBlank(item.getPrefix()) || item.getLoudspeaker_times()==null){
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
    public ResponseEntity<Byte> edit(@Valid AreaView item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        areaViewValidator.validate(item,result);
        if(result.hasErrors()){
            return new ResponseEntity<Byte>(Byte.valueOf("0"), HttpStatus.EXPECTATION_FAILED);//417
        }
        try{
            boolean checkExits=areaService.checkAreaByNameOrPrefix(item.getName(),item.getPrefix()).orElse(true);
            if(checkExits) return new ResponseEntity<Byte>(Byte.valueOf("2"), HttpStatus.CONFLICT);//409
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



}
