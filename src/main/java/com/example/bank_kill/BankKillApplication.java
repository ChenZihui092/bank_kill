package com.example.bank_kill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
//@EnableAspectJAutoProxy
@MapperScan("com.example.bank_kill.mapper")
public class BankKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankKillApplication.class, args);
    }

}
