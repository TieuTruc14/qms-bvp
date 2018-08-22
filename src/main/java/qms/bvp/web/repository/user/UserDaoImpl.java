package qms.bvp.web.repository.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/22/2018.
 */
@Repository
public class UserDaoImpl implements UserDao {
    private Logger logger= LogManager.getLogger(UserDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page,String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        predicates.add(cb.notEqual(root.get("deleted"),true));
        if(StringUtils.isNotBlank(username)){
            predicates.add(cb.like(root.get("username"),"%"+username+"%"));
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<User> rootCount = criteriaQuery.from(User.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if(rowCount==null || rowCount.longValue()==0){
            return Optional.ofNullable(page);
        }
        q.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(root.get("username")));
        List<User> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list==null) Optional.ofNullable(page);
        page.setItems(list);
        page.setRowCount(rowCount);
        return Optional.ofNullable(page);
    }
}
