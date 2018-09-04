package qms.bvp.model.view;

import lombok.Data;

/**
 * Created by Admin on 9/4/2018.
 */
@Data
public class UserView {
    private Long id;
    private String username;
    private String fullname;
    private String description;
    private Boolean disable;
}
