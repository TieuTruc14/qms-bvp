package qms.bvp.model.view;

/**
 * Created by Admin on 8/17/2018.
 */
public class DoorView {
    private Integer id;
    private String name;
    private String area_name;
    private String order_number;
    private String[] receptions_miss;

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

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String[] getReceptions_miss() {
        return receptions_miss;
    }

    public void setReceptions_miss(String[] receptions_miss) {
        this.receptions_miss = receptions_miss;
    }
}
