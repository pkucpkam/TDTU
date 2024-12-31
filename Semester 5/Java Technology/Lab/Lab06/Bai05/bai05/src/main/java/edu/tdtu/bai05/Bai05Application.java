package edu.tdtu.bai05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
//read description.properties
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:description.properties")
})
//@ComponentScan(basePackages = "edu.tdtu.bai05")

@SpringBootApplication
public class Bai05Application {

    public static void main(String[] args) {
//        SpringApplication.run(Bai05Application.class, args);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Bai05Application.class);

        // Khởi động ứng dụng Spring Boot và lấy context của ứng dụng
        ApplicationContext context = SpringApplication.run(Bai05Application.class, args);

        // Lấy bean Product từ context và sử dụng
        Product product = context.getBean(Product.class);
        System.out.println(product.getName());
        System.out.println(product.getDescription());

    }

}
