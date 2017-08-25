package com.sxsram.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.BsTitleQueryVo;
import com.sxsram.ssm.entity.CollageMajor;
import com.sxsram.ssm.entity.CollageMajorQueryVo;
import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.entity.StudentQueryVo;
import com.sxsram.ssm.entity.StudentTemp;
import com.sxsram.ssm.entity.TrainingClass;
import com.sxsram.ssm.entity.TrainingClassQueryVo;
import com.sxsram.ssm.entity.TrainingDir;
import com.sxsram.ssm.entity.TrainingDirQueryVo;
import com.sxsram.ssm.exception.SQLOperatorNotSupportedException;
import com.sxsram.ssm.mapper.StudentMapper;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

import sun.tools.jar.resources.jar;
import sun.util.logging.resources.logging;

@Service("studentService")
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TrainingDirService trainingDirService;
	@Autowired
	private BsTitleService bsTitleService;
	@Autowired
	private TrainingClassService trainingClassService;
	@Autowired
	private CollageMajorService majorService;

	public Student findStudent(StudentQueryVo studentQueryVo) throws Exception {
		return studentMapper.querySingleStudent(studentQueryVo);
	}

	public List<Student> findStudents(StudentQueryVo studentQueryVo) throws Exception {
		return studentMapper.queryMultiStudents(studentQueryVo);
	}

	public void addStudent(Student stu) throws Exception {
		if (stu.getPassword() == null)
			stu.setPassword(stu.getStuNo());
		studentMapper.inserNewStudent(stu);
	}

	public void updateStudent(Student stu) throws Exception {
		if (stu.getPassword() == null)
			stu.setPassword(stu.getStuNo());
		studentMapper.updateStudent(stu);
	}

	public void delStudent(Student stu) throws Exception {
		studentMapper.deleteStudent(stu.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void batchAddStudent(List<List<Object>> listob) throws Exception {
		List<Student> students = new ArrayList<Student>();
		Integer grade = null;
		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			if (String.valueOf(lo.get(0)).startsWith("##")) {
				continue;
			}

			Student vo = new Student();

			if (StringUtil.isEmpty(lo.get(6))) {
				vo.setName(null);
			} else {
				vo.setName(String.valueOf(lo.get(6)));
			}

			if (StringUtil.isEmpty(lo.get(3))) {
				vo.setClassNo(null);
			} else {
				vo.setClassNo(String.valueOf(lo.get(3)));
			}

			if (StringUtil.isEmpty(lo.get(4))) {
				vo.setGradeNo(null);
			} else {
				String gradeStr = String.valueOf(lo.get(4));
				if (gradeStr.indexOf('级') >= 0) {
					grade = Integer.valueOf(gradeStr.substring(0, gradeStr.lastIndexOf('级')));
				} else {
					grade = Integer.valueOf(gradeStr);
				}
				vo.setGradeNo(grade);
			}

			if (StringUtil.isEmpty(lo.get(5))) {
				vo.setStuNo(null);
			} else {
				vo.setStuNo(String.valueOf(lo.get(5)));
			}

			if (StringUtil.isEmpty(lo.get(7))) {
				vo.setGender((Integer) null);
			} else {
				vo.setGender(String.valueOf(lo.get(7)));
			}
			if (StringUtil.isEmpty(lo.get(8))) {
				vo.setIdCard(null);
			} else {
				vo.setIdCard(String.valueOf(lo.get(8)));
			}

			if (StringUtil.isEmpty(lo.get(9))) {
				vo.setPhone(null);
			} else {
				vo.setPhone(String.valueOf(lo.get(9)));
			}

			if (StringUtil.isEmpty(lo.get(14))) {
				vo.setBsInnerTeacher(null);
			} else {
				vo.setBsInnerTeacher(String.valueOf(lo.get(14)));
			}

			if (StringUtil.isEmpty(lo.get(15))) {
				vo.setBsOuterTeacher(null);
			} else {
				vo.setBsOuterTeacher(String.valueOf(lo.get(15)));
			}

			// 毕设方向 && 毕设选题
			BsTitle bsTitle = null;
			TrainingDir bsDir = null;
			if (StringUtil.isEmpty(lo.get(13))) {
				bsTitle = null;
			} else {
				if ((bsTitle = getBsTitle(String.valueOf(lo.get(13)))) == null) {
					if ((bsDir = getBsDir(String.valueOf(lo.get(12)))) == null) {
						throw new Exception(i + "," + 12 + "无效的选题方向");
					}
					bsTitle = new BsTitle();
					bsTitle.setName(String.valueOf(lo.get(13)));
					bsTitle.setBsDirId(bsDir.getId());
					bsTitleService.addNewBsTitle(bsTitle);
				}
			}
			vo.setBsTitle(bsTitle);

			// 实训方向 && 实训班级
			TrainingClass trainingClass = null;
			TrainingDir trainingDir = null;
			if (StringUtil.isEmpty(lo.get(11))) {
				trainingClass = null;
			} else {
				if ((trainingClass = getTrainingClass(String.valueOf(lo.get(11)))) == null) {
					if ((trainingDir = getTrainingDir(String.valueOf(lo.get(10)))) == null) {
						throw new Exception(i + "," + 10 + "无效的实训方向");
					}
					trainingClass = new TrainingClass();
					trainingClass.setName(String.valueOf(lo.get(11)));
					trainingClass.setTrainingId(trainingDir.getId());
					trainingClassService.addNewTrainingClass(trainingClass);
				}
			}
			vo.setTrainingClass(trainingClass);

			// 院校信息
			CollageMajor col = null, dep = null, major = null;
			String colName = String.valueOf(lo.get(0));
			String depName = String.valueOf(lo.get(1));
			String majorName = String.valueOf(lo.get(2));

			if ((major = getMajor(colName, depName, majorName)) == null) {
				if ((dep = getDep(colName, depName)) == null) {
					if ((col = getCol(colName)) == null) {
						col = new CollageMajor();
						col.setParentId(null);
						col.setName(colName);
						col.setSequence(0);
						majorService.addNewCollageMajor(col);
					}
					dep = new CollageMajor();
					dep.setParent(col);
					dep.setName(depName);
					dep.setSequence(0);
					majorService.addNewCollageMajor(dep);
				}
				major = new CollageMajor();
				major.setParent(dep);
				major.setName(majorName);
				major.setSequence(0);
				majorService.addNewCollageMajor(major);
			}
			vo.setMajor(major);
			vo.setPassword(vo.getStuNo());
			students.add(vo);
		}
		studentMapper.batchInsertStudent(students);
	}

	private CollageMajor getCol(String colName) throws Exception {
		// TODO Auto-generated method stub
		CollageMajor collage = null;
		CollageMajorQueryVo collageMajorQueryVo = new CollageMajorQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("major.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(colName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("major.name", colName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}
		whereCondList.add(new QueryConditionItem("major.parentId", "", QueryConditionOp.ISNULL));

		collageMajorQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		collage = majorService.findCollageMajor(collageMajorQueryVo);
		return collage;
	}

	private CollageMajor getDep(String colName, String depName) throws Exception {
		// TODO Auto-generated method stub
		CollageMajor dep = null;
		CollageMajorQueryVo collageMajorQueryVo = new CollageMajorQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("major.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(colName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("dep.name", colName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}
		if (!StringUtils.isEmpty(depName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("major.name", depName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}

		collageMajorQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		dep = majorService.findCollageMajor(collageMajorQueryVo);
		return dep;
	}

	private CollageMajor getMajor(String colName, String depName, String majorName) throws Exception {
		// TODO Auto-generated method stub
		CollageMajor major = null;
		CollageMajorQueryVo collageMajorQueryVo = new CollageMajorQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("major.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(colName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("col.name", colName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}
		if (!StringUtils.isEmpty(depName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("dep.name", depName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}
		if (!StringUtils.isEmpty(majorName)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("major.name", majorName, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}

		collageMajorQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		major = majorService.findCollageMajor(collageMajorQueryVo);
		return major;
	}

	private TrainingDir getTrainingDir(String dirName) throws Exception {
		TrainingDir trainingDir = null;
		TrainingDirQueryVo trainingDirQueryVo = new TrainingDirQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(dirName)) {
			whereCondList.add(new QueryConditionItem("name", dirName, QueryConditionOp.EQ));
		}
		whereCondList.add(new QueryConditionItem("delFlag", 0 + "", QueryConditionOp.EQ));
		trainingDirQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		trainingDir = trainingDirService.findTrainingDir(trainingDirQueryVo);
		return trainingDir;
	}

	private TrainingClass getTrainingClass(String name) throws Exception {

		TrainingClass trainingClass = null;

		TrainingClassQueryVo trainingClassQueryVo = new TrainingClassQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("c.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(name)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("c.name", name, QueryConditionOp.LIKE));
			whereCondList.add(queryConditionOrItems);
		}
		trainingClassQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		trainingClass = trainingClassService.findTrainingClass(trainingClassQueryVo);
		return trainingClass;
	}

	private TrainingDir getBsDir(String dirName) throws Exception {
		TrainingDir trainingDir = null;
		TrainingDirQueryVo trainingDirQueryVo = new TrainingDirQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(dirName)) {
			whereCondList.add(new QueryConditionItem("name", dirName, QueryConditionOp.EQ));
		}
		whereCondList.add(new QueryConditionItem("delFlag", 0 + "", QueryConditionOp.EQ));
		trainingDirQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		trainingDir = trainingDirService.findTrainingDir(trainingDirQueryVo);
		return trainingDir;
	}

	private BsTitle getBsTitle(String name) throws Exception {
		BsTitle bsTitle = null;
		BsTitleQueryVo bsTitleQueryVo = new BsTitleQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("t.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(name)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("t.name", name, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}
		bsTitleQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		bsTitle = bsTitleService.findBsTitle(bsTitleQueryVo);
		return bsTitle;
	}
}