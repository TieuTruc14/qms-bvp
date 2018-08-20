package qms.bvp.web.service.category;

import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.view.AreaView;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionAreaService {
    Optional<PagingResult> page(PagingResult page);
    Optional<List<ReceptionArea>> listAll();
    Optional<List<ReceptionArea>> listAllActive();
    Optional<Hashtable<Integer,Integer>> initAreaWithOrderNumber(List<ReceptionArea> listAll);

    Optional<List<ReceptionArea>> getByNameOrPrefix(String name,String prefix);
    Optional<Boolean> checkAreaByNameOrPrefix(String name,String prefix);
    Optional<Byte> add(AreaView item);
    Optional<Byte> edit(AreaView item);
    Optional<Byte> delete(Integer id);
}
