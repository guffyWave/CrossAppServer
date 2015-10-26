package com.guffy.study.dao;

import java.util.List;

import com.guffy.study.dto.Student;

public interface StudentDAO {

	public List<Student> getStudents();

	public void addStudent(Student s);

	
	public Student getStudent(long id);
}
