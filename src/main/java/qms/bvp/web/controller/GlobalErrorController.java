package qms.bvp.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Admin on 8/20/2018.
 */
//@Controller
//public class GlobalErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    public void error(HttpServletResponse response) throws IOException {
//        int status = response.getStatus();
//        switch (status) {
//            case 404:
//                response.sendRedirect("/404");
//                break;
//            case 401:
//                response.sendRedirect("/401");
//                break;
//            case 403:
//                response.sendRedirect("/403");
//                break;
//            case 500:
//                response.sendRedirect("/500");
//                break;
//            default:
//                response.sendRedirect("/404");
//        }
//
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//}
