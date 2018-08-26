package qms.bvp.web.controller.admin.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qms.bvp.common.PagingResult;
import qms.bvp.web.service.user.UserService;

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
    public ResponseEntity<Byte> add(){

        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.OK);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Byte> edit(){

        return new ResponseEntity<Byte>(Byte.valueOf("0"),HttpStatus.OK);
    }
}
