package qms.bvp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AUTHORITY")
public class Authority implements Serializable {
    private static final long serialVersionUID = 2894810169009008957L;
    @Id
    @SequenceGenerator(name="AUTHORITY_SEQ", sequenceName="AUTHORITY_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORITY_SEQ")
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "AUTHORITY",nullable = false)
    private String authority;

    @Column(name = "PARENT",nullable = false)
    private int parent;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORDER_ID")
    private int orderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
