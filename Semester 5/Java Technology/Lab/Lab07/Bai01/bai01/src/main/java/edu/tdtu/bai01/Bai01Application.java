package edu.tdtu.bai01;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Bai01Application {

    public static void main(String[] args) {
        SpringApplication.run(Bai01Application.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Môn học Công nghệ phần mềm");
        };
    }
}
