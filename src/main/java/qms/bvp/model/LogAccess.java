package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "LOG_ACCESS")
@Data
public class LogAccess implements Serializable{
    private static final long serialVersionUID = 1451508189162183268L;

    @Id
    @SequenceGenerator(name="LOG_ACCESS_SEQ", sequenceName="LOG_ACCESS_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ACCESS_SEQ")
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "USER_ID",nullable = false)
    private Long userId;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "IP",length = 100)
    private String ip;

    @Column(name = "ACTIONS")
    private String actions;

    @Column(name = "DATE_CREATED",nullable = false)
    private Date date_created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
}
