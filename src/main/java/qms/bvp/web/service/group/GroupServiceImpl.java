package qms.bvp.web.service.group;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.model.*;
import qms.bvp.model.view.GroupView;
import qms.bvp.web.repository.group.GroupDao;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    private Logger logger= LogManager.getLogger(GroupServiceImpl.class);
    @Autowired
    GroupDao groupDao;
//    @Autowired
//    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(String name, PagingResult page) {
        return groupDao.page(name,page);
    }

    @Override
    public Optional<List<Group>> loadAllGroup() {
        return groupDao.loadAllGroup();
    }

    @Override
    public Optional<List<Group>> loadAllGroupOfUser(Long userId) {
        return groupDao.loadAllGroupOfUser(userId);
    }

    @Override
    public Optional<List<Authority>> loadAllAuthority() {
        return groupDao.loadAllAuthority();
    }


    @Override
    @Transactional
    public Optional<Boolean> saveGroupView(GroupView item) {
        Group group=new Group();
        group.setGroup_name(item.getGroupName());
        group.setDisable(false);
        group.setDeleted(false);
        group.setDescription(item.getDescription());
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setUser_created(user.getId());
        group.setUser_updated(user.getId());
        group.setDate_created(new Date());
        group.setDate_updated(new Date());
        Integer groupId=groupDao.add(group).orElse(Integer.valueOf(0));
        if(groupId.intValue()==0) return Optional.of(false);
        genAuthority(item,groupId,user.getId());

        return Optional.of(true);
    }

    @Override
    public Optional<GroupView> getGroupView(Integer id) {
        Group group=groupDao.get(id).orElse(new Group());
        if(group==null || group.getId()==null){
            return null;
        }
        GroupView item=new GroupView();
        item.setId(group.getId());
        item.setGroupName(group.getGroup_name());
        item.setDescription(group.getDescription());
        List<GroupAuthority> groupAuthorities=groupDao.loadByGroupId(id).orElse(null);
        if(groupAuthorities!=null && groupAuthorities.size()>0){
            StringBuilder authoritiesString=new StringBuilder("");
            groupAuthorities.stream().forEach(g -> authoritiesString.append(g.getAuthority_id()+","));
            item.setListAuthority(authoritiesString.toString());
        }
        return Optional.ofNullable(item);
    }

    @Override
    @Transactional
    public Optional<Boolean> editGroupView(GroupView item) {

        Group group = groupDao.get(item.getId()).orElse(null);
        if (group == null) return Optional.of(false);

        group.setGroup_name(item.getGroupName());
        group.setDescription(item.getDescription());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setUser_updated(user.getId());
        group.setDate_updated(new Date());
        Integer groupId = groupDao.edit(group).orElse(Integer.valueOf(0));
        if (groupId.intValue() == 0) return Optional.of(false);
        genAuthority(item, groupId, user.getId());
        return Optional.of(true);
    }

    public void genAuthority(GroupView item,Integer groupId,Long userId){
        List<String> authorities=new ArrayList<>(Arrays.asList(item.getListAuthority().split(",")));
        List<GroupAuthority> groupAuthorities=new ArrayList<>();
        if(authorities.size()>0){
            authorities.stream().forEach(au -> groupAuthorities.add(new GroupAuthority(groupId,Integer.valueOf(au),userId,new Date())));
            if(item.getId()!=null){
                groupDao.deleteGroupAuthority(item.getId()).orElse(false);
            }
            groupDao.addListGroupAuthority(groupAuthorities).orElse(false);
        }
    }

    @Override
    @Transactional
    public Optional<Boolean> addListGroupUser(List<GroupUser> items, Long userId) {
        groupDao.deleteListGroupOfUser(userId);
        return groupDao.addListGroupUser(items);
    }

    @Override
    public Optional<Boolean> deleteListGroupOfUser(Long userId) {
        return groupDao.deleteListGroupOfUser(userId);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        return groupDao.loadListAuthorityOfUserByUsername(username);
    }

    @Override
    @Transactional
    public Optional<Integer> deleteGroup(Integer id,String ip) {
        List<GroupUser> listGU=groupDao.loadAllGroupUserByGroupId(id).orElse(new ArrayList<>());
        if(listGU.size()>0){
            return Optional.of(2);
        }
        groupDao.deleteGroup(id);
        groupDao.deleteGroupAuthority(id);
//        logAccessService.addLog("Xóa nhóm quyền Id:"+id,"Nhóm quyền",ip);
        return Optional.of(1);
    }
}

