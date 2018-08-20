package qms.bvp.web.repository.reception;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import qms.bvp.model.Reception;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/15/2018.
 */
@Repository
public interface ReceptionRepository extends JpaRepository<Reception,Long> {
    @Query("Select re from Reception re where re.status=0 and (re.date_created between ?1 and ?2)")
    Optional<List<Reception>> listAllReceptionWait(Date from, Date to);

    @Query("Select re from Reception re where re.status=1 and (re.date_created between ?1 and ?2)")
    Optional<List<Reception>> getReceptionCurrentInDoor(Date from, Date to);

    @Query("Select re from Reception re where re.status=3 and (re.date_created between ?1 and ?2)")
    Optional<List<Reception>> getAllReceptionMissOfDoor(Date from, Date to);

    @Query("Select count(re.id) from Reception re where re.reception_door=?1")
    Optional<Long> countReceptionByDoor(Integer doorId);

    @Query("Select count(re.id) from Reception re where re.reception_area=?1")
    Optional<Long> countReceptionByArea(Integer areaId);
}
