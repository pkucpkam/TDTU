package edu.tdtu.bai03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Bai03Application {

    public static void main(String[] args) {
        SpringApplication.run(Bai03Application.class, args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        TextEditor txtEditor = applicationContext.getBean(TextEditor.class);
        TextWriter txtWriter = applicationContext.getBean(PlainTextWriter.class);
        txtEditor.input("This is content of file");
        txtEditor.save("Hello Spring core");
        txtWriter.write("result.txt", "kkkk");

    }

}

