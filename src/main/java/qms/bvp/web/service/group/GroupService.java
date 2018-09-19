package qms.bvp.web.service.group;

import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.model.Authority;
import qms.bvp.model.GroupRole;
import qms.bvp.model.GroupUser;
import qms.bvp.model.swap.GroupSwap;

import java.util.List;
import java.util.Optional;

@Transactional
public interface GroupService {
    Optional<PagingResult> page(String name, PagingResult page);
    Optional<List<GroupRole>> loadAllGroup();
    Optional<List<GroupRole>> loadAllGroupOfUser(Long userId);
    Optional<GroupSwap> getGroupView(Integer id);
    Optional<List<Authority>> loadAllAuthority();
    Optional<Boolean> saveGroupView(GroupSwap item,String ip);
    Optional<Boolean> editGroupView(GroupSwap item,String ip);

    Optional<Boolean> addListGroupUser(List<GroupUser> items, Long userId,String ip);
    Optional<Boolean> deleteListGroupOfUser(Long userId,String ip);

    Optional<List<String>> loadListAuthorityOfUserByUsername(String username);
    Optional<Integer> deleteGroup(Integer id, String ip);
}
