package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Admin on 8/9/2018.
 */
@Entity
@Table(name = "RECEPTION_DOOR")
@Data
public class ReceptionDoor implements Serializable{
    private static final long serialVersionUID = 7292070598000709541L;
    @Id
    @SequenceGenerator(name="RECEPTION_DOOR_SEQ", sequenceName="RECEPTION_DOOR_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_DOOR_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    @Column(name = "NAME",nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEPTION_AREA",nullable = false)
    private ReceptionArea reception_area;
    @Column(name = "RECEPTION_TYPE_VALUE",nullable = false)
    private Long reception_type_value;
    @Column(name = "DESCRIPTION")
    private String description;
    private transient Integer order_number_current;
    private transient Reception reception_current;
    private transient String prefix;
    private transient TreeSet<Byte> prioritys;
    private transient TreeSet<Long> tree_value;
    private transient List<Reception> receptions_miss=new ArrayList<>();
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

    public ReceptionArea getReception_area() {
        return reception_area;
    }

    public void setReception_area(ReceptionArea reception_area) {
        this.reception_area = reception_area;
    }

    public Long getReception_type_value() {
        return reception_type_value;
    }

    public void setReception_type_value(Long reception_type_value) {
        this.reception_type_value = reception_type_value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder_number_current() {
        return order_number_current;
    }

    public void setOrder_number_current(Integer order_number_current) {
        this.order_number_current = order_number_current;
    }

    public Reception getReception_current() {
        return reception_current;
    }

    public void setReception_current(Reception reception_current) {
        this.reception_current = reception_current;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public TreeSet<Byte> getPrioritys() {
        return prioritys;
    }

    public void setPrioritys(TreeSet<Byte> prioritys) {
        this.prioritys = prioritys;
    }

    public List<Reception> getReceptions_miss() {
        return receptions_miss;
    }

    public void setReceptions_miss(List<Reception> receptions_miss) {
        this.receptions_miss = receptions_miss;
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

    public TreeSet<Long> getTree_value() {
        return tree_value;
    }

    public void setTree_value(TreeSet<Long> tree_value) {
        this.tree_value = tree_value;
    }
}
