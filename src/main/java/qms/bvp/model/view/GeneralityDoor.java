package qms.bvp.model.view;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by Admin on 8/16/2018.
 */
public class GeneralityDoor {
    private Integer id;
    private String name;
    private String area_name;
    private Long reception_type_value;
    private Integer order_number_current;
    private String code;
    private List<String> receptions_miss;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public Long getReception_type_value() {
        return reception_type_value;
    }

    public void setReception_type_value(Long reception_type_value) {
        this.reception_type_value = reception_type_value;
    }

    public Integer getOrder_number_current() {
        return order_number_current;
    }

    public void setOrder_number_current(Integer order_number_current) {
        this.order_number_current = order_number_current;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getReceptions_miss() {
        return receptions_miss;
    }

    public void setReceptions_miss(List<String> receptions_miss) {
        this.receptions_miss = receptions_miss;
    }
}
