package qms.bvp.web.repository.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionObjectType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Repository
public class ReceptionObjectTypeDaoImpl implements ReceptionObjectTypeDao {
    private Logger logger= LogManager.getLogger(ReceptionObjectTypeDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        Long count=(Long)entityManager.createQuery("select count(id) from ReceptionObjectType where deleted=false").getSingleResult();
        List<ReceptionObjectType> list=entityManager.createQuery("Select rea from ReceptionObjectType as rea where rea.deleted=false",ReceptionObjectType.class)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
}
