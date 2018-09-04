package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@Data
public class Authority implements Serializable {
    private static final long serialVersionUID = 2894810169009008957L;
    @Id
//    @SequenceGenerator(name="AUTHORITY_SEQ", sequenceName="AUTHORITY_SEQ",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORITY_SEQ")
//    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "AUTHORITY",nullable = false)
    private String authority;

    @Column(name = "PARENT",nullable = false)
    private int parent;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORDER_ID")
    private int orderId;


}
