package qms.bvp.web.service.category;

import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionAreaService {
    Optional<PagingResult> page(PagingResult page);
    Optional<List<ReceptionArea>> listAll();
    Optional<Hashtable<Integer,Integer>> initAreaWithOrderNumber();
}
