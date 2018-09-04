package qms.bvp.model.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by Admin on 8/16/2018.
 */
@Getter
@Setter
public class GeneralityDoor {
    private Integer id;
    private String name;
    private String area_name;
    private Long reception_type_value;
    private Integer order_number_current;
    private String code;
    private List<String> receptions_miss;

}
