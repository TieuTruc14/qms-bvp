package qms.bvp.web.service.category;

import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionDoor;
import qms.bvp.model.swap.Door;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
public interface ReceptionDoorService {
    Optional<PagingResult> page(PagingResult page);
    Optional<List<ReceptionDoor>> listAll();
    Optional<List<ReceptionDoor>> listAllActive();
    Hashtable<Integer,ReceptionDoor> initReceptionDoor();
    Optional<Byte> add(Door item);
    Optional<Byte> edit(Door item);
    Optional<Byte> delete(Integer id);
    Optional<ReceptionDoor> get(Integer id);
    Optional<ReceptionDoor> getNotDelete(Integer id);
    void deleteAllDoorOfArea(Integer areaId, Long userId, Date date);

}
