package qms.bvp.config;

public class ConstantAuthor {
    public class User{ //nguoi dung admin
        public static final String view="ROLE_SYSTEM_USER_VIEW";
        public static final String add="ROLE_SYSTEM_USER_ADD";
        public static final String edit="ROLE_SYSTEM_USER_EDIT";
        public static final String author="ROLE_SYSTEM_USER_AUTHORITY";
        public static final String delete="ROLE_SYSTEM_USER_DELETE";
    }
    public class Group{ // nhom quyen
        public static final String view="ROLE_SYSTEM_GROUP_VIEW";
        public static final String add="ROLE_SYSTEM_GROUP_ADD";
        public static final String edit="ROLE_SYSTEM_GROUP_EDIT";
        public static final String delete="ROLE_SYSTEM_GROUP_DELETE";
    }
    public class Log{//log
        public static final String view="ROLE_SYSTEM_LOG_VIEW";
    }
}
