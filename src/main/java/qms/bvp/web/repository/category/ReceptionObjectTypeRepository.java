package qms.bvp.web.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.ReceptionObjectType;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Repository
public interface ReceptionObjectTypeRepository extends JpaRepository<ReceptionObjectType,Long>,ReceptionObjectTypeDao {
    @Query("select rd from ReceptionObjectType rd where rd.deleted=false and rd.disable=false")
    Optional<List<ReceptionObjectType>> listAllActive();

    @Query("select rd from ReceptionObjectType rd where rd.deleted=false")
    Optional<List<ReceptionObjectType>> listAllNotDeleted();

}
