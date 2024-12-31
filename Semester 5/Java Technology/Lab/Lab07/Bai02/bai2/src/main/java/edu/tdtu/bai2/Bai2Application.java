package edu.tdtu.bai2;

import edu.tdtu.bai2.model.Student;
import edu.tdtu.bai2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Bai2Application {

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(Bai2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Công Nghệ Java");
            studentRepository.save(new Student(1, "Hao", 18, "hao@gmail.com", 7.0));
            studentRepository.save(new Student(2, "Phuc", 18, "phuc@gmail.com", 8.5));
            studentRepository.save(new Student(3, "Nhan", 18, "nhan@gmail.com", 8.0));

            System.out.println("-------------------------Read------------------------");
            studentRepository.findAll().forEach(System.out::println);

            System.out.println("-------------------------Update----------------------");
            Student updatedStudent = studentRepository.findById(1).get();
            updatedStudent.setName("Hao1");
            updatedStudent.setAge(18);
            updatedStudent.setEmail("hao1@gmail.com");
            studentRepository.save(updatedStudent);
            studentRepository.findAll().forEach(System.out::println);

            System.out.println("-------------------------Delete------------------------");
            studentRepository.deleteById(2);
            studentRepository.findAll().forEach(System.out::println);
        };
    }

}