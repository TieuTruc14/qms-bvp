package qms.bvp.web.service.logaccess;


import qms.bvp.common.PagingResult;

import java.util.Optional;

public interface LogAccessService {
    Optional<PagingResult> page(PagingResult page, String username);
    Optional<PagingResult> getByUserId(PagingResult page,Long userId);
    Optional<Boolean> addLog(Byte action_type,String action, String module,String table,String object_id,String object_str, String ipClient);
    Optional<Boolean> addLogWithUserId(Long userId,Byte action_type,String action, String module,String table,String object_id,String object_str, String ipClient);
}
