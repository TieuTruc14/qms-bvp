package qms.bvp.web.repository.group;

import qms.bvp.common.PagingResult;
import qms.bvp.model.Authority;
import qms.bvp.model.GroupAuthority;
import qms.bvp.model.GroupRole;
import qms.bvp.model.GroupUser;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface GroupDao {
    Optional<PagingResult> page(String name, PagingResult page);
    Optional<Integer> add(GroupRole item);
    Optional<GroupRole> get(Integer id);
    Optional<List<GroupRole>> loadAllGroup();
    Optional<List<GroupRole>> loadAllGroupOfUser(Long userId);
    Optional<List<GroupUser>> loadAllGroupUserByGroupId(Integer groupId);
    Optional<Integer> edit(GroupRole item);
    /*AUTHORITY*/
    Optional<List<Authority>> loadAllAuthority();
    Optional<Boolean> addListGroupAuthority(List<GroupAuthority> items) ;
    /*Group authority*/
    Optional<List<GroupAuthority>> loadByGroupId(Integer groupId);
    Optional<Boolean> deleteGroupAuthority(Integer groupId);
    Optional<Boolean> deleteGroup(Integer id);
    /*GroupUser*/
    Optional<Boolean> addListGroupUser(List<GroupUser> items);
    Optional<Boolean> deleteListGroupOfUser(Long userId);
    /*Authority of User*/
    Optional<List<String>> loadListAuthorityOfUserByUsername(String username);
    Optional<List<String>> loadListAuthorityOfUserByUserId(Long userId);
}
