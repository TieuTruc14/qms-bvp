package qms.bvp.web.repository.logaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qms.bvp.common.PagingResult;
import qms.bvp.model.LogAccess;

import java.util.Optional;

@Repository
public interface LogAccessRepository extends JpaRepository<LogAccess,Long>,LogAccessDao {

}
