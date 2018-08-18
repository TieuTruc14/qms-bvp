package qms.bvp.validator.category;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qms.bvp.common.Utils;
import qms.bvp.model.view.AreaView;

/**
 * Created by Admin on 8/18/2018.
 */
@Component
public class AreaViewValidator  implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return AreaView.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AreaView item = (AreaView) obj;
        Utils.trimAllFieldOfObject(item);
        if(StringUtils.isBlank(item.getName()) || StringUtils.isBlank(item.getName()) || item.getLoudspeaker_times()==null
                || item.getLoudspeaker_times().intValue()==0 || StringUtils.isBlank(item.getPrefix())){
            errors.rejectValue("name", "error");
        }
    }
}
