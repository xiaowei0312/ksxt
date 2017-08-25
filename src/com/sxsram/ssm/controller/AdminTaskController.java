package com.sxsram.ssm.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.entity.BsCommitFormat;
import com.sxsram.ssm.entity.BsTaskAdmin;
import com.sxsram.ssm.entity.BsTaskAdminQueryVo;
import com.sxsram.ssm.entity.BsTaskCommitRecord;
import com.sxsram.ssm.entity.BsTaskCommitRecordQueryVo;
import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.entity.StudentQueryVo;
import com.sxsram.ssm.entity.User;
import com.sxsram.ssm.service.AdminTaskService;
import com.sxsram.ssm.service.BsCommitFormatService;
import com.sxsram.ssm.service.CommitRecordService;
import com.sxsram.ssm.service.StudentService;
import com.sxsram.ssm.util.ConfigUtil;
import com.sxsram.ssm.util.DateUtil;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.Pagination;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

@Controller()
@RequestMapping(value = "/task", method = { RequestMethod.GET, RequestMethod.POST })
public class AdminTaskController {
	@Autowired
	private AdminTaskService adminTaskService;
	@Autowired
	private BsCommitFormatService bsCommitFormatService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CommitRecordService commitRecordService;

	@RequestMapping(value = "/taskManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String dirManagement() {
		return "task/taskManagement";
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

	@RequestMapping(value = "/getTaskListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getDirListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			String searchStartDate, String searchEndDate, String colSelect, String depSelect, String majorSelect,
			String gradeSelect, String statusSelect, String typeSelect, Integer addTimeOrderBy) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		List<BsTaskAdmin> adminTaskList = null;
		Integer totalNum = 0;

		BsTaskAdminQueryVo bsTaskAdminQueryVo = new BsTaskAdminQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(searchKey)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("adminTask.extra", searchKey, QueryConditionOp.LIKE));
			queryConditionOrItems.getItems()
					.add(new QueryConditionItem("adminTask.id", searchKey, QueryConditionOp.EQ));
			whereCondList.add(queryConditionOrItems);
		}

		if (!StringUtil.isEmpty(searchStartDate)) {
			String arr[] = searchStartDate.split("/");
			// Date startDate = new Date(Integer.valueOf(arr[2]),
			// Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			String startDate = arr[2] + "-" + arr[0] + "-" + arr[1];
			whereCondList.add(new QueryConditionItem("adminTask.beginTime", startDate.toString(), QueryConditionOp.GE));
		}

		if (!StringUtil.isEmpty(searchEndDate)) {
			String arr[] = searchEndDate.split("/");
			// Date endDate = new Date(Integer.valueOf(arr[2]),
			// Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			String endDate = arr[2] + "-" + arr[0] + "-" + arr[1];
			whereCondList.add(new QueryConditionItem("adminTask.beginTime", endDate.toString(), QueryConditionOp.LE));
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
			whereCondList.add(new QueryConditionItem("adminTask.gradeNo", gradeSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(statusSelect) && !statusSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("adminTask.status", statusSelect, QueryConditionOp.EQ));
		}
		if (!StringUtil.isEmpty(typeSelect) && !typeSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("adminTask.type", typeSelect, QueryConditionOp.EQ));
		}
		whereCondList.add(new QueryConditionItem("adminTask.delFlag", 0 + "", QueryConditionOp.LIKE));

		try {
			bsTaskAdminQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = adminTaskService.findBsTaskAdmins(bsTaskAdminQueryVo).size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		if (addTimeOrderBy != null) {
			if (addTimeOrderBy == 0) {
				orderByMap.put("adminTask.addTime", "desc");
			} else {
				orderByMap.put("adminTask.addTime", "asc");
			}
		}
		try {
			bsTaskAdminQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			adminTaskList = adminTaskService.findBsTaskAdmins(bsTaskAdminQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, adminTaskList);
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/updateTaskAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateTaskAjax(HttpSession session, Model model, BsTaskAdmin bsTaskAdmin, String beginTimeStr,
			String endTimeStr) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (bsTaskAdmin.getId() == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到任务id，请不要非法访问";
			return gson.toJson(jsonResult);
		}

		// 任务对象不能修改
		bsTaskAdmin.setMajor(null);
		bsTaskAdmin.setMajorId(null);
		bsTaskAdmin.setGradeNo(null);

		if (!StringUtils.isEmpty(beginTimeStr)) {
			bsTaskAdmin.setBeginTime(DateUtil.strToDate(beginTimeStr, "yyyy-MM-dd HH:mm"));
		}

		if (!StringUtils.isEmpty(endTimeStr)) {
			bsTaskAdmin.setEndTime(DateUtil.strToDate(endTimeStr, "yyyy-MM-dd HH:mm"));
		}
		if (!StringUtils.isEmpty(bsTaskAdmin.getStatus())) {
			if (bsTaskAdmin.getStatus() == 0) {
				bsTaskAdmin.setStatusPhase("进行中");
			} else if (bsTaskAdmin.getStatus() == -1) {
				bsTaskAdmin.setStatusPhase("已失效");
			} else if (bsTaskAdmin.getStatus() == -3) {
				bsTaskAdmin.setStatusPhase("未激活");
			}
		}

		if (bsTaskAdmin.getType() == 0) {
			bsTaskAdmin.setBsDbType(null);
			bsTaskAdmin.setBsDbTypeId(null);
		}
		if (bsTaskAdmin.getType() == 1) {
			bsTaskAdmin.setBsCommitFormat(null);
			bsTaskAdmin.setBsFormatId(null);
		}

		bsTaskAdmin.setUser((User) session.getAttribute("user"));

		// List<Student> students = null;
		// StudentQueryVo studentQueryVo = new StudentQueryVo();
		// List<QueryConditionAbstractItem> whereCondList = new
		// ArrayList<QueryConditionAbstractItem>();
		// whereCondList.add(new QueryConditionItem("stu.delFlag", 0 + "",
		// QueryConditionOp.LIKE));
		// whereCondList.add(new QueryConditionItem("stu.majorId",
		// bsTaskAdmin.getMajorId() + "", QueryConditionOp.LIKE));
		// whereCondList.add(new QueryConditionItem("stu.gradeNo",
		// bsTaskAdmin.getGradeNo() + "", QueryConditionOp.LIKE));

		try {
			// studentQueryVo.setQueryCondition(new
			// QueryCondition(whereCondList));
			// students = studentService.findStudents(studentQueryVo);
			adminTaskService.updateBsTaskAdmin(bsTaskAdmin);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteTaskAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteTaskAjax(HttpSession session, Model model, String id, Integer reqType) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null || reqType == null || reqType < 0 || reqType > 2) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到reqtype，非法操作!";
			return gson.toJson(jsonResult);
		}

		BsTaskAdmin bsTaskAdmin = new BsTaskAdmin();
		bsTaskAdmin.setId(Integer.valueOf(id));

		Integer totalNum = 0;
		switch (reqType) {
		case 0: // 获取类别下的商品数量
			// TrainingClassQueryVo trainingClassQueryVo = new
			// TrainingClassQueryVo();
			// List<QueryConditionAbstractItem> items = new
			// ArrayList<QueryConditionAbstractItem>();
			// items.add(new QueryConditionItem("trainingId", id + "",
			// QueryConditionOp.EQ));
			// items.add(new QueryConditionItem("c.delFlag", 0 + "",
			// QueryConditionOp.EQ));
			// try {
			// trainingClassQueryVo.setQueryCondition(new
			// QueryCondition(items));
			// totalNum =
			// trainingClassService.findTrainingClasss(trainingClassQueryVo).size();
			// jsonResult.resultObj = totalNum;
			// } catch (Exception e) {
			// e.printStackTrace();
			// jsonResult.logicCode = "-1";
			// jsonResult.resultMsg = e.getMessage();
			// }
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
				adminTaskService.delBsTaskAdmin(bsTaskAdmin);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addTaskAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addTypeAjax(HttpSession session, Model model, BsTaskAdmin bsTaskAdmin, String beginTimeStr,
			String endTimeStr) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (!StringUtils.isEmpty(beginTimeStr)) {
			bsTaskAdmin.setBeginTime(DateUtil.strToDate(beginTimeStr, "yyyy-MM-dd HH:mm"));
		}

		if (!StringUtils.isEmpty(endTimeStr)) {
			bsTaskAdmin.setEndTime(DateUtil.strToDate(endTimeStr, "yyyy-MM-dd HH:mm"));
		}
		if (!StringUtils.isEmpty(bsTaskAdmin.getStatus())) {
			if (bsTaskAdmin.getStatus() == 0) {
				bsTaskAdmin.setStatusPhase("进行中");
			} else if (bsTaskAdmin.getStatus() == -1) {
				bsTaskAdmin.setStatusPhase("已失效");
			} else if (bsTaskAdmin.getStatus() == -3) {
				bsTaskAdmin.setStatusPhase("未激活");
			}
		}

		if (bsTaskAdmin.getType() == 0) {
			bsTaskAdmin.setBsDbType(null);
			bsTaskAdmin.setBsDbTypeId(null);
		}
		if (bsTaskAdmin.getType() == 1) {
			bsTaskAdmin.setBsCommitFormat(null);
			bsTaskAdmin.setBsFormatId(null);
		}

		bsTaskAdmin.setUser((User) session.getAttribute("user"));

		List<Student> students = null;
		StudentQueryVo studentQueryVo = new StudentQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("stu.delFlag", 0 + "", QueryConditionOp.LIKE));
		whereCondList.add(new QueryConditionItem("stu.majorId", bsTaskAdmin.getMajorId() + "", QueryConditionOp.LIKE));
		whereCondList.add(new QueryConditionItem("stu.gradeNo", bsTaskAdmin.getGradeNo() + "", QueryConditionOp.LIKE));

		try {
			studentQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			students = studentService.findStudents(studentQueryVo);
			adminTaskService.addNewBsTaskAdmin(bsTaskAdmin, students);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/downloadCommitFile", method = { RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	public void downloadCommitFile(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model, String stuTaskId) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		// 1.根据任务Id，找到对应的文件
		BsTaskCommitRecord bsTaskCommitRecord = null;
		BsTaskCommitRecordQueryVo bsTaskCommitRecordQueryVo = new BsTaskCommitRecordQueryVo();

		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("stuTaskId", stuTaskId, QueryConditionOp.EQ));
		whereCondList.add(new QueryConditionItem("status", 0 + "", QueryConditionOp.EQ));

		try {
			bsTaskCommitRecordQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			bsTaskCommitRecordQueryVo.setPagination(new Pagination(1, 1, null, "{\"commitTime\":\"desc\"}"));
			bsTaskCommitRecord = commitRecordService.findCommitRecord(bsTaskCommitRecordQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			//return gson.toJson(jsonResult);
		}

		// 2.判断文件是否存在
		if (bsTaskCommitRecord == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到文件";
			//return gson.toJson(jsonResult);
		}

		// 3.下载文件
		// 3.1 将文件拷贝到临时下载目录
		try {
			FileUtils.copyFile(new File(ConfigUtil.FILEPATH_SUCCESS + bsTaskCommitRecord.getCommitFileNewName()),
					new File(ConfigUtil.FILEPATH_TMP + bsTaskCommitRecord.getCommitFileName()));
		} catch (IOException e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			//return gson.toJson(jsonResult);
		}

		// 3.2 下载
		try {
			downloadFile(bsTaskCommitRecord.getCommitFileName(), ConfigUtil.FILEPATH_TMP, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			//return gson.toJson(jsonResult);
		}
		//return gson.toJson(jsonResult);
	}

	/**
	 * 文件下载
	 * 
	 * @Description:
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadFile", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadFile(String fileName, String filePath, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println(filePath + " " + fileName);
		if (fileName != null) {
			File file = new File(filePath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}