package edu.tdtu.bai01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:AppConfig.xml")  // Nạp cấu hình XML vào Spring Boot
public class Bai01Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Bai01Application.class, args);

        // Lấy bean từ context
        Product product1 = context.getBean("product1", Product.class);

        System.out.println(product1);
    }
}
