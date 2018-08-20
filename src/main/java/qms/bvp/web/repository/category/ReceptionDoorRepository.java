package qms.bvp.web.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
public interface ReceptionDoorRepository  extends JpaRepository<ReceptionDoor,Integer>,ReceptionDoorDao {
    @Query("select rd from ReceptionDoor rd where rd.deleted=false and rd.disable=false and rd.reception_area.id in (select ra.id from ReceptionArea ra where ra.deleted=false and ra.disable=false)")
    Optional<List<ReceptionDoor>> listAllActive();

    @Query("select rd from ReceptionDoor rd where rd.deleted=false and rd.reception_area.id in (select ra.id from ReceptionArea ra where ra.deleted=false and ra.disable=false)")
    Optional<List<ReceptionDoor>> listAllNotDeleted();

    @Query("Select rd from ReceptionDoor rd where rd.id=?1 and rd.deleted=false")
    Optional<ReceptionDoor> getNotDeleted(Integer id);

    @Query("update ReceptionDoor rd set rd.deleted=true,rd.user_updated=?2,rd.date_updated=?3 where rd.reception_area.id=?1")
    @Modifying
    void deleteAllOfArea(Integer areaId, Long userId, Date date);
}
