package qms.bvp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "GROUP_AUTHORITY")
@Data
public class GroupAuthority implements Serializable {
    private static final long serialVersionUID = -166399391710801760L;

    @Id
    @Column(name = "GROUP_ID", nullable = false)
    private Integer groupId;

    @Id
    @Column(name = "AUTHORITY", nullable = false)
    private Integer authority;

    @Column(name = "USER_CREATED",nullable = false)
    private Long user_created;

    @Column(name = "DATE_CREATED",nullable = false)
    private Date date_created;

    public GroupAuthority() {
    }

    public GroupAuthority(Integer groupId, Integer authority, Long user_created, Date date_created) {
        this.groupId = groupId;
        this.authority = authority;
        this.user_created=user_created;
        this.date_created=date_created;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Long getUser_created() {
        return user_created;
    }

    public void setUser_created(Long user_created) {
        this.user_created = user_created;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
}
