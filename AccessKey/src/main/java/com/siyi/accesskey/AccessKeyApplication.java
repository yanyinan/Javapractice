package com.siyi.accesskey;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author 26481
 */
@SpringBootApplication
@MapperScan("com.siyi.accesskey.mapper")
@EnableScheduling //开启定时任务
public class AccessKeyApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessKeyApplication.class, args);
    }

}
