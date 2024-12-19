package edu.tdtu.bai2.repository;

import edu.tdtu.bai2.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
