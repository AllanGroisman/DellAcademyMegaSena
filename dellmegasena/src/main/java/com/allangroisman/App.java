package com.allangroisman;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(basePackages = {"com.allangroisman"})
@SpringBootApplication

public class App {
    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }
}
//http://localhost:8080/h2-console/login.do?jsessionid=59e3408ba87b37536ee5efa76edaab29