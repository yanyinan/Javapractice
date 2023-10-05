package ann;

import java.lang.reflect.Method;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class TestDemo {
    public static void main(String[] args) throws NoSuchMethodException {
        Class<Test> testClass = Test.class;
        Method f = testClass.getMethod("f");
        TestAnnotation annotation = f.getAnnotation(TestAnnotation.class);
        if (annotation != null){

            int id = annotation.id();
            String msg = annotation.msg();
            System.out.println(id);
            System.out.println(msg);
        }
    }
}
