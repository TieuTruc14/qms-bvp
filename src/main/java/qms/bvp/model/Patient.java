package qms.bvp.model;

import qms.bvp.common.DateUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Admin on 8/9/2018.
 */
public class Patient implements Serializable{

    // duyet phan tu tu cuoi' cua treeset(lay dc doi tuong chu dong thoat vong lap la ko vao catch)
//    public static void main(String[] args) {
//        String[] elements = new String[] { "A", "B", "C", "D" };
//
//        TreeSet set = new TreeSet(Arrays.asList(elements));
//
//        try {
//            Object last = set.last();
//            boolean first = true;
//            while (true) {
//                if (!first) {
//                    System.out.print(", ");
//                }
//                System.out.println(last);
//                last = set.headSet(last).last();
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("chet toi");
//        }
//    }

    //    public static void main(String[] args) {
//        TreeSet<Integer> tree=new TreeSet();
//        tree.add(1);tree.add(6);tree.add(4);tree.add(6);
//        Iterator iterator = tree.iterator();
//        System.out.println("Tree set data: ");
//        for(Integer i:tree){
//            System.out.println(i);
//        }
//        for(int i=tree.size()-1;i>=0;i--){
//            System.out.println(tree.);
//        }
////        while (iterator.hasNext()) {
////            System.out.print(iterator.next() + " ");
////        }
////        System.out.println("vong2");
////        while (iterator.hasNext()) {
////            System.out.print(iterator.next() + " ");
////        }
//        Set<Byte> set=new HashSet<>();
//        set.add(Byte.valueOf("1"));set.add(Byte.valueOf("2"));set.add(Byte.valueOf("2"));
//        for(Byte i:set){
//            System.out.println(i);
//        }
//        for(Byte i:set){
//            System.out.println(i);
//        }
//    }



    private Long id;
    private String name;
    private Date birthday;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
