package ann;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class TestDemo {
    public static void main(String[] args) throws NoSuchMethodException {
        Class<test> testClass = test.class;
        Method f = testClass.getMethod("f");
        TestAnnotation annotation = f.getAnnotation(TestAnnotation.class);
        if (annotation != null){
            // 有 Log 注解
            int id = annotation.id();
            String msg = annotation.msg();
            System.out.println(msg);
        }
    }
}
