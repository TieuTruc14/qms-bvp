package qms.bvp.model.swap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Admin on 8/17/2018.
 */
@Getter
@Setter
public class AreaSwap {
    private Integer id;
    private String name;
    private String description;
    private String prefix;
    private Byte loudspeaker_times;
    private Boolean disable;

}
