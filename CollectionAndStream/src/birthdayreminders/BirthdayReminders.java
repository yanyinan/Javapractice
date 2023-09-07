package birthdayreminders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ## 生日提醒
 * 编写一个生日提醒程序，使用Map来存储人名和对应的生日日期。够根据日期提醒用户哪些人今天过生日？
 * - 如何筛选显示出最近七天过生日的用户
 * @author:nanzhou
 * @date:
 */
public class BirthdayReminders {
    public static void main(String[] args) {
        // 创建一个存储人名和生日日期的映射
        Map<String, Date> birthday = new HashMap<>();

        // 添加人名和生日日期到映射中
        // 1990-06-15
        birthday.put("Alice", new Date(90, 5, 15));
        // 1985-09-22
        birthday.put("Bob", new Date(85, 8, 22));
        // 1995-03-08
        birthday.put("Charlie", new Date(95, 2, 8));
        System.out.println(birthday.get("Alice"));

    }

}
