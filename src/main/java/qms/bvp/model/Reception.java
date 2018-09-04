package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 09/08/2018.
 */
@Entity
@Table(name = "reception")
@Data
public class Reception implements Serializable {
    private static final long serialVersionUID = 8156914866552709243L;
    @Id
//    @SequenceGenerator(name="RECEPTION_SEQ", sequenceName="RECEPTION_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEPTION_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "reception_area",nullable = false)
    private Integer reception_area;
    @Column(name = "order_number",nullable = false)
    private Integer order_number;
    @Column(name = "code",nullable = false)
    private String code;
    @Column(name = "reception_type_value")
    private Long reception_type_value;
    @Column(name = "value",nullable = false,unique = true)
    private String value;
    @Column(name = "status",nullable = false,columnDefinition = "byte default 0")
    private Byte status;//0-chua kham, 1-dang kham,2-da kham,3-bo qua(miss)
    @Column(name = "reception_door")
    private Integer reception_door;
//    private transient List<Byte> prioritys;
    private transient Byte priority;
    @Column(name = "date_created")
    private Date date_created;
    @Column(name = "user_created")
    private Long user_created;
    @Column(name = "date_updated")
    private Date date_updated;
    @Column(name = "user_updated")
    private Long user_updated;

}
