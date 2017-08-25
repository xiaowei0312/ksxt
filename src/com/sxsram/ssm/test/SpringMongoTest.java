package com.sxsram.ssm.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sxsram.ssm.dao.StudentDao;
import com.sxsram.ssm.entity.ExcelStudent;

public class SpringMongoTest {
	private StudentDao studentDao;
	private String collectionName;
	private ClassPathXmlApplicationContext app;

	@Before
	public void initSpringMongo() {
		try {
			app = new ClassPathXmlApplicationContext("classpath:spring/springmvc-junit.xml");
			//app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mongo.xml");
			
			studentDao = (StudentDao) app.getBean("studentDao");
			collectionName = "col1";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert() {
		ExcelStudent student = new ExcelStudent();
		student.setName("zhangsan");
		student.setGender("男");
		student.setIdCard("142733198703123918");
		student.setStuNo("20130101");

		studentDao.insert(student, collectionName);
		// private String name;
		// private Integer gender;
		// private String idCard;
		// private String stuNo;
		// private Integer gradeNo;
		// private String classNo;
		// private String phone;
		// private Integer majorId;
		// private Integer trainingClassId;
		// // private Integer bsDirId;
		// private Integer bsTitleId;
		// private String bsInnerTeacher;
		// private String bsOuterTeacher;
		// private String password;
		// private CollageMajor major;
		// private TrainingClass trainingClass;
		// // private TrainingDir bsDir;
		// private BsTitle bsTitle;
	}
}
