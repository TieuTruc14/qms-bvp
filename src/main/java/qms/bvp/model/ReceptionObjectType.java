package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 8/12/2018.
 */
@Entity
@Table(name = "reception_object_type")
@Data
public class ReceptionObjectType implements Serializable {
    private static final long serialVersionUID = 4904274677098128773L;
    @Id
//    @SequenceGenerator(name="RECEPTION_OBJECT_TYPE_SEQ", sequenceName="RECEPTION_OBJECT_TYPE_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_OBJECT_TYPE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "code",nullable = false,unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "value",nullable = false,unique = true)
    private Long value;
    @Column(name = "priority")
    private Byte priority;
    @Column(name = "suffix")
    private String suffix;
    @Column(name = "description")
    private String description;
    @Column(name = "disable",nullable = false,columnDefinition = "boolean default false")
    private Boolean disable;
    @Column(name = "deleted",nullable = false,columnDefinition = "boolean default false")
    private Boolean deleted;
    @Column(name = "date_created")
    private Date date_created;
    @Column(name = "user_created")
    private Long user_created;
    @Column(name = "date_updated")
    private Date date_updated;
    @Column(name = "user_updated")
    private Long user_updated;

}
