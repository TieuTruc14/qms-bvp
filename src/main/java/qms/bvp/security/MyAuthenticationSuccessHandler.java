package qms.bvp.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import qms.bvp.common.Utils;
import qms.bvp.config.Constants;
import qms.bvp.model.User;
import qms.bvp.web.service.logaccess.LogAccessService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    LogAccessService logAccessService;
    private Logger logger= LogManager.getLogger(MyAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            httpServletRequest.getSession().setAttribute("userId",user.getId());
            String ip= Utils.getIpClient(httpServletRequest);
            httpServletRequest.getSession().setAttribute("ipClient",ip);
            logAccessService.addLogWithUserId(user.getId(),Constants.ActionLog.None,"Đăng nhập","Hệ thống",null,null,null,ip);
        }catch (Exception e){
            logger.error("Have an error onAuthenticationSuccess:"+e.getMessage());
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        String urlRedirect=httpServletRequest.getContextPath()+"/";
        httpServletResponse.sendRedirect(urlRedirect);
    }
}
