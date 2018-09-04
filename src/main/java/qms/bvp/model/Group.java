package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "group")
@Data
public class Group implements Serializable {
    private static final long serialVersionUID = -166399391710801760L;

    @Id
//    @SequenceGenerator(name="GROUP_SEQ", sequenceName="GROUP_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "group_name",nullable = false)
    private String group_name;

    @Column(name = "description")
    private String description;

    @Column(name = "disable",nullable = false)
    private Boolean disable;

    @Column(name = "deleted",nullable = false)
    private Boolean deleted;

    @Column(name = "date_created",nullable = false)
    private Date date_created;

    @Column(name = "user_created")
    private Long user_created;

    @Column(name = "date_updated",nullable = false)
    private Date date_updated;

    @Column(name = "user_updated")
    private Long user_updated;

}
