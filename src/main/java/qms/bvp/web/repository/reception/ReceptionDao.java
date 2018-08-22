package qms.bvp.web.repository.reception;

import qms.bvp.common.PagingResult;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by Admin on 8/22/2018.
 */
public interface ReceptionDao {
    Optional<PagingResult> page(PagingResult page);
}
