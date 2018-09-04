package qms.bvp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "group_user")
@Data
@AllArgsConstructor
public class GroupUser implements Serializable {
    private static final long serialVersionUID = -6175963269564113777L;

    @Id
    @Column(name = "group_id", nullable = false)
    private Integer group_id;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "USER_CREATED",nullable = false)
    private Long user_created;

    @Column(name = "DATE_CREATED",nullable = false)
    private Date date_created;

}
