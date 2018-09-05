package qms.bvp.web.controller.admin.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qms.bvp.common.PagingResult;
import qms.bvp.common.Utils;
import qms.bvp.model.User;
import qms.bvp.web.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 8/25/2018.
 */
@Controller
@RequestMapping("/admin/system/user")
public class UserController {
    private Logger logger= LogManager.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("")
    public String template(Model model){
        return "admin/system/user/list";
    }

    @GetMapping("/list")
    public ResponseEntity<PagingResult> list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,String username){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=userService.page(page,username).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error method list: "+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUserByUsername(String username){
        if(StringUtils.isBlank(username)){
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        }
        boolean check=true;
        try{
            check=userService.checkUserByUsername(username);
        }catch (Exception e){

        }
        return new ResponseEntity<Boolean>(check,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Byte> add(String username,String fullname,String password,String description,HttpServletRequest request){
        if(StringUtils.isBlank(username)||StringUtils.isBlank(fullname)||StringUtils.isBlank(password)){
            return new ResponseEntity<Byte>(Byte.valueOf("2"),HttpStatus.OK);
        }
        try{
            String ipClient= Utils.getIpClient(request);
            User item=new User();
            item.setUsername(username);
            item.setFullname(fullname);
            item.setDescription(description);
            item.setPassword(password);
            Byte result=userService.add(item,ipClient).orElse(Byte.valueOf("0"));
            return new ResponseEntity<Byte>(result,HttpStatus.OK);
        }catch (Exception e){

        }
        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Byte> edit(Long id,Boolean disable,String fullname,String description,HttpServletRequest request){
        if(id==null || !(id.longValue()>0) || StringUtils.isBlank(fullname)){
            return new ResponseEntity<Byte>(Byte.valueOf("2"),HttpStatus.OK);
        }
        Byte result=0;
        try{
            User item=userService.findById(id).orElse(null);
            if(item==null) return new ResponseEntity<Byte>(Byte.valueOf("3"),HttpStatus.OK);
            String ip=Utils.getIpClient(request);
            result=userService.edit(id,disable,fullname,description,ip).orElse(Byte.valueOf("0"));
        }catch (Exception e){

        }
        return new ResponseEntity<Byte>(result,HttpStatus.OK);
    }
}
