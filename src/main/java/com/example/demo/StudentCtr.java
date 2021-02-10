package com.example.demo;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentCtr {
	
	@Autowired
	private StudentRepository repository;
	
	@RequestMapping(value="/students", method=RequestMethod.GET)
	ResponseEntity<Object> getStudent(@RequestParam("si") Optional<String> si){
		Iterable<Student> res;
		if(si.isPresent())
			res = repository.findByStudentname(si);
		else
			res = repository.findAll();
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value="/students", method = RequestMethod.POST)
	ResponseEntity<Object> insertStudent(@RequestBody Student u) {
		
		if(Pattern.matches("^[a-z,A-Z, ]+$", u.getStudentname().trim()) == false) {
			return new ResponseEntity<Object>(new MyJSONWrapper("Error" ,"StudentName can not be empty!"), HttpStatus.BAD_REQUEST);
		}
		else {
			Student res = repository.save(u);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
			
		}
		
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateStudent(@RequestBody Student u, @PathVariable("id") Long id){
		u.setStudentId(id);
		Student res = repository.save(u);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteUser(@PathVariable("id") Long id){
		Optional<Student> u = repository.findById(id);

		if(u.isPresent()) {
			repository.delete(u.get());
			return new ResponseEntity<Object>(new MyJSONWrapper("deleted"), HttpStatus.OK);			
		}
		else
			return new ResponseEntity<Object>(new MyJSONWrapper("Error","no such object."), HttpStatus.BAD_REQUEST);
	}
	
}
