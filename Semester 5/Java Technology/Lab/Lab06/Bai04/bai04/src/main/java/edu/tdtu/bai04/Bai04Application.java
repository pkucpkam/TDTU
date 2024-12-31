package edu.tdtu.bai04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@Component
@SpringBootApplication
public class Bai04Application {

    public static void main(String[] args) {
//        SpringApplication.run(Bai04Application.class, args);
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Bai04Application.class);

        // Khởi động ứng dụng Spring Boot và lấy context của ứng dụng

        ApplicationContext applicationContext = SpringApplication.run(Bai04Application.class, args);
        TextEditor txtEditor = applicationContext.getBean(TextEditor.class);
        // TextWriter txtWriter = applicationContext.getBean(PlainTextWriter.class);
        txtEditor.input("This is content of file");
        txtEditor.save("Hello Spring core");
        // txtWriter.write("result.txt", "kkkk");

    }

}
