package qms.bvp.web.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.ReceptionArea;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
@Transactional
public interface ReceptionAreaRepository extends JpaRepository<ReceptionArea,Integer>,ReceptionAreaDao {
    @Query(value = "select ra.id, (select max(re.order_number) from reception re where re.reception_area=ra.id and re.date_created BETWEEN ?1 and ?2) from reception_area ra",nativeQuery = true)
    Optional<List<Object[]>> getMaxOrderNumberByArea(Date from,Date to);

    @Query(value="select ra.* from reception_area ra where ra.deleted=false and ra.disable=false",nativeQuery = true)
    Optional<List<ReceptionArea>> listAllActive();

    @Query("select ra from ReceptionArea ra where ra.deleted=false")
    Optional<List<ReceptionArea>> listAllNotDeleted();

    @Query("select ra from ReceptionArea ra where ra.name like %:name% or ra.prefix like %:prefix%")
    Optional<List<ReceptionArea>> getByNameOrPrefix(@Param("name") String name,@Param("prefix") String prefix);
}
