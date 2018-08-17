package qms.bvp.web.service.category;

import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionObjectType;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionObjectTypeService {
    Optional<PagingResult> page(PagingResult page);
    Optional<List<ReceptionObjectType>> listAll();
}
