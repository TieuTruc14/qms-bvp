package qms.bvp.web.repository.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Repository
public class ReceptionAreaDaoImpl implements ReceptionAreaDao {
    private Logger logger= LogManager.getLogger(ReceptionAreaDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        Long count=(Long)entityManager.createQuery("select count(id) from ReceptionArea").getSingleResult();
        List<ReceptionArea> list=entityManager.createQuery("Select rea from ReceptionArea as rea ",ReceptionArea.class)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
}
