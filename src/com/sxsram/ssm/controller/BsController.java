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
import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.BsTitleQueryVo;
import com.sxsram.ssm.entity.TrainingClassQueryVo;
import com.sxsram.ssm.entity.TrainingDir;
import com.sxsram.ssm.service.BsTitleService;
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
@RequestMapping(value = "/bs", method = { RequestMethod.GET, RequestMethod.POST })
public class BsController {
	@Autowired
	private BsTitleService bsTitleService;

	@RequestMapping(value = "/titleManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String dirManagement() {
		return "bs/titleManagement";
	}

	@RequestMapping(value = "/taskBookManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String classManagement() {
		return "bs/taskBookManagement";
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

	/*@RequestMapping(value = "/getXxListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getDirListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			Integer typeSeqOrderBy) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		List<BsTitle> bsTitles = null;
		Integer totalNum = 0;

		BsTitleQueryVo bsTitleQueryVo = new BsTitleQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		if (!StringUtils.isEmpty(searchKey)) {
			whereCondList.add(new QueryConditionItem("name", searchKey, QueryConditionOp.LIKE));
		}
		whereCondList.add(new QueryConditionItem("delFlag", 0 + "", QueryConditionOp.LIKE));
		try {
			bsTitleQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = bsTitleService.findBsTitles(bsTitleQueryVo).size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		if (typeSeqOrderBy != null) {
			if (typeSeqOrderBy == 0) {
				orderByMap.put("addTime", "desc");
			} else {
				orderByMap.put("addTime", "asc");
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
			;
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
	}*/

	// ============================================ 商品
	// =======================================================
	//
	@RequestMapping(value = "/getTitleListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getTitleListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			String searchStartDate, String searchEndDate, String statusSelect, String typeSelect,
			Integer endTimeOrderBy, Integer timeOrderBy) {
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
				.setDateFormat("yyyy-MM-dd HH:mm").create();

		JsonResult jsonResult = new JsonResult("0", "0");

		List<BsTitle> bsTitles = null;
		Integer totalNum = 0;

		BsTitleQueryVo bsTitleQueryVo = new BsTitleQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("t.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(searchKey)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("t.name", searchKey, QueryConditionOp.LIKE));
			whereCondList.add(queryConditionOrItems);
		}

		if (!StringUtil.isEmpty(statusSelect) && !statusSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("status", statusSelect, QueryConditionOp.EQ));
		}

		if (!StringUtil.isEmpty(typeSelect) && !typeSelect.equals("-2")) {
			whereCondList.add(new QueryConditionItem("t.bsDirId", typeSelect, QueryConditionOp.EQ));
		}

		// if (!StringUtil.isEmpty(flagSelect) && !flagSelect.equals("-2")) {
		// whereCondList.add(new QueryConditionItem("flagId", flagSelect,
		// QueryConditionOp.EQ));
		// }

		try {
			bsTitleQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = bsTitleService.findBsTitles(bsTitleQueryVo).size();
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
				orderByMap.put("t.lmtTime", "desc");
			} else {
				orderByMap.put("t.lmtTime", "asc");
			}
		}
		try {
			bsTitleQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			bsTitles = bsTitleService.findBsTitles(bsTitleQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, bsTitles);
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/updateTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateQAEntityAjax(HttpSession session, Model model, String id, String name, String bsDirId,
			String taskBookId, String extra, String beginTime, String endTime, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		BsTitle bsTitle = new BsTitle();
		bsTitle.setId(Integer.valueOf(id));
		if (!StringUtils.isEmpty(name))
			bsTitle.setName(name);
		if (!StringUtil.isEmpty(bsDirId))
			bsTitle.setBsDirId(Integer.valueOf(bsDirId));
		if (!StringUtil.isEmpty(taskBookId))
			bsTitle.setTaskBookId(Integer.valueOf(taskBookId));
		if (!StringUtil.isEmpty(extra))
			bsTitle.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			bsTitle.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			bsTitleService.updateBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteQAEntityAjax(HttpSession session, Model model, String id) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		BsTitle bsTitle = new BsTitle();
		bsTitle.setId(Integer.valueOf(id));
		bsTitle.setDelFlag(1);
		try {
			bsTitleService.updateBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addCommodityAjax(HttpSession session, Model model, String name, String bsDirId, String taskBookId,
			String extra, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		BsTitle bsTitle = new BsTitle();
		if (!StringUtils.isEmpty(name))
			bsTitle.setName(name);
		if (!StringUtil.isEmpty(bsDirId))
			bsTitle.setBsDirId(Integer.valueOf(bsDirId));
		if (!StringUtil.isEmpty(extra))
			bsTitle.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			bsTitle.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			bsTitleService.addNewBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}
}