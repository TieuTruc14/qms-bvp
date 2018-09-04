package qms.bvp.web.repository.reception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Optional<PagingResult> page(PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        Long count=(Long)entityManager.createQuery("select count(id) from Reception order by date_created desc ").getSingleResult();
        List<Object[]> list=entityManager.createNativeQuery("Select re.id,re.code,re.status,re.date_created," +
                "(select ra.name from reception_area ra where ra.id=re.reception_area) area_name," +
                "(select rd.name from reception_door rd where rd.id=re.reception_door) door_name," +
                "(select group_concat(ro.name SEPARATOR '; ') as names  from reception_object_type ro where (ro.value & re.reception_type_value)>0) type " +
                "from Reception re  order by re.date_created desc")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
}
