package com.guffy.study.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guffy.study.dto.Student;


@Repository
public class StudentDAOImpl implements StudentDAO {

	SessionFactory sessionFactory;

	public StudentDAOImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<Student> getStudents() {
		List<Student> students = null;

		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();

		Criteria c = s.createCriteria(Student.class);
		students = c.list();

		// s.getTransaction().commit();

		return students;
	}

	@Transactional
	@Override
	public Student getStudent(long id) {
		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		Student st = (Student) s.get(Student.class, id);
		// s.getTransaction().commit();
		return st;
	}

	@Override
	@Transactional
	public void addStudent(Student st) {
		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();

		s.save(st);

		// s.getTransaction().commit();
	}
}
