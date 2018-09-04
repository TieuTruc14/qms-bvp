package qms.bvp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 8/9/2018.
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable,UserDetails {
    private static final long serialVersionUID = 3386482117850732087L;
    @Id
//    @SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false ,length = 100)
    private String password;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "description")
    private String description;
    @Column(name = "reception_door")
    private Integer reception_door;
    @Column(name = "disable",nullable = false,columnDefinition = "boolean default false")
    private Boolean disable=false;
    @Column(name = "deleted",nullable = false,columnDefinition = "boolean default false")
    private Boolean deleted=false;
    @Column(name = "last_access_time")
    private Date last_access_time;
    @Column(name = "date_created")
    private Date date_created;
    @Column(name = "user_created")
    private Long user_created;
    @Column(name = "date_updated")
    private Date date_updated;
    @Column(name = "user_updated")
    private Long user_updated;

    private transient List<GrantedAuthority> grantedAuths;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
