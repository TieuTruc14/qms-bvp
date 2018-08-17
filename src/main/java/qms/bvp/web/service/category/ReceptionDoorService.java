package qms.bvp.web.service.category;

import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionDoor;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionDoorService {
    Optional<PagingResult> page(PagingResult page);
    Optional<List<ReceptionDoor>> listAll();
    Hashtable<Integer,ReceptionDoor> initReceptionDoor();

}
