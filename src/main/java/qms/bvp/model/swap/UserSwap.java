package qms.bvp.model.swap;

import lombok.Data;

/**
 * Created by Admin on 9/4/2018.
 */
@Data
public class UserSwap {
    private Long id;
    private String username;
    private String fullname;
    private String description;
    private Boolean disable;
}
