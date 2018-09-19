package qms.bvp.web.repository.logaccess;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.LogAccess;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LogAccessDaoImpl implements LogAccessDao {
    private Logger logger= LogManager.getLogger(LogAccessDaoImpl.class);
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page, String username) {
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<LogAccess> q = cb.createQuery(LogAccess.class);
            Root<LogAccess> root = q.from(LogAccess.class);
            List<Predicate> predicates = new ArrayList<Predicate>();
            int offset=0;
            if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
            if(StringUtils.isNotBlank(username)){
                predicates.add(cb.like(root.get("username"),"%"+username+"%"));
            }
            q.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(root.get("username")));
            List<LogAccess> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            if(list==null) Optional.ofNullable(page);
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
            Root<LogAccess> rootCount = criteriaQuery.from(LogAccess.class);
            criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
            Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
            if(rowCount==null || rowCount.longValue()==0){
                return Optional.ofNullable(page);
            }
            page.setItems(list);
            page.setRowCount(rowCount);
        }catch (Exception e){
            logger.error("Have error method page:"+e.getMessage());
        }
        return Optional.of(page);
    }

}
