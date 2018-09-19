package qms.bvp.web.repository.group;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.model.Authority;
import qms.bvp.model.GroupAuthority;
import qms.bvp.model.GroupRole;
import qms.bvp.model.GroupUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GroupDaoImpl implements GroupDao {

    private Logger logger= LogManager.getLogger(GroupDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(String name, PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        if(StringUtils.isBlank(name)) name="";
        Long count=(Long)entityManager.createQuery("select count(gr.id) from qms.bvp.model.GroupRole gr where gr.group_name like :name").setParameter("name","%"+name+"%").getSingleResult();
        List<GroupRole> list=entityManager.createQuery("select gr from qms.bvp.model.GroupRole gr where gr.group_name like :name",GroupRole.class).setParameter("name","%"+name+"%")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }

    @Override
    public Optional<Integer> add(GroupRole item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    @Override
    public Optional<GroupRole> get(Integer id) {
        GroupRole item=entityManager.find(GroupRole.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<List<GroupRole>> loadAllGroup() {
        List<GroupRole> items=entityManager.createQuery("Select gr from GroupRole gr where gr.disable=false and gr.deleted=false",GroupRole.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<GroupRole>> loadAllGroupOfUser(Long userId) {
        List<GroupRole> items=entityManager.createQuery("SELECT gr FROM GroupRole gr JOIN GroupUser gu ON gr.id=gu.group_id where gu.user_id=:userId and gr.disable=false and gr.deleted=false",GroupRole.class)
                .setParameter("userId",userId).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<GroupUser>> loadAllGroupUserByGroupId(Integer groupId) {
        List<GroupUser> items=entityManager.createQuery("Select gu from GroupUser gu where gu.groupId=:groupId",GroupUser.class).setParameter("groupId",groupId).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<Integer> edit(GroupRole item)  {
        entityManager.merge(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    /*AUTHORITY*/
    @Override
    public Optional<List<Authority>> loadAllAuthority() {
        List<Authority> list=entityManager.createQuery("select a from Authority a order by a.order_number asc",Authority.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Boolean> addListGroupAuthority(List<GroupAuthority> items){
        items.stream().forEach(item->entityManager.persist(item));
        entityManager.flush();
        return Optional.of(true);
    }

    /*GROUP AUTHORITY*/

    @Override
    public Optional<List<GroupAuthority>> loadByGroupId(Integer groupId) {
        List<GroupAuthority> list=entityManager.createQuery("select a from GroupAuthority a WHERE a.groupId=:groupId",GroupAuthority.class).setParameter("groupId",groupId).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Boolean> deleteGroupAuthority(Integer groupId) {
        Query query=entityManager.createQuery("delete from GroupAuthority a WHERE a.groupId=:groupId").setParameter("groupId",groupId);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> deleteGroup(Integer id) {
        Query query=entityManager.createQuery("delete from GroupRole gr where gr.id=:id").setParameter("id",id);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> addListGroupUser(List<GroupUser> items) {
        if(items.size()>0){
            items.stream().forEach(item->entityManager.persist(item));
            entityManager.flush();
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    @Override
    public Optional<Boolean> deleteListGroupOfUser(Long userId) {
        Query query=entityManager.createQuery("delete from GroupUser gu where gu.user_id=:userId").setParameter("userId",userId);
        query.executeUpdate();
        return Optional.of(true);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        List<String> items=entityManager.createQuery("SELECT au.authority FROM Authority au JOIN GroupAuthority ga ON au.id=ga.authority_id JOIN GroupRole gr ON gr.id=ga.group_id JOIN GroupUser gu ON gr.id=gu.group_id JOIN User us ON us.id=gu.user_id WHERE us.username=:username")
                .setParameter("username",username).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUserId(Long userId) {
        List<String> items=entityManager.createQuery("SELECT au.authority FROM Authority au JOIN GroupAuthority ga ON au.id=ga.authority_id JOIN GroupRole gr ON gr.id=ga.groupId JOIN GroupUser gu ON gr.id=gu.group_id JOIN User us ON us.id=gu.user_id WHERE us.id=:userId")
                .setParameter("userId",userId).getResultList();
        return Optional.ofNullable(items);
    }
}
