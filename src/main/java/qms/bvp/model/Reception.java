package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 09/08/2018.
 */
@Entity
@Table(name = "RECEPTION")
@Data
public class Reception implements Serializable {
    private static final long serialVersionUID = 8156914866552709243L;
    @Id
    @SequenceGenerator(name="RECEPTION_SEQ", sequenceName="RECEPTION_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_SEQ")
    private Long id;
    @Column(name = "RECEPTION_AREA",nullable = false)
    private Integer reception_area;
    @Column(name = "ORDER_NUMBER",nullable = false)
    private Integer order_number;
    @Column(name = "CODE",nullable = false)
    private String code;
    @Column(name = "RECEPTION_TYPE_VALUE")
    private Long reception_type_value;
    @Column(name = "ASSURANCE",nullable = false,columnDefinition = "boolean default false")
    private Boolean assurance;
    @Column(name = "STATUS",nullable = false,columnDefinition = "byte default 0")
    private Byte status;//0-chua kham, 1-dang kham,2-da kham,3-bo qua(miss)
    @Column(name = "RECEPTION_DOOR")
    private Integer reception_door;
    @Column(name = "PRIORITY",nullable = false)
    private Byte priority;
    @Column(name = "DATE_CREATED")
    private Date date_created;
    @Column(name = "USER_CREATED")
    private Long user_created;
    @Column(name = "DATE_UPDATED")
    private Date date_updated;
    @Column(name = "USER_UPDATED")
    private Long user_updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReception_area() {
        return reception_area;
    }

    public void setReception_area(Integer reception_area) {
        this.reception_area = reception_area;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getReception_type_value() {
        return reception_type_value;
    }

    public void setReception_type_value(Long reception_type_value) {
        this.reception_type_value = reception_type_value;
    }

    public Boolean getAssurance() {
        return assurance;
    }

    public void setAssurance(Boolean assurance) {
        this.assurance = assurance;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getReception_door() {
        return reception_door;
    }

    public void setReception_door(Integer reception_door) {
        this.reception_door = reception_door;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
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
