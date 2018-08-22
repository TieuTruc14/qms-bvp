package qms.bvp.web.service.reception;

import qms.bvp.common.PagingResult;
import qms.bvp.model.Reception;
import qms.bvp.model.ReceptionDoor;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/15/2018.
 */
public interface ReceptionService {
    Reception createReception(Reception item);
    Optional<List<Reception>> getAllReceptionWaitInDB();
    Optional<List<Reception>> getReceptionCurrentInDoor();
    Optional<List<Reception>> getAllReceptionMissOfDoor();
    Reception getReceptionForDoor(ReceptionDoor item);
    Optional<Boolean> confirmReceptionOfDoor(Integer doorId,byte status);
    Optional<Long> countReceptionByDoor(Integer doorId);
    Optional<Long> countReceptionByArea(Integer doorId);
    Optional<PagingResult> page(PagingResult page);
}
