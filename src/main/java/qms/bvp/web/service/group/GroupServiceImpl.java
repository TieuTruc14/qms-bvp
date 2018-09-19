package qms.bvp.web.service.group;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.model.*;
import qms.bvp.model.swap.GroupSwap;
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
    public Optional<List<GroupRole>> loadAllGroup() {
        return groupDao.loadAllGroup();
    }

    @Override
    public Optional<List<GroupRole>> loadAllGroupOfUser(Long userId) {
        return groupDao.loadAllGroupOfUser(userId);
    }

    @Override
    public Optional<List<Authority>> loadAllAuthority() {
        return groupDao.loadAllAuthority();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> saveGroupView(GroupSwap item,String ip) {
        GroupRole groupRole =new GroupRole();
        groupRole.setGroup_name(item.getGroupName());
        groupRole.setDisable(false);
        groupRole.setDeleted(false);
        groupRole.setDescription(item.getDescription());
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        groupRole.setUser_created(user.getId());
        groupRole.setUser_updated(user.getId());
        groupRole.setDate_created(new Date());
        groupRole.setDate_updated(new Date());
        Integer groupId=groupDao.add(groupRole).orElse(Integer.valueOf(0));
        if(groupId.intValue()==0) return Optional.of(false);
        genAuthority(item,groupId,user.getId());

        return Optional.of(true);
    }

    @Override
    public Optional<GroupSwap> getGroupView(Integer id) {
        GroupRole groupRole =groupDao.get(id).orElse(new GroupRole());
        if(groupRole ==null || groupRole.getId()==null){
            return null;
        }
        GroupSwap item=new GroupSwap();
        item.setId(groupRole.getId());
        item.setGroupName(groupRole.getGroup_name());
        item.setDescription(groupRole.getDescription());
        List<GroupAuthority> groupAuthorities=groupDao.loadByGroupId(id).orElse(null);
        if(groupAuthorities!=null && groupAuthorities.size()>0){
            StringBuilder authoritiesString=new StringBuilder("");
            groupAuthorities.stream().forEach(g -> authoritiesString.append(g.getAuthority_id()+","));
            item.setListAuthority(authoritiesString.toString());
        }
        return Optional.ofNullable(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> editGroupView(GroupSwap item,String ip) {

        GroupRole groupRole = groupDao.get(item.getId()).orElse(null);
        if (groupRole == null) return Optional.of(false);

        groupRole.setGroup_name(item.getGroupName());
        groupRole.setDescription(item.getDescription());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        groupRole.setUser_updated(user.getId());
        groupRole.setDate_updated(new Date());
        Integer groupId = groupDao.edit(groupRole).orElse(Integer.valueOf(0));
        if (groupId.intValue() == 0) return Optional.of(false);
        genAuthority(item, groupId, user.getId());
        return Optional.of(true);
    }

    public void genAuthority(GroupSwap item, Integer groupId, Long userId){
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
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> addListGroupUser(List<GroupUser> items, Long userId,String ip) {
        groupDao.deleteListGroupOfUser(userId);
        return groupDao.addListGroupUser(items);
    }

    @Override
    public Optional<Boolean> deleteListGroupOfUser(Long userId,String ip) {
        return groupDao.deleteListGroupOfUser(userId);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        return groupDao.loadListAuthorityOfUserByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

