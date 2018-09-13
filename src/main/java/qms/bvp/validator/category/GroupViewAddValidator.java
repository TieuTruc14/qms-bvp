package qms.bvp.validator.category;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qms.bvp.common.Utils;
import qms.bvp.model.swap.GroupSwap;

@Component
public class GroupViewAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return GroupSwap.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        GroupSwap item = (GroupSwap) obj;
        Utils.trimAllFieldOfObject(item);
        if(StringUtils.isBlank(item.getGroupName()) || item.getGroupName().length()>100){
            errors.rejectValue("groupName", "Không được để trống, độ dài không quá 100 ký tự");
        }

    }
}
