package qms.bvp.web.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.User;

import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User,Long>,UserDao {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    @Modifying(clearAutomatically=true)
    @Query("update User set password = ?1 where id = ?2")
    void changeMyPass(String pass,Long user_id);
}
