package qms.bvp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "GROUP_USER")
@Data
public class GroupUser implements Serializable {
    private static final long serialVersionUID = -6175963269564113777L;

    @Id
    @Column(name = "GROUP_ID", nullable = false)
    private Integer groupId;

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USER_CREATED",nullable = false)
    private Long user_created;

    @Column(name = "DATE_CREATED",nullable = false)
    private Date date_created;

    public GroupUser() {
    }

    public GroupUser(Integer groupId, Long userId, Long user_created, Date date_created) {
        this.groupId = groupId;
        this.userId = userId;
        this.user_created = user_created;
        this.date_created = date_created;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
