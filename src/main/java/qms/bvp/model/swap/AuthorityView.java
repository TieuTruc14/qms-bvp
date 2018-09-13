package qms.bvp.model.swap;

import lombok.Data;
import qms.bvp.model.Authority;

import java.util.List;

@Data
public class AuthorityView {
    private Authority parent;
    private List<Authority> childrens;
}
