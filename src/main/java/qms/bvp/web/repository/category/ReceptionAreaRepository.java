package qms.bvp.web.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.ReceptionArea;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
@Transactional
public interface ReceptionAreaRepository extends JpaRepository<ReceptionArea,Long>,ReceptionAreaDao {
    @Query(value = "select ra.id, (select max(re.order_number) from reception re where re.reception_area=ra.id) from reception_area ra",nativeQuery = true)
    Optional<List<Object[]>> getMaxOrderNumberByArea();
}
