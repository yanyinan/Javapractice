package com.satrt.springweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 26481
 */
@SpringBootApplication 
/**
 * 扫描 Servlet 组件
*/
@ServletComponentScan
public class SpringwebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringwebApplication.class, args);
    }

}
