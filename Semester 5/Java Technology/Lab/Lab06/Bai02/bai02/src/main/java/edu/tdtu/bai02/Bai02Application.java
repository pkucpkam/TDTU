package edu.tdtu.bai02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Bai02Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Bai02Application.class, args);

        // Lấy bean từ context
        Product product1 = context.getBean(Product.class);

        System.out.println(product1);
    }
}
