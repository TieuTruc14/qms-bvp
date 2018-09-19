package qms.bvp.web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.PagingResult;
import qms.bvp.config.Constants;
import qms.bvp.model.User;
import qms.bvp.web.repository.user.UserRepository;
import qms.bvp.web.service.logaccess.LogAccessService;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 8/10/2018.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    LogAccessService logAccessService;
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

    @Override
    public boolean checkUserByUsername(String username) {
        User item=findUserByUsername(username).orElse(null);
        if(item!=null) return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Byte> add(User item, String ip) {
        boolean check=checkUserByUsername(item.getUsername());
        if(check) return Optional.of(Byte.valueOf("3"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setPassword(encoder.encode(item.getPassword()));
        item.setDate_created(new Date());
        item.setUser_created(user.getId());
//        item.setDeleted(false);
//        item.setDisable(false);
        userRepository.save(item);
        logAccessService.addLog(Constants.ActionLog.Add,"Thêm người dùng","Quản trị hệ thống",Constants.TableName.ReceptionArea,item.getId().toString(),item.toString(),ip);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Byte> edit(Long id,Boolean disable,String fullname,String description, String ip) {
        User itemDB=userRepository.findUserById(id).orElse(null);
        if(itemDB==null) return Optional.of(Byte.valueOf("3"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemDB.setFullname(fullname);
        itemDB.setDisable(disable);
        itemDB.setDescription(description);
        itemDB.setDate_updated(new Date());
        itemDB.setUser_updated(user.getId());
        userRepository.save(itemDB);
        logAccessService.addLog(Constants.ActionLog.Edit,"Sửa người dùng","Quản trị hệ thống",Constants.TableName.ReceptionArea,itemDB.getId().toString(),itemDB.toString(),ip);
        return Optional.of(Byte.valueOf("1"));
    }

}
