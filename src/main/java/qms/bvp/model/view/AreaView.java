package qms.bvp.model.view;

/**
 * Created by Admin on 8/17/2018.
 */
public class AreaView {
    private Integer id;
    private String name;
    private String prefix;
    private Byte loudspeaker_times;

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
}
