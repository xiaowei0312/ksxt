package com.sxsram.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.entity.BsCommitFormat;
import com.sxsram.ssm.entity.BsTaskCommitRecord;
import com.sxsram.ssm.entity.BsTaskStu;
import com.sxsram.ssm.entity.BsTaskStuQueryVo;
import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.entity.StudentQueryVo;
import com.sxsram.ssm.exception.CommitFormatInvalidException;
import com.sxsram.ssm.exception.StudentIdNotFoundException;
import com.sxsram.ssm.service.CommitRecordService;
import com.sxsram.ssm.service.StudentService;
import com.sxsram.ssm.service.StudentTaskService;
import com.sxsram.ssm.util.ConfigUtil;
import com.sxsram.ssm.util.DateUtil;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.MD5Util;
import com.sxsram.ssm.util.Pagination;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.StringUtil;

@Controller()
@RequestMapping(value = "/main", method = { RequestMethod.GET, RequestMethod.POST })
public class MainController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentTaskService studentTaskService;
	@Autowired
	private CommitRecordService commitRecordService;

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login() {
		return "main/login";
	}

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String index() {
		return "main/index";
	}

	@RequestMapping(value = "/notice", method = { RequestMethod.GET, RequestMethod.POST })
	public String notice() {
		return "main/notice";
	}

	@RequestMapping(value = "/userInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String userInfo() {
		return "main/userInfo";
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

	@RequestMapping(value = "/loginAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String loginAjax(HttpServletRequest request, HttpSession session, String idcard, String password) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");
		jsonResult.url = request.getContextPath() + "/main/index.action";

		// 1.用户输入验证
		if (StringUtil.isEmpty(idcard) || StringUtil.isEmpty(password)) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "用户名或者密码不能为空!";
			return gson.toJson(jsonResult);
		}

		// 2.判断用户是否存在
		Student student = null;
		StudentQueryVo studentQueryVo = new StudentQueryVo();
		try {
			List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
			whereCondList.add(new QueryConditionItem("upper(stu.idcard)", idcard.toUpperCase(), QueryConditionOp.EQ));
			whereCondList.add(
					new QueryConditionItem("stu.password", MD5Util.MD5Encode(password, null), QueryConditionOp.EQ));
			whereCondList.add(new QueryConditionItem("stu.delFlag", "0", QueryConditionOp.EQ));
			studentQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			studentQueryVo.setPagination(null);
			student = studentService.findStudent(studentQueryVo);
			if (student == null) {
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = "用户名或者密码不正确";
			}
			jsonResult.resultObj = student;
			session.setAttribute("student", student);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/getTaskListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getTaskListAjax(HttpServletRequest request, HttpSession session, String idcard, String password,
			Integer pageNo, Integer pageSize, String taskStatus, String taskExecutionStatus, String taskType,
			String searchKey) {
		Gson gson = new GsonBuilder().setDateFormat("yy.MM.dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		Student sessionStu = (Student) session.getAttribute("student");
		if (sessionStu == null) {
			jsonResult.logicCode = "-2";
			jsonResult.resultMsg = "登录超时，请重新登录";
			jsonResult.url = request.getContextPath() + "/main/login.action";
		}

		Integer totalNum = 0;
		List<BsTaskStu> bsTaskStuList = null;
		BsTaskStuQueryVo bsTaskStuQueryVo = new BsTaskStuQueryVo();
		try {
			List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
			whereCondList.add(new QueryConditionItem("task.studentId", sessionStu.getId() + "", QueryConditionOp.EQ));
			whereCondList.add(new QueryConditionItem("task.delFlag", "0", QueryConditionOp.EQ));
			
			if(!StringUtil.isEmpty(taskStatus) && !taskStatus.equals("-2"))
				whereCondList.add(new QueryConditionItem("adminTask.status", taskStatus + "", QueryConditionOp.EQ));
			if(!StringUtil.isEmpty(taskExecutionStatus)&& !taskExecutionStatus.equals("-2"))
				whereCondList.add(new QueryConditionItem("task.status", taskExecutionStatus + "", QueryConditionOp.EQ));
			if(!StringUtil.isEmpty(taskType)&& !taskType.equals("-2"))
				whereCondList.add(new QueryConditionItem("adminTask.type", taskType + "", QueryConditionOp.EQ));
			if(!StringUtil.isEmpty(searchKey))
				whereCondList.add(new QueryConditionItem("adminTask.extra", searchKey + "", QueryConditionOp.LIKE));
			
			bsTaskStuQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			bsTaskStuList = studentTaskService.findBsTaskStus(bsTaskStuQueryVo);
			totalNum = bsTaskStuList.size();

			Map<String, String> orderByMap = new HashMap<String, String>();
			orderByMap.put("task.lmtTime", "desc");
			bsTaskStuQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			bsTaskStuList = studentTaskService.findBsTaskStus(bsTaskStuQueryVo);

			for (BsTaskStu bsTaskStu : bsTaskStuList) {
				Collections.sort(bsTaskStu.getCommitRecordList());
				Collections.sort(bsTaskStu.getDbRecordList());
			}

			jsonResult.resultObj = new PageObj(totalNum, bsTaskStuList);
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

	private List<String> getFormatInstanceList(String format, Integer stuId) throws Exception {
		List<String> validFileList = new ArrayList<String>();

		// 获取指定格式
		String formatArgs[] = format.split("\\.");
		if (formatArgs.length != 2) {
			throw new CommitFormatInvalidException("数据库中指定的文件上传格式不正确.");
		}
		String prefix = formatArgs[0];
		String suffix = formatArgs[1];
		List<String> prefixList = new ArrayList<String>(Arrays.asList(prefix.split("\\_")));
		List<String> suffixList = new ArrayList<String>(Arrays.asList(suffix.split("\\|")));

		String validFileName = "";
		for (String str : prefixList) {
			if (str.startsWith("${") && str.endsWith("}")) {
				validFileName += getStrByFormat(str, stuId + "");
			} else {
				validFileName += str;
			}
			validFileName += '_';
		}
		validFileName = validFileName.substring(0, validFileName.length() - 1);

		for (String str : suffixList) {
			validFileList.add(validFileName + "." + str);
		}

		return validFileList;
	}

	private String getStrByFormat(String str, String stuId) throws Exception {
		Student student = null;
		StudentQueryVo studentQueryVo = new StudentQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("stu.id", stuId + "", QueryConditionOp.EQ));
		whereCondList.add(new QueryConditionItem("stu.delFlag", "0", QueryConditionOp.EQ));
		studentQueryVo.setQueryCondition(new QueryCondition(whereCondList));
		studentQueryVo.setPagination(null);
		student = studentService.findStudent(studentQueryVo);
		if (student == null) {
			throw new StudentIdNotFoundException(stuId);
		}

		String keyword = "";
		if (str.equals("${姓名}"))
			keyword = student.getName();
		else if (str.equals("${学号}"))
			keyword = student.getStuNo();
		else if (str.equals("${身份证号}"))
			keyword = student.getIdCard();
		else if (str.equals("${年级}"))
			keyword = student.getGradeNo() + "";
		else if (str.equals("${院校}"))
			keyword = student.getMajor().getParent().getParent().getName();
		else if (str.equals("${系别}"))
			keyword = student.getMajor().getParent().getName();
		else if (str.equals("${专业}"))
			keyword = student.getMajor().getName();
		else if (str.equals("${实训班级}"))
			keyword = student.getTrainingClass().getName();
		else if (str.equals("${实训方向}"))
			keyword = student.getTrainingClass().getTrainingDir().getName();
		else if (str.equals("${毕设方向}"))
			keyword = student.getBsTitle().getBsDir().getName();
		else if (str.equals("${毕设选题}"))
			keyword = student.getBsTitle().getName();
		else if (str.equals("${校内指导老师}"))
			keyword = student.getBsInnerTeacher();
		else if (str.equals("${校外指导老师}"))
			keyword = student.getBsOuterTeacher();
		return keyword;
	}

	@RequestMapping(value = "/fileUploadAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String fileUploadAjax(HttpServletRequest request, HttpSession session, String taskId, MultipartFile img) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (taskId == null || img == null || StringUtil.isEmpty(img.getOriginalFilename())) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id或者文件，非法操作!";
			return gson.toJson(jsonResult);
		}
		Student student = (Student) session.getAttribute("student");
		if (student == null) {
			jsonResult.logicCode = "-2";
			jsonResult.resultMsg = "登录超时，请重新登录!";
			jsonResult.url = request.getContextPath() + "/main/login.action";
			return gson.toJson(jsonResult);
		}

		// 根据用户id和任务id获取对应的文件类型
		BsTaskStu bsTaskStu = null;
		BsTaskCommitRecord record = new BsTaskCommitRecord();
		BsTaskStuQueryVo bsTaskStuQueryVo = new BsTaskStuQueryVo();

		try {
			List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
			whereCondList.add(new QueryConditionItem("task.id", taskId + "", QueryConditionOp.EQ));
			whereCondList.add(new QueryConditionItem("task.delFlag", "0", QueryConditionOp.EQ));
			bsTaskStuQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			bsTaskStu = studentTaskService.findBsTaskStu(bsTaskStuQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-2";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		if (bsTaskStu == null || bsTaskStu.getTask() == null || bsTaskStu.getTask().getType() != 0) {
			jsonResult.logicCode = "-3";
			jsonResult.resultMsg = "无法找到对应的上传任务，请不要恶意访问.";
			return gson.toJson(jsonResult);
		}

		// 判断是否有效任务
		if (bsTaskStu.getTask().getStatus() == -1) {
			jsonResult.logicCode = "-3";
			jsonResult.resultMsg = "任务已失效，无法继续上传文件.";
			return gson.toJson(jsonResult);
		}

		BsCommitFormat bsCommitFormat = bsTaskStu.getTask().getBsCommitFormat();
		if (bsCommitFormat == null || StringUtil.isEmpty(bsCommitFormat.getFormat())) {
			jsonResult.logicCode = "-4";
			jsonResult.resultMsg = "无法找到对应上传格式,请联系系统管理员以查看问题.";
			return gson.toJson(jsonResult);
		}

		// 判断是否有效文件名
		String savedPath = "/validfilelist/";
		List<String> validFileList = null;
		try {
			validFileList = getFormatInstanceList(bsCommitFormat.getFormat(), student.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonResult.logicCode = "-4";
			jsonResult.resultMsg = e1.getMessage();
			return gson.toJson(jsonResult);
		}

		String originalFilename = img.getOriginalFilename();
		if (!validFileList.contains(originalFilename)) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "文件命名不符合提交规范";
			savedPath = "/errorfilelist/";
			record.setStatus(-1);
			record.setStatusPhase("文件命名不符合提交规范");
		} else {
			record.setStatus(0);
			record.setStatusPhase("提交成功");
			jsonResult.logicCode = "0";
			jsonResult.resultMsg = "提交成功.";
			if (DateUtil.dateGraterThan(new Date(), bsTaskStu.getTask().getEndTime())) {
				record.setStatus(1);
				record.setStatusPhase("提交成功,逾期");
				jsonResult.logicCode = "1";
				jsonResult.resultMsg = "提交成功，逾期.";
			}
		}

		// 上传文件
		String newImg = "";
		if (originalFilename == null || originalFilename.length() == 0) {
			newImg = null;
		} else {
			try {
				newImg = uploadImg(img, ConfigUtil.FILEPATH + savedPath);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
				return gson.toJson(jsonResult);
			}
		}

		// 在数据库中添加记录
		if (newImg != null) {
			record.setTask(bsTaskStu);
			record.setStuTaskId(Integer.valueOf(taskId));
			record.setCommitTime(new Date());
			record.setCommitFileName(originalFilename);
			record.setCommitFileNewName(savedPath + newImg);
		}

		try {
			commitRecordService.addNewCommitRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/fileUpload", method = { RequestMethod.GET, RequestMethod.POST })
	public String fileUpload(HttpServletRequest request, HttpSession session, String taskId, MultipartFile img) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (taskId == null || img == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id或者文件，非法操作!";
			return gson.toJson(jsonResult);
		}
		Student student = (Student) session.getAttribute("student");
		if (student == null) {
			jsonResult.logicCode = "-2";
			jsonResult.resultMsg = "登录超时，请重新登录!";
			jsonResult.url = request.getContextPath() + "/main/login.action";
			return gson.toJson(jsonResult);
		}

		// 根据用户id和任务id获取对应的文件类型
		BsTaskStu bsTaskStu = null;
		BsTaskCommitRecord record = new BsTaskCommitRecord();
		BsTaskStuQueryVo bsTaskStuQueryVo = new BsTaskStuQueryVo();

		try {
			List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
			whereCondList.add(new QueryConditionItem("task.id", taskId + "", QueryConditionOp.EQ));
			whereCondList.add(new QueryConditionItem("task.delFlag", "0", QueryConditionOp.EQ));
			bsTaskStuQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			bsTaskStu = studentTaskService.findBsTaskStu(bsTaskStuQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-2";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		// 判断任务提交类型和上传文件格式
		if (bsTaskStu == null || bsTaskStu.getTask() == null || bsTaskStu.getTask().getType() != 0) {
			jsonResult.logicCode = "-3";
			jsonResult.resultMsg = "无法找到对应的文件上传任务";
			return gson.toJson(jsonResult);
		}
		BsCommitFormat bsCommitFormat = bsTaskStu.getTask().getBsCommitFormat();
		if (bsCommitFormat == null || StringUtil.isEmpty(bsCommitFormat.getFormat())) {
			jsonResult.logicCode = "-4";
			jsonResult.resultMsg = "无法找到对应上传格式,请联系系统管理员以查看问题.";
			return gson.toJson(jsonResult);
		}

		// 判断文件名是否有效
		String savedPath = "/validfilelist";
		List<String> validFileList = null;
		try {
			validFileList = getFormatInstanceList(bsCommitFormat.getFormat(), student.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonResult.logicCode = "-4";
			jsonResult.resultMsg = e1.getMessage();
			return gson.toJson(jsonResult);
		}

		String originalFilename = img.getOriginalFilename();
		if (!validFileList.contains(originalFilename)) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "文件格式不正确.";
			savedPath = "/errorfilelist";
			record.setStatus(-1);
			record.setStatusPhase("文件格式不正确");
		} else {
			record.setStatus(0);
			record.setStatusPhase("提交成功");
		}

		// 上传文件
		String newImg = "";
		if (originalFilename == null || originalFilename.length() == 0) {
			newImg = null;
		} else {
			try {
				newImg = uploadImg(img, ConfigUtil.FILEPATH + savedPath);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
				return gson.toJson(jsonResult);
			}
		}

		// 在数据库中添加记录
		if (newImg != null) {
			record.setTask(bsTaskStu);
			record.setStuTaskId(Integer.valueOf(taskId));
			record.setCommitTime(new Date());
			record.setCommitFileName(originalFilename);
			record.setCommitFileNewName(savedPath + newImg);
		}

		try {
			commitRecordService.addNewCommitRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

}