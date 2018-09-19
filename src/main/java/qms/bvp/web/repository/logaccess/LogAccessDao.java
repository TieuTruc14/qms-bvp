package qms.bvp.web.repository.logaccess;

import qms.bvp.common.PagingResult;

import java.util.Optional;

public interface LogAccessDao {
    Optional<PagingResult> page(PagingResult page, String username);
}
