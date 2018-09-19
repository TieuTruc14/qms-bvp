package qms.bvp.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import qms.bvp.config.Constants;
import qms.bvp.model.User;
import qms.bvp.web.service.logaccess.LogAccessService;

@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {
    @Autowired
    LogAccessService logAccessService;
    private Logger logger= LogManager.getLogger(SessionEndedListener.class);

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        for (SecurityContext securityContext : event.getSecurityContexts())
        {
            try{
                Authentication authentication = securityContext.getAuthentication();
                User user = (User) authentication.getPrincipal();
                WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                String remoteIpAddress = webAuthenticationDetails.getRemoteAddress();
                logAccessService.addLogWithUserId(user.getId(),Constants.ActionLog.None,"Đăng xuất sessionTimeout","Hệ thống",null,null,null,remoteIpAddress);
            }catch (Exception e){
                logger.error("Have an error when end session Timeout:"+e.getMessage());
            }

        }
    }
}
