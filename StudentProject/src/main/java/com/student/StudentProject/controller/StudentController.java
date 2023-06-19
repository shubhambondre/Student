package com.student.StudentProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.StudentProject.bean.Student;
import com.student.StudentProject.repository.StudentRepository;


@RestController
@RequestMapping("/api")

public class StudentController {

	
	@Autowired
	StudentRepository StudentRepo;
	
	@PostMapping("/student")
	public String createstudent (@RequestBody Student student) {
	StudentRepo.save(student);
	return "student created is DataBase";
		
	}
	
	@GetMapping("/student")
	public ResponseEntity<List<Student>> getAllStudent(){
		List<Student> stu = new ArrayList<>();
		StudentRepo.findAll().forEach(stu::add);
		return new ResponseEntity<List<Student>>(stu,HttpStatus.OK);
		
	}
	@GetMapping("/student/{id}")
	public ResponseEntity <Student> getStudentById(@PathVariable Integer id){
		Optional<Student> stud = StudentRepo.findById(id);
		if(stud.isPresent()) {
			return new ResponseEntity<Student>(stud.get(),HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Student>(stud.get(),HttpStatus.NOT_FOUND);
		}
	
	}
	@PutMapping("/student/{id}")
	public String updateStudentById(@PathVariable Integer id, @RequestBody Student student) {
		Optional<Student> stud = StudentRepo.findById(id);
		if(stud.isPresent()) {
			Student exitstud =  stud.get();
			exitstud.setEmail_id(student.getEmail_id());
			exitstud.setMobile_no(student.getMobile_no());
			exitstud.setName(student.getName());
			exitstud.setRollno(student.getRollno());
			StudentRepo.save(exitstud);
			return "Student Details for Id "+ id + "updated";
					
		}else {
			return "Student Details for Id "+ id + "not found";
		}
		
	}
	@DeleteMapping("/student/{id}")
	public String DeleteStudentById(@PathVariable Integer id) {
		StudentRepo.deleteById(id);
		return "student deleted successfully";
	}
	
	@DeleteMapping("/student")
	public String DeleteAllStudent() {
	StudentRepo.deleteAll();
	return "student deleted successfully";
	
	}
			
	
}