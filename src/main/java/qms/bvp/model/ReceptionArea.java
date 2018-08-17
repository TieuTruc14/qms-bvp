package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 8/9/2018.
 */
@Entity
@Table(name = "RECEPTION_AREA")
@Data
public class ReceptionArea implements Serializable {
    private static final long serialVersionUID = 1722348804737317437L;
    @Id
    @SequenceGenerator(name="RECEPTION_AREA_SEQ", sequenceName="RECEPTION_AREA_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_AREA_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    @Column(name = "NAME",nullable = false)
    private String name;
    @Column(name = "PREFIX",nullable = false)
    private String prefix;
    @Column(name = "LOUDSPEAKER_TIMES",nullable = false)
    private Byte loudspeaker_times;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DISABLE",nullable = false,columnDefinition = "boolean default false")
    private Boolean disable;
    @Column(name = "DELETED",nullable = false,columnDefinition = "boolean default false")
    private Boolean deleted;
    @Column(name = "DATE_CREATED")
    private Date date_created;
    @Column(name = "USER_CREATED")
    private Long user_created;
    @Column(name = "DATE_UPDATED")
    private Date date_updated;
    @Column(name = "USER_UPDATED")
    private Long user_updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Byte getLoudspeaker_times() {
        return loudspeaker_times;
    }

    public void setLoudspeaker_times(Byte loudspeaker_times) {
        this.loudspeaker_times = loudspeaker_times;
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
