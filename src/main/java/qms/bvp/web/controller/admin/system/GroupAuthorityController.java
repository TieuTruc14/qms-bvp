package qms.bvp.web.controller.admin.system;

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
import qms.bvp.common.Utils;
import qms.bvp.model.Authority;
import qms.bvp.model.swap.AuthorityView;
import qms.bvp.model.swap.GroupSwap;
import qms.bvp.validator.category.GroupViewAddValidator;
import qms.bvp.web.service.group.GroupService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/system/group-authority")
public class GroupAuthorityController {
    private Logger logger= LogManager.getLogger(GroupAuthorityController.class);
    @Autowired
    GroupService groupService;
    @Autowired
    GroupViewAddValidator groupViewAddValidator;

    @GetMapping("")
    public String template(Model model){
        return "admin/system/authority/list";
    }

    @GetMapping("/list")
    public ResponseEntity<PagingResult> list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String filtername){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=groupService.page(filtername,page).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error method list: "+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String groupAdd(Model model) {
        try{
            List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
            if(items==null && items.size()==0){
                return "404";
            }
            loadAuthorityToModel(model,items);
            GroupSwap item=new GroupSwap();
            model.addAttribute("item",item);
        }catch (Exception e){
            logger.error("Have an error method groupAdd: "+e.getMessage());
        }

        return "admin/system/authority/add";
    }

    public void loadAuthorityToModel(Model model,List<Authority> items){
        List<AuthorityView> list=new ArrayList<>();
        List<Authority> childrens=new ArrayList<>();
        for(Authority item:items){
            if(item.getParent()==0){
                AuthorityView au=new AuthorityView();
                au.setParent(item);
                list.add(au);
            }
        }

        for(AuthorityView item:list){
            childrens=new ArrayList<>();
            for(Authority authority:items){
                if(authority.getParent()==item.getParent().getId()){
                    childrens.add(authority);
                }
            }
            item.setChildrens(childrens);
        }
        model.addAttribute("groups",list);
    }

    @PostMapping("/add")
    public String groupAddSave(Model model, @Valid GroupSwap item, BindingResult result, RedirectAttributes attributes,HttpServletRequest request) {
        groupViewAddValidator.validate(item,result);
        if(StringUtils.isBlank(item.getGroupName()) || item.getGroupName().length()>100){
            model.addAttribute("errorName","Tên không được trống và không quá 100 ký tự!");
        }
        try{
            String ip= Utils.getIpClient(request);
            if(!result.hasErrors() && groupService.saveGroupView(item,ip).orElse(false)){
                attributes.addFlashAttribute("success","Thêm đối tượng thành công!");
                return "redirect:/admin/system/group-authority";
            }
        }catch (Exception e){
            logger.error("Have error GroupController.groupAddSave: "+e.getMessage());
        }
        /*Reload when error*/
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null || items.size()==0){
            return "404";
        }
        loadAuthorityToModel(model,items);
        model.addAttribute("messageError","Thêm đối tượng thất bại!");
        model.addAttribute("item",item);
        return "admin/system/authority/add";
    }


    @GetMapping("/edit/{id}")
    public String groupEdit(Model model,@PathVariable("id") Integer id) {
        if(id==null || id.intValue()==0) return "404";
        try{
            GroupSwap item=groupService.getGroupView(id).orElse(null);
            if(item==null) return "404";
            List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
            if(items==null || items.size()==0)return "404";
            loadAuthorityToModel(model,items);
            model.addAttribute("item",item);
        }catch (Exception e){
            logger.error("Have error groupEdit: "+e.getMessage());
            return "404";
        }

        return "admin/system/authority/edit";
    }

    @PostMapping("/edit")
    public String groupEditSave(Model model, @Valid GroupSwap item, BindingResult result, RedirectAttributes attributes,HttpServletRequest request) {
        if(item.getId()==null|| item.getId().intValue()==0) return "404";
        groupViewAddValidator.validate(item,result);
        try{
            String ip= Utils.getIpClient(request);
            if(!result.hasErrors() && groupService.editGroupView(item,ip).orElse(false)){
                attributes.addFlashAttribute("success","Sửa nhóm quyền thành công!");
                return "redirect:/admin/system/group-authority";
            }
        }catch (Exception e){
            logger.error("Have error GroupController.groupEditSave: "+e.getMessage());
        }
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null && items.size()==0) return "404";
        loadAuthorityToModel(model,items);
        model.addAttribute("messageError","Sửa đối tượng thất bại!");
        model.addAttribute("item",item);
        return "admin/system/authority/edit";
    }

}
