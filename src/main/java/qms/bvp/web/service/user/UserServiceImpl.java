package qms.bvp.web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.User;
import qms.bvp.web.repository.user.UserRepository;

import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page,String username) {
        return userRepository.page(page,username);
    }
}
