package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "log_access")
@Data
public class LogAccess implements Serializable{
    private static final long serialVersionUID = 1451508189162183268L;

    @Id
//    @SequenceGenerator(name="LOG_ACCESS_SEQ", sequenceName="LOG_ACCESS_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ACCESS_SEQ")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id",nullable = false)
    private Long user_id;

    @Column(name = "module")
    private String module;

    @Column(name = "ip",length = 100)
    private String ip;

    @Column(name = "action",nullable = false)
    private String action;

    @Column(name = "date_created",nullable = false)
    private Date date_created;

    @Column(name = "table_name",length = 64)
    private String table_name;

    @Column(name = "object_id",length = 64)
    private String object_id;

    @Column(name = "object_str")
    @Lob
    private String object_str;

}
