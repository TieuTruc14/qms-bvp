package qms.bvp.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

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

    public static String getIpClient(HttpServletRequest request){
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    public static String trim(String str){
        if(StringUtils.isNotBlank(str)){
            str.trim();
        }
        return str;
    }

    public static void trimAllFieldOfObject(Object item) {
        if (item == null) {
            return;
        }
        Field[] fields = item.getClass().getDeclaredFields();
        if (fields == null) {
            return;
        }

        for (Field f : fields) {
            if (f.getType().isPrimitive()) {
                continue;
            }
            if (f.getType().equals(String.class)) {
                try {
                    f.setAccessible(true);
                    String value = (String) f.get(item);
                    f.set(item, org.apache.commons.lang3.StringUtils.trimToNull(value));
                } catch (IllegalAccessException e) {
                }

            }
        }
    }
}
