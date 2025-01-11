package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.example")
@EntityScan(basePackages = "org.example.model")
@EnableJpaRepositories(basePackages = "org.example.repository")
@EnableCaching
public class GerenciamentoDePedidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoDePedidosApplication.class, args);
    }
}