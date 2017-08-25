package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.entity.StudentQueryVo;
import com.sxsram.ssm.entity.Student;

public interface StudentMapper {
	public void inserNewStudent(Student stu) throws Exception;

	public void deleteStudent(Integer stuId) throws Exception;

	public void updateStudent(Student stu) throws Exception;

	public Student querySingleStudent(StudentQueryVo stuQueryVo) throws Exception;

	public List<Student> queryMultiStudents(StudentQueryVo stuQueryVo) throws Exception;

	public void batchInsertStudent(List<Student> stuList) throws Exception;
}