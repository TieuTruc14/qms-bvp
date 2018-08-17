package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "GROUP")
public class Group implements Serializable {
    private static final long serialVersionUID = -166399391710801760L;

    @Id
    @SequenceGenerator(name="GROUP_SEQ", sequenceName="GROUP_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_SEQ")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "GROUP_NAME",nullable = false)
    private String group_name;

    @Column(name = "description")
    private String description;

    @Column(name = "DISABLE",nullable = false)
    private Boolean disable;

    @Column(name = "DELETED",nullable = false)
    private Boolean deleted;

    @Column(name = "DATE_CREATED",nullable = false)
    private Date date_created;

    @Column(name = "USER_CREATED")
    private Long user_created;

    @Column(name = "DATE_UPDATED",nullable = false)
    private Date date_updated;

    @Column(name = "USER_UPDATED")
    private Long user_updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
