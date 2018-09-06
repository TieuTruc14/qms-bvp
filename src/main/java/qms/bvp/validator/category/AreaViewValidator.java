package qms.bvp.validator.category;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qms.bvp.common.Utils;
import qms.bvp.model.swap.AreaSwap;

/**
 * Created by Admin on 8/18/2018.
 */
@Component
public class AreaViewValidator  implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return AreaSwap.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AreaSwap item = (AreaSwap) obj;
        Utils.trimAllFieldOfObject(item);
        if(StringUtils.isBlank(item.getName()) || item.getLoudspeaker_times()==null
                || item.getLoudspeaker_times().intValue()==0 || StringUtils.isBlank(item.getPrefix())){
            errors.rejectValue("name", "error");
        }
    }
}
