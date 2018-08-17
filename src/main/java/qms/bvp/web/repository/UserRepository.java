package qms.bvp.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.User;

import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
}
