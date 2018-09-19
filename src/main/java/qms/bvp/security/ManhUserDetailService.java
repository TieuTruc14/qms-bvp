package qms.bvp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.model.User;
import qms.bvp.web.service.group.GroupService;
import qms.bvp.web.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManhUserDetailService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userService.findUserByUsername(username).orElse(null);
        if (user != null) {
            List<GrantedAuthority> lstAuths = new ArrayList<GrantedAuthority>();
            List<String> list=groupService.loadListAuthorityOfUserByUserId(user.getId()).orElse(new ArrayList<>());
            if(list!=null && list.size()>0){
                for(String role:list){
                    lstAuths.add(new SimpleGrantedAuthority(role));
                }
            }
            user.setGrantedAuths(lstAuths);
        }else{
            throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        }
        return user;
    }
}
