package qms.bvp.web.service.logaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.LogAccess;
import qms.bvp.model.User;
import qms.bvp.web.repository.logaccess.LogAccessRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class LogAccessServiceImpl implements LogAccessService {

    @Autowired
    LogAccessRepository logAccessRepository;

    @Override
    public Optional<PagingResult> page(PagingResult page, String username) {
        return Optional.empty();
    }

    @Override
    public Optional<PagingResult> getByUserId(PagingResult page, Long userId) {

        return Optional.empty();
    }

    @Override
    public Optional<Boolean> addLog(Byte action_type,String action, String module,String table,String object_id,String object_str, String ipClient) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LogAccess log =new LogAccess();
        log.setAction_type(action_type);
        log.setAction(action);
        log.setModule(module);
        log.setUser_id(user.getId());
        log.setIp(ipClient);
        log.setDate_created(new Date());
        log.setTable_name(table);
        log.setObject_id(object_id);
        log.setObject_str(object_str);
        logAccessRepository.save(log);
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> addLogWithUserId(Long userId,Byte action_type, String action, String module, String table, String object_id, String object_str, String ipClient) {
        LogAccess log =new LogAccess();
        log.setAction_type(action_type);
        log.setAction(action);
        log.setModule(module);
        log.setUser_id(userId);
        log.setIp(ipClient);
        log.setDate_created(new Date());
        log.setObject_id(object_id);
        log.setTable_name(table);
        log.setObject_str(object_str);
        logAccessRepository.save(log);
        return Optional.of(true);
    }
}
