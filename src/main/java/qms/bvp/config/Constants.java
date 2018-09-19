package qms.bvp.config;

public class Constants {
    public static class TableName{
        public static String ReceptionArea="reception_area";
        public static String ReceptionDoor="reception_door";
        public static String ReceptionObjectType="reception_object_type";
        public static String Reception="reception";
        public static String Authority="authority";
        public static String GroupAuthority="group_authority";
        public static String GroupRole="group_role";
        public static String User="users";
        public static String GroupUser="group_user";
        public static String LogAccess="log_access";
    }
    public static class ActionLog{
        public static Byte Add = Byte.valueOf("1");
        public static Byte Edit = Byte.valueOf("2");
        public static Byte Delete = Byte.valueOf("3");
        public static Byte View = Byte.valueOf("4");
        public static Byte None = Byte.valueOf("0");
    }


}
