package qms.bvp.model;

import lombok.Data;
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
@Table(name = "USERS")
@Data
public class User implements Serializable,UserDetails {
    private static final long serialVersionUID = 3386482117850732087L;
    @Id
    @SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "USERNAME",nullable = false,unique = true)
    private String username;
    @Column(name = "PASSWORD",nullable = false ,length = 100)
    private String password;
    @Column(name = "FULLNAME")
    private String fullname;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "RECEPTION_DOOR")
    private Integer reception_door;
    @Column(name = "DISABLE",nullable = false,columnDefinition = "boolean default false")
    private Boolean disable;
    @Column(name = "DELETED",nullable = false,columnDefinition = "boolean default false")
    private Boolean deleted;
    @Column(name = "LAST_ACCESS_TIME")
    private Date last_access_time;
    @Column(name = "DATE_CREATED")
    private Date date_created;
    @Column(name = "USER_CREATED")
    private Long user_created;
    @Column(name = "DATE_UPDATED")
    private Date date_updated;
    @Column(name = "USER_UPDATED")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReception_door() {
        return reception_door;
    }

    public void setReception_door(Integer reception_door) {
        this.reception_door = reception_door;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getLast_access_time() {
        return last_access_time;
    }

    public void setLast_access_time(Date last_access_time) {
        this.last_access_time = last_access_time;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Long getUser_created() {
        return user_created;
    }

    public void setUser_created(Long user_created) {
        this.user_created = user_created;
    }

    public Date getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Date date_updated) {
        this.date_updated = date_updated;
    }

    public Long getUser_updated() {
        return user_updated;
    }

    public void setUser_updated(Long user_updated) {
        this.user_updated = user_updated;
    }

    public List<GrantedAuthority> getGrantedAuths() {
        return grantedAuths;
    }

    public void setGrantedAuths(List<GrantedAuthority> grantedAuths) {
        this.grantedAuths = grantedAuths;
    }
}
