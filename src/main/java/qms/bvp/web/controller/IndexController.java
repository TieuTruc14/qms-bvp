package qms.bvp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.User;
import qms.bvp.model.swap.AreaSwap;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.category.ReceptionAreaService;
import qms.bvp.web.service.logaccess.LogAccessService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/9/2018.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    ReceptionAreaService areaService;
    @Autowired
    RootService rootService;
    @Autowired
    LogAccessService logAccessService;

    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/404")
    public String get404() {
        return "public/error/404";
    }

    @GetMapping("/401")
    public String get401() {
        return "public/error/401";
    }

    @GetMapping("/403")
    public String get403() {
        return "public/error/403";
    }

    @GetMapping("/500")
    public String get500() {
        return "public/error/500";
    }

    @GetMapping("/area/list")
    public ResponseEntity<List<AreaSwap>> listAreas(){
        List<ReceptionArea> listAll=areaService.listAllActive().orElse(new ArrayList<>());
        List<AreaSwap> list=new ArrayList<>();
        listAll.forEach(item->{
            AreaSwap view=new AreaSwap();
            view.setId(item.getId());
            view.setName(item.getName());
            view.setPrefix(item.getPrefix());
            view.setLoudspeaker_times(item.getLoudspeaker_times());
            list.add(view);
        });
        return new ResponseEntity<List<AreaSwap>>(list, HttpStatus.OK);
    }

    @GetMapping("/history")
    public String getOfUser(){
        return "admin/system/log/my.history";
    }

    @GetMapping("/history/my-log")
    public ResponseEntity<PagingResult> logOfUser(@RequestParam(value = "p", required = false, defaultValue = "1")int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            page=logAccessService.getByUserId(page,user.getId()).orElse(new PagingResult());
        }catch (Exception e){
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

}
