package qms.bvp.validator.category;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qms.bvp.common.Utils;
import qms.bvp.model.view.Door;

/**
 * Created by Admin on 8/20/2018.
 */
@Component
public class DoorValidator  implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return Door.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Door item = (Door) obj;
        Utils.trimAllFieldOfObject(item);
        if (StringUtils.isBlank(item.getName()) || item.getArea() == null || item.getArea().intValue() == 0 || item.getReception_type_value() == null
                || item.getReception_type_value().intValue() == 0) {
            errors.rejectValue("name", "error");
        }
    }
}
