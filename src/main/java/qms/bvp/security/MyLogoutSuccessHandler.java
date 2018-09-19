package qms.bvp.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import qms.bvp.config.Constants;
import qms.bvp.model.User;
import qms.bvp.web.service.logaccess.LogAccessService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    LogAccessService logAccessService;
    private Logger logger= LogManager.getLogger(MyLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                User user=(User)authentication.getPrincipal();
                if(user!=null && user.getId()!=null){
                    WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                    String remoteIpAddress = webAuthenticationDetails.getRemoteAddress();
                    logAccessService.addLogWithUserId(user.getId(),Constants.ActionLog.None,"Đăng xuất","Hệ thống",null,null,null,remoteIpAddress);
                }
                httpServletRequest.getSession().invalidate();
            } catch (Exception e) {
                logger.error("Have an error onLogoutSuccess:"+e.getMessage());
            }
        }
        String urlRedirect=httpServletRequest.getContextPath()+"/login";
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        //redirect to login
        httpServletResponse.sendRedirect(urlRedirect);
    }

}
