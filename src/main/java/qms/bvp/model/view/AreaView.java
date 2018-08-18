package qms.bvp.model.view;

/**
 * Created by Admin on 8/17/2018.
 */
public class AreaView {
    private Integer id;
    private String name;
    private String description;
    private String prefix;
    private Byte loudspeaker_times;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Byte getLoudspeaker_times() {
        return loudspeaker_times;
    }

    public void setLoudspeaker_times(Byte loudspeaker_times) {
        this.loudspeaker_times = loudspeaker_times;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }
}
