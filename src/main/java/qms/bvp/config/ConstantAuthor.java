package qms.bvp.config;

public class ConstantAuthor {

    public class User{ //nguoi dung admin
        public static final String view="ROLE_USER_VIEW";
        public static final String add="ROLE_USER_ADD";
        public static final String edit="ROLE_USER_EDIT";
        public static final String author="ROLE_USER_AUTHORITY";
        public static final String delete="ROLE_USER_DELETE";
        public static final String grant="ROLE_USER_GRANT";
    }

    public class Log{//log
        public static final String view="ROLE_SYSTEM_HISTORY_VIEW";
    }

    public class Area{
        public static final String view="ROLE_AREA_VIEW";
        public static final String add="ROLE_AREA_ADD";
        public static final String edit="ROLE_AREA_EDIT";
        public static final String delete="ROLE_AREA_DELETE";
    }

    public class Door{
        public static final String view="ROLE_DOOR_VIEW";
        public static final String add="ROLE_DOOR_ADD";
        public static final String edit="ROLE_DOOR_EDIT";
        public static final String delete="ROLE_DOOR_DELETE";
    }
    public class ObjectType{
        public static final String view="ROLE_OBJECT_TYPE_VIEW";
        public static final String add="ROLE_OBJECT_TYPE_ADD";
        public static final String edit="ROLE_OBJECT_TYPE_EDIT";
        public static final String delete="ROLE_OBJECT_TYPE_DELETE";
    }
    public class GroupAuthority{
        public static final String view="ROLE_GROUP_AUTHORITY_VIEW";
        public static final String add="ROLE_GROUP_AUTHORITY_ADD";
        public static final String edit="ROLE_GROUP_AUTHORITY_EDIT";
        public static final String delete="ROLE_GROUP_AUTHORITY_DELETE";
    }


    public class MANAGEMENT{
        public static final String door="ROLE_MANAGEMENT_DOOR";
        public static final String reception="ROLE_MANAGEMENT_RECEPTION";
    }


}
