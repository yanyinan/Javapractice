package ann;

import java.lang.annotation.Documented;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
@Documented
public class Test {
    @TestAnnotation(id = 1,msg = "ss")
    public void f(){}
}
