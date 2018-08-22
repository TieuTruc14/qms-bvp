package qms.bvp.web.repository.user;

import qms.bvp.common.PagingResult;

import java.util.Optional;

/**
 * Created by Admin on 8/22/2018.
 */
public interface UserDao {
    Optional<PagingResult> page(PagingResult page,String username);
}
