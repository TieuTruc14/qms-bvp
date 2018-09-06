package qms.bvp.model.swap;

import lombok.Getter;
import lombok.Setter;
import qms.bvp.model.Authority;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
@Getter
@Setter
public class AuthoritySwap {
    private Authority parent;
    private List<Authority> childrens;

}
