package qms.bvp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.view.AreaView;
import qms.bvp.model.view.DoorView;
import qms.bvp.web.service.RootService;
import qms.bvp.web.service.category.ReceptionAreaService;

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


    @GetMapping("/area/list")
    public ResponseEntity<List<AreaView>> listAreas(){
        List<ReceptionArea> listAll=areaService.listAll().orElse(new ArrayList<>());
        List<AreaView> list=new ArrayList<>();
        listAll.forEach(item->{
            AreaView view=new AreaView();
            view.setId(item.getId());
            view.setName(item.getName());
            view.setPrefix(item.getPrefix());
            view.setLoudspeaker_times(item.getLoudspeaker_times());
            list.add(view);
        });
        return new ResponseEntity<List<AreaView>>(list, HttpStatus.OK);
    }



}
