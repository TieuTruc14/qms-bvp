package qms.bvp.web.repository.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionDoor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Repository
public class ReceptionDoorDaoImpl implements ReceptionDoorDao {
    private Logger logger= LogManager.getLogger(ReceptionDoorDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        Long count=(Long)entityManager.createQuery("select count(id) from ReceptionDoor where deleted=false and disable=false").getSingleResult();
        List<Object[]> list=entityManager.createNativeQuery("Select rd.id,rd.name,(select ra.name from reception_area ra where ra.id=rd.reception_area) area_name,(select listagg(TO_CHAR(ro.name),'; ') within group (order by ro.id) as names  from reception_object_type ro where BITAND(ro.value,rd.reception_type_value)>0) type,rd.description,rd.disable,rd.reception_area,rd.reception_type_value from Reception_Door rd where rd.deleted=0 and rd.disable=0 order by rd.reception_area")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
}
