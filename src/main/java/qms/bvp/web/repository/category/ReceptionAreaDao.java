package qms.bvp.web.repository.category;

import qms.bvp.common.PagingResult;

import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionAreaDao {
    Optional<PagingResult> page(PagingResult page);
}
