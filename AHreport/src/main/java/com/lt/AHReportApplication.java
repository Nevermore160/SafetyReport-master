package com.lt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author teng.li
 * @version 1.0
 * @date 2020/8/16 15:55
 */
@SpringBootApplication
@MapperScan("com.lt.mapper")
public class AHReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(AHReportApplication.class, args);
    }
}
