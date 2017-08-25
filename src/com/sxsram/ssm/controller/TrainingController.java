package com.sxsram.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.entity.TrainingClass;
import com.sxsram.ssm.entity.TrainingClassQueryVo;
import com.sxsram.ssm.entity.TrainingDir;
import com.sxsram.ssm.entity.TrainingDirQueryVo;
import com.sxsram.ssm.service.TrainingClassService;
import com.sxsram.ssm.service.TrainingDirService;
import com.sxsram.ssm.util.DateUtil;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.NullStringToEmptyAdapterFactory;
import com.sxsram.ssm.util.Pagination;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

@Controller()
@RequestMapping(value = "/training", method = { RequestMethod.GET, RequestMethod.POST })
public class TrainingController {
	@Autowired
	private TrainingDirService trainingDirService;
	@Autowired
	private TrainingClassService trainingClassService;

	@RequestMapping(value = "/dirManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String dirManagement() {
		return "training/dirManagement";
	}

	@RequestMapping(value = "/classManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String classManagement() {
		return "training/classManagement";
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

	@RequestMapping(value = "/getDirListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getDirListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			Integer typeSeqOrderBy) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		List<TrainingDir> trainingDirs = null;
		Integer totalNum = 0;

		TrainingDirQueryVo trainingDirQueryVo = new TrainingDirQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(searchKey)) {
			whereCondList.add(new QueryConditionItem("name", searchKey, QueryConditionOp.LIKE));
		}
		whereCondList.add(new QueryConditionItem("delFlag", 0 + "", QueryConditionOp.LIKE));
		try {
			trainingDirQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			trainingDirs = trainingDirService.findTrainingDirs(trainingDirQueryVo);
			totalNum = trainingDirs.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		if (typeSeqOrderBy != null) {
			if (typeSeqOrderBy == 0) {
				orderByMap.put("sequence", "desc");
			} else {
				orderByMap.put("sequence", "asc");
			}
		}
		try {
			trainingDirQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			trainingDirs = trainingDirService.findTrainingDirs(trainingDirQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, trainingDirs);
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/updateDirAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateTypeAjax(HttpSession session, Model model, String id, String name, String sequence) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		TrainingDir trainingDir = new TrainingDir();
		trainingDir.setId(Integer.valueOf(id));
		if (!StringUtils.isEmpty(name))
			trainingDir.setName(name);
		if (!StringUtil.isEmpty(sequence))
			trainingDir.setSequence(Integer.valueOf(sequence));

		try {
			trainingDirService.updateTrainingDir(trainingDir);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteDirAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteTypeAjax(HttpSession session, Model model, String id, Integer reqType) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null || reqType == null || reqType < 0 || reqType > 2) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到reqtype，非法操作!";
			return gson.toJson(jsonResult);
		}

		TrainingDir trainingDir = new TrainingDir();
		trainingDir.setId(Integer.valueOf(id));

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
				trainingDirService.deleteTraininggDirCascadeTrainingClasses(trainingDir);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		case 2: // 级联删除
			try {
				trainingDirService.delTrainingDir(trainingDir);
			} catch (Exception e) {
				e.printStackTrace();
				jsonResult.logicCode = "-1";
				jsonResult.resultMsg = e.getMessage();
			}
			break;
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addDirAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addTypeAjax(HttpSession session, Model model, Integer sequence, String name) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		TrainingDir trainingDir = new TrainingDir();
		if (!StringUtils.isEmpty(name))
			trainingDir.setName(name);
		if (!StringUtil.isEmpty(sequence))
			trainingDir.setSequence(sequence);

		try {
			trainingDirService.addNewTrainingDir(trainingDir);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	// ============================================ 商品
	// =======================================================
	//
	@RequestMapping(value = "/getClassListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getQAEntityListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize,
			String searchKey, String searchStartDate, String searchEndDate, String statusSelect, String typeSelect,
			Integer endTimeOrderBy, Integer timeOrderBy) {
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
				.setDateFormat("yyyy-MM-dd HH:mm").create();

		JsonResult jsonResult = new JsonResult("0", "0");

		List<TrainingClass> trainingClasses = null;
		Integer totalNum = 0;

		TrainingClassQueryVo trainingClassQueryVo = new TrainingClassQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("c.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(searchKey)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("c.name", searchKey, QueryConditionOp.LIKE));
			whereCondList.add(queryConditionOrItems);
		}

		if (!StringUtil.isEmpty(searchStartDate)) {
			String arr[] = searchStartDate.split("/");
			// Date startDate = new Date(Integer.valueOf(arr[2]),
			// Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			String startDate = arr[2] + "-" + arr[0] + "-" + arr[1];
			whereCondList.add(new QueryConditionItem("c.beginTime", startDate.toString(), QueryConditionOp.GE));
		}

		if (!StringUtil.isEmpty(searchEndDate)) {
			String arr[] = searchEndDate.split("/");
			// Date endDate = new Date(Integer.valueOf(arr[2]),
			// Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			String endDate = arr[2] + "-" + arr[0] + "-" + arr[1];
			whereCondList.add(new QueryConditionItem("c.beginTime", endDate.toString(), QueryConditionOp.LE));
		}

		if (!StringUtil.isEmpty(statusSelect) && !statusSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("status", statusSelect, QueryConditionOp.EQ));
		}

		if (!StringUtil.isEmpty(typeSelect) && !typeSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("c.trainingId", typeSelect, QueryConditionOp.EQ));
		}

		// if (!StringUtil.isEmpty(flagSelect) && !flagSelect.equals("-2")) {
		// whereCondList.add(new QueryConditionItem("flagId", flagSelect,
		// QueryConditionOp.EQ));
		// }

		try {
			trainingClassQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = trainingClassService.findTrainingClasss(trainingClassQueryVo).size();
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		// if (orderAmountOrderBy != null) {
		// if (orderAmountOrderBy == 0) {
		// orderByMap.put("totalAmount", "desc");
		// } else {
		// orderByMap.put("totalAmount", "asc");
		// }
		// }
		//
		if (timeOrderBy != null) {
			if (timeOrderBy == 0) {
				orderByMap.put("c.beginTime", "desc");
			} else {
				orderByMap.put("c.beginTime", "asc");
			}
		}
		if (endTimeOrderBy != null) {
			if (timeOrderBy == 0) {
				orderByMap.put("c.endTime", "desc");
			} else {
				orderByMap.put("c.endTime", "asc");
			}
		}
		try {
			trainingClassQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			trainingClasses = trainingClassService.findTrainingClasss(trainingClassQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, trainingClasses);
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/updateClassAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateQAEntityAjax(HttpSession session, Model model, String id, String name, String dirId,
			String status, String extra, String beginTime, String endTime, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		TrainingClass trainingClass = new TrainingClass();
		trainingClass.setId(Integer.valueOf(id));
		if (!StringUtils.isEmpty(name))
			trainingClass.setName(name);
		if (!StringUtil.isEmpty(dirId))
			trainingClass.setTrainingId(Integer.valueOf(dirId));
		if (!StringUtil.isEmpty(beginTime))
			trainingClass.setBeginTime(DateUtil.strToDate(beginTime, "yyyy-MM-dd"));
		if (!StringUtil.isEmpty(endTime))
			trainingClass.setEndTime(DateUtil.strToDate(endTime, "yyyy-MM-dd"));
		if (!StringUtil.isEmpty(status))
			trainingClass.setStatus(Integer.valueOf(status));
		if (!StringUtil.isEmpty(extra))
			trainingClass.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			trainingClass.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			trainingClassService.updateTrainingClass(trainingClass);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteClassAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteQAEntityAjax(HttpSession session, Model model, String id) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		TrainingClass trainingClass = new TrainingClass();
		trainingClass.setId(Integer.valueOf(id));
		trainingClass.setDelFlag(1);
		try {
			trainingClassService.updateTrainingClass(trainingClass);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addClassAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addCommodityAjax(HttpSession session, Model model, String name, String dirId, String status,
			String extra, String beginTime, String endTime, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		TrainingClass trainingClass = new TrainingClass();
		if (!StringUtils.isEmpty(name))
			trainingClass.setName(name);
		if (!StringUtil.isEmpty(dirId))
			trainingClass.setTrainingId(Integer.valueOf(dirId));
		if (!StringUtil.isEmpty(beginTime))
			trainingClass.setBeginTime(DateUtil.strToDate(beginTime, "yyyy-MM-dd"));
		if (!StringUtil.isEmpty(endTime))
			trainingClass.setEndTime(DateUtil.strToDate(endTime, "yyyy-MM-dd"));
		if (!StringUtil.isEmpty(status))
			trainingClass.setStatus(Integer.valueOf(status));
		if (!StringUtil.isEmpty(extra))
			trainingClass.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			trainingClass.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			trainingClassService.addNewTrainingClass(trainingClass);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}
	//
	// @RequestMapping(value = "/loadQAEntityUrlContent", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	// public String loadQAEntityUrlContent(String url) {
	// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
	// JsonResult jsonResult = new JsonResult("0", "0");
	//
	// try {
	// String content = "";
	// if (!StringUtil.isEmpty(url.trim()))
	// content = FileUtil.readFileContent(ConfigUtil.QALINKCONTENTPATH + url);
	// jsonResult.resultMsg = content;
	// } catch (Exception e) {
	// jsonResult.logicCode = "-1";
	// jsonResult.resultMsg = e.getMessage();
	// }
	// return gson.toJson(jsonResult);
	// }
	//
	// @RequestMapping(value = "/updateQAEntityDetailAjax", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	// public String updateCommodityDetailAjax(String id, String editor1) {
	// Gson gson = new Gson();
	// JsonResult jsonResult = new JsonResult("0", "0");
	// try {
	// QAEntity qaEntity = new QAEntity();
	// if (StringUtil.isEmpty(id)) {
	// jsonResult.logicCode = "-1";
	// jsonResult.resultMsg = "Can't find id";
	// return gson.toJson(jsonResult);
	// }
	// String filename = UUID.randomUUID().toString().replaceAll("-", "") +
	// ".html";
	// String newFile = ConfigUtil.QALINKCONTENTPATH + filename;
	// FileUtil.writeContentToFile(editor1.trim(), newFile);
	// qaEntity.setId(id);
	// qaEntity.setUrl(filename);
	// qaService.updateQAEntity(qaEntity);
	// } catch (Exception e) {
	// // e.printStackTrace();
	// jsonResult.logicCode = "-1";
	// jsonResult.resultMsg = e.getMessage();
	// }
	// return gson.toJson(jsonResult);
	// }
}