package qms.bvp.web.controller.admin.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qms.bvp.common.PagingResult;
import qms.bvp.common.Utils;
import qms.bvp.config.ConstantAuthor;
import qms.bvp.model.GroupRole;
import qms.bvp.model.GroupUser;
import qms.bvp.model.User;
import qms.bvp.web.service.group.GroupService;
import qms.bvp.web.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 8/25/2018.
 */
@Controller
@RequestMapping("/admin/system/user")
public class UserController {
    private Logger logger= LogManager.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;

    @GetMapping("")
    public String template(Model model){
        return "admin/system/user/list";
    }

    @GetMapping("/list")
    @Secured(ConstantAuthor.User.view)
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
    @Secured(ConstantAuthor.User.view)
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
    @Secured(ConstantAuthor.User.add)
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
    @Secured(ConstantAuthor.User.edit)
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


    @GetMapping("/user-group/{id}")
    @Secured(ConstantAuthor.User.grant)
    public String userGroup(Model model,@PathVariable("id") Long id){
        if(id==null) return "404";
        User user=userService.findById(id).orElse(null);
        if(user==null) return "404";
        //load all groups
        List<GroupRole> allGroups=groupService.loadAllGroup().orElse(new ArrayList<>());
        //load group of user
        List<GroupRole> listGroups=groupService.loadAllGroupOfUser(id).orElse(new ArrayList<>());
        String groups="";
        if(listGroups.size()>0){
            for(GroupRole item:listGroups){
                groups+=item.getId()+",";
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("groups",groups);
        model.addAttribute("allGroups",allGroups);
        return "admin/system/user/user.group";
    }


    @PostMapping("user-group")
    @Secured(ConstantAuthor.User.grant)
    public String addUserGroup(Model model, Long id,String listGroup,RedirectAttributes attributes,HttpServletRequest request){
        if(id==null) return "404";
        User user = userService.findById(id).orElse(null);
        if (user == null) return "404";
        listGroup=Utils.trim(listGroup);
        try {
            String ip= Utils.getIpClient(request);
            if (listGroup.length() > 0) {
                String[] array = listGroup.split(",");
                List<String> stringList = Arrays.stream(array).collect(Collectors.toList());
                if (stringList.size() > 0) {
                    List<GroupUser> items = new ArrayList<>();
                    User userCurrent = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    for (String item : stringList) {
                        items.add(new GroupUser(Integer.valueOf(item), id, userCurrent.getId(), new Date()));
                    }
                    if (items.size() > 0) {
                        groupService.addListGroupUser(items,id,ip);
                    }
                }
            } else {
                groupService.deleteListGroupOfUser(id,ip);
            }
            attributes.addFlashAttribute("success","Phân quyền thành công!");
            return "redirect:/admin/system/user";
        }catch (Exception e){
            logger.error("Have an error UserController.addUserGroup:"+e.getMessage());
            model.addAttribute("errorMessage","Có lỗi xảy ra, hãy thử lại sau!");
            //load all groups
            List<GroupRole> allGroups=groupService.loadAllGroup().orElse(new ArrayList<>());
            model.addAttribute("user",user);
            model.addAttribute("groups",listGroup);
            model.addAttribute("allGroups",allGroups);
        }
        model.addAttribute("errorMessage","Có lỗi xảy ra, hãy thử lại sau!");
        return "admin/system/user/user.group";
    }


    @GetMapping("/change-my-pass")
    public String changeMyPassView(){
        return "admin/system/user/change.my.pass";
    }
    @PutMapping("change-my-pass")
    public ResponseEntity<Integer> changeMyPass(@RequestParam String passwordCurrent, @RequestParam String passwordNew,HttpServletRequest request){
        //0-dieu kien ko phu hop, 1-oke thanh cong,2-mat khau cu khong dung,3-co loi server khi change
        passwordCurrent=Utils.trim(passwordCurrent);
        passwordNew=Utils.trim(passwordNew);
        if(StringUtils.isBlank(passwordCurrent)||StringUtils.isBlank(passwordNew)){
            return new  ResponseEntity<Integer>(0, HttpStatus.OK);
        }
        Integer result=0;
        try{
            String ip= Utils.getIpClient(request);
            result=userService.changeMyPass(passwordCurrent,passwordNew,ip).orElse(3);

        }catch (Exception e){
            logger.error("Have an error changMyPass:"+e.getMessage());
            return new  ResponseEntity<Integer>(3, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

}
