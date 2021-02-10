package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
	
	List<Student> findByStudentname(Optional<String> si);

}
