package qms.bvp.model.view;

/**
 * Created by Admin on 8/20/2018.
 */
public class Door {
    private Integer id;
    private String name;
    private Integer area;
    private Long reception_type_value;
    private String description;
    private Boolean disable;

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

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Long getReception_type_value() {
        return reception_type_value;
    }

    public void setReception_type_value(Long reception_type_value) {
        this.reception_type_value = reception_type_value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }
}
