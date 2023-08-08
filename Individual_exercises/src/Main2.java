import java.util.Scanner;
/**
 * @title:
 * @author:nanzhou
 * @date:
 */
/*
 四舍五入
 描述
        定义一个int类型变量i,i为由浮点数变量d四舍五入后的整数类型，请将转换后的i进行输出
        输入描述：
        用户随机输入的浮点数
        输出描述：
        四舍五入之后的整数（小数点后一位>=5则进一，否则舍去）*/
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double d= scanner.nextDouble();
        //write your code here......
        int i = (int) (d * 100);
        int temp = i%100;
        if (temp > 50){
            i = i/100+1;
        }else {i = i/100;}
        System.out.println(i);
        
    }
}