package qms.bvp.model.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Admin on 8/17/2018.
 */
@Getter
@Setter
public class DoorView {
    private Integer id;
    private String name;
    private String area_name;
    private String order_number;
    private String[] receptions_miss;

}
