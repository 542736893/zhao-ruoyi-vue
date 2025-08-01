package com.ruoyi.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 若依系统启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(scanBasePackages = {"com.ruoyi.server","com.ruoyi.module.*","com.ruoyi.framework.*"})
@MapperScan("com.ruoyi.module.*.mapper")
public class RuoYiServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(RuoYiServerApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依系统启动成功   ლ(´ڡ`ლ)ﾞ");

    }
}