package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class ReflectDemo1 {
    public static void main(String[] args) throws ClassNotFoundException {
//        testDemo testDemo = new testDemo();
//        System.out.println(testDemo.getClass());
        Class t = Class.forName("reflection.testDemo");
//        System.out.println(t);
//        for (Constructor constructor : t.getConstructors()) {
//            System.out.println(constructor);
//        }
//        for (Field field : t.getDeclaredFields()) {
//            System.out.println(field);
//        }
        for (Class declaredClass : t.getDeclaredClasses()) {
            System.out.println(declaredClass.isMemberClass());
        }
    }
}
class testDemo{
    String name;
    private String hob;
    public int age;
    protected String bao;

    public testDemo() {
    }

    public testDemo(String name, String hob, int age, String bao) {
        this.name = name;
        this.hob = hob;
        this.age = age;
        this.bao = bao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHob() {
        return hob;
    }

    public void setHob(String hob) {
        this.hob = hob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBao() {
        return bao;
    }

    private void setBao(String bao) {
        this.bao = bao;
    }
    class d{
        String test;
    }
}
