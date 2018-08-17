package qms.bvp.web.service.user;

import qms.bvp.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findUserByUsername(String username);
}
