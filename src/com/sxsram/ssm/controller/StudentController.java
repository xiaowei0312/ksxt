package com.sxsram.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.entity.StudentQueryVo;
import com.sxsram.ssm.entity.StudentTemp;
import com.sxsram.ssm.entity.TrainingClassQueryVo;
import com.sxsram.ssm.service.BsTitleService;
import com.sxsram.ssm.service.StudentService;
import com.sxsram.ssm.service.TrainingClassService;
import com.sxsram.ssm.service.TrainingDirService;
import com.sxsram.ssm.util.ImportExcelUtil;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.Pagination;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

import net.sf.morph.reflect.reflectors.StubbornDelegatingReflector;

@Controller()
@RequestMapping(value = "/student", method = { RequestMethod.GET, RequestMethod.POST })
public class StudentController {
	@Autowired
	private TrainingDirService trainingDirService;
	@Autowired
	private TrainingClassService trainingClassService;
	@Autowired
	private BsTitleService BsTitleService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/stuManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String dirManagement() {
		return "student/stuManagement";
	}

	class PageObj {
		Integer totalCount;
		Object objList;

		public PageObj() {
		}

		public PageObj(Integer totalCount, Object objList) {
			this.totalCount = totalCount;
			this.objList = objList;
		}
	}

	@RequestMapping(value = "/getStuListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getDirListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			String colSelect, String depSelect, String majorSelect, String gradeSelect, String classSelect,
			String trainingClassSelect, String trainingDirSelect, String bsDirSelect, String bsInnerTeacher,
			String bsOuterTeacher, Integer addTimeOrderBy) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		List<Student> students = null;
		Integer totalNum = 0;

		StudentQueryVo studentQueryVo = new StudentQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(searchKey)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("stu.name", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("stu.idcard", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems().add(new QueryConditionItem("stu.stuNo", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("stu.bsInnerTeacher", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("stu.bsOuterTeacher", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("bsTitle.name", searchKey, QueryConditionOp.LIKE));
			whereCondList.add(queryConditionOrItems);
		}

		if (!StringUtil.isEmpty(colSelect) && !colSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("col.id", colSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(depSelect) && !depSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("dep.id", depSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(majorSelect) && !majorSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("major.id", majorSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(gradeSelect) && !gradeSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("stu.gradeNo", gradeSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(classSelect) && !classSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("stu.classNo", classSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(trainingClassSelect) && !trainingClassSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("class.id", trainingClassSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(trainingDirSelect) && !trainingDirSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("dir.id", trainingDirSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(bsDirSelect) && !bsDirSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("bsDir.id", bsDirSelect, QueryConditionOp.EQ));
		}
		whereCondList.add(new QueryConditionItem("stu.delFlag", 0 + "", QueryConditionOp.LIKE));

		try {
			studentQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = studentService.findStudents(studentQueryVo).size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		if (addTimeOrderBy != null) {
			if (addTimeOrderBy == 0) {
				orderByMap.put("stu.addTime", "desc");
			} else {
				orderByMap.put("stu.addTime", "asc");
			}
		}
		try {
			studentQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			students = studentService.findStudents(studentQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, students);
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/updateStuAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateStudentAjax(HttpSession session, Model model, Student student) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (student.getId() == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		try {
			if (student.getBsTitleId() == -2) {
				student.setBsTitle(null);
				student.setBsTitleId(null);
			}
			if (student.getTrainingClassId() == -2) {
				student.setTrainingClass(null);
				student.setTrainingClassId(null);
			}
			studentService.updateStudent(student);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteStuAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteStudentAjax(HttpSession session, Model model, String id, Integer reqType) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null || reqType == null || reqType < 0 || reqType > 2) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到reqtype，非法操作!";
			return gson.toJson(jsonResult);
		}

		Student student = new Student();
		student.setId(Integer.valueOf(id));

		Integer totalNum = 0;
		switch (reqType) {
		case 0: // 获取类别下的商品数量
			TrainingClassQueryVo trainingClassQueryVo = new TrainingClassQueryVo();
			List<QueryConditionAbstractItem> items = new ArrayList<QueryConditionAbstractItem>();
			items.add(new QueryConditionItem("trainingId", id + "", QueryConditionOp.EQ));
			items.add(new QueryConditionItem("c.delFlag", 0 + "", QueryConditionOp.EQ));
			try {
				trainingClassQueryVo.setQueryCondition(new QueryCondition(items));
				totalNum = trainingClassService.findTrainingClasss(trainingClassQueryVo).size();
				jsonResult.resultObj = totalNum;
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		case 1: // 级联删除
			try {
				// trainingDirService.deleteTraininggDirCascadeTrainingClasses(trainingDir);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		case 2: // 级联删除
			try {
				studentService.delStudent(student);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addStuAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addTypeAjax(HttpSession session, Model model, Student student) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		try {
			if (student.getBsTitleId() == -2) {
				student.setBsTitle(null);
				student.setBsTitleId(null);
			}
			if (student.getTrainingClassId() == -2) {
				student.setTrainingClass(null);
				student.setTrainingClassId(null);
			}
			studentService.addStudent(student);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	private String uploadImg(MultipartFile img, String path) throws IllegalStateException, IOException {
		String orginalFilename = img.getOriginalFilename();
		String filename = UUID.randomUUID() + orginalFilename.substring(orginalFilename.lastIndexOf('.'));
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		File newFile = new File(path + filename);
		img.transferTo(newFile);
		return filename;
	}

	@RequestMapping(value = "/importStusAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String importStusAjax(HttpSession session, Model model, MultipartFile uploadFile) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		InputStream in = null;
		List<List<Object>> listob = null;
		if (uploadFile.isEmpty()) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "文件不存在";
			return gson.toJson(jsonResult);
		}
		try {
			in = uploadFile.getInputStream();
			listob = new ImportExcelUtil().getBankListByExcel(in, uploadFile.getOriginalFilename());
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		try {
			studentService.batchAddStudent(listob);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}
		return gson.toJson(jsonResult);
	}
}