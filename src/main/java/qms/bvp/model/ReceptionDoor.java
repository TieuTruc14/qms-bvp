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
@Table(name = "reception_door")
@Data
public class ReceptionDoor implements Serializable{
    private static final long serialVersionUID = 7292070598000709541L;
    @Id
//    @SequenceGenerator(name="RECEPTION_DOOR_SEQ", sequenceName="RECEPTION_DOOR_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_DOOR_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
    @Column(name = "name",nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reception_area",nullable = false)
    private ReceptionArea reception_area;
    @Column(name = "reception_type_value",nullable = false)
    private Long reception_type_value;
    @Column(name = "description")
    private String description;
    private transient Integer order_number_current;
    private transient Reception reception_current;
    private transient String prefix;
    private transient TreeSet<Byte> prioritys;
    private transient TreeSet<Long> tree_value;
    private transient List<Reception> receptions_miss=new ArrayList<>();
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
