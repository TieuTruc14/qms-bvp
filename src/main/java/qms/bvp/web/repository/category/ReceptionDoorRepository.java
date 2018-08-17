package qms.bvp.web.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.ReceptionDoor;

import java.util.List;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
public interface ReceptionDoorRepository  extends JpaRepository<ReceptionDoor,Long>,ReceptionDoorDao {

}
