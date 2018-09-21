package qms.bvp.web.repository.reception;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.common.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/22/2018.
 */
@Repository
public class ReceptionDaoImpl implements ReceptionDao {
    private Logger logger= LogManager.getLogger(ReceptionDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page,String code,Date from,Date to) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
//        Long count=(Long)entityManager.createQuery("select count(id) from Reception order by date_created desc ").getSingleResult();
//        List<Object[]> list=entityManager.createNativeQuery("Select re.id,re.code,re.status,re.date_created," +
//                "(select ra.name from reception_area ra where ra.id=re.reception_area) area_name," +
//                "(select rd.name from reception_door rd where rd.id=re.reception_door) door_name," +
//                "(select group_concat(ro.name SEPARATOR '; ') as names  from reception_object_type ro where (ro.value & re.reception_type_value)>0) type " +
//                "from reception re  order by re.date_created desc")
//                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        StringBuffer sqlCount=new StringBuffer("select count(id) from Reception re ");
        StringBuffer sqlSelect=new StringBuffer("Select re.id,re.code,re.status,re.date_created, " +
                "(select ra.name from reception_area ra where ra.id=re.reception_area) area_name, " +
                "(select rd.name from reception_door rd where rd.id=re.reception_door) door_name, " +
                "(select group_concat(ro.name SEPARATOR '; ') as names  from reception_object_type ro where (ro.value & re.reception_type_value)>0) type from reception re ");
        Query query = filterBuilderSingle(sqlSelect,true,code,from,to);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlCount,false,code,from,to);
        Long rowCount = (Long)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.of(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String code, Date from, Date to){

        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);

            if (StringUtils.isNotBlank(code)) {
                builder.and(QueryBuilder.LIKE, "re.code", "%"+code+"%");
            }
            if (from!=null) {
                builder.and(QueryBuilder.GE, "re.date_created", from);
            }
            if (to!=null) {
                builder.and(QueryBuilder.LE, "re.date_created", to);
            }

            builder.addOrder("re.date_created", QueryBuilder.DESC);

            if(order){
                result = builder.initNativeQuery(order);
            }else{
                result = builder.initQuery(order);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
