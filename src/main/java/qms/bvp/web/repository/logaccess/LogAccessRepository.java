package qms.bvp.web.repository.logaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qms.bvp.model.LogAccess;
@Repository
public interface LogAccessRepository extends JpaRepository<LogAccess,Long>,LogAccessDao {

}
