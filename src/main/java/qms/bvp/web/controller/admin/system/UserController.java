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
    @PutMapping("/edit/{id}")
    public ResponseEntity<Byte> edit(){

        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.OK);
    }
}
