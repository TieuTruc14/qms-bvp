package qms.bvp.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 8/15/2018.
 */
public class Utils {
    public static boolean isActive(String navPath,HttpServletRequest request){
        String namespace=request.getRequestURI();
        if (namespace.startsWith(navPath)) {
            return true;
        }
        return false;
    }
}
