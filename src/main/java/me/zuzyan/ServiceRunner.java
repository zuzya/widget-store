package me.zuzyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServiceRunner {

    public static void main(String[] args) {

        SpringApplication.run(ServiceRunner.class, args);
    }

}
