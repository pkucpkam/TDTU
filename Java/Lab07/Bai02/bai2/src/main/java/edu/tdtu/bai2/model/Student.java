package edu.tdtu.bai2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    private int id;
    @Column(unique=false)
    private String name;
    private int age;
    @Column(nullable = false)
    private String email;
    private double ieltsScore;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", ieltsScore=" + ieltsScore +
                '}';
    }
}