package com.gxuwz.ccsa_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 关键点：扫描 Mapper 接口所在的包路径
@MapperScan("com.gxuwz.ccsa_server.mapper")
public class CcsaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcsaServerApplication.class, args);
    }

}