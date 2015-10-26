package com.guffy.study.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.guffy.study.dao.StudentDAO;
import com.guffy.study.dto.Student;
import com.guffy.study.dto.Subject;

@Controller
public class StudentContoller {

	@Autowired
	StudentDAO studentDAO;

	Logger logger = Logger.getLogger(StudentContoller.class.getName());

	@RequestMapping(value = "/getStudents", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody
	List<Student> fetchStudentList() {
		logger.info("Inside Fetch Student Method ");
		return studentDAO.getStudents();
	}

	@RequestMapping(value = "/getStudent/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody
	Student fetchStudent(@PathVariable("userId") Long userId) {
		Student st = studentDAO.getStudent(userId);
		logger.info("Inside Fetch Student Method ");
		return st;
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public ModelAndView addStudent(@RequestParam("studentName") String name,
			@RequestParam("studentWeight") double weight) {

		logger.info("Inside Add Student Method ");

		logger.info("Obtained Name " + name);
		logger.info("Obtained Weight " + weight);

		Student st = new Student();
		st.setName(name);
		st.setWeight(weight);

		Subject sub = new Subject();
		sub.setSubjectName("Maths");
		sub.setTotalMarks(100);
		
		Subject sub2 = new Subject();
		sub2.setSubjectName("Science");
		sub2.setTotalMarks(100);

		st.getSubjects().add(sub);
		st.getSubjects().add(sub2);

		studentDAO.addStudent(st);

		ModelAndView mv = new ModelAndView("StudentAdded");
		return mv;
	}
}
