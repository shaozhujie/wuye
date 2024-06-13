package com.project.wuye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author AA
 */
@SpringBootApplication(scanBasePackages = "com.project.*")
@ComponentScan("com.project")
@MapperScan("com.**.mapper")
public class WuyeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WuyeServerApplication.class, args);
    }

}
