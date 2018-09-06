package qms.bvp.web.service.group;

import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.model.Authority;
import qms.bvp.model.Group;
import qms.bvp.model.GroupUser;
import qms.bvp.model.swap.GroupSwap;

import java.util.List;
import java.util.Optional;

@Transactional
public interface GroupService {
    Optional<PagingResult> page(String name, PagingResult page);
    Optional<List<Group>> loadAllGroup();
    Optional<List<Group>> loadAllGroupOfUser(Long userId);
    Optional<GroupSwap> getGroupView(Integer id);
    Optional<List<Authority>> loadAllAuthority();
    Optional<Boolean> saveGroupView(GroupSwap item);
    Optional<Boolean> editGroupView(GroupSwap item);

    Optional<Boolean> addListGroupUser(List<GroupUser> items, Long userId);
    Optional<Boolean> deleteListGroupOfUser(Long userId);

    Optional<List<String>> loadListAuthorityOfUserByUsername(String username);
    Optional<Integer> deleteGroup(Integer id, String ip);
}
