package com.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.model.Course;
import com.springboot.model.Student;
import com.springboot.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/students/{studentId}/courses")
	public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
		System.out.println("for testing purpose and doing changes ");
		return studentService.retrieveCourses(studentId);
	}

	@GetMapping("/students/{studentId}")
	public Student retrieveStudentById(@PathVariable String studentId) {
		return studentService.retrieveStudent(studentId);
	}

	@GetMapping("/first")
	public String helloTest() {
		return "Spring boot application First API";
	}

	@GetMapping("/students")
	public List<Student> retrieveStudents() {
		return studentService.retrieveAllStudents();
	}

	@GetMapping("/courses")
	public List<Course> retrieveCourses() {
		return studentService.retrieveAllCourses();
	}

	@GetMapping("/students/{studentId}/courses/{courseId}")
	public Course retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId) {
		return studentService.retrieveCourse(studentId, courseId);
	}

	@PostMapping("/students/{studentId}/courses")
	public ResponseEntity<Void> registerStudentForCourse(@PathVariable String studentId,
			@RequestBody Course newCourse) {
		System.out.println("In Post ");
		Course course = studentService.addCourse(studentId, newCourse+" testing");
		System.out.println(course);
		if (course == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId())
				.toUri();
		System.out.println(location+" location");
		System.out.println(ResponseEntity.created(location).build());
		System.out.println("end line of line");
		return ResponseEntity.created(location).build();
	}

}