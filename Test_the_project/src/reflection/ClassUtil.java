package reflection;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassUtil {
    /**
     * 设计一个方法获取类对象的所有属性
     * @param cla 类对象
     * @return 属性集合
     */
    public static Collection<Field> getAllFields(Class cla) {
        Field[] fields = cla.getDeclaredFields();
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        for (Field field : fields) {
            fieldArrayList.add(field);
        }
        return fieldArrayList;
    }
    public static Collection<Method> getAllMethods(Class cla) {
        Method[] methods = cla.getDeclaredMethods();
        ArrayList<Method> methodArrayList = new ArrayList<>();
        for (Method method : methods) {
            methodArrayList.add(method);
        }
        return methodArrayList; }
    /**
     * 获取指定包下所有的类
     * @return 包下所有类的集合
     */
    public static Collection<Class> getAllClasses(String packAge) throws ClassNotFoundException {
        File file = new File(packAge);
        File[] files = file.listFiles();
        List<Class> classArrayList = new ArrayList<>();
        if (files != null && files.length > 0){
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()){
                    classArrayList.addAll(getAllClasses(files[i].getAbsolutePath()));
                }else if ( files[i].isFile() &&files[i].getName().endsWith(".class")){
                    classArrayList.add(Class.forName(files[i].getName().substring(0, files[i].getName().length() - 6)));
                }
            }
        }
        return classArrayList; }

    public static void main(String[] args) throws ClassNotFoundException {
//        getAllFields(Books.class).forEach(field -> System.out.println(field.getName()));
//        getAllMethods(Books.class).forEach(field -> System.out.println(field.getName()));
        System.out.println(getAllClasses("C:\\Users\\26481\\Desktop\\git_up\\javapractice\\Test_the_project\\out\\production\\untitled\\Collection").size());
    }
}
