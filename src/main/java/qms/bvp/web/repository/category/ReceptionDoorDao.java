package qms.bvp.web.repository.category;

import qms.bvp.common.PagingResult;

import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionDoorDao {
    Optional<PagingResult> page(PagingResult page);
}
