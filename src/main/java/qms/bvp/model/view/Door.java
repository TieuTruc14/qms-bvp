package qms.bvp.model.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Admin on 8/20/2018.
 */
@Getter
@Setter
public class Door {
    private Integer id;
    private String name;
    private Integer area;
    private Long reception_type_value;
    private String description;
    private Boolean disable;
}
