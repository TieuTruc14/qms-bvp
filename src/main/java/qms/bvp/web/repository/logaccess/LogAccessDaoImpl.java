package qms.bvp.web.repository.logaccess;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.common.QueryBuilder;
import qms.bvp.model.LogAccess;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public Optional<PagingResult> page(PagingResult page, String username) {
//        try{
//            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//            CriteriaQuery<LogAccess> q = cb.createQuery(LogAccess.class);
//            Root<LogAccess> root = q.from(LogAccess.class);
//            List<Predicate> predicates = new ArrayList<Predicate>();
//            int offset=0;
//            if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
//            if(StringUtils.isNotBlank(username)){
//                predicates.add(cb.like(root.get("username"),"%"+username+"%"));
//            }
//            q.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(cb.desc(root.get("date_created")));
//            List<LogAccess> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
//            if(list==null) Optional.ofNullable(page);
//            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
//            Root<LogAccess> rootCount = criteriaQuery.from(LogAccess.class);
//            criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
//            Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
//            if(rowCount==null || rowCount.longValue()==0){
//                return Optional.ofNullable(page);
//            }
//            page.setItems(list);
//            page.setRowCount(rowCount);
//        }catch (Exception e){
//            logger.error("Have error method page:"+e.getMessage());
//        }
//        return Optional.of(page);
//    }

    @Override
    public Optional<PagingResult> page(PagingResult page,String username) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        StringBuffer sqlBuffer = new StringBuffer("SELECT DISTINCT log.id,log.user_id,u.username,log.module,log.ip,log.action,log.date_created,log.table_name,log.object_id "+
                "from LogAccess log LEFT JOIN User u ON log.user_id=u.id ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(DISTINCT log.id) "+
                "from LogAccess log ");
        Query query = filterBuilderSingle(sqlBuffer,true,username);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && list.size()>0) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount,false,username);
        Long rowCount = (Long)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String username){
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.LIKE, "u.username","%"+ username+"%");
            }
            builder.addOrder("log.date_created", QueryBuilder.DESC);
            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<PagingResult> getByUserId(PagingResult page,Long userId) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        List<LogAccess> items=entityManager.createQuery("Select log from LogAccess log where log.user_id=:userId order by log.date_created desc ",LogAccess.class).setParameter("userId",userId)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        Long rowCount=(Long)entityManager.createQuery("Select count(log.id) from LogAccess log where log.user_id=:userId").setParameter("userId",userId).getSingleResult();
        if(items!=null && items.size()>0){
            page.setItems(items);
        }
        if(rowCount!=null && rowCount.longValue()>0){
            page.setRowCount(rowCount.longValue());
        }
        return Optional.of(page);
    }

}
