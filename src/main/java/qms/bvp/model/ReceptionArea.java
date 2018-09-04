package qms.bvp.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 8/9/2018.
 */
@Entity
@Table(name = "reception_area")
@Data
public class ReceptionArea implements Serializable {
    private static final long serialVersionUID = 1722348804737317437L;
    @Id
//    @SequenceGenerator(name="RECEPTION_AREA_SEQ", sequenceName="RECEPTION_AREA_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_AREA_SEQ")
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "prefix",nullable = false)
    private String prefix;
    @Column(name = "loudspeaker_times",nullable = false)
    private Byte loudspeaker_times;
    @Column(name = "description")
    private String description;
    @Column(name = "disable",columnDefinition = "boolean default false")
    private Boolean disable;
    @Column(name = "deleted",columnDefinition = "boolean default false")
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
