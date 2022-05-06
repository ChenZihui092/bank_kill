package com.example.bank_kill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bank_kill.mapper")
public class BankKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankKillApplication.class, args);
    }

}
