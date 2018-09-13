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
@Table(name = "group_authority")
@Data
@AllArgsConstructor
public class GroupAuthority implements Serializable {
    private static final long serialVersionUID = -166399391710801760L;

    @Id
    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    @Id
    @Column(name = "authority_id", nullable = false)
    private Integer authority_id;

    @Column(name = "user_created",nullable = false)
    private Long user_created;

    @Column(name = "date_created",nullable = false)
    private Date date_created;

    public GroupAuthority(){}

//    public GroupAuthority(Integer groupId, Integer authority, Long user_created, Date genDate) {
//        this.groupId = groupId;
//        this.authority_id = authority;
//        this.user_created = user_created;
//        this.date_created = genDate;
//    }
}
